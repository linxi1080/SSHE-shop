package lh.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.base.BaseLog;
import lh.base.action.BaseAction;
import lh.pageModel.Role;
import lh.pageModel.SessionInfo;
import lh.pageModel.TreeJson;
import lh.pageModel.User;
import lh.service.RoleServiceI;
import lh.service.UserServiceI;
import lh.util.ConfigUtil;
import lh.util.DataGrid;
import lh.util.Json;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

@ParentPackage("basePackage")
@Namespace("/")
@Action(value="roleAction")
public class RoleAction extends BaseAction<Role> {
	private static final Logger logger = Logger.getLogger(RoleAction.class);

	private static final long serialVersionUID = 4941629536933423543L;
	
	private RoleServiceI roleService;

	public RoleServiceI getRoleService() {
		return roleService;
	}
	@Autowired
	public void setRoleService(RoleServiceI roleService) {
		this.roleService = roleService;
	}
	@Resource
	private UserServiceI userService;
	public void test(){
		logger.info("test");
	}
	/**
	 * 增加角色
	 */
	public void addRole(){
		Json json = new Json();
		try{
			Role r = roleService.save(model);
			json.setSuccess(true);
			json.setMsg("保存角色成功");
			json.setObj(r);
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("保存角色失败");
		}finally{
			writeJson(json);
		}
	}
	/**
	 * 查询记录
	 */
	public void find(){
		Map<String,Object> params = new HashMap<String, Object>();
		String hql = " from Trole ";
		String hqlWhere = addWhere(params);
		hql += hqlWhere;
		String hqlCount = "select count(*) " + hql;
		
		List<Role> roles = roleService.find(hql, params, page, rows, sort, order);
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(roleService.findCount(hqlCount, params));
		dataGrid.setRows(roles);
		writeJson(dataGrid);
	}
	/**
	 * 添加条件
	 * @param params
	 * @return
	 */
	private String addWhere(Map<String, Object> params) {
		String hqlWhere = " where 1=1 ";
		if(this.model.getName() == null || this.model.getName().trim().equals("")){
			
		}else{
			hqlWhere += " and name like :name";
			params.put("name","%%"+this.model.getName().trim()+"%%");
		}
		return hqlWhere;
	}
	public void delete(){
		Json json = new Json();
		try{
			int i = roleService.executeHql(this.model.getIds());
			json.setSuccess(true);
			json.setMsg("删除【"+i+"】角色成功");
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除角色失败"+e.getMessage());
		}finally{
			writeJson(json);
		}
	}
	/**
	 * 修改角色
	 */
	public void editRole(){
		Json json = new Json();
		try {
			Role r = roleService.update(model);
			json.setSuccess(true);
			json.setMsg("修改角色成功");
			json.setObj(r);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("修改角色失败");
		}finally{
			writeJson(json);
		}
	}
	/**
	 * 获得当前用户能看到的所有角色树
	 */
	public void doNotNeedGrant_getRolesTree(){
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getFrontendSessionInfoName());
		User user = userService.getById(sessionInfo.getUser().getId());
		this.writeJson(roleService.getAllTreeNode());
	}
	/**
	 * 获得当前用户的角色
	 */
	public void doNotNeedGrant_getRoleByUserId(){
		logger.info("doNotNeedGrant_getRoleByUserId");
		logger.info(model.getId());
		User user = userService.getById(model.getId());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("troleIds",user.getTrolesId().split(","));
		map.put("trolesName", user.getTrolesName().split(","));
		writeJson(map);
	}
	/**
	 * 用户角色分布报表
	 */
	public void doNotNeedGrant_userRoleChart() {
		List<Role> roles = roleService.find();
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (Role role : roles) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", role.getName());
			m.put("y", userService.countUserByRoleId(role.getId()));
			m.put("sliced", false);
			m.put("selected", false);
			l.add(m);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		Long notRoleCount = userService.countUserByNotRoleId();
		if (notRoleCount > 0){
			m.put("name", "无");
			m.put("y", userService.countUserByNotRoleId());
			m.put("sliced", true);
			m.put("selected", true);
			l.add(m);	
		}
		writeJson(l);
	}
	/**
	 * 
	 */
	public void grantResource(){
		Json j = new Json();
		try {
			Role r = roleService.grantResource(model.getId(),model.getMenuIds());
			j.setSuccess(true);
			j.setMsg("修改用户角色成功！");
			j.setObj(r);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("修改用户角色失败！" + e.getMessage());
		}finally{
			writeJson(j);
		}
	}
}
