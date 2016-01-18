package lh.app.shop.core.dao.impl;

import lh.app.shop.core.dao.TypeDaoI;
import lh.app.shop.core.entity.TshopGoodsType;
import lh.base.dao.impl.BaseDaoImpl;

import org.springframework.stereotype.Repository;
@Repository("typeDao")
public class TypeDaoImpl extends BaseDaoImpl<TshopGoodsType> implements TypeDaoI {

}
