package com.sh3h.dataprovider.data.entity;


public class DULoginResult extends DUResponse {
    public static final int DEFAULT_CODE = 0;
    public static final int SUCCESS_CODE = 1;
    public static final int ACCOUNT_NOT_EXIST_CODE = -1;
    public static final int ERROR_CODE = -2;

    private int code;
    private int userID;

    public DULoginResult() {
        code = DEFAULT_CODE;
        userID = 0;
    }

    public DULoginResult(int code, int userID) {
        this.code = code;
        this.userID = userID;
        //this.responseHandler = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
