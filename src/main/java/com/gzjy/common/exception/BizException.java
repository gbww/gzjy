package com.gzjy.common.exception;

/**
 * Business Exception
 */
public class BizException extends RuntimeException {
  private static final long serialVersionUID = 1089861679981847231L;

  public BizException(String message) {
    super(message);
  }
}
