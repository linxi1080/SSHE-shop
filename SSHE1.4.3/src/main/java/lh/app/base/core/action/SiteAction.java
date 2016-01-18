package lh.app.base.core.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.app.base.core.pageModel.Site;
import lh.app.base.core.service.SiteServiceI;
import lh.base.action.BaseAction;
import lh.util.ConfigUtil;
import lh.util.DataGrid;
import lh.util.Json;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
@ParentPackage("public")
@Namespace("/common")
@Action(value = "siteAction")
//@Action(value = "login",results = {@Result(name="success",type="freemarker",location="/WEB-INF/html/welcome.html"),
//        @Result(name="login",type="freemarker",location="/WEB-INF/jsp/welcome.jsp")})
public class SiteAction extends BaseAction<Site> {
	private static final long serialVersionUID = 452521374928356726L;
    
	String ctx;
	@Resource
	private SiteServiceI siteService;
	public void find(){
		Map<String,Object> params = new HashMap<String, Object>();
		String hql = " from TshopSite t ";
		
		String hqlWhere = addWhere(params);
		
		hql += hqlWhere;
		String hqlCount = "select count(*) " + hql;
		
		List<Site> sites = new ArrayList<Site>();
//		users = siteService.find(hql,params,this.model.getPage(),this.model.getRows(),this.sort,this.order);
		sites = siteService.find(hql);
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(siteService.findCount(hqlCount,params));
		dataGrid.setRows(sites);
		writeJson(dataGrid);
	}

	private String addWhere(Map<String, Object> params) {
		String hqlWhere = " where 1=1 ";
//		if (this.model.getDate_from() != null && this.model.getDate_to() != null ){
//			hqlWhere += " and t.createdatetime >= :date_from and t.createdatetime <= :date_to"; 
//			params.put("date_from",this.model.getDate_from());
//			params.put("date_to", this.model.getDate_to());
//		}
//		if(this.model.getName() == null || this.model.getName().trim().equals("")){
//			
//		}else{
//			hqlWhere += " and t.name=:name";
//			params.put("name",this.model.getName().trim());
//		}
		return hqlWhere;
	}
	public void getCurrentSite(){
		String hql = " from TshopSite t ";
		List<Site> sites = new ArrayList<Site>();
		sites = siteService.find(hql);
		//
		ctx = getRequest().getContextPath();
		if ("/".equals(ctx)) {
			ctx = "";
		}
		//
		if (sites != null){
			getSession().setAttribute(ConfigUtil.get("siteSessionInfoName"),sites.get(0));
			Json json = new Json();
			json.setSuccess(true);
			json.setMsg("商城信息！"+sites.get(0).getSitename());
			writeJson(json);
		}else{
			Json json = new Json();
			json.setSuccess(false);
			json.setMsg("没有商城信息！");
			writeJson(json);
		}
	}
	public void edit(){
		Json json = new Json();
		try{
			this.model = siteService.getById("1");
			this.model.setSitename("辉红商城2");
			Site site = siteService.update(this.model);
			json.setSuccess(true);
			json.setMsg("修改商城信息成功");
			json.setObj(site);
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("修改商城信息失败"+e.getMessage());
		}finally{
			writeJson(json);
		}
	}
	public void add(){
		Json json = new Json();
		try{
			this.model = siteService.getById("1");
			this.model.setId(null);
			Site site = siteService.save(this.model);
			json.setSuccess(true);
			json.setMsg("添加商城信息成功");
			json.setObj(site);
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("添加商城信息失败"+e.getMessage());
		}finally{
			writeJson(json);
		}
	}
	public void delete(){
		Json json = new Json();
		/*try{
			int siteCount = siteService.delete(this.model.getIds());
			json.setSuccess(true);
			json.setMsg("删除商城信息【"+siteCount+"】条");
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除商城信息失败"+e.getMessage());
		}finally{
			writeJson(json);
		}*/
		json.setMsg("不能删除商城信息");
		json.setSuccess(false);
	}

	public String getCtx() {
		return ctx;
	}

	public void setCtx(String ctx) {
		this.ctx = ctx;
	}
	
}
