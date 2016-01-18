package lh.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.base.dao.BaseDaoI;
import lh.entity.Tuser;
import lh.util.RemoveFile;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz;

	public BaseDaoImpl() {
		/**
		 * 重点关注 重点理解
		 */
		// 使用反射技术
		// 获得当前T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();// 获取当前new的对象的泛型的父类类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];// 获得第一个类型参数的真实类型
		// System.out.println(this.clazz);
		// this.clazz = (Class<T>) this.getClass();
		// System.out.println(this.clazz);
	}

	/**
	 * 获取当前可用的Session对象
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Serializable save(T entity) {
		return getSession().save(entity);
	}
	/**
	 * 根据id传入的字符串，如果多个id用半角,隔开，删除多个实体
	 */
	@Override
	public void delete(String id) {
		//
		String hql = " from " + clazz.getSimpleName() + " where id in (:id)";
		String[] ids = id.split(",");
		List<T> Ts = getSession().createQuery(hql).setParameterList("id", ids)
				.list();
		for (T t : Ts) {
			getSession().delete(t);
		}
		//
		// Object obj = getById(id);
		// if (obj != null) {
		// getSession().delete(obj);
		// }
	}

	@Override
	public void update(T entity) {
//		getSession().update(entity);
		getSession().merge(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(int page, int rows, String sort, String order) {
		// 拼接hql
		String hql = "from " + clazz.getSimpleName() + " ";

		if (sort != null && order != null) {
			hql += "Order by " + sort + " " + order;
		}
		// 使用分页
		return getSession().createQuery(//
				hql).setFirstResult((page - 1) * rows)//
				.setMaxResults(rows).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(String id) {
		if (id == null) {
			return null;
		} else {
			return (T) getSession().get(clazz, id);
		}
	}

	@Override
	public List<T> getByIds(String[] ids) {
		if (ids == null || ids.length == 0) {
			// 此处不要返回null，如果返回null值会在使用中报空指针异常
			// 此处返回了空的List列表
			return Collections.EMPTY_LIST;
		} else {
			return getSession().createQuery(//
					// "from "+clazz.getName()+" where id in (:ids)")//这句代码和下一句功能好像一样
					"from " + clazz.getSimpleName() + " where id in (:ids)")
					.setParameterList("ids", ids)//
					.list();
		}
	}

	@Override
	public Long findCount(T entity) {
		return (Long) getSession().createQuery(
				"select count(*) from " + clazz.getSimpleName()).uniqueResult();
	}

	@Override
	public T get(String hql) {
		List<T> l = getSession().createQuery(hql).list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public T get(String hql, Object[] params) {
		Query q = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getSession().saveOrUpdate(entity);
	}

	@Override
	public List<T> find(String hql) {
		List<T> l = getSession().createQuery(hql).list();
		if (l != null && l.size() > 0) {
			return l;
		}
		return null;
	}

	@Override
	public List<T> find(String hql, Object[] params) {
		Query q = getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				q.setParameter(i, params[i]);
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l;
		}
		return null;
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l;
		}
		return null;
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page,
			int rows, String sort, String order) {
		if (sort != null && order != null) {
			hql += " Order by " + sort + " " + order;
		}
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.setFirstResult((page - 1) * rows)//
				.setMaxResults(rows).list();
		if (l != null && l.size() > 0) {
			return l;
		}
		return null;
	}

	@Override
	public Long findCount(String hql) {
		return (Long) getSession().createQuery(hql).uniqueResult();
	}

	@Override
	public Long findCount(String hql, Map<String, Object> params) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql,Map<String,Object> params) {
		Query q = getSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				if (params.get(key) instanceof Object[]){
					Object[] arrays = (Object[]) params.get(key);
					q.setParameterList(key,arrays);
				}else{
					q.setParameter(key, params.get(key));
				}
			}
		}
		return q.executeUpdate();
	}
}
