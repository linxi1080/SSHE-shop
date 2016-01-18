package lh.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDaoI<T> {
	/**
	 * 保存实体
	 * @param entity
	 */
//	void save(T entity);
	public Serializable save(T entity);
	/**
	 * 删除实体
	 * 根据id传入的字符串，如果多个id用半角,隔开，删除多个实体
	 * @param id
	 */
	public void delete(String id);
	/**
	 * 修改实体
	 * @param entity
	 */
	public void update(T entity);
	/**
	 * 保存或更新实体
	 * @param entity
	 */
	public void saveOrUpdate(T entity);
	/**
	 * 记录数
	 * @param entity
	 * @return
	 */
	public Long findCount(T entity);
	/**
	 * 查询记录数，无参数
	 * @param hql hql语句
	 * @return
	 */
	public Long findCount(String hql);
	/**
	 * 查询记录数，有参数
	 * @param hql hql语句
	 * @param params :参数名格式的参数，用Map键值对传入
	 * @return
	 */
	public Long findCount(String hql, Map<String,Object> params);
	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<T> findAll(int page, int rows,String sort,String order);
	/**
	 * 根据id获得实体
	 * @param id
	 * @return
	 */
	public T getById(String id);
	/**
	 * 根据实体数组获得实体对应的List
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(String[] ids);
	/**
	 * 获得一个实体，只传递hql，没有参数
	 * @param hql
	 * @return
	 */
	public T get(String hql);
	/**
	 * 获得一个实体，用?做占位符传参数
	 * @param hql
	 * @param params
	 * @return
	 */
	public T get(String hql,Object[] params);
	/**
	 * 获得一个实体，用:参数名的方式传参数，hibernate推荐的使用方法
	 * @param hql
	 * @param params
	 * @return
	 */
	public T get(String hql,Map<String,Object> params);
	/**
	 * 查询实体，只有hql一个参数，无分页
	 * @param hql
	 * @return
	 */
	public List<T> find(String hql);
	/**
	 * 查询实体，用?做占位符传参数，无分页
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> find(String hql,Object[] params);
	/**
	 * 查询实体，用:参数名的方式传参数，hibernate推荐的使用方法，无分页
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> find(String hql,Map<String,Object> params);
	/**
	 * 查询实体，用:参数名的方式传参数，hibernate推荐的使用方法，有分页
	 * @param hql hql语句
	 * @param params Map类型参数，使用键值对的形式传递参数
	 * @param page 开始页
	 * @param rows 每页行数
	 * @param sort 排序属性名称 
	 * @param order 排序规则 asc、desc 由于传入了hql语句可以把排序用hql传入，所以可以不用在这里另外做sort、order参数
	 * @return
	 */
	public List<T> find(String hql,Map<String,Object> params,int page, int rows,String sort,String order);
	/**
	 * 直接执行HQL语句
	 * @param hql
	 */
	public int executeHql(String hql,Map<String,Object> params);
}
