package com.lqw.core.security;

import com.lqw.core.model.UserInfo;

import java.util.Map;

public interface IAccountService {
	XinUserDetails findUserDetails(String paramString);

	default XinUserDetails findUserDetailsForWx(String name) {
		return null;
	}

	default XinUserDetails findUserDetailsForAqpt(String aqptid) {
		return null;
	}

	default String getOpenIdByYuangongId(long yuangongId) {
		return null;
	}

	default void bindOpenid(long id, String openid) {}

	default void unbindOpenid(long id) {}

	XinUserDetails loadUserDetails(long paramLong);

	UserInfo loadUserInfo(String paramString, long paramLong1, long paramLong2, long paramLong3);

	Map<String, Object> postLogin(long paramLong, String paramString);

	boolean isAdmin(long paramLong1, long paramLong2);

	default boolean isAdmin(long xitongZhanghaoId) {
		return false;
	}

	default boolean isXitongGuanliyuan(long pintaiId, long shiyanshiId) {
		return false;
	}

	default boolean isXitongGuanliyuan(long xitongZhanghaoId) {
		return false;
	}

	default void onIncorrectPassword(String account) {}

	boolean matchPassword(String paramString1, String paramString2);

	default void bindWeixinXcxId(long id, String openid, String unionId) {}

	default void unbindWeixinXcxId(long id) {}

	default XinUserDetails findPingtaiByOpenid(String name) {
		return null;
	}

	default XinUserDetails findUserDetailsForQq(String openid, String nickname) {
		return null;
	}

	default void bindQqWebOpenid(long id, String openid) {}

	default void unbindQqWebOpenid(long id) {}

	default XinUserDetails findUserDetailsByWxUnionid(String unionid, String openid, String nickName) {
		return null;
	}

	default XinUserDetails findUserDetailsByWxUnionid(String unionid, String openid, String phoneNumber, String nickName, String LOGIN_CODE) {
		return null;
	}

	default void bindWeixinWebId(long id, String openid, String unionId) {}

	default void unbindWeixinWebId(long id) {}
}
