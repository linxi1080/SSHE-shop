package lh.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.base.dao.BaseDaoI;
import lh.base.service.BaseServiceI;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础业务逻辑
 * 
 * @author 孙宇
 * 
 * @param <T>
 */
//@Service("baseService")
//由于使用spring管理事务并且配置了该类对应的包所以可以不用事务注解
//@Transactional
public class BaseServiceImpl<T> implements BaseServiceI<T> {

//	@Autowired
//	private BaseDaoI<T> baseDao;
//	@Resource
	private BaseDaoI<T> baseDao;
	/** 
     * 注入BaseDao 
     */  
    public BaseDaoI<T> getBaseDao() {
    	return baseDao;
    }
    
    @Resource 
    public void setBaseDao(BaseDaoI<T> baseDao) {
    	this.baseDao = baseDao;
    }
//	private Class<T> clazz;
//    
//	public BaseServiceImpl() {
//		/**
//		 * 重点关注 重点理解
//		 */
//		// 使用反射技术
//		// 获得当前T的真实类型
//		ParameterizedType pt = (ParameterizedType) this.getClass()
//				.getGenericSuperclass();// 获取当前new的对象的泛型的父类类型
//		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];// 获得第一个类型参数的真实类型
//	}

	@Override
	public Serializable save(T o) {
		return baseDao.save(o);
	}

	@Override
	public int delete(String id) {
		String[] arrStrings = id.split(",");
		baseDao.delete(id);
		return arrStrings.length;		
	}

	@Override
	public T update(T o) {
		baseDao.update(o);
		return o;
	}

	@Override
	public void saveOrUpdate(T o) {
		baseDao.saveOrUpdate(o);
	}

	@Override
	public T getById(Serializable id) {
//		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return baseDao.getById((String) id);
	}

	@Override
	public List<T> findAll(int page, int rows, String sort, String order) {
		List<T> lists = baseDao.findAll(page, rows, sort, order);
		if(lists != null && lists.size() > 0){
//			for(T t :lists){
//				T r = null ;
//				BeanUtils.copyProperties(t,r);
//				lists.add(r);
//			}
			return lists;
		}
		return null;
	}

	@Override
	public T getById(String id) {
		return baseDao.getById(id);
	}

	@Override
	public List<T> getByIds(String[] ids) {
		List<T> lists = baseDao.getByIds(ids);
		if(lists != null && lists.size() > 0){
//			for(T t :lists){
//				T r = null ;
//				BeanUtils.copyProperties(t,r);
//				lists.add(r);
//			}
			return lists;
		}
		return null;
	}

	@Override
	public T get(String hql) {
		T t = baseDao.get(hql);
		return t;
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		T t = baseDao.get(hql, params);
		return t;
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page,
			int rows, String sort, String order) {
		return baseDao.find(hql, params, page, rows, sort, order);
	}

	@Override
	public List<T> find(String hql) {
		return baseDao.find(hql);
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page,
			int rows) {
		return null;
	}

	@Override
	public List<T> find(int page, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long findCount(T o) {
		return baseDao.findCount(o);
	}

	@Override
	public Long findCount(String hql) {
		return baseDao.findCount(hql);
	}

	@Override
	public Long findCount(String hql, Map<String, Object> params) {
		return baseDao.findCount(hql, params);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		return baseDao.executeHql(hql, params);
	}

}
