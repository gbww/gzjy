package com.gzjy.common.rest.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

public class AsyncSSLClientFactory extends SimpleClientHttpRequestFactory {

  @Override
  public void setTaskExecutor(AsyncListenableTaskExecutor taskExecutor) {
    super.setTaskExecutor(taskExecutor);
  }

  @Override
  protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
    try {
      if (!(connection instanceof HttpsURLConnection)) {
        throw new RuntimeException("An instance of HttpsURLConnection is expected");
      }

      HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;

      TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

        public void checkServerTrusted(X509Certificate[] certs, String authType) {}

      }};
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      httpsConnection
          .setSSLSocketFactory(new EpicCustomSSLSocketFactory(sslContext.getSocketFactory()));

      httpsConnection.setHostnameVerifier(new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });

      super.prepareConnection(httpsConnection, httpMethod);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * We need to invoke sslSocket.setEnabledProtocols(new String[] {"SSLv3"});
   */
  private static class EpicCustomSSLSocketFactory extends SSLSocketFactory {

    private final SSLSocketFactory delegate;

    public EpicCustomSSLSocketFactory(SSLSocketFactory delegate) {
      this.delegate = delegate;
    }

    @Override
    public String[] getDefaultCipherSuites() {
      return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
      return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(final Socket socket, final String host, final int port,
        final boolean autoClose) throws IOException {
      final Socket underlyingSocket = delegate.createSocket(socket, host, port, autoClose);
      return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final String host, final int port) throws IOException {
      final Socket underlyingSocket = delegate.createSocket(host, port);
      return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localAddress,
        final int localPort) throws IOException {
      final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
      return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port) throws IOException {
      final Socket underlyingSocket = delegate.createSocket(host, port);
      return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port,
        final InetAddress localAddress, final int localPort) throws IOException {
      final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
      return overrideProtocol(underlyingSocket);
    }

    private Socket overrideProtocol(final Socket socket) {
      if (!(socket instanceof SSLSocket)) {
        throw new RuntimeException("An instance of SSLSocket is expected");
      }
      ((SSLSocket) socket).setEnabledProtocols(new String[] {"TLSv1"});
      return socket;
    }
  }
}
