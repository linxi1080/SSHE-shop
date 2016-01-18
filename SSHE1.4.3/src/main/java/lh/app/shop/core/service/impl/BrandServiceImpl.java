package lh.app.shop.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import lh.app.base.core.base.service.impl.BaseServiceImpl;
import lh.app.shop.core.dao.BrandDaoI;
import lh.app.shop.core.entity.TshopGoodsBrand;
import lh.app.shop.core.entity.TshopGoodsType;
import lh.app.shop.core.pageModel.Brand;
import lh.app.shop.core.service.BrandServiceI;
import lh.base.dao.BaseDaoI;
@Service("brandService")
public class BrandServiceImpl extends BaseServiceImpl<Brand,TshopGoodsBrand> implements
		BrandServiceI {
	@Resource
	private BrandDaoI brandDao;
	/** 
     * 注入DAO 
     */  
    @Resource(name = "brandDao")  
    public void setBaseDao(BaseDaoI<TshopGoodsBrand> dao) {  
        super.setBaseDao(dao);  
    }
}
