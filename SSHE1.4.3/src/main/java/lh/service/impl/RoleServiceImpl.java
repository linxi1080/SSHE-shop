package lh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import lh.dao.MenuDaoI;
import lh.dao.RoleDaoI;
import lh.entity.Tmenu;
import lh.entity.Trole;
import lh.entity.Tuser;
import lh.pageModel.Role;
import lh.pageModel.TreeJson;
import lh.pageModel.User;
import lh.service.RoleServiceI;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service("roleService")
public class RoleServiceImpl implements RoleServiceI {

	private static final Logger logger = Logger
			.getLogger(RoleServiceImpl.class);

	@Resource
	private RoleDaoI roleDao;
	@Resource
	private MenuDaoI menuDoa;
	@Override
	public Role save(Role e) {
		Trole trole = new Trole();
		e.setId(UUID.randomUUID().toString());
		BeanUtils.copyProperties(e,trole);
		roleDao.save(trole);
		return e;
	}

	@Override
	public List<Role> find(String hql, Map<String, Object> dataGridJson,
			int page, int rows, String sort, String order) {
		List<Trole> troles = new ArrayList<Trole>();
		List<Role> roles = new ArrayList<Role>();
		
		troles = roleDao.find(hql, dataGridJson, page, rows, sort, order);
		if(troles != null && troles.size() > 0){
			for(Trole t:troles){
				Role r = new Role();
				BeanUtils.copyProperties(t,r);
				//
				Set<Tmenu> ms = new HashSet<Tmenu>();
				ms = t.getTmenus();
				if(ms.size() > 0){
					String menuIds="",menusTexts="";
					for(Tmenu m: ms){
						menuIds +=m.getId()+",";
						menusTexts +=m.getText()+",";
					}
					r.setMenuIds(menuIds.substring(0, menuIds.length() - 1));
					r.setMenuTexts(menusTexts.substring(0, menusTexts.length() - 1));
				}
				//
				roles.add(r);
			}
			return roles;
		}
		return null;
	}

	@Override
	public long findCount(String hql, Map<String, Object> params) {
		return roleDao.findCount(hql, params);
	}

	@Override
	public long findCount(Role e) {
		Trole trole = new Trole();
		BeanUtils.copyProperties(e, trole);
		return roleDao.findCount(trole);
	}

	@Override
	public int delete(String ids) {
		String[] idArrays = ids.split(",");
		roleDao.delete(ids);
		return idArrays.length;
	}

	@Override
	public int executeHql(String ids) {
		Map<String,Object> params = new HashMap<String, Object>();
		String[] arrStrings = ids.split(",");
		params.put("id",arrStrings);
		String hql = "delete from Trole where id in (:id)";
		return roleDao.executeHql(hql, params);
	}

	@Override
	public Role update(Role e) {
		Trole trole = new Trole();
		BeanUtils.copyProperties(e, trole);
		roleDao.update(trole);
		return e; 
	}

	@Override
	public List<TreeJson> getAllTreeNode() {
		List<Trole> tr = new ArrayList<Trole>();
		String hql ="from Trole a";
		tr = roleDao.find(hql);
		List<TreeJson>  trees = new ArrayList<TreeJson>();
		if(tr!=null && tr.size() > 0){
			for(Trole s:tr){
				TreeJson tree = new TreeJson();
				tree.setId(s.getId());
				tree.setText(s.getName());
				trees.add(tree);
			}
			return trees;
		}
		return null;
	}

	@Override
	public List<Role> find() {
		List<Trole> troles = roleDao.find("from Trole");
		List<Role> roles = new ArrayList<Role>();
		if(troles!=null && troles.size() > 0){
			for(Trole tr : troles){
				Role r = new Role();
				BeanUtils.copyProperties(tr,r);
				roles.add(r);
			}
			return roles;
		}
		return null;
	}

	@Override
	public Role getById(String id) {
		Trole t = roleDao.getById(id);
		Role r = new Role();
		BeanUtils.copyProperties(t, r);
		Set<Tmenu> rs = new HashSet<Tmenu>();
		rs = t.getTmenus();
		if(rs.size() > 0){
			String menusId="",menusName="";
			for(Tmenu m: rs){
				menusId +=m.getId()+",";
				menusName +=m.getText()+",";
			}
			r.setMenuIds(menusId.substring(0, menusId.length() - 1));
			r.setMenuTexts(menusName.substring(0, menusName.length() - 1));
		}
		return r;
//		return roleDao.getById(id);
	}

	@Override
	public Role grantResource(String id, String menuIds) {
		Trole trole = roleDao.getById(id);
		Role  role = new Role();
		BeanUtils.copyProperties(trole, role);
		String menusNames="";
		if(trole != null){
			trole.setTmenus(new HashSet<Tmenu>());
			for(String menuid : menuIds.split(",")){
				if(!StringUtils.isBlank(menuid)){
					Tmenu tmenu = menuDoa.getById(menuid);
					if(tmenu != null){
						trole.getTmenus().add(tmenu);
						menusNames +=tmenu.getText()+",";
					}
				}
			}
//			rolesNames.substring(0, rolesNames.length() - 1);
			role.setMenuIds(menuIds);
			if(menusNames.length() > 0){
				role.setMenuTexts(menusNames.substring(0, menusNames.length() - 1));
			}else{
				role.setMenuTexts(menusNames);
			}
			return role;
//			roleDao.saveOrUpdate(trole);
		}
		return null;
	}

}
