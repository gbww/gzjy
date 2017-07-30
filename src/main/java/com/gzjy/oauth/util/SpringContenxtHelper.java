package com.gzjy.oauth.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContenxtHelper implements ApplicationContextAware {

  private static ApplicationContext context;

  // 提供一个接口，获取容器中的Bean实例，根据名称获取
  public static Object getBean(String beanName) {
    return context.getBean(beanName);
  }

  public static Object getBean(Class<?> clazz) {
    return context.getBean(clazz);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
}
