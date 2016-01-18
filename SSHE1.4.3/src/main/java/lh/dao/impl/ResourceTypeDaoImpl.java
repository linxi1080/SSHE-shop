package lh.dao.impl;

import org.springframework.stereotype.Repository;

import lh.base.dao.impl.BaseDaoImpl;
import lh.dao.ResourceTypeDaoI;
import lh.entity.TresourceType;
@Repository("resourceTypeDao")
public class ResourceTypeDaoImpl extends BaseDaoImpl<TresourceType> implements
		ResourceTypeDaoI {

}
