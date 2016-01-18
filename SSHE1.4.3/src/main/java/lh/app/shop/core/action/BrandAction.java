package lh.app.shop.core.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import lh.app.shop.core.pageModel.Brand;
import lh.app.shop.core.service.BrandServiceI;
import lh.base.action.BaseAction;
import lh.util.Json;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
@ParentPackage("public")
@Namespace("/common")
@Action(value = "brandAction")
public class BrandAction extends BaseAction<Brand> {
	@Resource BrandServiceI brandService;
	public void find(){
		Json json = new Json();
		String hql = " from TshopGoodsBrand ";
		List<Brand> brands = new ArrayList<Brand>();
		try{
			brands = brandService.find(hql);
			json.setMsg("查询到"+brands.size()+"条数据");
			json.setSuccess(true);
			json.setObj(brands);
		}catch(Exception e){
			json.setMsg("查询商品分类失败！"+e.getMessage());
			json.setSuccess(false);
		}finally{
			writeJson(brands);
		}
	}
}
