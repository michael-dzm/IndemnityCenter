/**
 * @author qiweiwei
 *
 */
package com.sh3h.serverprovider.entity;

import org.json.JSONObject;

public class UserInfoEntity {
	// / <summary>
	// / 用户编号
	// / </summary>
	private int UserId;

	// / <summary>
	// / 用户名
	// / </summary>
	private String UserName;

	//用户性别
	// 0：男，1：女
	private int Sex;

	// / <summary>
	// / 简单账号
	// / </summary>
	private String Account;

	// / <summary>
	// / 地址
	// / </summary>
	private String Address;

	// / <summary>
	// / 手机
	// / </summary>
	private String Mobile;

	// / <summary>
	// / 固定电话
	// / </summary>
	private String Tel;


	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public int getSex() {
		return Sex;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public static UserInfoEntity fromJSON(JSONObject resp) {
		UserInfoEntity userinfo = new UserInfoEntity();

		userinfo.setUserId(resp.optInt("userId"));
		userinfo.setUserName(resp.optString("userName"));
		userinfo.setSex(resp.optInt("sex"));
		userinfo.setAccount(resp.optString("account"));
		userinfo.setAddress(resp.optString("address"));
		userinfo.setMobile(resp.optString("mobile"));
		userinfo.setTel(resp.optString("tel"));

		return userinfo;
	}

}
