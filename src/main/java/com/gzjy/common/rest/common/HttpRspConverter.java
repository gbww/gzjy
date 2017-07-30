package com.gzjy.common.rest.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Date Converter for RestTemaplate
 * @author yangxiaoming@cmss.chinamobile.com
 *
 */
public class HttpRspConverter {
  private List<HttpMessageConverter<?>> converters = null;
  private ObjectMapper objectMapper = null;
  private DateFormat dateFormat = null;
  
  /**
   * Specify the date with specific DateFormat
   * <p>if dateFormat is null then assign the DateFormat as "yyyy-MM-dd HH:mm:ss"
   * @param dateFormat : date format
   */
  public HttpRspConverter(DateFormat dateFormat) {
    converters = new ArrayList<HttpMessageConverter<?>>();
    objectMapper = new ObjectMapper();
//    TypeFactory typeFactory = objectMapper.getTypeFactory();
//    JavaType javaType = objectMapper.getTypeFactory().constructType(CrabRole.class);
//    typeFactory.constructSpecializedType(javaType, CrabRole.class);
//    typeFactory.constructReferenceType(Role.class, javaType);
//    typeFactory.constructSimpleType(Role.class, new JavaType[]{javaType});
    if (null == dateFormat) {
      this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      objectMapper.setDateFormat(this.dateFormat);
    } else {
      this.dateFormat = dateFormat;
      objectMapper.setDateFormat(dateFormat);
    }
    init();
  }
  
  public DateFormat getDateFormat() {
    return dateFormat;
  }
  
  public void setDateFormat (DateFormat dateFormat) {
    this.dateFormat = dateFormat;
  }
  
  private void init() {
    // support for byte array
    converters.add(new ByteArrayHttpMessageConverter());
    // support for String
    converters.add(new StringHttpMessageConverter());
    // support for Resource
    converters.add(new ResourceHttpMessageConverter());
    // support for Source (eg. SAXSource)
    converters.add(new SourceHttpMessageConverter<>());
    // support for XML and JSON-based parts
    converters.add(new AllEncompassingFormHttpMessageConverter());
    // support for XML using JAXB2
    converters.add(new Jaxb2RootElementHttpMessageConverter());
    // support for JSON
    converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
  }
  
  /**
   * Date Converters for RestTemplate
   * @return converters
   */
  public List<HttpMessageConverter<?>> getConverters() {
    return converters;
  }
  
  public HttpMessageConverter<?> getMappingJackson2HttpMessageConverter() {    return new MappingJackson2HttpMessageConverter(objectMapper);
  }
}
