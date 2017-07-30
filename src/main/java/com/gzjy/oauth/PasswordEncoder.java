package com.gzjy.oauth;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Created by szt on 2017/1/12.
 */
public class PasswordEncoder implements EpicPasswordEncoder {
  @Override
  public String encode(String password) {
    Md5PasswordEncoder md5=new Md5PasswordEncoder();
    return md5.encodePassword(password, null);
  }
}
