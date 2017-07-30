package com.gzjy.common.rest;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.gzjy.common.rest.common.AfterWorks;

/**
 * 
 * @author yangxiaoming@cmss.chinamobile.com
 *
 */
@Service
public class EpcASyncRestService {
  private EpcAsyncRestTemplate asyncHelper = new EpcAsyncRestTemplate();
  /**
   * Handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic
   * @param headers : http headers
   * @return execute response
   */
  public <T> T post(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers) {
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.post(url, requestBody, type, headers);
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
  public <T> T post(String url, Object requestBody, ParameterizedTypeReference<T> type,
                    Map<String, String> headers, String token) {
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.post(url, requestBody, type, headers, token);
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
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.post(url, type, requestParams, headers);
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
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.post(url, type, requestParams, headers, token);
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
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.delete(url, type, headers);
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
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.delete(url, type, headers, token);
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
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.put(url, type, requestParams, headers);
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
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.put(url, type, requestParams, headers, token);
  }
  
  /**
   * Handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic
   * @param headers : http headers
   * @return execute response
   */
  public <T> T put(String url, Object requestBody, ParameterizedTypeReference<T> type, 
      Map<String, String> headers) {
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.put(url, requestBody, type, headers);
  }
  
  /**
   * Handle the given put request, performing appropriate response
   * 
   * @param url : request url
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic
   * @param headers : http headers
   * @param token : OAuth token
   * @return execute response
   */
  public <T> T put(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers, String token) {
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.put(url, requestBody, type, headers, token);
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
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.get(url, type, uriVariables, headers);
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
    EpcSyncRestTemplate helper = new EpcSyncRestTemplate();
    return helper.get(url, type, uriVariables, headers, token);
  }
      
  /**
   * Asynchronize handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic
   * @param headers : http headers
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void asyncPost(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers, AfterWorks<T> callBack) {
    asyncHelper.post(url, requestBody, type, headers, callBack);
  }
  
  /**
   * Asynchronize handle the given post request, performing appropriate response
   * 
   * @param url : request URL
   * @param requestBody : request body
   * @param type : enable capturing and passing a generic
   * @param headers : HTTP headers
   * @param token : OAuth token
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void asyncPost(String url, Object requestBody, ParameterizedTypeReference<T> type,
                    Map<String, String> headers, String token, AfterWorks<T> callBack) {
    asyncHelper.post(url, requestBody, type, headers, token, callBack);
  }
  
  /**
   * Asynchronize handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestParams : request params
   * @param headers : http headers
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void asyncPost(String url, ParameterizedTypeReference<T> type,
                    Map<String, Object> requestParams, Map<String, String> headers, AfterWorks<T> callBack) {
    asyncHelper.post(url, type, requestParams, headers, callBack);
  }
  
  /**
   * Asynchronize handle the given post request, performing appropriate response
   * 
   * @param url : request url
   * @param type : enable capturing and passing a generic
   * @param requestParams : request params
   * @param headers : http headers
   * @param token : OAuth token
   * @param callBack : Callback mechanism for the outcome, success or failure
   * @return execute response
   */
  public <T> void asyncPost(String url, ParameterizedTypeReference<T> type,
                    Map<String, Object> requestParams, Map<String, String> headers, String token, AfterWorks<T> callBack) {
    asyncHelper.post(url, type, requestParams, headers, token, callBack);
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
  public <T> void asyncDelete(String url, ParameterizedTypeReference<T> type,
      Map<String, String> headers, AfterWorks<T> callBack) {
    asyncHelper.delete(url, type, headers, callBack);
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
  public <T> void asyncDelete(String url, ParameterizedTypeReference<T> type,
      Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    asyncHelper.delete(url, type, headers, token, callBack);
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
  public <T> void asyncPut(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers,
      AfterWorks<T> callBack) {
    asyncHelper.put(url, type, requestParams, headers, callBack);
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
  public <T> void asyncPut(String url, ParameterizedTypeReference<T> type,
      Map<String, Object> requestParams, Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    asyncHelper.put(url, type, requestParams, headers, token, callBack);
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
  public <T> void asyncPut(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers, AfterWorks<T> callBack) {
    asyncHelper.put(url, requestBody, type, headers, callBack);
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
  public <T> void asyncPut(String url, Object requestBody, ParameterizedTypeReference<T> type,
      Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    asyncHelper.put(url, requestBody, type, headers, token, callBack);
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
  public <T> void asyncGet(String url, ParameterizedTypeReference<T> type,
      Map<String, String> uriVariables, Map<String, String> headers,
      AfterWorks<T> callBack) {
    asyncHelper.get(url, type, uriVariables, headers, callBack);
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
  public <T> void asyncGet(String url, ParameterizedTypeReference<T> type,
      Map<String, String> uriVariables, Map<String, String> headers, String token,
      AfterWorks<T> callBack) {
    asyncHelper.get(url, type, uriVariables, headers, token, callBack);
  }
}
