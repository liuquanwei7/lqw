package com.lqw.core.db;

import com.lqw.core.pool.IEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public class HibernateAccessor {
	static final Logger logger = LoggerFactory.getLogger(HibernateAccessor.class);

	@Autowired
	private EntityManager entityManager;

	@Transactional
	public void add(Object entity) {
		this.entityManager.persist(entity);
	}

	@Transactional
	public void addBatch(List entityList) {
		for (Object entity : entityList) {
			this.entityManager.persist(entity);
		}
	}

	@Transactional
	public void update(Object entity) {
		this.entityManager.merge(entity);
	}

	@Transactional
	public void updateBatch(List entityList) {
		for (Object entity : entityList) {
			this.entityManager.merge(entity);
		}
	}

	@Transactional
	public <K extends java.io.Serializable, T extends IEntity<K>> void delete(T entity) {
		if (entity == null) {
			return;
		}
		this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));
	}

	@Transactional
	public <K extends java.io.Serializable, T extends IEntity<K>> void delete(Class<T> entityClass, K id) {
		IEntity iEntity = (IEntity)this.entityManager.find(entityClass, id);
		this.entityManager.remove(iEntity);
	}

	public <K extends java.io.Serializable, T extends IEntity<K>> T get(Class<T> entityClass, K id) {
		return (T)this.entityManager.find(entityClass, id);
	}

	public <T> List<T> list(Class<T> entityClass, String sql) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql, entityClass);
		return nativeQuery.getResultList();
	}

	public <T> List<T> list(Class<T> entityClass, String sql, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql, entityClass);
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return nativeQuery.getResultList();
	}

	public <T> List<T> list(String sql, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql);
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return nativeQuery.getResultList();
	}

	public <T> List<T> listPage(Class<T> entityClass, String sql, int curPage, int pageSize, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql, entityClass);
		nativeQuery.setFirstResult((curPage - 1) * pageSize);
		nativeQuery.setMaxResults(pageSize);
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return nativeQuery.getResultList();
	}

	public List listPage(String sql, int curPage, int pageSize, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql);
		nativeQuery.setFirstResult((curPage - 1) * pageSize);
		nativeQuery.setMaxResults(pageSize);
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return nativeQuery.getResultList();
	}

	public int count(String sql, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql);
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return ((BigInteger)nativeQuery.getSingleResult()).intValue();
	}

	public int counts(String sql, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery("select count(*) from (" + sql + ") t");
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return ((BigInteger)nativeQuery.getSingleResult()).intValue();
	}

	public <T> T unique(String sql, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql);
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		try {
			return (T)nativeQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public <T> T unique(Class<T> entityClass, String sql, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql, entityClass);
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		try {
			return (T)nativeQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public Integer executeUpdate(String sql, Map<String, Object> paramters) {
		Query nativeQuery = this.entityManager.createNativeQuery(sql);
		if (paramters != null) {
			for (Map.Entry<String, Object> entry : paramters.entrySet()) {
				nativeQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return Integer.valueOf(nativeQuery.executeUpdate());
	}
}
