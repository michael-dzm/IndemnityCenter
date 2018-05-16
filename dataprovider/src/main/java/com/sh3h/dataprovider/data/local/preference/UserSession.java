package com.sh3h.dataprovider.data.local.preference;


import android.content.Context;
import android.content.SharedPreferences;

import com.sh3h.mobileutil.util.TextUtil;

import java.util.Date;
import java.util.UUID;

public class UserSession {
    /**
     * 共享存储名称
     */
    private static final String FILE_SESSION = "shanghai3h_user_session";

    /**
     * session id
     */
    private static final String KEY_SESSION_ID = "session_id";

    /**
     * 存储用户名称
     */
    private static final String KEY_USER_NAME = "user_name";
    /**
     * 存储用户名称
     */
    private static final String KEY_USER_ACCOUNT = "user_account";
    /**
     * 存储用户编号名称
     */
    private static final String KEY_USER_ID = "user_id";
    /**
     * 性别
     */
    private static final String KEY_SEX = "sex";
    /**
     * roles
     */
    private static final String KEY_ROLES = "roles";
    /**
     * 存储用户地址
     */
    private static final String KEY_ADDRESS = "address";
    /**
     * 存储用户手机号码
     */
    private static final String KEY_MOBILE = "mobile";
    /**
     * 存储用户电话号码
     */
    private static final String KEY_TEL = "tel";
    /**
     * 存储用户所属施工队
     */
    private static final String KEY_CONSTRUCTION_TEAM = "contructionTeam";
    /**
     * 用户id
     */
    private int _userId = 0;
    /**
     * 用户账号
     */
    private String _account = null;
    /**
     * 用户姓名
     */
    private String _userName = null;
    /**
     * 0：男，1：女
     */
    private int _sex;
    /**
     * 用户地址
     */
    private String _address = null;
    /**
     * 用户手机号码
     */
    private String _mobile = null;
    /**
     * 用户电话号码
     */
    private String _tel = null;
    /**
     * 用户所属施工队
     */
    private String _constructionTeam = null;
    /**
     *
     */
    private String _sessionId = "-1";
    /**
     * 角色
     */
    private String _roles;

    private Context mContext;

    public UserSession(Context context) {
        mContext = context;
        readSharedPreferences();
    }

    public String getSessionId() {
        return _sessionId;
    }

    public int getUserId() {
        return _userId;
    }

    public void setUserId(int _userId) {
        this._userId = _userId;
    }

    public String getAccount() {
        return _account;
    }

    public void setAccount(String _account) {
        this._account = _account;
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String _userName) {
        this._userName = _userName;
    }

    public String getRoles() {
        return _roles;
    }

    public void setRoles(String _roles) {
        this._roles = _roles;
    }

    public int getSex() {
        return _sex;
    }

    public void setSex(int _sex) {
        this._sex = _sex;
    }

    public String getAddress() {
        return _address;
    }

    public void setAddress(String _address) {
        this._address = _address;
    }

    public String getMobile() {
        return _mobile;
    }

    public void setMobile(String _mobile) {
        this._mobile = _mobile;
    }

    public String getTel() {
        return _tel;
    }

    public void setTel(String _tel) {
        this._tel = _tel;
    }

    public String getConstructionTeam() {
        return _constructionTeam;
    }

    public void setConstructionTeam(String _constructionTeam) {
        this._constructionTeam = _constructionTeam;
    }

    private void readSharedPreferences() {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_SESSION, Context.MODE_PRIVATE);
        if (sp != null) {
            _sessionId = sp.getString(KEY_SESSION_ID, "-1");
            setUserId(sp.getInt(KEY_USER_ID, 0));
            setUserName(sp.getString(KEY_USER_NAME, TextUtil.EMPTY));
            setAccount(sp.getString(KEY_USER_ACCOUNT, TextUtil.EMPTY));
            setSex(sp.getInt(KEY_SEX, 0));
            setRoles(sp.getString(KEY_ROLES, TextUtil.EMPTY));
            setAddress(sp.getString(KEY_ADDRESS, TextUtil.EMPTY));
            setMobile(sp.getString(KEY_MOBILE, TextUtil.EMPTY));
            setTel(sp.getString(KEY_TEL, TextUtil.EMPTY));
            setConstructionTeam(sp.getString(KEY_CONSTRUCTION_TEAM, TextUtil.EMPTY));
        }
        if (_sessionId.equals("-1")) {
            _sessionId = UUID.randomUUID().toString();
        }
    }

    public boolean save() {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_SESSION_ID, this._sessionId);
        // 记录用户编号
        editor.putInt(KEY_USER_ID, _userId);
        // 记录用户名称
        editor.putString(KEY_USER_NAME, TextUtil.isNullOrEmpty(_userName) ? TextUtil.EMPTY : _userName);
        // 记录用户账号
        editor.putString(KEY_USER_ACCOUNT, TextUtil.isNullOrEmpty(_account) ? TextUtil.EMPTY : _account);
        //记录用户性别
        editor.putInt(KEY_SEX, _sex);
        //记录用户地址
        editor.putString(KEY_ADDRESS, TextUtil.isNullOrEmpty(_address) ? TextUtil.EMPTY : _address);
        //记录用户手机号码
        editor.putString(KEY_MOBILE, TextUtil.isNullOrEmpty(_mobile) ? TextUtil.EMPTY : _mobile);
        //记录用户电话号码
        editor.putString(KEY_TEL, TextUtil.isNullOrEmpty(_tel) ? TextUtil.EMPTY : _tel);
        //记录用户所属施工队
        editor.putString(KEY_CONSTRUCTION_TEAM, TextUtil.isNullOrEmpty(_constructionTeam) ? TextUtil.EMPTY : _constructionTeam);
        //记录用户权限
        editor.putString(KEY_ROLES, TextUtil.isNullOrEmpty(_roles) ? TextUtil.EMPTY : _roles);
        return editor.commit();
    }

    public void clearSessionId() {
        _sessionId = "-1";
    }

    public boolean clearUserSession() {
        SharedPreferences sp = mContext.getSharedPreferences(FILE_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (editor.clear().commit()) {
            readSharedPreferences();
            return true;
        }
        return false;
    }

}
