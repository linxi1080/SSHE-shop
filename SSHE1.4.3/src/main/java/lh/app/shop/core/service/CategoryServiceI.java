package lh.app.shop.core.service;

import java.math.BigDecimal;
import java.util.List;

import lh.app.base.core.base.service.BaseServiceI;
import lh.app.shop.core.entity.TshopGoodsCategory;
import lh.app.shop.core.pageModel.Category;
import lh.pageModel.TreeJson;

public interface CategoryServiceI extends BaseServiceI<Category, TshopGoodsCategory> {

	List<Category> getTreeGrid();
	/**
	 * 获取全部的菜单节点，使用扁平化的简单tree数据形式，但是必须有pid属性
	 */
	List<TreeJson> getAllTreeNode();
	int saveOrder(String[] ids, BigDecimal[] categoryOrders);

}
