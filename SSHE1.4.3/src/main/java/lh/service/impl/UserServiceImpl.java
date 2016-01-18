package lh.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import lh.dao.RoleDaoI;
import lh.dao.UserDao;
import lh.entity.Trole;
import lh.entity.Tuser;
import lh.pageModel.User;
import lh.service.UserServiceI;
import lh.util.Encrypt;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service("userService")
//@Transactional
public class UserServiceImpl implements UserServiceI {
	@Resource
	private UserDao userDao;
	@Resource
	private RoleDaoI roleDao;
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(UserServiceImpl.class);

	@Override
	public void test() {
		logger.info("====spring test sucess=====");
	}

	@Override
	public Serializable save(User e) {
		Tuser tuser = new Tuser();
		BeanUtils.copyProperties(e, tuser, new String[] {"pwd"});
		tuser.setPwd(Encrypt.e(e.getPwd()));
		return userDao.save(tuser);
	}

	@Override
	public User add(User user) {
		Tuser tuser = new Tuser();
		BeanUtils.copyProperties(user, tuser,new String[]{"pwd"});
		tuser.setPwd(Encrypt.e(user.getPwd()));
		tuser.setId(UUID.randomUUID().toString());
		userDao.save(tuser);
		BeanUtils.copyProperties(tuser, user);
		return user;
		
	}

	@Override
	public User login(User user) {
//		return userDao.get(" from Tuser a where a.name='"+user.getName()+"' and a.pwd = '"+user.getPwd()+"'");
//		return userDao.get(" from Tuser a where a.name=? and a.pwd = ?", new Object[]{user.getName(),user.getPwd()});
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("loginName",user.getLoginName());
		params.put("password",Encrypt.e(user.getPwd()));
		Tuser tuser = new Tuser();
		tuser = userDao.get(" from Tuser a where a.loginName=:loginName and a.pwd = :password",params);
		if (!(tuser==null)){
			BeanUtils.copyProperties(tuser, user);
			return setRole2User(tuser,user);
		}else{
			return null;
		}
	}

	@Override
	public List<User> find(String hql, Map<String, Object> dataGridJson, int page,
			int rows,String sort,String order) {
		List<Tuser> tusers = new ArrayList<Tuser>();
		List<User> users = new ArrayList<User>();
		tusers = userDao.find(hql, dataGridJson, page, rows,sort,order);

		for (Tuser u : tusers){
			User user = new User();
			BeanUtils.copyProperties(u, user);
			Set<Trole> rs = new HashSet<Trole>();
			rs = u.getTroles();
			if(rs.size() > 0){
				String rolesId="",rolesName="";
				for(Trole r: rs){
					rolesId +=r.getId()+",";
					rolesName +=r.getName()+",";
				}
				user.setTrolesId(rolesId.substring(0, rolesId.length() - 1));
				user.setTrolesName(rolesName.substring(0, rolesName.length() - 1));
			}
			users.add(user);
		}
		return users;
	}

	@Override
	public long findCount(String hql,Map<String, Object> params) {
		return userDao.findCount(hql,params);
	}

	@Override
	public long findCount(User user) {
		Tuser tuser = new Tuser();
		BeanUtils.copyProperties(user, tuser);
		return userDao.findCount(tuser);
	}

	@Override
	public int delete(String ids) {
		String[] idArrays = ids.split(",");
		userDao.delete(ids);
		return idArrays.length;
	}

	@Override
	public int executeHql(String ids) {
		Map<String,Object> params = new HashMap<String, Object>();
		String[] idArrays = ids.split(",");
		String hql = "delete from Tuser where id in (:id)";
		params.put("id",idArrays);
		return userDao.executeHql(hql,params);
	}

	@Override
	public User update(User user) {
//		Tuser t = new Tuser();
		Tuser t = userDao.getById(user.getId());
		BeanUtils.copyProperties(user, t,new String[] {"pwd"});
//		logger.info(user.getPwd());
//		logger.info(StringUtils.isBlank(user.getPwd()));
		if(!StringUtils.isBlank(user.getPwd())){
			t.setPwd(Encrypt.e(user.getPwd()));
		}
		t.setTroles(userDao.getById(t.getId()).getTroles());
		t.setTdept(userDao.getById(t.getId()).getTdept());
		userDao.update(t);
		BeanUtils.copyProperties(t, user);
		return user;
	}

	@Override
	public User getById(String id) {
		Tuser t = userDao.getById(id);
		User u = new User();
		BeanUtils.copyProperties(t, u);
		return setRole2User(t, u);
	}

	private User setRole2User(Tuser t, User u) {
		Set<Trole> rs = new HashSet<Trole>();
		rs = t.getTroles();
		if(rs.size() > 0){
			String rolesId="",rolesName="";
			for(Trole r: rs){
				rolesId +=r.getId()+",";
				rolesName +=r.getName()+",";
			}
			u.setTrolesId(rolesId.substring(0, rolesId.length() - 1));
			u.setTrolesName(rolesName.substring(0, rolesName.length() - 1));
		}
		return u;
	}

	@Override
	public Long countUserByRoleId(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", id);
		String hql = "select count(*) from Tuser t join t.troles role where role.id = :roleId";
		return userDao.findCount(hql, params);
	}

	@Override
	public Long countUserByNotRoleId() {
		String hql = "select count(*) from Tuser t left join t.troles role where role.id is null";
		return userDao.findCount(hql);
	}

	@Override
	public User grantRole(String id, String trolesId) {
		Tuser tuser = userDao.getById(id);
		User  user = new User();
		BeanUtils.copyProperties(tuser, user);
		String rolesNames="";
		if(tuser != null){
			tuser.setTroles(new HashSet<Trole>());
			for(String roleId : trolesId.split(",")){
				if(!StringUtils.isBlank(roleId)){
					Trole trole = roleDao.getById(roleId);
					if(trole != null){
						tuser.getTroles().add(trole);
						rolesNames +=trole.getName()+",";
					}
				}
			}
//			rolesNames.substring(0, rolesNames.length() - 1);
			user.setTrolesId(trolesId);
			if(rolesNames.length() > 0){
				user.setTrolesName(rolesNames.substring(0, rolesNames.length() - 1));
			}else{
				user.setTrolesName(rolesNames);
			}
			return user;
//			userDao.saveOrUpdate(tuser);
		}
		return null;
	}

	@Override
	public Tuser getTuserById(String id) {
		return userDao.getById(id);
	}

	@Override
	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("a", k);
			params.put("b", k + 2);
			k = k + 2;
			l.add(findCount("select count(*) from Tuser t where HOUR(t.createdatetime)>=:a and HOUR(t.createdatetime)<:b", params));
		}
		return l;
	}
}
