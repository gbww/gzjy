package com.gzjy.common.rest;

import java.text.DateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.FacesWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.exception.SysException;
import com.gzjy.common.rest.common.AfterWorks;
import com.gzjy.common.rest.common.AsyncSSLClientFactory;
import com.gzjy.common.rest.common.HttpRspConverter;

/**
 * Epic's central class for asynchronous client-side HTTP/HTTPS access.</strong> It simplifies
 * communication with HTTP/HTTPS servers, and enforces RESTful principles. It handles HTTP/HTTPS
 * connections, leaving application code to provide URLs (with possible template variables) and
 * extract results
 * 
 * @author yangxiaoming@cmss.chinamobile.com
 *
 */
public class EpcAsyncRestTemplate {
  private final int REQUEST_TYPE_PARAM = 0;
  private final int REQUEST_TYPE_BODY = 1;
  private final int REQUEST_PROTOCOL_HTTP = 0;
  private final int REQUEST_PROTOCOL_HTTPS = 1;
  private final String FORM_ENTITY = "formEntity";
  private final String URL = "url";
  private final String URIVARIABLES = "urivariables";
  private final String ASYNCRESTTEMPLATE = "asyncresttemplate";
  private AsyncRestTemplate asyncHttpsRestTemplate = null;
  private AsyncRestTemplate asyncHttpRestTemplate =  null;
  
  public EpcAsyncRestTemplate() {
    List<HttpMessageConverter<?>> converters = new HttpRspConverter(null).getConverters();
    
    asyncHttpsRestTemplate = new AsyncRestTemplate(initSSL());
    asyncHttpsRestTemplate.setMessageConverters(converters);
    asyncHttpRestTemplate = new AsyncRestTemplate();
    asyncHttpRestTemplate.setMessageConverters(converters);
  }

  /**
   * Asynchronize handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param headers : http headers
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void post(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers, AfterWorks<T> callBack) {
    execute(url, HttpMethod.POST, this.REQUEST_TYPE_BODY, type, null, requestBody, null, null,
        headers, null, callBack);
  }

  /**
   * Asynchronize handle the given post request, performing appropriate response
   * 
   * @param url : request URL
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param headers : HTTP headers
   * @param token : OAuth token
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void post(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.POST, this.REQUEST_TYPE_BODY, type, null, requestBody, null, null,
        headers, token, callBack);
  }

  /**
   * Asynchronize handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param requestParams : request params
   * @param headers : http headers
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void post(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.POST, this.REQUEST_TYPE_PARAM, type, null, null, requestParams, null,
        headers, null, callBack);
  }

  /**
   * Asynchronize handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param requestParams : request params
   * @param headers : http headers
   * @param token : OAuth token
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void post(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.POST, this.REQUEST_TYPE_PARAM, type, null, null, requestParams, null,
        headers, token, callBack);
  }

  /**
   * Asynchronize handle the given delete request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param headers : http headers
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void delete(String url, ParameterizedTypeReference<T> type,
      Map<String, String> headers, AfterWorks<T> callBack) {
    execute(url, HttpMethod.DELETE, null, type, null, null, null, null, headers, null, callBack);
  }

  /**
   * Asynchronize handle the given delete request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param headers : http headers
   * @param token : OAuth token
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void delete(String url, ParameterizedTypeReference<T> type,
      Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.DELETE, null, type, null, null, null, null, headers, token, callBack);
  }

  /**
   * Asynchronize handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param requestParams : request params
   * @param headers : http headers
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void put(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.PUT, this.REQUEST_TYPE_PARAM, type, null, null, requestParams, null,
        headers, null, callBack);
  }

  /**
   * Asynchronize handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param requestParams : request params
   * @param headers : HTTP headers
   * @param token : OAuth token
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void put(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.PUT, this.REQUEST_TYPE_PARAM, type, null, null, requestParams, null,
        headers, token, callBack);
  }

  /**
   * Asynchronize handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param headers : http headers
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void put(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers, AfterWorks<T> callBack) {
    execute(url, HttpMethod.PUT, this.REQUEST_TYPE_BODY, type, null, requestBody, null, null,
        headers, null, callBack);
  }

  /**
   * Asynchronize handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param headers : http headers
   * @param token : OAuth token
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void put(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.PUT, this.REQUEST_TYPE_BODY, type, null, requestBody, null, null,
        headers, token, callBack);
  }

  /**
   * Asynchronize handle the given get request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param uriVariables : url params, exp (url?xxx=...&yyy=...)
   * @param headers : http headers
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void get(String url, ParameterizedTypeReference<T> type,
      Map<String, String> uriVariables, Map<String, String> headers,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.GET, null, type, null, null, null, uriVariables, headers, null,
        callBack);
  }

  /**
   * Asynchronize handle the given get request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic<p>ParameterizedTypeReference<List<MyBean>> myBean = new ParameterizedTypeReference<List<MyBean>>() {};
   * @param uriVariables : url params, exp (url?xxx=...&yyy=...)
   * @param headers : http headers
   * @param token : OAuth token
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void get(String url, ParameterizedTypeReference<T> type,
      Map<String, String> uriVariables, Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    execute(url, HttpMethod.GET, null, type, null, null, null, uriVariables, headers, token,
        callBack);
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
      Map<String, String> headers, String token, Map<String, Object> execVars) {
    HttpHeaders localHeaders = getRequestHeaders();
    MultiValueMap<String, Object> localRequestParams = null;
    String localRequestBody = null;
    String localUrl = null;
    HttpEntity<Object> localFormEntity = null;
    Map<String, String> localUriVariables = null;
    AsyncRestTemplate template = null;

    int protocol = protoclAnalyzer(url);
    if (REQUEST_PROTOCOL_HTTPS == protocol)
      template = this.asyncHttpsRestTemplate;
    else
      template = this.asyncHttpRestTemplate;

    /**
     * Set HTTP headers for this request
     */
    if (headers != null) {
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        localHeaders.add(entry.getKey(), entry.getValue());
      }
    }

    /**
     * envelope OAuth token in HTTP header
     */
    if (token != null) {
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
    localUrl = urlVariablesBuilder(url, uriVariables);
    localUriVariables = uriVariables == null ? new HashMap<>() : uriVariables;

    MediaType contentType = null;
    switch (method) {
      case GET:
        localFormEntity = new HttpEntity<>(null, localHeaders);
        break;
      case POST:
        if (requestType == REQUEST_TYPE_PARAM) {
          contentType =
              MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
          localHeaders.setContentType(contentType);
          localFormEntity = new HttpEntity<>(localRequestParams, localHeaders);
        } else if (requestType == REQUEST_TYPE_BODY) {
          contentType = MediaType.parseMediaType("application/json; charset=UTF-8");
          localHeaders.setContentType(contentType);
          localFormEntity = new HttpEntity<>(localRequestBody, localHeaders);
        }
        break;
      case DELETE:
        localFormEntity = new HttpEntity<>(null, localHeaders);
        break;
      case PUT:
        if (requestType == REQUEST_TYPE_PARAM) {
          contentType =
              MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
          localHeaders.setContentType(contentType);
          localFormEntity = new HttpEntity<>(localRequestParams, localHeaders);
        } else if (requestType == REQUEST_TYPE_BODY) {
          contentType = MediaType.parseMediaType("application/json; charset=UTF-8");
          localHeaders.setContentType(contentType);
          localFormEntity = new HttpEntity<>(localRequestBody, localHeaders);
        }
        break;
      default:
        throw new IllegalArgumentException("Unsupported http method: " + method);
    }
    execVars.put(ASYNCRESTTEMPLATE, template);
    execVars.put(URL, localUrl);
    execVars.put(FORM_ENTITY, localFormEntity);
    execVars.put(URIVARIABLES, localUriVariables);
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
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return
   */
  @SuppressWarnings("unchecked")
  private <T> void execute(String url, HttpMethod method, Integer requestType,
      ParameterizedTypeReference<T> type, DateFormat dateFormat, Object requestBody,
      Map<String, Object> requestParams, Map<String, String> uriVariables,
      Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    Map<String, Object> execVars = new HashMap<>();
    init(url, method, requestType, dateFormat, requestBody, requestParams, uriVariables, headers,
        token, execVars);

    ListenableFuture<ResponseEntity<T>> future = null;
    AsyncRestTemplate restTemplate = (AsyncRestTemplate) execVars.get(ASYNCRESTTEMPLATE);
    String execUrl = (String) execVars.get(URL);
    HttpEntity<Object> execFormEntity = (HttpEntity<Object>) execVars.get(FORM_ENTITY);
    Map<String, String> execUriVariables = (Map<String, String>) execVars.get(URIVARIABLES);
    try {
      future = restTemplate.exchange(execUrl, method, execFormEntity, type, execUriVariables);
    } catch (Exception e) {
      throw new BizException(e.getMessage());
    }
    future.addCallback(callBack);
  }

  /**
   * Analyze the given url to decide if enable HTTPS
   * 
   * @param url
   * @return
   */
  private int protoclAnalyzer(String url) {
    if (null != url && url.toUpperCase().contains("HTTPS")) {
      return REQUEST_PROTOCOL_HTTPS;
    }
    return REQUEST_PROTOCOL_HTTP;
  }
  
  /**
   * Get headers from request
   * @return
   */
  private HttpHeaders getRequestHeaders() {
    HttpHeaders headers = new HttpHeaders();
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes instanceof ServletRequestAttributes) {
      HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
      Enumeration<String> hdNames = request.getHeaderNames();
      while(hdNames.hasMoreElements()) {
        String hdName = hdNames.nextElement();
        headers.add(hdName, request.getHeader(hdName));
      }
    } else if (requestAttributes instanceof FacesWebRequest) {
      FacesWebRequest request = (FacesWebRequest) requestAttributes;
      Iterator<String> hdNames = request.getHeaderNames();
      while(hdNames.hasNext()) {
        String hdName = hdNames.next();
        headers.add(hdName, request.getHeader(hdName));
      }
    } else {
      throw new SysException("unsupported request");
    }
    return headers;
  }
  
  /**
   * Initialize the ClientHttpRequestFactory
   * @return
   */
  private SimpleClientHttpRequestFactory initSSL() {
    SimpleClientHttpRequestFactory ssl = new AsyncSSLClientFactory();
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setCorePoolSize(5);
    pool.setMaxPoolSize(100);
    pool.setWaitForTasksToCompleteOnShutdown(true);
    pool.initialize();
    ssl.setTaskExecutor(pool);
    return ssl;
  }
}
