package com.gzjy.oauth.condition;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class TokenStoreJdbcCondition implements Condition {
  @Override
  public boolean matches(ConditionContext conditionContext,
      AnnotatedTypeMetadata annotatedTypeMetadata) {
    try {
      Resource resource =
          new PathMatchingResourcePatternResolver().getResource("classpath:epc.properties");
      Properties properties = null;
      properties = PropertiesLoaderUtils.loadProperties(resource);

      String property = properties.getProperty("epc.oauth.token-store-type");
      if ("JDBC".equals(property)) {
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
