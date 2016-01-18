package lh.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import lh.dao.MenuDaoI;
import lh.dao.ResourceTypeDaoI;
import lh.dao.RoleDaoI;
import lh.dao.UserDao;
import lh.entity.Tmenu;
import lh.entity.TresourceType;
import lh.entity.Trole;
import lh.entity.Tuser;
import lh.service.RepairServiceI;
import lh.util.Encrypt;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service("repairService")
public class RepairServiceImpl implements RepairServiceI {
	@Resource
	private MenuDaoI menuDao;
	@Resource
	private UserDao userDao;
	@Resource
	private ResourceTypeDaoI resourceTypeDao;
	@Resource
	private RoleDaoI roleDao;
	
	private static final Logger logger = Logger
			.getLogger(MenuServiceImpl.class);
	private static final String FILEPATH = "initDataBase.xml";
	@Override
	synchronized public void repair() {
		try{
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));
			repairResourcetype(document);// 初始化资源类型
			repairResource(document);//初始化资源文件，菜单
			repairRole(document);//修复角色
			repairRoleResource(document);//修复角色资源
			repairUser(document);//修复用户
			repairUserRole(document);//修复用户角色
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
//		repairMenu();
//		repairUser();
//		repairResource();
	}
	private void repairUserRole(Document document) {
		List<Node> childNodes = document.selectNodes("//users_roles/user");
		for (Node node : childNodes) {
			Tuser user = userDao.getById(node.valueOf("@id"));
			if (user != null) {
				user.setTroles(new HashSet());
				List<Node> cNodes = node.selectNodes("role");
				for (Node n : cNodes) {
					Trole role = roleDao.getById(n.valueOf("@id"));
					if (role != null) {
						user.getTroles().add(role);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
				userDao.saveOrUpdate(user);
			}
		}

		Tuser user = userDao.getById("0");// 用户为0的超级管理员，赋予所有角色
		if (user != null) {
			user.getTroles().addAll(roleDao.find("from Trole"));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			userDao.saveOrUpdate(user);
		}
	}
	private void repairUser(Document document) {
		List<Node> childNodes = document.selectNodes("//users/user");
		for (Node node : childNodes) {
			Tuser user = userDao.getById(node.valueOf("@id"));
			if (user == null) {
				user = new Tuser();
			}
			user.setId(node.valueOf("@id"));
			user.setName(node.valueOf("@name"));
			user.setLoginName(node.valueOf("@loginname"));
			user.setPwd(Encrypt.e(node.valueOf("@pwd")));
			user.setGender(node.valueOf("@sex"));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			List<Tuser> ul = userDao.find("from Tuser u where u.loginName = '" + user.getLoginName() + "' and u.id != '" + user.getId() + "'");
			if(ul != null){
				for (Tuser u : ul) {
					userDao.delete(u.getId());
				}
			}
			userDao.saveOrUpdate(user);
		}
	}
	private void repairRoleResource(Document document) {
		List<Node> childNodes = document.selectNodes("//roles_resources/role");
		for (Node node : childNodes) {
			Trole role = roleDao.getById(node.valueOf("@id"));
			if (role != null) {
				role.setTmenus(new HashSet());
				List<Node> cNodes = node.selectNodes("resource");
				for (Node n : cNodes) {
					Tmenu resource = menuDao.getById(n.valueOf("@id"));
					if (resource != null) {
						role.getTmenus().add(resource);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
				roleDao.saveOrUpdate(role);
			}
		}

		Trole role = roleDao.getById("0");// 将角色为0的超级管理员角色，赋予所有权限
		if (role != null) {
			role.getTmenus().addAll(menuDao.find("from Tmenu t"));
			logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
			roleDao.saveOrUpdate(role);
		}
	}
	/**
	 * 初始化资源类型
	 * @param document
	 */
	private void repairResourcetype(Document document) {
		List childNodes = document.selectNodes("//resourcetypes/resourcetype");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			TresourceType type = resourceTypeDao.getById(node.valueOf("@id"));
			if (type == null) {
				type = new TresourceType();
			}
			type.setId(node.valueOf("@id"));
			type.setName(node.valueOf("@name"));
			type.setDescription(node.valueOf("@description"));
			logger.info(JSON.toJSONStringWithDateFormat(type, "yyyy-MM-dd HH:mm:ss"));
			resourceTypeDao.saveOrUpdate(type);
		}
	}
	/**
	 * 初始化资源数据
	 * @param document
	 */
	private void repairResource(Document document){
		List childNodes = document.selectNodes("//resources/resource");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Tmenu resource = menuDao.getById(node.valueOf("@id"));
			if (resource == null) {
				resource = new Tmenu();
			}
			resource.setId(node.valueOf("@id"));
			resource.setText(node.valueOf("@name"));
			resource.setUrl(node.valueOf("@url"));
			resource.setNotes(node.valueOf("@description"));
			resource.setIconCls(node.valueOf("@iconCls"));
			resource.setSeqNo(Integer.parseInt(node.valueOf("@seq")));

//			if (!StringUtils.isBlank(node.valueOf("@target"))) {
//				resource.setTarget(node.valueOf("@target"));
//			} else {
//				resource.setTarget("");
//			}

			if (!StringUtils.isBlank(node.valueOf("@pid"))) {
				resource.setTmenu(menuDao.getById(node.valueOf("@pid")));
			} else {
				resource.setTmenu(null);
			}
			if(!StringUtils.isBlank(node.valueOf("@openMode"))){
				resource.setOpenMode(node.valueOf("@openMode"));
			}
			Node n = node.selectSingleNode("resourcetype");
			TresourceType type = resourceTypeDao.getById(n.valueOf("@id"));
			if (type != null) {
				resource.setTresourceType(type);
			}
			logger.info(JSON.toJSONStringWithDateFormat(resource, "yyyy-MM-dd HH:mm:ss"));
			menuDao.saveOrUpdate(resource);
		}
	}
	private void repairRole(Document document) {
		List childNodes = document.selectNodes("//roles/role");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Trole role = roleDao.getById(node.valueOf("@id"));
			if (role == null) {
				role = new Trole();
			}
			role.setId(node.valueOf("@id"));
			role.setName(node.valueOf("@name"));
			role.setNotes(node.valueOf("@description"));
			role.setModifydatetime(new Date());
//			role.setSeq(Integer.parseInt(node.valueOf("@seq")));
			logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
			roleDao.saveOrUpdate(role);
		}
	}
//	private void repairResource(){
//		TresourceType tresourceType =  new TresourceType();
//		tresourceType.setId("1");
//		tresourceType.setName("菜单");
//		
//		TresourceType tresourceType2 =  new TresourceType();
//		tresourceType2.setId("2");
//		tresourceType2.setName("功能");
//	}
//	private void repairUser() {
//		Map<String, Object> params = new HashMap<String, Object>();
//
//		params.put("name", "admin");
//		Tuser t = userDao.get("from Tuser a where a.name = :name and a.id != '0'", params);
//		if (t != null) {
//			userDao.delete(t.getId());
//		}
//		t = userDao.get("from Tuser a where a.name = :name and a.id = '0'", params);
//		if(t==null){
//			Tuser admin = new Tuser();
//			admin.setId("0");
//			admin.setName("admin");
//			admin.setPwd(Encrypt.e("123456"));
//			admin.setCreatedatetime(new Date());
//			admin.setModifydatetime(new Date());
//			userDao.saveOrUpdate(admin);
//		}else{
//			t.setPwd(Encrypt.e("123456"));
//		}
//	}
//
//	private void repairMenu() {
//		// 根节点菜单
//		Tmenu root = new Tmenu();
//		TresourceType tresourceType =  new TresourceType();
//		tresourceType.setId("1");
//		root.setIconCls("");
//		root.setText("首页");
//		root.setUrl("");
//		root.setId("root");
//		// root.setId(UUID.randomUUID().toString());
//		root.setSeqNo((long) 0);
//		root.setUseFlag("Y");
//		root.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(root);
//
//		// 系统管理菜单
//		Tmenu xtgl = new Tmenu();
//		xtgl.setIconCls("");
//		xtgl.setText("系统管理");
//		xtgl.setUrl("");
//		xtgl.setId("xtgl");
//		xtgl.setTmenu(root);
//		xtgl.setSeqNo((long) 0);
//		xtgl.setUseFlag("Y");
//		xtgl.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(xtgl);
//		// 用户管理菜单
//		Tmenu yhgl = new Tmenu();
//		//ext-icon-user
//		yhgl.setIconCls("ext-icon-anchor");
//		yhgl.setText("用户管理");
//		yhgl.setUrl("/jsp/admin/yhgl.jsp");
//		yhgl.setId("yhgl");
//		yhgl.setTmenu(xtgl);
//		yhgl.setSeqNo((long) 1);
//		yhgl.setUseFlag("Y");
//		yhgl.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(yhgl);
//		// 角色管理菜单
//		Tmenu jsgl = new Tmenu();
//		jsgl.setIconCls("ext-icon-user_orange");
//		jsgl.setText("角色管理");
//		jsgl.setUrl("/jsp/admin/role.jsp");
//		jsgl.setId("jsgl");
//		jsgl.setTmenu(xtgl);
//		jsgl.setSeqNo((long) 2);
//		jsgl.setUseFlag("Y");
//		jsgl.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(jsgl);
//		// 权限管理菜单
//		Tmenu qxgl = new Tmenu();
//		qxgl.setIconCls("");
//		qxgl.setText("权限管理");
//		qxgl.setUrl("/jsp/admin/role.html");
//		qxgl.setId("qxgl");
//		qxgl.setIconCls("ext-icon-world_go");
//		qxgl.setTmenu(xtgl);
//		qxgl.setSeqNo((long) 3);
//		qxgl.setUseFlag("Y");
//		qxgl.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(qxgl);
//		// 菜单管理菜单
//		Tmenu cdgl = new Tmenu();
//		cdgl.setIconCls("ext-icon-cup_key");
//		cdgl.setText("菜单管理");
//		cdgl.setUrl("/jsp/admin/resource.jsp");
//		cdgl.setId("cdgl");
//		cdgl.setTmenu(xtgl);
//		cdgl.setSeqNo((long) 4);
//		cdgl.setUseFlag("Y");
//		cdgl.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(cdgl);
//		// BUG管理菜单
//		Tmenu buggl = new Tmenu();
//		buggl.setIconCls("");
//		buggl.setText("BUG管理");
//		buggl.setUrl("");
//		buggl.setId("buggl");
//		buggl.setTmenu(xtgl);
//		buggl.setSeqNo((long) 5);
//		buggl.setUseFlag("Y");
//		buggl.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(buggl);
//
//		// 日志管理菜单
//		Tmenu rzgl = new Tmenu();
//		rzgl.setIconCls("");
//		rzgl.setText("日志管理");
//		rzgl.setUrl("");
//		rzgl.setId("rzgl");
//		rzgl.setTmenu(root);
//		rzgl.setSeqNo((long) 1);
//		rzgl.setUseFlag("Y");
//		rzgl.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(rzgl);
//		// 系统报表
//		Tmenu xtbb = new Tmenu();
//		xtbb.setIconCls("ext-icon-chart_curve");
//		xtbb.setText("系统报表");
//		xtbb.setUrl("");
//		xtbb.setId("xtbb");
//		xtbb.setTmenu(root);
//		xtbb.setSeqNo((long) 2);
//		xtbb.setUseFlag("Y");
//		xtbb.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(xtbb);
//		// 用户角色分布菜单
//		Tmenu userRole = new Tmenu();
//		//ext-icon-user
//		userRole.setIconCls("ext-icon-chart_pie");
//		userRole.setText("用户角色分布");
//		userRole.setUrl("/jsp/admin/chart/userRoleChart.jsp");
//		userRole.setId("userRoleChart");
//		userRole.setTmenu(xtbb);
//		userRole.setSeqNo((long) 1);
//		userRole.setUseFlag("Y");
//		userRole.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(userRole);
//		// 用户注册时间分布菜单
//		Tmenu userCreateDatetime = new Tmenu();
//		//ext-icon-user
//		userCreateDatetime.setIconCls("ext-icon-chart_bar");
//		userCreateDatetime.setText("用户注册时间分布");
//		userCreateDatetime.setUrl("/jsp/admin/chart/userCreateDatetimeChart.jsp");
//		userCreateDatetime.setId("userCreateDatetimeChart");
//		userCreateDatetime.setTmenu(xtbb);
//		userCreateDatetime.setSeqNo((long) 1);
//		userCreateDatetime.setUseFlag("Y");
//		userCreateDatetime.setTresourceType(tresourceType);
//		menuDao.saveOrUpdate(userCreateDatetime);
//	}
}
