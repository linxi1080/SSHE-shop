package lh.app.shop.core.pageModel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lh.app.shop.core.entity.TshopGoods;

public class Brand {
	private String id;
	private BigDecimal sortNo;
	private String note;
	private String logo;
	private String keywords;
	private String brief;
	private String url;
	private String disabled;
	private String goodsIds;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getSortNo() {
		return sortNo;
	}
	public void setSortNo(BigDecimal sortNo) {
		this.sortNo = sortNo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getGoodsIds() {
		return goodsIds;
	}
	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}
	
}
