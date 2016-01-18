package lh.app.base.core.service.impl;

import javax.annotation.Resource;

import lh.app.base.core.base.service.impl.BaseServiceImpl;
import lh.app.base.core.dao.SiteDaoI;
import lh.app.base.core.entity.TshopSite;
import lh.app.base.core.pageModel.Site;
import lh.app.base.core.service.SiteServiceI;
import lh.base.dao.BaseDaoI;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service("siteService")
public class SiteServiceImpl extends BaseServiceImpl<Site,TshopSite> implements SiteServiceI {
	@Resource
	private SiteDaoI siteDao;
	/** 
     * 注入DAO 
     */  
    @Resource(name = "siteDao")  
    public void setBaseDao(BaseDaoI<TshopSite> dao) {  
        super.setBaseDao(dao);  
    }  
	/*@Override
	public Site save(Site site) {
		TshopSite tsite = new TshopSite();
		BeanUtils.copyProperties(site, tsite);
		siteDao.save(tsite);
		return site;
	}*/

	/*@Override
	public Site update(Site site) {
		TshopSite tsite = siteDao.getById(site.getId());
		BeanUtils.copyProperties(site, tsite);
		siteDao.update(tsite);
		return site;
	}*/

}
