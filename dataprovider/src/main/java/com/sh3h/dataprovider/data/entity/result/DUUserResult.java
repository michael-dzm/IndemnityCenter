package com.sh3h.dataprovider.data.entity.result;

/**
 * Created by dengzhimin on 2016/6/1.
 */
public class DUUserResult {

    private int userId;//用户Id
    private String userName;//用户名
    private int sex;//性别
    private String account;//登录账号
    private String address;//地址
    private String mobile;//手机号码
    private String tel;//电话号码
    private String roles;//用户权限
    private String constructionTeam;//用户所属施工队

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getConstructionTeam() {
        return constructionTeam;
    }

    public void setConstructionTeam(String constructionTeam) {
        this.constructionTeam = constructionTeam;
    }
}
