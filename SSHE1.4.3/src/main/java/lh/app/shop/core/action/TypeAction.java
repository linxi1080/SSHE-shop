package lh.app.shop.core.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import lh.app.shop.core.pageModel.Type;
import lh.app.shop.core.service.TypeServiceI;
import lh.base.action.BaseAction;
import lh.util.Json;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
@ParentPackage("public")
@Namespace("/common")
@Action(value = "typeAction")
public class TypeAction extends BaseAction<Type> {
	private static final long serialVersionUID = 8190812748425211346L;
	@Resource
	private TypeServiceI typeService;
	public void find(){
		Json json = new Json();
		String hql = " from TshopGoodsType ";
		List<Type> types = new ArrayList<Type>();
		try{
			types = typeService.find(hql);
			json.setMsg("查询到"+types.size()+"条数据");
			json.setSuccess(true);
			json.setObj(types);
		}catch(Exception e){
			json.setMsg("查询商品分类失败！"+e.getMessage());
			json.setSuccess(false);
		}finally{
			writeJson(types);
		}
	}
}
