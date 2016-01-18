package lh.app.base.core.dao.impl;

import lh.app.base.core.dao.IndexItemDaoI;
import lh.app.base.core.entity.TshopIndexItem;
import lh.base.dao.impl.BaseDaoImpl;

import org.springframework.stereotype.Repository;
@Repository("indexItemDao")
public class IndexItemDaoImpl extends BaseDaoImpl<TshopIndexItem> implements IndexItemDaoI {

}
