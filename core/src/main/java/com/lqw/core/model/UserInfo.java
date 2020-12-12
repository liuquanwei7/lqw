package com.lqw.core.model;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class UserInfo {

	private String account;

	private long userId;

	private long accountId;

	private Long yuangongId;

	private String yuangongName;

	private String userName;

	private String userType;

	private Object authority_list;

	private Object zixitong_list;

	private boolean xuyaobangdingzhanghao;

	private boolean diyiciDenglu;

	public UserInfo(String account, long userId, long accountId, String userName, String userType, Object authority_list) {
		this.account = account;
		this.userId = userId;
		this.accountId = accountId;
		this.userName = userName;
		this.userType = userType;
		this.authority_list = authority_list;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Object getAuthority_list() {
		return this.authority_list;
	}

	public void setAuthority_list(Object authority_list) {
		this.authority_list = authority_list;
	}

	public Long getYuangongId() {
		return this.yuangongId;
	}

	public void setYuangongId(Long yuangongId) {
		this.yuangongId = yuangongId;
	}

	public String getYuangongName() {
		return this.yuangongName;
	}

	public void setYuangongName(String yuangongName) {
		this.yuangongName = yuangongName;
	}

	public Object getZixitong_list() {
		return this.zixitong_list;
	}

	public void setZixitong_list(Object zixitong_list) {
		this.zixitong_list = zixitong_list;
	}

	public boolean isDiyiciDenglu() {
		return this.diyiciDenglu;
	}

	public void setDiyiciDenglu(boolean diyiciDenglu) {
		this.diyiciDenglu = diyiciDenglu;
	}

	public boolean isXuyaobangdingzhanghao() {
		return this.xuyaobangdingzhanghao;
	}

	public void setXuyaobangdingzhanghao(boolean xuyaobangdingzhanghao) {
		this.xuyaobangdingzhanghao = xuyaobangdingzhanghao;
	}
}
