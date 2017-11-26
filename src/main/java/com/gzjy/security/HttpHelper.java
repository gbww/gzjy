package com.gzjy.security;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.rest.common.HttpRspConverter;
import com.gzjy.common.rest.common.SSLClientFactory;

/**
 * Epic's central class for synchronous client-side HTTP/HTTPS access.</strong> It simplifies
 * communication with HTTP/HTTPS servers, and enforces RESTful principles. It handles HTTP/HTTPS
 * connections, leaving application code to provide URLs (with possible template variables) and
 * extract results
 * 
 * @author 
 *
 */
public class HttpHelper {
  private final int REQUEST_TYPE_PARAM = 0;
  private final int REQUEST_TYPE_BODY = 1;
  private RestTemplate restTemplate;
  private String url;
  private HttpEntity<Object> formEntity;
  private Map<String, String> uriVariables;

  /**
   * Handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestBody : request body
   * @param headers : http headers
   * @return execute response
   */
  public <T> T post(String url, ParameterizedTypeReference<T> type, Object requestBody,
      Map<String, String> headers) {
    return execute(url, HttpMethod.POST, this.REQUEST_TYPE_BODY, type, null, requestBody, null,
        null, headers, null);
  }

  /**
   * Handle the given post request, performing appropriate response
   * 
   * @param url : request URL
   * @param type : enable capturing and passing a generic
   * @param requestBody : request body
   * @param headers : HTTP headers
   * @param token : OAuth token
   * @return execute response
   */
  public <T> T post(String url, ParameterizedTypeReference<T> type, Object requestBody,
      Map<String, String> headers, String token) {
    return execute(url, HttpMethod.POST, this.REQUEST_TYPE_BODY, type, null, requestBody, null,
        null, headers, token);
  }

  /**
   * Handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestParams : request params
   * @param headers : http headers
   * @return execute response
   */
  public <T> T post(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers) {
    return execute(url, HttpMethod.POST, this.REQUEST_TYPE_PARAM, type, null, null, requestParams,
        null, headers, null);
  }

  /**
   * Handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestParams : request params
   * @param headers : http headers
   * @param token : OAuth token
   * @return execute response
   */
  public <T> T post(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers, String token) {
    return execute(url, HttpMethod.POST, this.REQUEST_TYPE_PARAM, type, null, null, requestParams,
        null, headers, token);
  }

  /**
   * Handle the given delete request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param headers : http headers
   * @return execute response
   */
  public <T> T delete(String url, ParameterizedTypeReference<T> type, Map<String, String> headers) {
    return execute(url, HttpMethod.DELETE, null, type, null, null, null, null, headers, null);
  }

  /**
   * Handle the given delete request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param headers : http headers
   * @param token : OAuth token
   * @return execute response
   */
  public <T> T delete(String url, ParameterizedTypeReference<T> type, Map<String, String> headers,
      String token) {
    return execute(url, HttpMethod.DELETE, null, type, null, null, null, null, headers, token);
  }

  /**
   * Handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestParams : request params
   * @param headers : http headers
   * @return execute response
   */
  public <T> T put(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers) {
    return execute(url, HttpMethod.PUT, this.REQUEST_TYPE_PARAM, type, null, null, requestParams,
        null, headers, null);
  }

  /**
   * Handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestParams : request params
   * @param headers : HTTP headers
   * @param token : OAuth token
   * @return execute response
   */
  public <T> T put(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers, String token) {
    return execute(url, HttpMethod.PUT, this.REQUEST_TYPE_PARAM, type, null, null, requestParams,
        null, headers, token);
  }

  /**
   * Handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestBody : request body
   * @param headers : http headers
   * @return execute response
   */
  public <T> T put(String url, ParameterizedTypeReference<T> type, Object requestBody,
      Map<String, String> headers) {
    return execute(url, HttpMethod.PUT, this.REQUEST_TYPE_BODY, type, null, requestBody, null, null,
        headers, null);
  }

  /**
   * Handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestBody : request body
   * @param headers : http headers
   * @param token : OAuth token
   * @return execute response
   */
  public <T> T put(String url, ParameterizedTypeReference<T> type, Object requestBody,
      Map<String, String> headers, String token) {
    return execute(url, HttpMethod.PUT, this.REQUEST_TYPE_BODY, type, null, requestBody, null, null,
        headers, token);
  }

  /**
   * Handle the given get request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param uriVariables : url params, exp (url?xxx=...&yyy=...)
   * @param headers : http headers
   * @return execute response
   */
  public <T> T get(String url, ParameterizedTypeReference<T> type, Map<String, String> uriVariables,
      Map<String, String> headers) {
    return execute(url, HttpMethod.GET, null, type, null, null, null, uriVariables, headers, null);
  }

  /**
   * Handle the given get request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param uriVariables : url params, exp (url?xxx=...&yyy=...)
   * @param headers : http headers
   * @param token : OAuth token
   * @return execute response
   */
  public <T> T get(String url, ParameterizedTypeReference<T> type, Map<String, String> uriVariables,
      Map<String, String> headers, String token) {
    return execute(url, HttpMethod.GET, null, type, null, null, null, uriVariables, headers, token);
  }

  /**
   * When using params uriVariables as calling method exchange() of RestTemplate, you should expand
   * request url to adapt the params
   * 
   * @param url : request url
   * @param uriVariables : url params, exp (url?xxx=...&yyy=...)
   * @return expand url
   */
  private String urlVariablesBuilder(String url, Map<String, String> uriVariables) {
    if (url == null) return null;
    StringBuffer sb = new StringBuffer();
    sb.append(url);
    if (uriVariables != null && uriVariables.size() > 0) {
      int first = 0;
      sb.append("?");
      for (Entry<String, String> entry : uriVariables.entrySet()) {
        String and = first == 0 ? "" : "&";
        sb.append(and);
        sb.append(entry.getKey());
        sb.append("={");
        sb.append(entry.getKey());
        sb.append("}");
        first++;
      }
    }
    return sb.toString();
  }

  /**
   * Initialize the related request params
   * 
   * @param url : request url
   * @param method : http method
   * @param requestType : support for POST/PUT method to decide using RequestParams or RequestBody
   *        details: 0 is RequestParams, 1 is RequestBody
   * @param dateFormat : Specify the date with specific DateFormat, if dateFormat is null then
   *        assign the DateFormat as "yyyy-MM-dd HH:mm:ss"
   * @param requestBody : request body
   * @param requestParams : request params
   * @param uriVariables : expand request url to adapt the uriVariables
   * @param headers : http headers
   * @param token : OAuth token
   */
  private void init(String url, HttpMethod method, Integer requestType, DateFormat dateFormat,
      Object requestBody, Map<String, Object> requestParams, Map<String, String> uriVariables,
      Map<String, String> headers, String token) {
    HttpHeaders localHeaders = null;
    MultiValueMap<String, Object> localRequestParams = null;
    String localRequestBody = null;

    ClientHttpRequestFactory ssl = protoclAnalyzer(url);
    if (null != ssl)
      this.restTemplate = new RestTemplate(ssl);
    else
      this.restTemplate = new RestTemplate();

    /**
     * Set the httpMessage converters for this RestTemplate
     */
    List<HttpMessageConverter<?>> converters = new HttpRspConverter(dateFormat).getConverters();
    restTemplate.setMessageConverters(converters);

    /**
     * Set HTTP headers for this request
     */
    if (headers != null) {
      localHeaders = localHeaders == null ? new HttpHeaders() : localHeaders;
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        localHeaders.add(entry.getKey(), entry.getValue());
      }
    }

    /**
     * envelope OAuth token in HTTP header
     */
    if (token != null) {
      localHeaders = localHeaders == null ? new HttpHeaders() : localHeaders;
      token = token.trim();
      if (token.toUpperCase().contains("BEARER")) {
        localHeaders.add("Authorization", token);
      } else {
        localHeaders.add("Authorization", "bearer " + token);
      }
    }

    /**
     * Set HTTP requestParams for this request
     */
    if (requestParams != null) {
      localRequestParams = new LinkedMultiValueMap<String, Object>();
      for (Map.Entry<String, Object> entry : requestParams.entrySet()) {
        localRequestParams.add(entry.getKey(), entry.getValue().toString());
      }
    }

    /**
     * Set http requestBody for this request
     */
    if (requestBody != null) {
      ObjectMapper om = new ObjectMapper();
      try {
        localRequestBody = om.writeValueAsString(requestBody);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }

    /**
     * Set uriVariables for this request
     */
    this.url = urlVariablesBuilder(url, uriVariables);
    this.uriVariables = uriVariables == null ? new HashMap<>() : uriVariables;

    MediaType contentType = null;
    switch (method) {
      case GET:
        this.formEntity = new HttpEntity<>(null, localHeaders);
        break;
      case POST:
        if (requestType == REQUEST_TYPE_PARAM) {
          contentType =
              MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
          localHeaders.setContentType(contentType);
          this.formEntity = new HttpEntity<>(localRequestParams, localHeaders);
        } else if (requestType == REQUEST_TYPE_BODY) {
          contentType = MediaType.parseMediaType("application/json; charset=UTF-8");
          localHeaders.setContentType(contentType);
          this.formEntity = new HttpEntity<>(localRequestBody, localHeaders);
        }
        break;
      case DELETE:
        this.formEntity = new HttpEntity<>(null, localHeaders);
        break;
      case PUT:
        if (requestType == REQUEST_TYPE_PARAM) {
          contentType =
              MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
          localHeaders.setContentType(contentType);
          this.formEntity = new HttpEntity<>(localRequestParams, localHeaders);
        } else if (requestType == REQUEST_TYPE_BODY) {
          contentType = MediaType.parseMediaType("application/json; charset=UTF-8");
          localHeaders.setContentType(contentType);
          this.formEntity = new HttpEntity<>(localRequestBody, localHeaders);
        }
        break;
      default:
        throw new IllegalArgumentException("Unsupported http method: " + method);
    }
  }

  /**
   * 
   * Execute the request
   * 
   * @param url : request url
   * @param method : http method
   * @param requestType : support for POST/PUT method to decide using RequestParams or RequestBody
   *        details: 0 is RequestParams, 1 is RequestBody
   * @param type : enable capturing and passing a generic
   * @param dateFormat : Specify the date with specific DateFormat, if dateFormat is null then
   *        assign the DateFormat as "yyyy-MM-dd HH:mm:ss"
   * @param requestBody : request body
   * @param requestParams : request params
   * @param uriVariables : expand request url to adapt the uriVariables
   * @param headers : http headers
   * @param token : OAuth token
   * @return
   */
  private <T> T execute(String url, HttpMethod method, Integer requestType,
      ParameterizedTypeReference<T> type, DateFormat dateFormat, Object requestBody,
      Map<String, Object> requestParams, Map<String, String> uriVariables,
      Map<String, String> headers, String token) {
    init(url, method, requestType, dateFormat, requestBody, requestParams, uriVariables, headers,
        token);
    ResponseEntity<T> response = null;
    try {
      response = restTemplate.exchange(this.url, method, formEntity, type, this.uriVariables);
    } catch (Exception e) {
      throw new BizException(e.getMessage());
    }
    if (response.getStatusCode().value() == 200)
      return response.getBody();
    throw new BizException("未知错误");
  }

  /**
   * Analyze the given url to decide if enable HTTPS
   * 
   * @param url
   * @return
   */
  private SimpleClientHttpRequestFactory protoclAnalyzer(String url) {
    if (null != url && url.toUpperCase().contains("HTTPS")) {
      return new SSLClientFactory();
    }
    return null;
  }
}
