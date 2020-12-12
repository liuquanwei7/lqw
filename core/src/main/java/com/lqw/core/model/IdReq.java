package com.lqw.core.model;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class IdReq {
	public Long id;

	public IdReq() {}

	public IdReq(Long id) {
		this.id = id;
	}

	public static IdReq valueOf(Long id) {
		return new IdReq(id);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
