package lh.app.base.core.service.impl;

import javax.annotation.Resource;

import lh.app.base.core.entity.TshopIndexItem;
import lh.app.base.core.service.IndexItemServiceI;
import lh.base.dao.BaseDaoI;
import lh.base.service.impl.BaseServiceImpl;

import org.springframework.stereotype.Service;
@Service("indexItemService")
//由于使用spring管理事务并且配置了该类对应的包所以可以不用事务注解
//@Transactional 
public class IndexItemServiceImpl extends BaseServiceImpl<TshopIndexItem>implements
		IndexItemServiceI {
	//可以不用用定义Dao属性，使用@Resource(name = "indexItemDao")配合setBaseDao方法注入
//	@Resource
//	IndexItemDaoI indexItemDao;
	/** 
     * 注入DAO 
     */  
    @Resource(name = "indexItemDao")  
    public void setBaseDao(BaseDaoI<TshopIndexItem> dao) {  
        super.setBaseDao(dao);  
    }  
	
}
