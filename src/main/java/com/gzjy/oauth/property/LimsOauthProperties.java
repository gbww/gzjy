package com.gzjy.oauth.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LimsOauthProperties.EPC_PREFIX, locations = "classpath:epc.properties")
public class LimsOauthProperties {
  public final static String EPC_PREFIX = "epc.oauth";

  public enum TokenStoreType {
    MEMORY, JDBC;
  }

  private String[] ignorePaths;
  private Integer filterOrder;

  private String encoder;
  private String decoder;
  
  private String kaptchaModel;
  private String kaptchaReceiver;
  //memory,jdbc
  private TokenStoreType tokenStoreType = TokenStoreType.MEMORY;

  public Integer getFilterOrder() {
    return filterOrder;
  }

  public void setFilterOrder(Integer filterOrder) {
    this.filterOrder = filterOrder;
  }

  public String[] getIgnorePaths() {
    return ignorePaths;
  }

  public void setIgnorePaths(String[] ignorePaths) {
    this.ignorePaths = ignorePaths;
  }

  public TokenStoreType getTokenStoreType() {
    return tokenStoreType;
  }

  public void setTokenStoreType(TokenStoreType tokenStoreType) {
    this.tokenStoreType = tokenStoreType;
  }

  public String getEncoder() {
    return encoder;
  }

  public void setEncoder(String encoder) {
    this.encoder = encoder;
  }

  public String getDecoder() {
    return decoder;
  }

  public void setDecoder(String decoder) {
    this.decoder = decoder;
  }

  public String getKaptchaModel() {
    return kaptchaModel;
  }

  public void setKaptchaModel(String kaptchaModel) {
    this.kaptchaModel = kaptchaModel;
  }

  public String getKaptchaReceiver() {
    return kaptchaReceiver;
  }
  
  public void setKaptchaReceiver(String kaptchaReceiver) {
    this.kaptchaReceiver = kaptchaReceiver;
  }
}
