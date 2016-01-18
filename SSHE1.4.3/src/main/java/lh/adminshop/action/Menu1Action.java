package lh.adminshop.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.base.action.BaseAction;
import lh.entity.Tuser;
import lh.pageModel.Menu;
import lh.pageModel.Role;
import lh.pageModel.SessionInfo;
import lh.service.MenuServiceI;
import lh.service.RoleServiceI;
import lh.util.ConfigUtil;
import lh.util.DataGrid;
import lh.util.Json;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
@ParentPackage("admin")
@Namespace("/admin/setting")
@Action(value = "menu1Action")
public class Menu1Action extends BaseAction<Menu> {
	private static final Logger logger = Logger.getLogger(Menu1Action.class);

	private static final long serialVersionUID = 2073651762408241450L;
	
	@Resource
	private MenuServiceI menuService;
	@Resource
	private RoleServiceI roleService;
	/**
	 * 异步获取菜单树节点
	 */
	public void getTreeNode(){
		this.writeJson(menuService.getTreeNode(model.getId()));
	}
	
	public void doNotNeedGrant_getAllTreeNode(){
		this.writeJson(menuService.getAllTreeNode());
	}
	public void doNotNeedGrant_getTreeNodeByUser(){
//		SessionInfo sessionInfo = new SessionInfo();
//		getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
		Tuser tuser = ((SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ConfigUtil.getFrontendSessionInfoName())).getUser();
		String name = tuser.getName();
		this.writeJson(menuService.getTreeNodeByUser(name,model.getId()));
	}
	/**
	 * 获得资源菜单的TreeGrid数据
	 */
	public void getTreeGrid(){
		Map<String,Object> params = new HashMap<String, Object>();
		String hql = " from Tmenu ";
		String hqlCount = "select count(*) " + hql;
		List<Menu> menus = menuService.getTreeGrid();
		DataGrid datagrid = new DataGrid();
		datagrid.setTotal(menuService.findCount(hqlCount, params));
		datagrid.setRows(menus);
		Map<String,Object> treegridmap = new HashMap<String, Object>();
		treegridmap.put("rows", datagrid.getRows());
		this.writeJson(datagrid);
	}
	/**
	 * 增加资源
	 */
	public void add(){
		Json json = new Json();
		try{
			Menu r = menuService.save(model);
			json.setSuccess(true);
			json.setMsg("保存资源成功");
			json.setObj(r);
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("保存资源失败");
		}finally{
			writeJson(json);
		}
	}
	/**
	 * 查询记录
	 */
	public void find(){
		Map<String,Object> params = new HashMap<String, Object>();
		String hql = " from Tmenu ";
		String hqlWhere = addWhere(params);
		hql += hqlWhere;
		String hqlCount = "select count(*) " + hql;
		
		List<Menu> menus = menuService.find(hql, params, page, rows, sort, order);
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(menuService.findCount(hqlCount, params));
		dataGrid.setRows(menus);
		writeJson(dataGrid);
	}
	/**
	 * 添加条件
	 * @param params
	 * @return
	 */
	private String addWhere(Map<String, Object> params) {
		String hqlWhere = " where 1=1 ";
		if(this.model.getText() == null || this.model.getText().trim().equals("")){
			
		}else{
			hqlWhere += " and name like :name";
			params.put("name","%%"+this.model.getText().trim()+"%%");
		}
		return hqlWhere;
	}
	public void delete(){
		Json json = new Json();
		try{
			int i = menuService.executeHql(this.model.getIds());
			json.setSuccess(true);
			json.setMsg("删除【"+i+"】资源成功");
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除资源失败"+e.getMessage());
		}finally{
			writeJson(json);
		}
	}
	/**
	 * 修改角色
	 */
	public void edit(){
		Json json = new Json();
		try {
			Menu m = menuService.update(model);
			json.setSuccess(true);
			json.setMsg("修改资源成功");
			json.setObj(m);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("修改资源失败");
		}finally{
			writeJson(json);
		}
	}
	/**
	 * 获得当前角色的资源
	 */
	public void doNotNeedGrant_getRoleByRoleId(){
//		logger.info("doNotNeedGrant_getRoleByUserId");
//		logger.info(model.getId());
		Role role = roleService.getById(model.getId());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("menuIds",role.getMenuIds().split(","));
		map.put("menuTexts", role.getMenuTexts().split(","));
		writeJson(map);
	}
}
