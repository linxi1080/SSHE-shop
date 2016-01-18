package lh.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lh.entity.Tonline;
/**
 * 
 * @author 孙宇
 * 
 */
public interface OnlineServiceI {
	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Tonline> findAll(int page, int rows,String sort,String order);
	/**
	 * 根据id获得实体
	 * @param id
	 * @return
	 */
	public Tonline getById(String id);
	/**
	 * 根据实体数组获得实体对应的List
	 * @param ids
	 * @return
	 */
	public List<Tonline> getByIds(String[] ids);
	/**
	 * 获得一个实体，只传递hql，没有参数
	 * @param hql
	 * @return
	 */
	public Tonline get(String hql);
	/**
	 * 获得一个实体，用:参数名的方式传参数，hibernate推荐的使用方法
	 * @param hql
	 * @param params
	 * @return
	 */
	public Tonline get(String hql,Map<String,Object> params);
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
	public List<Tonline> find(String hql,Map<String,Object> params,int page, int rows,String sort,String order);
	//////////////////
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable save(Tonline o);

	/**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public int delete(String id);
	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public Tonline update(Tonline o);

	/**
	 * 保存或更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void saveOrUpdate(Tonline o);

	/**
	 * 通过主键获得对象
	 * 
	 * @param c
	 *            类名.class
	 * @param id
	 *            主键
	 * @return 对象
	 */
	public Tonline getById(Serializable id);
	/**
	 * 查询实体，只有hql一个参数，无分页
	 * @param hql
	 * @return List
	 */
	public List<Tonline> find(String hql);
	/**
	 * 查询实体，用:参数名的方式传参数，hibernate推荐的使用方法，无分页
	 * @param hql
	 * @param params
	 * @return List
	 */
	public List<Tonline> find(String hql, Map<String, Object> params);
	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public List<Tonline> find(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 获得分页后的对象列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Tonline> find(int page, int rows);
	/**
	 * 记录数
	 * @param o
	 * @return
	 */
	public Long findCount(Tonline o);
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
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 响应结果数目
	 */
	public int executeHql(String hql, Map<String, Object> params);
	public List<Long> userCreateDatetimeChart();
}
