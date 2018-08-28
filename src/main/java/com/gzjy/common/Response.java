package com.gzjy.common;

/**
 * Response for restful api
 */
public class Response<T> {
    public Response() {
    }

    private boolean success;

    private String message;

    private T entity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Response(boolean success) {
        this.success = success;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(boolean success, String message, T entity) {
        this.success = success;
        this.message = message;
        this.entity = entity;
    }

    /**
     * @param message
     * @return
     */
    public static Response fail(String message) {
        return new Response(false, message);
    }

    /**
     * @param entity
     * @return
     */
    public static <T> Response success(T entity) {
        return new Response(true, null, entity);
    }


}
