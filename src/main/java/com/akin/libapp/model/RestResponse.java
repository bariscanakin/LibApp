package com.akin.libapp.model;

public class RestResponse {
	private boolean success;
    private String message;
    private Object data;

    public static RestResponse ok(Object data, String message) {
        return new RestResponse(Boolean.TRUE, message, data);
    }

    public static RestResponse ok(Object data) {
        return new RestResponse(Boolean.TRUE, "success", data);
    }

    public static RestResponse error(Object data, String message) {
        return new RestResponse(Boolean.FALSE, message, data);
    }

    public static RestResponse error(String message) {
        return new RestResponse(Boolean.FALSE, message, null);
    }

    public RestResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public RestResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public RestResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
