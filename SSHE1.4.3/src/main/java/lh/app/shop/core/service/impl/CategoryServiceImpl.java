package lh.app.shop.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.app.base.core.base.service.impl.BaseServiceImpl;
import lh.app.shop.core.dao.CategoryDaoI;
import lh.app.shop.core.dao.TypeDaoI;
import lh.app.shop.core.entity.TshopGoodsCategory;
import lh.app.shop.core.pageModel.Category;
import lh.app.shop.core.service.CategoryServiceI;
import lh.base.dao.BaseDaoI;
import lh.entity.Tmenu;
import lh.pageModel.TreeJson;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<Category,TshopGoodsCategory> implements
		CategoryServiceI {
	@Resource
	private CategoryDaoI categoryDao;
	@Resource
	private TypeDaoI typeDao;
	/** 
     * 注入DAO 
     */  
    @Resource(name = "categoryDao")  
    public void setBaseDao(BaseDaoI<TshopGoodsCategory> dao) {  
        super.setBaseDao(dao);  
    }  
	@Override
	public List<Category> getTreeGrid() {
		String hql = " from TshopGoodsCategory a order by a.categoryOrder asc";
		List<Category> categorys = this.find(hql);
		if(categorys.size() > 0 && categorys != null){
			for(int i=0;i<categorys.size();i++){
				Category category = new Category();
				category = categorys.get(i);
				category.set_parentId(category.getPid());
				categorys.set(i, category);
			}
		}
		return categorys;
	}
	@Override
	public List<Category> find(String hql) {
		List<TshopGoodsCategory> tcategorys = categoryDao.find(hql);
		List<Category>  categorys = new ArrayList<Category>();
		if(tcategorys != null & tcategorys.size() > 0){
			for(TshopGoodsCategory t:tcategorys){
				Category r = new Category();
				BeanUtils.copyProperties(t,r);
				String pid;
				if (t.getTshopGoodsCategory() != null){
					pid = t.getTshopGoodsCategory().getId();
					r.setPid(pid);
				}
				if (t.getTshopGoodsType() != null){
					r.setTypeId(t.getTshopGoodsType().getId());
					r.setTypeName(t.getTshopGoodsType().getNote());
				}
				categorys.add(r);
			}
			return categorys;
		}
		return null;
	}
	@Override
	public Category getById(String id) {
		TshopGoodsCategory tcategory = categoryDao.getById(id);
		Category category = new Category();
		BeanUtils.copyProperties(tcategory, category);
		if (tcategory.getTshopGoodsCategory() != null){
			category.setPid(tcategory.getTshopGoodsCategory().getId());
		}
		if (tcategory.getTshopGoodsType() != null){
			category.setTypeId(tcategory.getTshopGoodsType().getId());
			category.setTypeName(tcategory.getTshopGoodsType().getNote());
		}
		return category;
	}
	@Override
	public Category update(Category o) {
		TshopGoodsCategory tshopGoodsCategory = categoryDao.getById(o.getId());
		BeanUtils.copyProperties(o,tshopGoodsCategory);
		if (!StringUtils.isBlank(o.getPid())){
			tshopGoodsCategory.setTshopGoodsCategory(categoryDao.getById(o.getPid()));
		}
		if(!StringUtils.isBlank(o.getTypeId())){
			tshopGoodsCategory.setTshopGoodsType(typeDao.getById(o.getTypeId()));
		}
		categoryDao.update(tshopGoodsCategory);
		return o;
	}
	
	@Override
	public Category save(Category o) {
		TshopGoodsCategory tshopGoodsCategory = new TshopGoodsCategory();
		BeanUtils.copyProperties(o,tshopGoodsCategory);
		if (!StringUtils.isBlank(o.getPid())){
			tshopGoodsCategory.setTshopGoodsCategory(categoryDao.getById(o.getPid()));
		}
		if(!StringUtils.isBlank(o.getTypeId())){
			tshopGoodsCategory.setTshopGoodsType(typeDao.getById(o.getTypeId()));
		}
		categoryDao.save(tshopGoodsCategory);
		return o;
	}
	@Override
	public List<TreeJson> getAllTreeNode() {
		List<TshopGoodsCategory> tcategorys = new ArrayList<TshopGoodsCategory>();
		String hql ="from TshopGoodsCategory a order by a.categoryOrder asc";
		tcategorys = categoryDao.find(hql);
		List<TreeJson>  trees = new ArrayList<TreeJson>();
		if(tcategorys!=null && tcategorys.size() > 0){
			for(TshopGoodsCategory s:tcategorys){
				TreeJson tree = new TreeJson();
				tree.setId(s.getId());
				TshopGoodsCategory parent = s.getTshopGoodsCategory();
				if(parent!=null){
					tree.setPid(parent.getId());
				}
				tree.setText(s.getText());
				trees.add(tree);
			}
			return trees;
		}
		return null;
	}
	@Override
	public int saveOrder(String[] ids, BigDecimal[] categoryOrders) {
		int i=0;
		for (String id : ids) {
			TshopGoodsCategory tshopGoodsCategory = categoryDao.getById(id);
			tshopGoodsCategory.setCategoryOrder(categoryOrders[i]);
			i++;
			categoryDao.update(tshopGoodsCategory);
		}
		
		return i;
	}

}
