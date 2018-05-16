package com.sh3h.dataprovider.data.entity.base;

/**
 * Created by dengzhimin on 2017/4/11.
 */
public class DUBaseResult {

    public static final int CODE_0 = 0;
    public static final int STATUS_CODE_200 = 200;
    private int code;
    private int statusCode;
    private String message;

    public DUBaseResult(int code, int statusCode, String message) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
