package lh.app.base.core.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * TshopIndexItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TSHOP_INDEX_ITEM")
public class TshopIndexItem implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String url;
	private BigDecimal sortNo;

	// Constructors

	/** default constructor */
	public TshopIndexItem() {
	}

	/** full constructor */
	public TshopIndexItem(String title, String url, BigDecimal sortNo) {
		this.title = title;
		this.url = url;
		this.sortNo = sortNo;
	}

	// Property accessors
//	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
//	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		if(!StringUtils.isBlank(this.id)){//非空返回id
			return this.id;
		}
		return UUID.randomUUID().toString();//为空返回UUID
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "URL")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "SORT_NO", precision = 22, scale = 0)
	public BigDecimal getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(BigDecimal sortNo) {
		this.sortNo = sortNo;
	}

}