package lh.app.base.core.pageModel;

import java.math.BigDecimal;

public class IndexItem {
	private String id;
	private String title;
	private String url;
	private BigDecimal sortNo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BigDecimal getSortNo() {
		return sortNo;
	}
	public void setSortNo(BigDecimal sortNo) {
		this.sortNo = sortNo;
	}
	
}
