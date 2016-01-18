package lh.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.base.action.BaseAction;
import lh.entity.Trole;
import lh.entity.Tuser;
import lh.pageModel.SessionInfo;
import lh.pageModel.User;
import lh.service.UserServiceI;
import lh.util.ConfigUtil;
import lh.util.DataGrid;
import lh.util.Encrypt;
import lh.util.IpUtil;
import lh.util.Json;
import lh.util.RemoveFile;
import lh.util.SecurityUtil;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;

@ParentPackage("basePackage")
@Namespace("/")
@Action(value = "userAction")
public class UserAction extends BaseAction<User> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6351186737399039557L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserAction.class);
	@Resource
	private UserServiceI userService;

	private String username;
	private String password;
	private String repassword;

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

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public void addUser() {
		Json json = new Json();
//		t.setName("王五");
//		t.setPwd("123");
//		// t.setId(UUID.randomUUID().toString());
//		t.setCreatedatetime(new Date());
//		t.setModifydatetime(new Date());
		try {
			this.model.setCreatedatetime(new Date());
			User u = userService.add(model);
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

	public void reg() {
		Json json = new Json();
		if (!this.username.equals("") && !this.password.equals("")) {
			if (this.password.equals(this.repassword)) {
				Tuser user = new Tuser();
				user.setName(this.username);
				//
				user.setPwd(Encrypt.e(password));
				user.setCreatedatetime(new Date());
				try {
					userService.add(model);
					json.setSuccess(true);
					json.setMsg("注册成功！");
				} catch (Exception e) {
					e.printStackTrace();
					json.setSuccess(false);
					json.setMsg("注册失败！" + e.getMessage());
				}
			} else {
				json.setSuccess(false);
				json.setMsg(this.username + "两次输入的密码不一致！");
			}
		}
		writeJson(json);
		// ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		// try {
		// ServletActionContext.getResponse().getWriter().write(JSON.toJSONString(map));
		// // JSON.toJSONString(new
		// Date(),SerializerFeature.WriteDateUseDateFormat);
		// // JSON.toJSONStringWithDateFormat(new Date(),
		// "yyyy-MM-dd HH:mm:ss.SSS");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}
	/**
	 * 不登录注册用户
	 */
	public void doNotNeedGrant_reg() {
		Json json = new Json();
		if (!this.username.equals("") && !this.password.equals("")) {
			if (this.password.equals(this.repassword)) {
				model.setName(this.username);
				model.setPwd(Encrypt.e(password));
				model.setCreatedatetime(new Date());
				try {
					userService.add(model);
					json.setSuccess(true);
					json.setMsg("注册成功！");
				} catch (Exception e) {
					e.printStackTrace();
					json.setSuccess(false);
					json.setMsg("注册失败！" + e.getMessage());
				}
			} else {
				json.setSuccess(false);
				json.setMsg(this.username + "两次输入的密码不一致！");
			}
		}
		writeJson(json);
	}
	public void login() {
		Json json = new Json();
		//忽略大小写比较验证码
		if (!this.model.getVerifyCode().equalsIgnoreCase((String) getSession().getAttribute("vCode")) & "1".equals("2")){
			json.setSuccess(false);
			json.setMsg("验证码错误！");
		}else{
			if (!this.model.getLoginName().equals("") && !this.model.getPwd().equals("")) {
				User user = new User();
				Tuser tuser = new Tuser();
				//
				try {
					user = userService.login(model);
					if(user==null){
						json.setSuccess(false);
						json.setMsg(this.model.getLoginName() + "登录失败,无效的用户名或口令！");
					}else{
						json.setSuccess(true);
						json.setMsg(this.model.getName() + "登录成功！");
						//
						SessionInfo sessionInfo = new SessionInfo();
						user.setLoginName(user.getName());
//						BeanUtils.copyProperties(user, tuser);
						tuser = userService.getTuserById(user.getId());
						tuser.setIp(IpUtil.getIpAddr(getRequest()));
						sessionInfo.setUser(tuser);
						Hibernate.initialize(tuser.getTroles());
						for (Trole trole : tuser.getTroles()) {
							Hibernate.initialize(trole.getTmenus());
						}
						sessionInfo.setSecurityUtil(new SecurityUtil(getSession()));
						getSession().setAttribute(ConfigUtil.getFrontendSessionInfoName(), sessionInfo);
						//
					}
				} catch (Exception e) {
					e.printStackTrace();
					json.setSuccess(false);
					json.setMsg(this.model.getName() + "登录失败！"+e.getMessage());
				}
			} else {
				json.setSuccess(false);
				json.setMsg(this.model.getName() + "用户名或密码为空！");
			}
		}
		writeJson(json);
	}
	
	public void find(){
		Map<String,Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		
		String hqlWhere = addWhere(params);
		
		hql += hqlWhere;
		String hqlCount = "select count(*) " + hql;
		
		List<User> users = new ArrayList<User>();
		users = userService.find(hql,params,this.model.getPage(),this.model.getRows(),this.sort,this.order);
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal(userService.findCount(hqlCount,params));
		dataGrid.setRows(users);
		writeJson(dataGrid);
	}

	private String addWhere(Map<String, Object> params) {
		String hqlWhere = " where 1=1 ";
		if (this.model.getDate_from() != null && this.model.getDate_to() != null ){
			hqlWhere += " and t.createdatetime >= :date_from and t.createdatetime <= :date_to"; 
			params.put("date_from",this.model.getDate_from());
			params.put("date_to", this.model.getDate_to());
		}
		if(this.model.getName() == null || this.model.getName().trim().equals("")){
			
		}else{
			hqlWhere += " and t.name=:name";
			params.put("name",this.model.getName().trim());
		}
		return hqlWhere;
	}
	public void delete(){
		Json json = new Json();
		try {
			int i = userService.executeHql(model.getIds());
//			int i = userService.delete(model.getIds());
			json.setSuccess(true);
			json.setMsg(i+"个用户删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("删除失败！" + e.getMessage());
		}finally{
			writeJson(json);
		}
	}
	public void removePhoto(){
		Json json = new Json();
		logger.info("removePhoto");
		User user = userService.getById(this.model.getIds());
		RemoveFile.removeFile(this.request,user.getPhoto());
		json.setSuccess(true);
		json.setMsg("照片删除成功！");
		writeJson(json);
	}
	public void editUser(){
		Json json = new Json();
		try{
			User u = userService.update(model);
			json.setSuccess(true);
			json.setMsg("用户修改成功");
			json.setObj(u);
		}catch(Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("修改用户失败");
		}finally{
			writeJson(json);
		}
	}
	/**
	 * 注销系统
	 */
	public void logout() {
		if (getSession() != null) {
			getSession().invalidate();
		}
		Json j = new Json();
		j.setSuccess(true);
		writeJson(j);
	}
	/**
	 * 用户角色维护
	 */
	public void grantRole(){
		Json j = new Json();
		try {
			User u = userService.grantRole(model.getId(),model.getTrolesId());
			j.setSuccess(true);
			j.setMsg("修改用户角色成功！");
			j.setObj(u);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("修改用户角色失败！" + e.getMessage());
		}finally{
			writeJson(j);
		}
	}
	/**
	 * 修改口令
	 */
	public void changedPassword(){
		Json json = new Json();
		Tuser tuser = ((SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ConfigUtil.getFrontendSessionInfoName())).getUser();
		User  u = new User();
		u.setName(tuser.getName());
		u.setPwd(model.getPwd());
//		t.setPwd(Encrypt.e(user.getPwd()));
		if(tuser.getPwd().equals(Encrypt.e(model.getPwd()))){
			try{
				BeanUtils.copyProperties(tuser, u);
				u.setPwd(password);
				u.setModifydatetime(new Date());
				u = userService.update(u);
				json.setSuccess(true);
				json.setMsg("用户密码修改成功");
//				SessionInfo sessionInfo = new SessionInfo();
				SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getFrontendSessionInfoName());
				tuser.setPwd(u.getPwd());
				sessionInfo.setUser(tuser);
				getSession().setAttribute(ConfigUtil.getFrontendSessionInfoName(), sessionInfo);
				json.setObj(u);
				
			}catch(Exception e){
				e.printStackTrace();
				json.setSuccess(false);
				json.setMsg("修改用户密码失败");
			}finally{
				writeJson(json);
			}
		}else{
			json.setSuccess(false);
			json.setMsg(u.getName() + "原密码错误！");
		}
		writeJson(json);
	}
	/**
	 * 统计用户注册时间图表
	 */
	public void doNotNeedGrant_userCreateDatetimeChart() {
		writeJson(userService.userCreateDatetimeChart());
	}
	/**
	 * 取商城信息并且放到site中
	 */
	private void putCommonData() {
//		SiteAction siteAction = new SiteAction();
//		siteAction.getCurrentSite();
	}
		
}
