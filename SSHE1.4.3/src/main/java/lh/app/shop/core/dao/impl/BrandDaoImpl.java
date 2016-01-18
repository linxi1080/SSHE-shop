package lh.app.shop.core.dao.impl;

import org.springframework.stereotype.Repository;

import lh.app.shop.core.dao.BrandDaoI;
import lh.app.shop.core.entity.TshopGoodsBrand;
import lh.base.dao.impl.BaseDaoImpl;
@Repository("brandDao")
public class BrandDaoImpl extends BaseDaoImpl<TshopGoodsBrand> implements BrandDaoI {

}
