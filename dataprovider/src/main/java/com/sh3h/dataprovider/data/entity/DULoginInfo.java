package com.sh3h.dataprovider.data.entity;


public class DULoginInfo extends DURequest {
    private String account;
    private String pwd;
    private String AppIdentify;

    public DULoginInfo() {

    }

    public DULoginInfo(String account,
                       String pwd,
                       String AppIdentify) {
        this.account = account;
        this.pwd = pwd;
        this.AppIdentify = AppIdentify;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAppIdentify() {
        return AppIdentify;
    }

    public void setAppIdentify(String AppIdentify) {
        this.AppIdentify = AppIdentify;
    }
}
