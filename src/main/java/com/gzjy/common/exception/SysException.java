package com.gzjy.common.exception;

/**
 * System Exception
 */
public class SysException extends RuntimeException {
    private static final long serialVersionUID = -381201781656355583L;

    public SysException() {
        super();
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(Throwable cause) {
        super(cause);
    }
}
