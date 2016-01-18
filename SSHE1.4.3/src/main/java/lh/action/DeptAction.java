package lh.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.base.action.BaseAction;
import lh.pageModel.Dept;
import lh.service.DeptServiceI;
import lh.util.DataGrid;
import lh.util.Json;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
@ParentPackage("basePackage")
@Namespace("/")
@Action("deptAction")
public class DeptAction extends BaseAction<Dept> {
	private static final long serialVersionUID = 7748717743682412133L;
	private static final Logger logger = Logger.getLogger(DeptAction.class);
	@Resource
	private DeptServiceI deptService ;
	/**
	 * 获得部门的TreeGrid数据
	 */
	public void getTreeGrid(){
		Map<String,Object> params = new HashMap<String, Object>();
		String hql = " from Tdept ";
		String hqlCount = "select count(*) " + hql;
		List<Dept> depts = deptService.getTreeGrid();
		DataGrid datagrid = new DataGrid();
		datagrid.setTotal(deptService.findCount(hqlCount, params));
		datagrid.setRows(depts);
		Map<String,Object> treegridmap = new HashMap<String, Object>();
		treegridmap.put("rows", datagrid.getRows());
		System.out.println("===");
		System.out.println(datagrid);
		this.writeJson(datagrid);
	}
	/**
	 * 获得所有部门tree
	 */
	public void getAllTreeNode(){
		this.writeJson(deptService.getAllTreeNode());
	}
	public void delete(){
		Json json = new Json();
		try{
			int i = deptService.delete(this.model.getIds());
			json.setSuccess(true);
			json.setMsg("删除"+i+"个部门成功！");
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除部门失败"+e.getMessage());
		}finally{
			writeJson(json);
		}
	}
	public void edit(){
		Json json = new Json();
		try{
			Dept dept = deptService.update(this.model);
			json.setSuccess(true);
			json.setMsg("修改部门成功");
			json.setObj(dept);
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("修改部门失败"+e.getMessage());
		}finally{
			writeJson(json);
		}
	}
	public void add(){
		Json json = new Json();
		try{
			Dept dept = deptService.save(this.model);
			json.setSuccess(true);
			json.setMsg("添加部门成功");
			json.setObj(dept);
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("添加部门失败"+e.getMessage());
		}finally{
			writeJson(json);
		}
	}
}
