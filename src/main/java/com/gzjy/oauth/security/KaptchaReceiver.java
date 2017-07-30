package com.gzjy.oauth.security;

public interface KaptchaReceiver<T> {
  T getKaptcha(String id, String url);

  boolean checkKaptcha(String id, String kaptchaReceiveUrl, String kaptchaTxt);
}
