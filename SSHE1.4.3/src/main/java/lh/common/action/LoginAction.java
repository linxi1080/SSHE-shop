package lh.common.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lh.pageModel.Role;
import lh.service.RoleServiceI;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;
@ParentPackage("public")
@Namespace("/common")
@Action(value = "login",results = {@Result(name="success",type="freemarker",location="/WEB-INF/html/welcome.html"),
		                           @Result(name="login",type="freemarker",location="/WEB-INF/jsp/welcome.jsp")})
public class LoginAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2651510582436559296L;
	private String name1 = "AAAAA";
	private String username;
	private String password;
	@Resource
	private RoleServiceI roleService;
	public String execute() throws Exception {
		 HttpServletRequest request = ServletActionContext.getRequest();  
		 request.setAttribute("name2", "BBBBB");  
		 //
//		 List<Role> roles = roleService.find();
		 List<Role> roles =  new ArrayList<Role>();
		 request.setAttribute("roleList", roles);
		 //
		 return SUCCESS;  
	}
	public String login(){
		HttpServletRequest request = ServletActionContext.getRequest();  
		 request.setAttribute("name2", "BBBBB");  
		 //
		 List<Role> roles = roleService.find();
//		 List<Role> roles =  new ArrayList<Role>();
		 request.setAttribute("roleList", roles);
		 //
		 return "login";  
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
