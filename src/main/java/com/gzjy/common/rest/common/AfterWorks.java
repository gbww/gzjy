package com.gzjy.common.rest.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Callback mechanism for the outcome, success or failure
 * @author yangxiaoming@cmss.chinamobile.com
 *
 * @param <T>
 */
public abstract class AfterWorks<T> implements ListenableFutureCallback<ResponseEntity<T>> {

  @Override
  public final void onSuccess(ResponseEntity<T> result) {
    if (result.getStatusCode() == HttpStatus.OK)
      whenSuccess(result.getBody());
    else
      whenFailure(new Exception());
  }

  @Override
  public final void onFailure(Throwable ex) {
    whenFailure(ex);
  }
  
  /**
   * the operation maybe to do when request succeed 
   * @param result : success response
   */
  public abstract void whenSuccess(T result);
  
  /**
   * the operation maybe to do when request failed 
   * @param ex : Exception
   */
  public abstract void whenFailure(Throwable ex);
}
