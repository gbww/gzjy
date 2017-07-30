package com.gzjy.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * Default Jackson Mappings
 *
 * @author Jeremy Unruh
 */
public class ObjectMapperFactory {


  ObjectMapper mapper;
  ObjectMapper rootMapper;

  private ObjectMapperFactory() {

    mapper = new ObjectMapper();

    mapper.setSerializationInclusion(Include.NON_NULL);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    mapper.setDateFormat(formatter);
    rootMapper = new ObjectMapper();
    rootMapper.setSerializationInclusion(Include.NON_NULL);
    rootMapper.enable(SerializationFeature.INDENT_OUTPUT);
    rootMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    rootMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    rootMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    rootMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    rootMapper.setDateFormat(formatter);
  }

  public static ObjectMapper getContext(Class<?> type) {
    if (type == null) {
      return new ObjectMapperFactory().mapper;
    }
    return type.getAnnotation(JsonRootName.class) == null ?
        new ObjectMapperFactory().mapper :
        new ObjectMapperFactory().rootMapper;
  }

}
