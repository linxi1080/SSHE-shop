package lh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import lh.dao.DeptDaoI;
import lh.entity.Tdept;
import lh.entity.Tmenu;
import lh.pageModel.Dept;
import lh.pageModel.Menu;
import lh.pageModel.TreeJson;
import lh.service.DeptServiceI;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service("deptService")
public class DeptServiceImpl implements DeptServiceI {
	private static final Logger logger = Logger
			.getLogger(DeptServiceImpl.class);

	@Resource
	private DeptDaoI deptDao;
	@Override
	public Dept save(Dept entity) {
		Tdept tdept = new Tdept();
		if(StringUtils.isBlank(entity.getId())){
			entity.setId(UUID.randomUUID().toString());
		}
		BeanUtils.copyProperties(entity, tdept);
//		logger.info(StringUtils.isBlank(entity.getPid()));
		if(!StringUtils.isBlank(entity.getPid())){
			tdept.setTdept(deptDao.getById(entity.getPid()));
			entity.setParentName(deptDao.getById(entity.getPid()).getName());
		}
		deptDao.save(tdept);
//		logger.info(entity.getId());
		return entity;
	}

	@Override
	public int delete(String id) {
		String[] arrStrings = id.split(",");
		deptDao.delete(id);
		return arrStrings.length;
	}

	@Override
	public Dept update(Dept entity) {
		Tdept tdept = deptDao.getById(entity.getId());
		BeanUtils.copyProperties(entity, tdept);
		if(!StringUtils.isBlank(entity.getPid())){
			tdept.setTdept(deptDao.getById(entity.getPid()));
		}
		deptDao.update(tdept);
		entity.setParentName(tdept.getTdept().getName());
		return entity;
	}

	@Override
	public void saveOrUpdate(Dept entity) {
		Tdept tdept = new Tdept();
		BeanUtils.copyProperties(entity, tdept);
		deptDao.saveOrUpdate(tdept);
	}

	@Override
	public Long findCount(Dept entity) {
		Tdept tdept = new Tdept();
		BeanUtils.copyProperties(entity, tdept);
		return deptDao.findCount(tdept);
	}

	@Override
	public Long findCount(String hql) {
		return deptDao.findCount(hql);
	}

	@Override
	public Long findCount(String hql, Map<String, Object> params) {
		return deptDao.findCount(hql, params);
	}

	@Override
	public List<Dept> findAll(int page, int rows, String sort, String order) {
		List<Tdept>  tdepts = deptDao.findAll(page, rows, sort, order);
		List<Dept>   depts = new ArrayList<Dept>();
		if(tdepts != null){
			return entitys2PageModels(tdepts);
		}
		return null;
	}

	@Override
	public Tdept getById(String id) {
		return deptDao.getById(id);
	}

	@Override
	public List<Dept> getByIds(String[] ids) {
		List<Tdept> tdepts = deptDao.getByIds(ids);
		if(tdepts != null){
			return entitys2PageModels(tdepts);
		}
		return null;
	}

	private List<Dept> entitys2PageModels(List<Tdept> tdepts) {
		List<Dept>  depts = new ArrayList<Dept>();
		for(Tdept tdept:tdepts){
			Dept dept = new Dept();
			dept = entity2PageModel(tdept);
			depts.add(dept);
		}
		return depts;
	}

	@Override
	public Dept get(String hql) {
		Tdept tdept = deptDao.get(hql);
		if(tdept!=null){
			return entity2PageModel(tdept);
		}
		return null;
	}

	private Dept entity2PageModel(Tdept tdept) {
		Dept  dept = new Dept();
		BeanUtils.copyProperties(tdept, dept);
//		logger.info(tdept.getTdept());
		if(tdept.getTdept() != null){
			dept.setPid(tdept.getTdept().getId());
			dept.setParentName(tdept.getTdept().getName());
		}
		if(!tdept.getTdepts().isEmpty()){
			String childerId = new String();
			String childerName = new String();
			for(Tdept t:tdept.getTdepts()){
				childerId +=t.getId()+",";
				childerName +=t.getName()+",";
			}
			dept.setTdeptIds(childerId.substring(0, childerId.length() - 1));
			dept.setTdeptNames(childerName.substring(0, childerName.length() - 1));
		}
		return dept;
	}

	@Override
	public Dept get(String hql, Map<String, Object> params) {
		Tdept tdept = deptDao.get(hql, params);
		if(tdept!=null){
			Dept dept = new Dept();
			dept = entity2PageModel(tdept);
			return dept;
		}
		return null;
	}

	@Override
	public List<Dept> find(String hql) {
		List<Tdept> tdepts = deptDao.find(hql);
		if(tdepts != null){
			return entitys2PageModels(tdepts);
		}
		return null;
	}

	@Override
	public List<Dept> find(String hql, Map<String, Object> params) {
		List<Tdept> tdepts = deptDao.find(hql, params);
		if(tdepts != null){
			return entitys2PageModels(tdepts);
		}
		return null;
	}

	@Override
	public List<Dept> find(String hql, Map<String, Object> params, int page,
			int rows, String sort, String order) {
		List<Tdept> tdepts = deptDao.find(hql, params, page, rows, sort, order);
		if(tdepts!=null){
			return entitys2PageModels(tdepts);
		}
		return null;
	}
	/**
	 * 异步加载部门tree
	 */
	@Override
	public List<TreeJson> getTreeNode(String id) {
		List<Tdept> tdepts = new ArrayList<Tdept>();
		String hql ;
		Map<String,Object> params = new HashMap<String, Object>();
		if (id == null || id.equals("")){
			//如果id为空查询pid为空的根节点
			hql = "from Tdept a where a.tdept is null";
		}else{
			//如果id不为空查询pid=id的节点
			hql = " from Tdept a where a.tdept.id = :pid";
			params.put("pid",id);
		}
		tdepts = deptDao.find(hql, params);
		List<TreeJson>  trees = new ArrayList<TreeJson>();
		if(tdepts!=null && tdepts.size() > 0){
			for(Tdept s:tdepts){
				TreeJson tree = new TreeJson();
				tree.setId(s.getId());
				tree.setIconCls(s.getIconCls());
				//
				Tdept parentDept = s.getTdept();
				if(parentDept!=null){
					tree.setPid(parentDept.getId());
				}
				Set<Tdept> set = s.getTdepts();
				if(set.size() == 0 && set.isEmpty()){
					tree.setState("open");//默认图标以文件图标
				}else{
					tree.setState("closed");//默认图标以文件夹图标
				}
				tree.setText(s.getName());
				trees.add(tree);
			}
			return trees;
		}
		return null;
	}

	@Override
	public List<TreeJson> getAllTreeNode() {
		List<Tdept> depts = new ArrayList<Tdept>();
		String hql ="from Tdept a";
		depts = deptDao.find(hql);
		List<TreeJson>  trees = new ArrayList<TreeJson>();
		if(depts!=null && depts.size() > 0){
			for(Tdept s:depts){
				TreeJson tree = new TreeJson();
				tree.setId(s.getId());
				tree.setIconCls(s.getIconCls());
				Tdept parentDept = s.getTdept();
				if(parentDept!=null){
					tree.setPid(parentDept.getId());
				}
				tree.setText(s.getName());
				trees.add(tree);
			}
			return trees;
		}
		return null;
	}

	@Override
	public int executeHql(String ids) {
		Map<String,Object> params = new HashMap<String, Object>();
		String[]  arrStrings = ids.split(",");
		params.put("id",arrStrings);
		String hql = "delete from Tdept where id in (:id)";
		return deptDao.executeHql(hql, params);
	}

	@Override
	public List<Dept> getTreeGrid() {
		String hql ="from Tdept a";
		List<Dept> depts = find(hql);
		if(depts != null){
			for(int i=0;i<depts.size();i++){
				Dept dept = new Dept();
				dept = depts.get(i);
				dept.set_parentId(dept.getPid());
				depts.set(i, dept);
			}
		}
		return depts;
	}

}
