/**
 * @author liukaiyu
 */
package com.sh3h.serverprovider.rpc.service;

import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.serverprovider.entity.LoginInfoEntity;
import com.sh3h.serverprovider.entity.LoginResultEntity;
import com.sh3h.serverprovider.entity.UserInfoEntity;

import org.json.JSONObject;

/**
 * 用户管理服务
 */
public class UserApiService extends BaseApiService {

	private static final String URL = "User.ashx";

	private static final String METHOD_LOGIN = "Login";
	private static final String METHOD_LOGOUT = "Logout";
	private static final String METHOD_GET_USERINFO = "GetUserInfo";

	@Override
	public String getHandlerURL() {
		return UserApiService.URL;
	}

	/**
	 * 登陆
	 */
	public LoginResultEntity Login(LoginInfoEntity info) throws ApiException {
		JSONObject resp = null;
		try {
			resp = this.callJSONObject(UserApiService.METHOD_LOGIN,
					new Object[] { info.toJSON() });
		} catch (ApiException e) {
			LogUtil.e("API", "登陆方法调用失败", e);
			throw e;
		}

		LoginResultEntity result = null;
		if (resp != null) {
			result = LoginResultEntity.fromJSON(resp);
		}

		return result;
	}

	/**
	 * 登出
	 */
	public void Logout() throws ApiException {
		try {
			this.call(UserApiService.METHOD_LOGOUT);
		} catch (ApiException e) {
			LogUtil.e("API", "登出方法调用失败", e);
			throw e;
		}
	}

	/**
	 * 获取用户资料
	 * @param userId
	 * @return
	 */
	public UserInfoEntity getUserInfo(int userId) throws ApiException {
		JSONObject resp = null;
		try {
			resp = this.callJSONObject(UserApiService.METHOD_GET_USERINFO, userId);
		} catch (ApiException e) {
			LogUtil.e("API", "获取用户资料方法调用失败", e);
			throw e;
		}

		UserInfoEntity result = null;
		if (resp != null) {
			result = UserInfoEntity.fromJSON(resp);
		}

		return result;
	}
}
