package lh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import lh.dao.MenuDaoI;
import lh.dao.ResourceTypeDaoI;
import lh.entity.Tmenu;
import lh.entity.TresourceType;
import lh.pageModel.Menu;
import lh.pageModel.TreeJson;
import lh.service.MenuServiceI;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service("menuService")
//@Transactional
public class MenuServiceImpl implements MenuServiceI {
	private static final Logger logger = Logger
			.getLogger(MenuServiceImpl.class);

	@Resource
	private MenuDaoI menuDao;
	@Resource
	private ResourceTypeDaoI resourceTypeDao;
	

	@Override
	public Menu save(Menu entity) {
		Tmenu tmenu = new Tmenu();
		TresourceType tresourceType = new TresourceType();
		if(!StringUtils.isBlank(entity.getId())){
			entity.setId(UUID.randomUUID().toString());
		}
		BeanUtils.copyProperties(entity, tmenu);
		tmenu.setTmenu(getById(entity.getPid()));
		tresourceType = resourceTypeDao.getById(entity.getTresourceTypeId());
		tmenu.setTresourceType(tresourceType);
		menuDao.save(tmenu);
		entity.setTresourceTypeName(tresourceType.getName());
		return entity;
	}

	@Override
	public int delete(String id) {
		String[] arrStrings = id.split(",");
		menuDao.delete(id);
		return arrStrings.length;
	}

	@Override
	public Menu update(Menu entity) {
//		Tmenu tmenu = new Tmenu();
		Tmenu tmenu = menuDao.getById(entity.getId());
		BeanUtils.copyProperties(entity, tmenu);
		tmenu.setTmenu(getById(entity.getPid()));
		TresourceType tresourceType = new TresourceType();
		tresourceType = resourceTypeDao.getById(entity.getTresourceTypeId());
		tmenu.setTresourceType(tresourceType);
		menuDao.update(tmenu);
		entity.setTresourceTypeName(tresourceType.getName());
		return entity;
	}

	@Override
	public void saveOrUpdate(Menu entity) {
		Tmenu tmenu = new Tmenu();
		BeanUtils.copyProperties(entity, tmenu);
		menuDao.saveOrUpdate(tmenu);
	}

	@Override
	public Long findCount(Menu entity) {
		Tmenu tmenu = new Tmenu();
		BeanUtils.copyProperties(entity, tmenu);
		return menuDao.findCount(tmenu);
	}

	@Override
	public Long findCount(String hql) {
		return menuDao.findCount(hql);
	}

	@Override
	public Long findCount(String hql, Map<String, Object> params) {
		return menuDao.findCount(hql, params);
	}

	@Override
	public List<Menu> findAll(int page, int rows, String sort, String order) {
		List<Tmenu> tmenus = menuDao.findAll(page, rows, sort, order);
		List<Menu>  menus = new ArrayList<Menu>();
		if(tmenus != null && tmenus.size() > 0){
			for(Tmenu t :tmenus){
				Menu r = new Menu();
				BeanUtils.copyProperties(t,r);
				menus.add(r);
			}
			return menus;
		}
		return null;
	}

	@Override
	public Tmenu getById(String id) {
		return menuDao.getById(id);
	}

	@Override
	public List<Menu> getByIds(String[] ids) {
		List<Tmenu> tmenus = menuDao.getByIds(ids);
		List<Menu>  menus = new ArrayList<Menu>();
		if(tmenus != null && tmenus.size() > 0){
			for(Tmenu t :tmenus){
				Menu r = new Menu();
				BeanUtils.copyProperties(t,r);
				menus.add(r);
			}
			return menus;
		}
		return null;
	}

	@Override
	public Menu get(String hql) {
		Tmenu t = menuDao.get(hql);
		Menu r = new Menu();
		BeanUtils.copyProperties(t,r);
		return r;
	}

	@Override
	public Menu get(String hql, Map<String, Object> params) {
		Tmenu t = menuDao.get(hql, params);
		Menu r = new Menu();
		BeanUtils.copyProperties(t,r);
		return r;
	}

	@Override
	public List<Menu> find(String hql) {
		List<Tmenu> tmenus = menuDao.find(hql);
		List<Menu>  menus = new ArrayList<Menu>();
		if(tmenus != null & tmenus.size() > 0){
			for(Tmenu t:tmenus){
				Menu r = new Menu();
				BeanUtils.copyProperties(t,r);
				String pid;
				if (t.getTmenu() != null){
					pid = t.getTmenu().getId();
					r.setPid(pid);
				}
				if (t.getTresourceType() != null){
					r.setTresourceTypeId(t.getTresourceType().getId());
					r.setTresourceTypeName(t.getTresourceType().getName());
				}
				menus.add(r);
			}
			return menus;
		}
		return null;
	}

	@Override
	public List<Menu> find(String hql, Map<String, Object> params) {
		List<Tmenu> tmenus = new ArrayList<Tmenu>();
		List<Menu>  menus = new ArrayList<Menu>();
		
		tmenus = menuDao.find(hql, params);
		if(tmenus != null && tmenus.size() > 0){
			for(Tmenu t :tmenus){
				Menu r = new Menu();
				BeanUtils.copyProperties(t,r);
				menus.add(r);
			}
			return menus;
		}
		return null;
	}

	@Override
	public List<Menu> find(String hql, Map<String, Object> params, int page,
			int rows,String sort,String order) {
		List<Tmenu> tmenus = new ArrayList<Tmenu>();
		List<Menu> menus = new ArrayList<Menu>();
		
		tmenus = menuDao.find(hql, params, page, rows, sort, order);
		if(tmenus != null && tmenus.size() > 0){
			for(Tmenu t:tmenus){
				Menu r = new Menu();
				BeanUtils.copyProperties(t,r);
				menus.add(r);
			}
			return menus;
		}
		return null;
	}
	/**
	 * 异步加载菜单树
	 */
	@Override
	public List<TreeJson> getTreeNode(String id) {
		List<Tmenu> tm = new ArrayList<Tmenu>();
		String hql ;
		Map<String,Object> params = new HashMap<String, Object>();
		if (id == null || id.equals("")){
			//如果id为空查询pid为空的根节点
			hql = "from Tmenu a where a.tmenu is null order by seqNo asc";
		}else{
			//如果id不为空查询pid=id的节点
			hql = " from Tmenu a where a.tmenu.id = :pid order by seqNo asc";
			params.put("pid",id);
		}
		tm = menuDao.find(hql, params);
		List<TreeJson>  trees = new ArrayList<TreeJson>();
		if(tm!=null && tm.size() > 0){
			for(Tmenu s:tm){
				TreeJson tree = new TreeJson();
				tree.setId(s.getId());
				tree.setIconCls(s.getIconCls());
				//
				Tmenu parent = s.getTmenu();
				if(parent!=null){
					tree.setPid(parent.getId());
				}
				Set<Tmenu> set = s.getTmenus();
				if(set.size() == 0 && set.isEmpty()){
					tree.setState("open");//默认图标以文件图标
				}else{
					tree.setState("closed");//默认图标以文件夹图标
				}
				tree.setText(s.getText());
				trees.add(tree);
			}
			return trees;
		}
		return null;
	}
	/**
	 * 获取全部的菜单节点，使用扁平化的简单tree数据形式，但是必须有pid属性
	 */
	@Override
	public List<TreeJson> getAllTreeNode() {
		List<Tmenu> tm = new ArrayList<Tmenu>();
		String hql ="from Tmenu a order by seqNo asc";
		tm = menuDao.find(hql);
		List<TreeJson>  trees = new ArrayList<TreeJson>();
		if(tm!=null && tm.size() > 0){
			for(Tmenu s:tm){
				TreeJson tree = new TreeJson();
				tree.setId(s.getId());
				tree.setIconCls(s.getIconCls());
				Tmenu parent = s.getTmenu();
				if(parent!=null){
					tree.setPid(parent.getId());
				}
				//如果使用下边的方法设置state会造成对数据库的多次查询对效率有影响
				//故在jsp页面中使用tree的collapseAll方法
//				Set<Tmenu> set = s.getTmenus();
//				if(set.size() == 0 && set.isEmpty()){
//					tree.setState("open");//默认图标以文件图标
//				}else{
//					tree.setState("closed");//默认图标以文件夹图标
//				}
				tree.setText(s.getText());
				String url = s.getUrl();
//				if(url!=null || "".equals(url)){
					Map<String, Object> attributes = new HashMap<String, Object>();
					attributes.put("url", url);
					tree.setAttributes(attributes);
//				}
				trees.add(tree);
			}
			return trees;
		}
		return null;
	}
	/**
	 * 获取用户的菜单，使用异步加载
	 * @param userId
	 * @return
	 */
	@Override
	public List<TreeJson> getTreeNodeByUser(String userId,String pid){
		List<Tmenu> tm = new ArrayList<Tmenu>();
		List<TreeJson>  trees = new ArrayList<TreeJson>();
		String hql ;
		Map<String,Object> params = new HashMap<String, Object>();
		//select distinct t from Tresource t join fetch t.tresourcetype type join fetch t.troles role join role.tusers user where user.id = :userId order by t.seq
		//from Department as d inner join fetch d.employees e where e.name='Tom'; 
		if (userId == null || userId.equals("")){
			//如果id为空查询pid为空的根节点
//			hql  = "select distinct m from Tmenu m join fetch m.troles r join r.tusers u where u.name = :userid ";
			hql = " from Tmenu a where a.tmenu is null order by seqNo asc";
		}else{
			//如果userId不为空查询userId的节点
//			hql  = "select distinct m from Tmenu m join fetch m.troles r join r.tusers u where u.name = :userid  and m.tmenu.id = :pid";
			hql  = "select distinct m from Tmenu m join fetch m.troles r join r.tusers u where u.name = :userid and m.tresourceType = '1' order by m.seqNo asc ";
			//		select distinct u from User u, u.roleList.elements r where r.inspector = '' and u.orgId = '' 
			//		select distinct c from Customer c , c.Orders.elements o where o.OrderDate > :orderDate
//			hql  = "select distinct m from Tmenu m,m.troles.elements r, r.tusers.elements u where u.name = :userid and m.tmenu.id = :pid";
			//		select distinct u from User u, u.roleList.elements r where r.inspector = '' and u.orgId = '' 
//			logger.info(hql);
			params.put("userid",userId);
//			params.put("pid",pid);
		}
		tm = menuDao.find(hql, params);
		if(tm!=null && tm.size() > 0){
			for(Tmenu s:tm){
				TreeJson tree = new TreeJson();
				tree.setId(s.getId());
				tree.setIconCls(s.getIconCls());
				//
				Tmenu parent = s.getTmenu();
				if(parent!=null){
					tree.setPid(parent.getId());
				}
				Set<Tmenu> set = s.getTmenus();
				if(set.size() == 0 && set.isEmpty()){
					tree.setState("open");//默认图标以文件图标
				}else{
					for(Tmenu s1:set){
						if(s1.getTresourceType().getName()=="1"){
							tree.setState("closed");//默认图标以文件夹图标
						}
					}
					if(tree.getState()==null){
						tree.setState("open");//默认图标以文件图标
					}
				}
				tree.setText(s.getText());
				String url = s.getUrl();
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("url", url);
				attributes.put("target",s.getTarget());
				attributes.put("openMode", s.getOpenMode());
				tree.setAttributes(attributes);
				trees.add(tree);
			}
			return trees;
		}
		return null;
	}
	@Override
	public int executeHql(String ids) {
		Map<String,Object> params = new HashMap<String, Object>();
		String[] arrStrings = ids.split(",");
		params.put("id",arrStrings);
		String hql = "delete from Tmenu where id in (:id)";
		return menuDao.executeHql(hql, params);
	}

	@Override
	public List<Menu> getTreeGrid() {
		String hql ="from Tmenu a order by seqNo asc";
		List<Menu> menus = find(hql);
		if(menus.size() > 0 && menus != null){
			for(int i=0;i<menus.size();i++){
				Menu menu = new Menu();
				menu = menus.get(i);
				menu.set_parentId(menu.getPid());
				menus.set(i, menu);
			}
		}
		return menus;
	}
}
