package com.lqw.core.pool;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
@MappedSuperclass
public abstract class AbstractEntity<K extends Serializable> implements IEntity<K> {

	private static final long serialVersionUID = 8225035887303574136L;

	public static final String FIELD_DEL_FLAG = "del_flag";

	public static final String FIELD_ZHUTIDANWEI_ID = "zhutidanwei_id";

	public static final String FIELD_CREATE_DATE = "create_date";

	public static final String FIELD_UPDATE_DATE = "update_date";

	private String remark;

	private Date create_date;

	private Date update_date;

	@Column(name = "del_flag", columnDefinition = "bit default 0 ", nullable = false)
	private Boolean del_flag = Boolean.valueOf(false);

	private Long zhutidanwei_id;

	private Long tijiaoren_id;

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreate_date() {
		return this.create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getUpdate_date() {
		return this.update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public Boolean isDel_flag() {
		return this.del_flag;
	}

	public void setDel_flag(Boolean del_flag) {
		this.del_flag = del_flag;
	}

	public String getFeature_code() {
		return getClass().getName();
	}

	public Long getZhutidanwei_id() {
		return this.zhutidanwei_id;
	}

	public void setZhutidanwei_id(Long zhutidanwei_id) {
		this.zhutidanwei_id = zhutidanwei_id;
	}

	public Boolean getDel_flag() {
		return this.del_flag;
	}

	public Long getTijiaoren_id() {
		return this.tijiaoren_id;
	}

	public void setTijiaoren_id(Long tijiaoren_id) {
		this.tijiaoren_id = tijiaoren_id;
	}
}
