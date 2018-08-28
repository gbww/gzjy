package com.gzjy.common.rest;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.FacesWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JavaType;
import com.gzjy.common.Response;
import com.gzjy.common.exception.SysException;


/**
 * 项目间调用的辅助类 ,项目使用oauth2使用。如果平常使用，请使用<code>EpcRestTemplate<code/>
 *
 * Created by szt on 2016/6/12.
 */

@Component
public class EpcRestService {

  private AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

  public <T> Response<T> exchangeBody(String url, HttpMethod method, Object requestBody, Class<T> responseType) {
    EpcRestTemplate restTemplate = new EpcRestTemplate();
    EpcRestTemplate.Header header = getHeader(null);
    JavaType javaType = getJavaType(responseType, restTemplate);
    return restTemplate.exchangeBody(url, method, javaType, requestBody,header);
  }
  public <T> Response<T> exchangeBody(String url, HttpMethod method, Object requestBody, Class<T> responseType,String oauthToken) {
    EpcRestTemplate restTemplate = new EpcRestTemplate();
    EpcRestTemplate.Header header = getHeader(oauthToken);
    JavaType javaType = getJavaType(responseType, restTemplate);
    return restTemplate.exchangeBody(url, method, javaType, requestBody,header);
  }



  public <T> Response<T> exchangeBody(String url, HttpMethod method, Object requestBody, Type responseType) {
    EpcRestTemplate restTemplate = new EpcRestTemplate();
    EpcRestTemplate.Header header = getHeader(null);
    JavaType javaType = getJavaType(responseType, restTemplate);
    return restTemplate.exchangeBody(url, method, javaType, requestBody,header);
  }
  public <T> Response<T> exchangeBody(String url, HttpMethod method, Object requestBody, Type responseType,String oauthToken) {
    EpcRestTemplate restTemplate = new EpcRestTemplate();
    EpcRestTemplate.Header header = getHeader(oauthToken);
    JavaType javaType = getJavaType(responseType, restTemplate);
    return restTemplate.exchangeBody(url, method, javaType, requestBody,header);
  }



  public <T> Response<T> exchangeParam(String url, HttpMethod method, Type responseType, Map<String, Object> requestParams) {
    EpcRestTemplate restTemplate = new EpcRestTemplate();
    EpcRestTemplate.Header header = getHeader(null);
    JavaType javaType = getJavaType(responseType, restTemplate);
    return restTemplate.exchangeParams(url, method, javaType, requestParams,header);
  }
  public <T> Response<T> exchangeParam(String url, HttpMethod method, Type responseType, Map<String, Object> requestParams,String oauthToken) {
    EpcRestTemplate restTemplate = new EpcRestTemplate();
    EpcRestTemplate.Header header = getHeader(oauthToken);
    JavaType javaType = getJavaType(responseType, restTemplate);
    return restTemplate.exchangeParams(url, method, javaType, requestParams,header);
  }

  public <T> Response<T> exchangeParam(String url, HttpMethod method, Class<T> responseClass, Map<String, Object> requestParams) {
    EpcRestTemplate restTemplate = new EpcRestTemplate();
    EpcRestTemplate.Header header = getHeader(null);
    JavaType javaType = getJavaType(responseClass, restTemplate);
    return restTemplate.exchangeParams(url, method, javaType, requestParams,header);
  }
 public <T> Response<T> exchangeParam(String url, HttpMethod method, Class<T> responseClass, Map<String, Object> requestParams,String oauthToken) {
    EpcRestTemplate restTemplate = new EpcRestTemplate();
    EpcRestTemplate.Header header = getHeader(oauthToken);
    JavaType javaType = getJavaType(responseClass, restTemplate);
    return restTemplate.exchangeParams(url, method, javaType, requestParams,header);
  }

  public <T> void asyncExchangeBody(String authorization, String url, HttpMethod method, T requestBody, Map<String, Object> requestParams, ListenableFutureCallback callBack) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);


    if (StringUtils.isNotEmpty(authorization)) {
      headers.set("Authorization", authorization);
    }
    HttpEntity<T> entity;
    if (requestBody != null) {
      entity = new HttpEntity<>(requestBody, headers);
    } else {
      entity = new HttpEntity<>(headers);
    }
    if (requestParams == null) {
      requestParams = Collections.emptyMap();
    }
    ListenableFuture<ResponseEntity<Response>> listenableFuture =
        asyncRestTemplate.exchange(url, method, entity, Response.class, requestParams);

    listenableFuture.addCallback(callBack);
  }
  
  public <T> void asyncExchangeBody( String url, HttpMethod method, T requestBody, Map<String, Object> requestParams, ListenableFutureCallback callBack) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    String authorization = getAuthorization();

    if (StringUtils.isNotEmpty(authorization)) {
      headers.set("Authorization", authorization);
    }
    HttpEntity<T> entity;
    if (requestBody != null) {
      entity = new HttpEntity<>(requestBody, headers);
    } else {
      entity = new HttpEntity<>(headers);
    }
    if (requestParams == null) {
      requestParams = Collections.emptyMap();
    }
    ListenableFuture<ResponseEntity<Response>> listenableFuture =
        asyncRestTemplate.exchange(url, method, entity, Response.class, requestParams);

    listenableFuture.addCallback(callBack);
  }


  private String getAuthorization() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    String authorization;
    if (requestAttributes instanceof ServletRequestAttributes) {
      authorization =
          ((ServletRequestAttributes) requestAttributes).getRequest().getHeader("Authorization");
    } else if (requestAttributes instanceof FacesWebRequest) {
      authorization = ((FacesWebRequest) requestAttributes).getHeader("Authorization");
    } else {
      throw new SysException("无法获得用户认证");
    }
    return authorization;
  }
  private <T> JavaType getJavaType(Type responseType, EpcRestTemplate restTemplate) {
    JavaType innerType = restTemplate.getTypeFactory().constructType(responseType);
    return restTemplate.getTypeFactory().constructParametrizedType(Response.class, Response.class, innerType);
  }

  private EpcRestTemplate.Header getHeader(String oauthToken) {
    String authorization;
    if (StringUtils.isEmpty(oauthToken)) {
       authorization = getAuthorization();
    } else {
      authorization=oauthToken;
    }

    EpcRestTemplate.Header header=new EpcRestTemplate.Header();
    header.addValue("Authorization",authorization);
    return header;
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}
