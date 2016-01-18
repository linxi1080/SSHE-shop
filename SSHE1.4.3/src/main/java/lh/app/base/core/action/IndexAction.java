package lh.app.base.core.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lh.app.base.core.entity.TshopIndexItem;
import lh.app.base.core.pageModel.IndexItem;
import lh.app.base.core.service.IndexItemServiceI;
import lh.base.BaseLog;
import lh.base.action.BaseAction;
import lh.util.Json;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.BeanUtils;

@ParentPackage("public")
//@Namespace("/core/admin")
@Namespace("/common")
@Action(value = "indexAction",results = {@Result(name="success",type="freemarker",location="/WEB-INF/html/welcome.html")})
public class IndexAction extends BaseAction<IndexItem> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8699050109683490221L;
	private Logger logger = BaseLog.getLogger();
	private List<IndexItem> indexItemList;
//	@Resource  
//	private IndexItemServiceI indexItemService;
	@Resource  
	private IndexItemServiceI indexItemService;
//	@Resource
//	private UserServiceI userService;
	public String execute(){
		String hql = " from TshopIndexItem ";
		Json json = new Json();
		/*indexItemList = indexItemService.find(hql);*/
		try {
			model.setTitle("1111");
			model.setSortNo(new BigDecimal("1"));
			model.setUrl("http://www.baidu.com");
			model.setId(UUID.randomUUID().toString());
//			IndexItem u = indexItemService.add(model);
			TshopIndexItem tshopIndexItem = new TshopIndexItem();
			BeanUtils.copyProperties(model,tshopIndexItem);
			String u = (String)indexItemService.save(tshopIndexItem);
			json.setSuccess(true);
			json.setMsg("添加成功！");
			json.setObj(u);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("添加失败！" + e.getMessage());
		}finally{
//			writeJson(json);
		}
		HttpServletRequest request = ServletActionContext.getRequest();  
		request.setAttribute("name1", "AAAAA"); 
		request.setAttribute("name2", "BBBBB"); 
//		writeJson(indexItemList);
		return SUCCESS;
	}
	public void add() {
		Json json = new Json();
		try {
			model.setTitle("1111");
			model.setSortNo(new BigDecimal("1"));
			model.setUrl("http://www.baidu.com");
			model.setId(UUID.randomUUID().toString());
//			IndexItem u = indexItemService.add(model);
			TshopIndexItem tshopIndexItem = new TshopIndexItem();
			BeanUtils.copyProperties(model,tshopIndexItem);
			String u = (String) indexItemService.save(tshopIndexItem);
			json.setSuccess(true);
			json.setMsg("添加成功！");
			json.setObj(u);
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("添加失败！" + e.getMessage());
		}finally{
			writeJson(json);
		}
	}
}
