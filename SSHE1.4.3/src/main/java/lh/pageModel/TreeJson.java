package lh.pageModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * easui中的tree_data.json数据,
 * 只能有一个root节点 
 * 	[{ "id":1, 
 * 		"text":"Folder1",
 * 		"iconCls":"icon-save", 
 * 		"children":[
 * 			{ 
 * 				"text":"File1", 
 * 		      	"checked":true 
 * 			}
 * 		] }]
 * 提供静态方法formatTree(List<TreeJson> list) 返回结果 TreeJson.formatTree(treeJsonlist)
 * ;
 * 
 * @author linxi1080
 * 
 */
public class TreeJson implements Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String id;
	private String pid;
	private String text;
	private String iconCls;
	//当state为closed是默认图标为 文件夹图标，open，且没有children 默认为文件图标
	private String state = "open";//open,closed
	private String checked;
	private Map<String, Object> attributes;
	private List<TreeJson> children = new ArrayList<TreeJson>();
	
	public TreeJson() {
	}
	
	public TreeJson(String id, String text, String pid) {
		this.id = id;
		this.pid = pid;
		this.text = text;
	}

	/******** setter and getter **********/
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public List<TreeJson> getChildren() {
		return children;
	}

	public void setChildren(List<TreeJson> children) {
		this.children = children;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
