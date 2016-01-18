package lh.service;

import java.util.List;
import java.util.Map;

import lh.pageModel.Role;
import lh.pageModel.TreeJson;


public interface RoleServiceI {
	public Role save(Role e);
	public List<Role> find(String hql, Map<String, Object> dataGridJson, int page,
			int rows,String sort,String order);
	public long findCount(String hql,Map<String, Object> params);
	public long findCount(Role e);
	public int delete(String ids);
	public int executeHql(String ids);
	public Role update(Role e);
	public List<TreeJson> getAllTreeNode();
	public List<Role> find();
	public Role getById(String id);
	public Role grantResource(String id, String menuIds);
	
}
