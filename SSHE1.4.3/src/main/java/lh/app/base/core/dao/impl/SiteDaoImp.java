package lh.app.base.core.dao.impl;

import lh.app.base.core.dao.SiteDaoI;
import lh.app.base.core.entity.TshopSite;
import lh.base.dao.impl.BaseDaoImpl;

import org.springframework.stereotype.Repository;
@Repository("siteDao")
public class SiteDaoImp extends BaseDaoImpl<TshopSite> implements SiteDaoI {

}
