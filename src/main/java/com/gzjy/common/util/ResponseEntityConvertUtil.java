package com.gzjy.common.util;

import java.lang.reflect.Field;
import java.util.Map;

import com.gzjy.common.exception.BizException;

public class ResponseEntityConvertUtil {
  @SuppressWarnings("unchecked")
  public static <T> T convert(Object result, Class<T> clazz){
    Map<Object, Object> entity = null;
    if(result.getClass().getSimpleName().contains(Map.class.getSimpleName())){
      entity = (Map<Object, Object>) result;
    }
    else{
      throw new BizException("无法进行转换");
    }
    Field[] fields = clazz.getDeclaredFields();
    T model = null;
    try {
      model = clazz.newInstance();
      for(Field field : fields){
        field.setAccessible(true);
        field.set(model, entity.get(field.getName()));
      }
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      throw new BizException("entity无法转换成功");
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      throw new BizException("entity无法转换成功");
    }
    return model;
  }
}
