package com.gzjy.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by szt on 2016/10/27.
 */
public class ReflectUtil<T> {
  private final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);
  private ClassLoader classloader;

  public ClassLoader getClassLoader() {
    return classloader == null ? Thread.currentThread().getContextClassLoader() : classloader;
  }

  public T getInstance(String name) {
    try {
      Class<?> aClass = getClassLoader().loadClass(name);
      return (T) aClass.newInstance();
    } catch (ClassNotFoundException e) {
      logger.warn("Could not examine class '" + name + "'" + " due to a " + e.getClass().getName()
          + " with message: " + e.getMessage());
    } catch (InstantiationException e) {
      logger.warn("Could not get new instance class", e);
    } catch (IllegalAccessException e) {
      logger.warn("Could not get new instance class", e);
    }
    return null;
  }
}
