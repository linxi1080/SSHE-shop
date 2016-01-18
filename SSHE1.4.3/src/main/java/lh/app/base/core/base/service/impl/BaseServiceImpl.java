package lh.app.base.core.base.service.impl;

import java.io.Serializable;
//import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;

import lh.app.base.core.base.service.BaseServiceI;
import lh.app.base.core.entity.TshopSite;
import lh.base.dao.BaseDaoI;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
/**
 * 基础业务逻辑
 * @author cocim-liuhui 2015-12-9下午7:13:28
 * @param <P> 页面model
 * @param <E> 实体model
 */
@Service("baseService")
@Lazy(value=true)//延迟初始化，防止自身实例化时，调用构造BaseServiceImpl方法出现object类型转换异常
public class BaseServiceImpl<P,E> implements BaseServiceI<P,E> {
	
	// 当前的T类型
	private Class pclazz=null;
	private Class eclazz=null;

//	@Resource   // baseDao与BaseService一样都是泛型信息,不能被实例化
	private BaseDaoI<E> baseDao;	// 但是下面需要用baseDao的CRUD方法. baseDao里面的对象不是固定, 可能 baseDao=accountDao, categoryDao,forderDao
	public BaseServiceImpl(){
//		System.out.println("1.当前子类对象:" + this);
//		System.out.println("2.获取当前this对象相应的类型:" + this.getClass());
//		System.out.println("3.获取当前类型的父类型信息(并不包括父类型参数信息):" + this.getClass().getSuperclass());
//		System.out.println("4.获取当前类型的父类型信息(包括父类型参数信息):" + this.getClass().getGenericSuperclass());
		ParameterizedType type=(ParameterizedType)this.getClass().getGenericSuperclass();
		pclazz=(Class)type.getActualTypeArguments()[0];
//		System.out.println("5."+pclazz);
		eclazz=(Class)type.getActualTypeArguments()[1];
//		System.out.println("6."+eclazz);
	}

		/**
		 * 这个方法会在构造函数和spring以来注入之后执行
		 * @Title: init
		 * @Description: TODO(通过反射来实例化baseDao)
		 * 这里使用了setBaseDao的方法实现了实例化baseDao,如果用上边的方法实例化Dao好像还需要在这个类里定义要用到的所有Dao，如果下边两行
		 * @Resource
		 * private SiteDaoI siteDao;
		 * @param @throws Exception 设定文件
		 * @return void 返回类型
		 */
		/*
		@PostConstruct
		public void init() throws Exception
		{
			System.out.println("7.BaseServiceImpl.init()");
			// 根据相应的clazz,吧相应  ****Dao 赋值给BaseDao即可
			// 1: 获取当前clazz的类型,然后获取相应的类名称
			String pclazzName = pclazz.getSimpleName();
			System.out.println("8."+pclazzName);
			// 2:Account===>account===>account+Dao  Category====>CategoryDao
			String pclazzDaoName = pclazzName.substring(0,1).toLowerCase() + pclazzName.substring(1) + "Dao";//toLowerCase首字母小写
			System.out.println("9."+pclazzDaoName);
			// 3: 通过clazzDaoName获取相应 Field字段    this.getClass().getSuperclass():获取到相应BaseServiceImpl
			Field pdaoNameField = null;
//			try {  
				pdaoNameField = this.getClass().getSuperclass().getDeclaredField(pclazzDaoName); 
				System.out.println("10."+pdaoNameField.getName());
//            } catch (Exception e) {  
//                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
//                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
//            }   
			Object pobject = pdaoNameField.get(this);
			System.out.println("11."+pobject);
			// 4: 获取baseDao 字段
			Field pbaseDaoNameField = this.getClass().getSuperclass().getDeclaredField("baseDao");
			pbaseDaoNameField.set(this, pobject);
			//EEEEEE
			String eclazzName = pclazz.getSimpleName();
			System.out.println("12."+eclazzName);
			String eclazzDaoName = eclazzName.substring(0,1).toLowerCase() + eclazzName.substring(1) + "Dao";//toLowerCase首字母小写
			System.out.println("13."+eclazzDaoName);
			Field edaoNameField = this.getClass().getSuperclass().getDeclaredField(eclazzDaoName);
			System.out.println("14."+edaoNameField.getName());
			Object eobject = edaoNameField.get(this);
			System.out.println("15."+eobject);
			// 4: 获取baseDao 字段
			Field ebaseDaoNameField = this.getClass().getSuperclass().getDeclaredField("baseDao");
			ebaseDaoNameField.set(this, pobject);
			
		}*/

	@Override
	public P save(P o) {
		E e = null;
		try {
			e = (E) this.eclazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		BeanUtils.copyProperties(o,e);
		baseDao.save(e);
		return o;
	}
	/**
	 * 这里可以传入一个id数组删除多个对象
	 */
	@Override
	public int delete(String id) {
		String[] arrStrings = id.split(",");
		baseDao.delete(id);
		return arrStrings.length;		
	}

	@Override
	public P update(P o) {
		E e = null;
		try {
			e = (E) this.eclazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		BeanUtils.copyProperties(o, e);
		baseDao.update(e);
		return o;
	}

	@Override
	public void saveOrUpdate(P o) {
		E e = null;
		try {
			e = (E) this.eclazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		BeanUtils.copyProperties(o, e);
		baseDao.saveOrUpdate(e);
	}

	@Override
	public E getById(Serializable id) {
//		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return (E) baseDao.getById((String) id);
	}

	@Override
	public List<P> findAll(int page, int rows, String sort, String order) {
		List<E> lists = baseDao.findAll(page, rows, sort, order);
		if(lists != null && lists.size() > 0){
			return entitys2PageModels(lists);
		}
		return null;
	}

	private List<P> entitys2PageModels(List<E> lists) {
		List<P>  plists = new ArrayList<P>();
		for(E e:lists){
			P p;
			p = entity2PageModel(e);
			plists.add(p);
		}
		return plists;
	}

	private P entity2PageModel(E e) {
		P p = null;
		try {
			p = (P) this.pclazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		BeanUtils.copyProperties(e, p);
		return p;
	}

	@Override
	public P getById(String id) {
		E e = baseDao.getById(id);
		P p = null;
		try {
			p = (P) this.pclazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		BeanUtils.copyProperties(e, p);
		return p;
	}

	@Override
	public List<P> getByIds(String[] ids) {
		List<E> lists = baseDao.getByIds(ids);
		if(lists != null && lists.size() > 0){
			return entitys2PageModels(lists);
		}
		return null;
	}

	@Override
	public P get(String hql) {
		E t = baseDao.get(hql);
		P p = null;
		try {
			p = (P) this.pclazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		BeanUtils.copyProperties(t, p);
		return p;
	}

	@Override
	public P get(String hql, Map<String, Object> params) {
		E t = baseDao.get(hql, params);
		P p = null;
		try {
			p = (P) this.pclazz.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		BeanUtils.copyProperties(t, p);
		return p;
	}

	@Override
	public List<P> find(String hql, Map<String, Object> params, int page,
			int rows, String sort, String order) {
		List<E> elists = baseDao.find(hql, params, page, rows, sort, order);
		if(elists != null && elists.size() > 0){
			return entitys2PageModels(elists);
		}
		return null;
	}

	@Override
	public List<P> find(String hql) {
		List<E> elists = baseDao.find(hql);
		if(elists != null && elists.size() > 0){
			return entitys2PageModels(elists);
		}
		return null;
	}

	@Override
	public List<P> find(String hql, Map<String, Object> params) {
		List<E> elists = baseDao.find(hql, params);
		if(elists != null && elists.size() > 0){
			return entitys2PageModels(elists);
		}
		return null;
	}

	@Override
	public List<P> find(String hql, Map<String, Object> params, int page,
			int rows) {
		List<E> elists = baseDao.find(hql, params, page, rows, "","");
		if(elists != null && elists.size() > 0){
			return entitys2PageModels(elists);
		}
		return null;
	}

	@Override
	public Long findCount(E o) {
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

	public BaseDaoI<E> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDaoI<E> baseDao) {
		this.baseDao = baseDao;
	}

}
