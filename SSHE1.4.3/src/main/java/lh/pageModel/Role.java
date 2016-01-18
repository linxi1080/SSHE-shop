package lh.pageModel;

import java.io.Serializable;
import java.util.Date;


public class Role implements Serializable{

	private String id;
	private String name;
	private String notes;
	private String menuIds;
	private String menuTexts;
	private String userIds;
	private String ids;
	
	private Date createdatetime;
	private Date modifydatetime;
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	public Date getModifydatetime() {
		return modifydatetime;
	}
	public void setModifydatetime(Date modifydatetime) {
		this.modifydatetime = modifydatetime;
	}
	public String getMenuTexts() {
		return menuTexts;
	}
	public void setMenuTexts(String menuTexts) {
		this.menuTexts = menuTexts;
	}
}
