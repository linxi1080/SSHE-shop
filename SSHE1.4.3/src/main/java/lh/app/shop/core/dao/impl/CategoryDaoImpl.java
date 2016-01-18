package lh.app.shop.core.dao.impl;

import lh.app.shop.core.dao.CategoryDaoI;
import lh.app.shop.core.entity.TshopGoodsCategory;
import lh.base.dao.impl.BaseDaoImpl;

import org.springframework.stereotype.Repository;
@Repository("categoryDao")
public class CategoryDaoImpl extends BaseDaoImpl<TshopGoodsCategory> implements CategoryDaoI {

}
