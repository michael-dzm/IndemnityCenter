package com.sh3h.dataprovider.data.entity;


public abstract class DUResponse implements IDUResponse {
    public static final int RESPONSE_SUCCESS_CODE = 0;
    public static final int RESPONSE_FAILURE_CODE = 1;
    public static final int RESPONSE_RECOVERY_CODE = 2;

    public static final String RESPONSE_PARAM_ERROR = "RESPONSE_PARAM_ERROR";
    public static final String RESPONSE_RETURN_NULL = "RESPONSE_RETURN_NULL";
    public static final String RESPONSE_RETURN_FAILURE = "RESPONSE_RETURN_FAILURE";

    private int responseCode;
    private String responseError;


    public DUResponse() {
        responseCode = RESPONSE_FAILURE_CODE;
        responseError = null;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseError() {
        return responseError;
    }

    public void setResponseError(String responseError) {
        this.responseError = responseError;
    }
}
