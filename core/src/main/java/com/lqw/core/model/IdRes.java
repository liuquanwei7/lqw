package com.lqw.core.model;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class IdRes {
	public Long id;

	public IdRes() {}

	public IdRes(long id) {
		this.id = Long.valueOf(id);
	}

	public static IdRes valueOf(long id) {
		return new IdRes(id);
	}

	public static IdRes valueOf(IdReq req) {
		return new IdRes(req.getId().longValue());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
