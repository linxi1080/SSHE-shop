package lh.service;

import java.util.List;
import java.util.Map;

import lh.entity.Tmenu;
import lh.entity.TresourceType;
import lh.pageModel.Menu;
import lh.pageModel.ResourceType;

public interface ResourceTypeServiceI {

	List<ResourceType> find(String hql);

	TresourceType getById(String id);
}
