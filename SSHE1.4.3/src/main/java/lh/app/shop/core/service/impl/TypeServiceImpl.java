package lh.app.shop.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lh.app.base.core.base.service.impl.BaseServiceImpl;
import lh.app.shop.core.dao.TypeDaoI;
import lh.app.shop.core.entity.TshopGoodsType;
import lh.app.shop.core.pageModel.Type;
import lh.app.shop.core.service.TypeServiceI;
import lh.base.dao.BaseDaoI;
@Service("typeService")
public class TypeServiceImpl extends BaseServiceImpl<Type, TshopGoodsType> implements
		TypeServiceI {
	@Resource
	private TypeDaoI typeDao;
	/** 
     * 注入DAO 
     */  
    @Resource(name = "typeDao")  
    public void setBaseDao(BaseDaoI<TshopGoodsType> dao) {  
        super.setBaseDao(dao);  
    }
}
