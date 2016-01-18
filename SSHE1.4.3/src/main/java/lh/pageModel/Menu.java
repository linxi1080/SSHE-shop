package lh.pageModel;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;


public class Menu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1098216532178211225L;
	private String pid;
	private String ptext;
	private String id;
	private String iconCls;
	private String url;
	private String text;
	private String useFlag="Y";
	private Long seqNo;
	private String roleIds ;
	private String roleNames;
	private String childrenIds;
	private String ids;
	private String notes;
	private String tresourceTypeId;
	private String tresourceTypeName;
	private String _parentId;
	private String state = "open"; //open,closed
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getChildrenIds() {
		return childrenIds;
	}
	public void setChildrenIds(String childrenIds) {
		this.childrenIds = childrenIds;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPtext() {
		return ptext;
	}
	public void setPtext(String ptext) {
		this.ptext = ptext;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public Long getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTresourceTypeId() {
		return tresourceTypeId;
	}
	public void setTresourceTypeId(String tresourceTypeId) {
		this.tresourceTypeId = tresourceTypeId;
	}
	public String getTresourceTypeName() {
		return tresourceTypeName;
	}
	public void setTresourceTypeName(String tresourceTypeName) {
		this.tresourceTypeName = tresourceTypeName;
	}
	
}
