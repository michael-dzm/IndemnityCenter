package com.sh3h.dataprovider.data.entity.result;

/**
 * Created by dengzhimin on 2016/5/31.
 */
public class DULoginResult {
    private int userId;
    private String access_token;

    public DULoginResult(int userId, String access_token){
        this.userId = userId;
        this.access_token = access_token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
