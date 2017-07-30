package com.gzjy.common.util.fs;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by szt on 2016/11/1.
 */
public class EpicNFSCondition implements Condition {
  @Override
  public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {


    try {
      Resource resource =
          new PathMatchingResourcePatternResolver().getResource("classpath:epc.properties");

      Properties properties = null;
      properties = PropertiesLoaderUtils.loadProperties(resource);
      String property = properties.getProperty("epc.use.nfs");
      if ("false".equalsIgnoreCase(property)) {
        return false;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


    return true;

  }
}
