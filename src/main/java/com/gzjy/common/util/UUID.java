package com.gzjy.common.util;

/**
 * UUID Generator
 */
public class UUID {
  public static String random() {
    return java.util.UUID.randomUUID().toString().replaceAll("-", "");
  }
}
