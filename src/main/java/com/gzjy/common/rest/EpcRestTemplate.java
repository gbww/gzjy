package com.gzjy.common.rest;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Maps;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.exception.SysException;
import com.gzjy.common.util.ObjectMapperFactory;

/**
 * 线程不安全，另外只能在发起请求线程中使用，不能用定时器，或者自定义线程使用
 * Created by szt on 2016/6/29.
 */
public class EpcRestTemplate {
  private static final Logger logger = LoggerFactory.getLogger(EpcRestTemplate.class);
  CloseableHttpClient httpclient;
  HttpUriRequest clientReq;
  ObjectMapper mapper = ObjectMapperFactory.getContext(null);
  TypeFactory typeFactory=null;

  public TypeFactory getTypeFactory() {
    return mapper.getTypeFactory();
  }

  public <T> T exchangeBody(String url, HttpMethod method, Class<T> responseType, Object requestBody, Header header) {
    JavaType javaType =  getJavaType(responseType);
    return (T) execute(header, url, method, javaType, requestBody, null);
  }
  public <T> T exchangeBody(String url, HttpMethod method, Class<T> responseType, Object requestBody) {
    JavaType javaType =  getJavaType(responseType);
    return (T) execute(null, url, method, javaType, requestBody, null);
  }

  public <T> T exchangeBody(String url, HttpMethod method, Type type, Object requestBody, Header header) {

    JavaType javaType = mapper.getTypeFactory().constructType(type);
    return (T) execute(header, url, method, javaType, requestBody, null);
  }
  public <T> T exchangeBody(String url, HttpMethod method, Type type, Object requestBody) {

    JavaType javaType = mapper.getTypeFactory().constructType(type);
    return (T) execute(null, url, method, javaType, requestBody, null);
  }

  public <T> T exchangeParams(String url, HttpMethod method, Class<T> responseType, Map<String, Object> requestParams, Header header) {
    JavaType javaType = getJavaType(responseType);

    return (T) execute(header, url, method, javaType, null, requestParams);
  }
  public <T> T exchangeParams(String url, HttpMethod method, Class<T> responseType, Map<String, Object> requestParams) {
    JavaType javaType = getJavaType(responseType);

    return (T) execute(null, url, method, javaType, null, requestParams);
  }

  private <T> JavaType getJavaType(Class<T> responseType) {
    if(responseType.isPrimitive()||responseType==String.class) {
      return null;
    }
    return mapper.getTypeFactory().constructType(responseType);
  }

  public <T> T exchangeParams(String url, HttpMethod method, Type type, Map<String, Object> requestParams, Header header) {

    JavaType javaType = mapper.getTypeFactory().constructType(type);

    return (T) execute(header, url, method, javaType, null, requestParams);
  }
  public <T> T exchangeParams(String url, HttpMethod method, Type type, Map<String, Object> requestParams) {

    JavaType javaType = mapper.getTypeFactory().constructType(type);

    return (T) execute(null, url, method, javaType, null, requestParams);
  }

  public <T> T exchange(String url, HttpMethod method, Type type, Header header) {
    JavaType javaType = mapper.getTypeFactory().constructType(type);

    return (T) execute(header, url, method, javaType, null, null);
  }

  public <T> T exchange(String url, HttpMethod method, Class<T> responseType, Header header) {
    JavaType javaType =  getJavaType(responseType);

    return (T) execute(header, url, method, javaType, null, null);
  }
  public <T> T exchange(String url, HttpMethod method, Type type) {
    JavaType javaType = mapper.getTypeFactory().constructType(type);

    return (T) execute(null, url, method, javaType, null, null);
  }

  public <T> T exchange(String url, HttpMethod method, Class<T> responseType) {
    JavaType javaType =  getJavaType(responseType);

    return (T) execute(null, url, method, javaType, null, null);
  }

  private Object execute(Header header, String url, HttpMethod method, JavaType javaType, Object requestBody, Map<String, Object> requestParams) {
    Object value = null;
    try {
      initialize(url, method, requestParams);

      CloseableHttpResponse httpResponse = execute(header, requestBody);
      try {
        value = getResponse(javaType, value, httpResponse);
      } finally {
        httpResponse.close();
      }
    } catch (Exception e) {
      throw new SysException(e);
    } finally {

      try {
        httpclient.close();
      } catch (IOException e) {
        throw new SysException(e);
      }

    }
    return value;
  }

  private Object getResponse(JavaType javaType, Object value, CloseableHttpResponse httpResponse) throws IOException {
    if (httpResponse.getStatusLine().getStatusCode()==200) {
      HttpEntity entity = httpResponse.getEntity();

      if (entity != null) {
        InputStream stream = entity.getContent();
        try {
          if (javaType==null) {
            String s = EntityUtils.toString(entity);
            value=s;
          }else
          {
            value = mapper.readValue(stream, javaType);
          }
        } finally {
          if (stream != null) {
            stream.close();
          }
        }

      }
    } else {
      HttpEntity entity = httpResponse.getEntity();
      String s = EntityUtils.toString(entity);
      logger.info("模块间调用解析出错：{}",s);
      throw new BizException(s);
    }
    return value;
  }



  private CloseableHttpResponse execute(Header header, Object requestBody) throws Exception {

    EntityBuilder builder = null;
    if (requestBody != null) {
      builder =
          EntityBuilder.create().setContentType(ContentType.APPLICATION_JSON).setContentEncoding(CharEncoding.UTF_8).setText(ObjectMapperFactory.getContext(requestBody.getClass()).writer().writeValueAsString(requestBody));
    }
    if (builder != null && this.clientReq instanceof HttpEntityEnclosingRequestBase) {
      ((HttpEntityEnclosingRequestBase) this.clientReq).setEntity(builder.build());
    }
    if (header != null) {
      Map<String, String> mapHeader = header.getHeader();
      for (Map.Entry<String, String> entry : mapHeader.entrySet()) {
        this.clientReq.setHeader(entry.getKey(), entry.getValue());
      }
    }

    return this.httpclient.execute(this.clientReq);
  }

  private void initialize(String urlStr, HttpMethod method, Map<String, Object> requestParams) throws URISyntaxException {
    httpclient = HttpClients.createDefault();
    URI uri;

    URIBuilder uriBuilder = new URIBuilder(urlStr);
    if (requestParams != null) {
      for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
        if(entry.getValue() != null){
          uriBuilder.addParameter(entry.getKey(), entry.getValue().toString());
        }
      }
    }
    uri = uriBuilder.build();


    switch (method) {
      case POST:
        this.clientReq = new HttpPost(uri);
        break;
      case GET:
        this.clientReq = new HttpGet(uri);
        break;
      case PUT:
        this.clientReq = new HttpPut(uri);
        break;
      case DELETE:
        this.clientReq = new HttpDelete(uri);
        break;
      case HEAD:
        this.clientReq = new HttpHead(uri);
        break;
      case PATCH:
        this.clientReq = new HttpPatch(uri);
        break;
      default:
        throw new IllegalArgumentException("Unsupported http method: " + method);
    }

  }

  public static class Header {
    private Map<String, String> map = Maps.newHashMap();

    public Header addValue(String key, String value) {
      map.put(key, value);
      return this;
    }

    Map<String, String> getHeader() {
      return this.map;
    }
  }
}
