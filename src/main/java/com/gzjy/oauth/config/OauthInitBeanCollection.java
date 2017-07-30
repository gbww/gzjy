package com.gzjy.oauth.config;

import com.google.common.collect.Maps;

import java.util.Map;

public class OauthInitBeanCollection {
  private static final Map<String, Object> beanMaps = Maps.newHashMap();

  /**
   *
   * @param className
   * @return the value to which the specified key is mapped, or {@code null} if this map contains no
   *         mapping for the key
   */
  public static Object getBean(String className) {
    return beanMaps.get(className);
  }

  protected static void add(String className, Object object) {
    beanMaps.put(className, object);
  }
}
