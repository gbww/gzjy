package com.gzjy.oauth.security;

/**
 * Created by szt on 2017/1/11.
 */
public interface EpicLoginDecoder {
  String usernameDecode(String username);
  String passwordDecode(String password);
  }
