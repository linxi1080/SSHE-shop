package lh.app.shop.core.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * TshopGoodsBrand entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TSHOP_GOODS_BRAND")
public class TshopGoodsBrand implements java.io.Serializable {

	// Fields

	private String id; //ID
	private BigDecimal sortNo; //排序
	private String note; //名称
	private String logo; //品牌logo
	private String keywords; //品牌搜索关键字
	private String brief; //介绍说明
	private String url;   //品牌url
	private String disabled = "Y"; //启用标志
	private Set<TshopGoods> tshopGoodses = new HashSet<TshopGoods>(0);

	// Constructors

	/** default constructor */
	public TshopGoodsBrand() {
	}

	/** full constructor */
	public TshopGoodsBrand(BigDecimal sortNo, String note, String logo,
			String keywords, String brief, String url, String disabled,
			Set<TshopGoods> tshopGoodses) {
		this.sortNo = sortNo;
		this.note = note;
		this.logo = logo;
		this.keywords = keywords;
		this.brief = brief;
		this.url = url;
		this.disabled = disabled;
		this.tshopGoodses = tshopGoodses;
	}

	// Property accessors
//	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
//	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		if (!StringUtils.isBlank(this.id)){
			return this.id;
		}
		return UUID.randomUUID().toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "SORT_NO", precision = 22, scale = 0)
	public BigDecimal getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(BigDecimal sortNo) {
		this.sortNo = sortNo;
	}

	@Column(name = "NOTE", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "LOGO")
	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "KEYWORDS", length = 2000)
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "BRIEF", length = 2000)
	public String getBrief() {
		return this.brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Column(name = "URL")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "DISABLED", length = 1)
	public String getDisabled() {
		return this.disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tshopGoodsBrand")
	public Set<TshopGoods> getTshopGoodses() {
		return this.tshopGoodses;
	}

	public void setTshopGoodses(Set<TshopGoods> tshopGoodses) {
		this.tshopGoodses = tshopGoodses;
	}

}