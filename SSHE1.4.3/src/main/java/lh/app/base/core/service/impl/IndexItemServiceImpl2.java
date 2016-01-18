package lh.app.base.core.service.impl;
/*package lh.app.base.core.service.impl;

import java.io.Serializable;
import java.util.UUID;

import javax.annotation.Resource;

import lh.BaseServiceImpl;
import lh.app.base.core.dao.IndexItemDaoI;
import lh.app.base.core.entity.TshopIndexItem;
import lh.app.base.core.pageMode.IndexItem;
import lh.app.base.core.service.IndexItemServiceI;
import lh.base.BaseLog;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service("indexItemService")
public class IndexItemServiceImpl extends BaseServiceImpl<IndexItem,TshopIndexItem> implements IndexItemServiceI {
	private Logger logger = BaseLog.getLogger();
	@Resource
	private IndexItemDaoI indexItemDao;
	@Override
	public IndexItem save(IndexItem e) {
		TshopIndexItem shopIndexItem = new TshopIndexItem();
		if(StringUtils.isBlank(e.getId())){
			e.setId(UUID.randomUUID().toString());
		}
		BeanUtils.copyProperties(e,shopIndexItem);
		Serializable s = indexItemDao.save(shopIndexItem);
		return e;
	}
	
}
*/