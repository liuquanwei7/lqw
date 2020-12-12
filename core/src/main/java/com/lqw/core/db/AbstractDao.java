package com.lqw.core.db;

import com.google.common.collect.Maps;
import com.lqw.core.pool.IEntity;
import com.lqw.core.model.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public abstract class AbstractDao<K extends Serializable, T extends IEntity<K>> {
	
	@Autowired
	protected HibernateAccessor accessor;

	protected Class<T> clazz;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T get(K id) {
		return this.accessor.get(this.clazz, id);
	}

	public void add(T entity) {
		this.accessor.add(entity);
	}

	public void update(T entity) {
		this.accessor.update(entity);
	}

	public void delete(T entity) {
		this.accessor.delete(entity);
	}

	public int count() {
		return this.accessor.count("select count(*) from " + this.clazz.getSimpleName(), Maps.newHashMap());
	}

	public List<T> list(String sql, Map<String, Object> paramters) {
		return this.accessor.list(this.clazz, sql, paramters);
	}

	public Page<T> listPage(int curPage, int pageSize, String sql, Map<String, Object> paramters) {
		int totalSize = this.accessor.counts(sql, paramters);
		List<T> dataList = this.accessor.listPage(this.clazz, sql, curPage, pageSize, paramters);
		return Page.valueOf(totalSize, pageSize, curPage, dataList);
	}
}
