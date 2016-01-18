package lh.pageModel;

import com.alibaba.fastjson.annotation.JSONField;

public class Dept {
	private String ids;
	private String id;
	private String pid;
	private String parentName;
	private String deptNo;
	private String name;
	private String notes;
	private String stateId;
	private String address;
	private String iconCls;
	private String tuserIds;
	private String tuserName;
	private String tdeptIds;
	private String tdeptNames;
	private String _parentId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
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
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getTuserIds() {
		return tuserIds;
	}
	public void setTuserIds(String tuserIds) {
		this.tuserIds = tuserIds;
	}
	public String getTuserName() {
		return tuserName;
	}
	public void setTuserName(String tuserName) {
		this.tuserName = tuserName;
	}
	public String getTdeptIds() {
		return tdeptIds;
	}
	public void setTdeptIds(String tdeptIds) {
		this.tdeptIds = tdeptIds;
	}
	public String getTdeptNames() {
		return tdeptNames;
	}
	public void setTdeptNames(String tdeptNames) {
		this.tdeptNames = tdeptNames;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	//定义序列化Json后的名称，如果不定义，名称会把下划线去掉
	@JSONField(name="_parentId")
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
