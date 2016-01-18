package lh.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.base.action.BaseAction;
import lh.pageModel.ResourceType;
import lh.service.ResourceTypeServiceI;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
@ParentPackage("basePackage")
@Namespace("/")
@Action(value = "resourceTypeAction")
public class ResourceTypeAction extends BaseAction<ResourceType> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7019546856712995679L;
	private static final Logger logger = Logger
			.getLogger(ResourceTypeAction.class);
	@Resource
	private ResourceTypeServiceI resourceTypeService;
	/**
	 * 查询
	 */
	public void doNotNeedGrant_find(){
		String hql = " from TresourceType ";
		
		List<ResourceType> resourceTypes = resourceTypeService.find(hql);
		writeJson(resourceTypes);
	}
}
