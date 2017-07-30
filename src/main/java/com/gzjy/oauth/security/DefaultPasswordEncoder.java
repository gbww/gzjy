package com.gzjy.oauth.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultPasswordEncoder implements PasswordEncoder {
  private Encoder passwordEncoder;

  public void setPasswordEncoder(Encoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encode(CharSequence charSequence) {
    if (passwordEncoder == null) {
      return charSequence.toString();
    } else {
      String encodePassword = passwordEncoder.encode(charSequence.toString());
      return encodePassword;
    }
  }

  @Override
  public boolean matches(CharSequence charSequence, String s) {
    return encode(charSequence.toString()).equals(s);
  }
}
