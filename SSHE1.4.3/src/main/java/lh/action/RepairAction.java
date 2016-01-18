package lh.action;

import javax.annotation.Resource;

import lh.base.action.BaseAction;
import lh.pageModel.Menu;
import lh.service.RepairServiceI;
import lh.util.Json;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
@ParentPackage("basePackage")
@Namespace("/")
@Action(value = "repairAction",results={@Result(name="success", location = "/index.jsp")})
public class RepairAction extends BaseAction<Menu> {

	@Resource
	private RepairServiceI repairService;
	
	public String init(){
		try{
			repairService.repair();
			return "success";
		}catch(Exception e){			
			System.out.println(e.getMessage());
//			return "fail";
			return "success";
		}
	}
}
