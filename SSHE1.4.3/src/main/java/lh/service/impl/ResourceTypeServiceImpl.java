package lh.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lh.dao.ResourceTypeDaoI;
import lh.dao.UserDao;
import lh.entity.Tmenu;
import lh.entity.TresourceType;
import lh.pageModel.ResourceType;
import lh.service.ResourceTypeServiceI;
@Service("resourceTypeService")
public class ResourceTypeServiceImpl implements ResourceTypeServiceI {
	@Resource
	private ResourceTypeDaoI resourceTypeDao;
	
	private static final Logger logger = Logger
			.getLogger(ResourceTypeServiceImpl.class);

	@Override
	public List<ResourceType> find(String hql) {
		List<TresourceType> ts = resourceTypeDao.find(hql);
		List<ResourceType>  rs = new ArrayList<ResourceType>();
		for(TresourceType t : ts){
			ResourceType r = new ResourceType();
			BeanUtils.copyProperties(t, r);
			rs.add(r);
		}
		return rs;
	}
	@Override
	public TresourceType getById(String id) {
		return resourceTypeDao.getById(id);
	}
	
}
