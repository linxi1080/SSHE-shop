package lh.pageModel;

import java.io.Serializable;

public class ResourceType implements Serializable{
	private String id;
	private String name;
	private String description;
	private String menuIds;
	private String menuNames;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public String getMenuNames() {
		return menuNames;
	}
	public void setMenuNames(String menuNames) {
		this.menuNames = menuNames;
	}
	
}
