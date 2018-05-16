/**
 * @author qiweiwei
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONObject;

/**
 * 登录结果信息实体
 */
public class LoginResultEntity {
	/**
	 * 是否成功
	 */
	private boolean _successed;
	/**
	 * 返回码
	 */
	private int _error;

	/**
	 * 用户编号
	 */
	private int _UserID;

	public int get_UserID() {
		return _UserID;
	}

	public void set_UserID(int _UserID) {
		this._UserID = _UserID;
	}

	/**
	 * @return the successed
	 */
	public boolean isSuccessed() {
		return _successed;
	}

	/**
	 * @param successed
	 *            the successed to set
	 */
	public void setSuccessed(boolean successed) {
		_successed = successed;
	}

	/**
	 * @return the error
	 */
	public int getError() {
		return _error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(int error) {
		_error = error;
	}

	public static LoginResultEntity fromJSON(JSONObject object) {
		LoginResultEntity result = new LoginResultEntity();

		result.setSuccessed(object.optBoolean("success"));
		result.setError(object.optInt("error"));
		result.set_UserID(object.optInt("userID"));

		return result;

	}

}
