package lh.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lh.entity.Tuser;
import lh.pageModel.User;


public interface UserServiceI {
	public void test();
	public Serializable save(User user);
	public User add(User user);
	public User login(User user);
	public List<User> find(String hql, Map<String, Object> dataGridJson, int page,
			int rows,String sort,String order);
	public long findCount(String hql,Map<String, Object> params);
	public long findCount(User tuser);
	public int delete(String ids);
	public int executeHql(String ids);
	public User update(User user);
	public User getById(String id);
	public Tuser getTuserById(String id);
	public Long countUserByRoleId(String id);
	public Long countUserByNotRoleId();
	public User grantRole(String id, String trolesId);
	public List<Long> userCreateDatetimeChart();
	
}
