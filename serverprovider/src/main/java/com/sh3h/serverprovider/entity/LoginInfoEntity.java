/**
 * @author liukaiyu
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录信息
 */
public class LoginInfoEntity {
    public enum VerType {
        /**
         * 域认证
         */
        Domain,
        /**
         * 简单认证
         */
        Simple

    }

    /**
     * 员工账号
     */
    private String _account;

    /**
     * 员工密码
     */
    private String _password;

    /**
     * 登陆方式
     */
    private VerType _verType;

    public LoginInfoEntity() {
        _account = null;
        _password = null;
        _verType = VerType.Simple;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return _account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        _account = account;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return _password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        _password = password;
    }

    /**
     * @return the verType
     */
    public VerType getVerType() {
        return _verType;
    }

    /**
     * @param verType the verType to set
     */
    public void setVerType(VerType verType) {
        _verType = verType;
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();

        try {
            result.put("Account", getAccount());
            result.put("Password", getPassword());
            result.put("VerType", getVerType().toString());
        } catch (JSONException e) {
            return null;
        }
        return result;
    }
}
