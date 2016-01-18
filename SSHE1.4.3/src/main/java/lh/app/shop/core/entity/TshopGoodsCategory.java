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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * TshopGoodsCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TSHOP_GOODS_CATEGORY")
public class TshopGoodsCategory implements java.io.Serializable {
	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = -2439416052950312257L;
	private String id;
	private TshopGoodsType tshopGoodsType;
	private TshopGoodsCategory tshopGoodsCategory;
	private String text;
	private String categoryPath;
	private BigDecimal goodsCount;
	private BigDecimal categoryOrder;
	private String listShow;
	private String imgPath;
	private Set<TshopGoods> tshopGoodses = new HashSet<TshopGoods>(0);
	private Set<TshopGoodsCategory> tshopGoodsCategories = new HashSet<TshopGoodsCategory>(
			0);
	// Constructors
	/** default constructor */
	public TshopGoodsCategory() {
	}

	/** full constructor */
	public TshopGoodsCategory(TshopGoodsType tshopGoodsType,
			TshopGoodsCategory tshopGoodsCategory, String text,
			String categoryPath, BigDecimal goodsCount,
			BigDecimal categoryOrder, String listShow, String imgPath,
			Set<TshopGoods> tshopGoodses,
			Set<TshopGoodsCategory> tshopGoodsCategories) {
		this.tshopGoodsType = tshopGoodsType;
		this.tshopGoodsCategory = tshopGoodsCategory;
		this.text = text;
		this.categoryPath = categoryPath;
		this.goodsCount = goodsCount;
		this.categoryOrder = categoryOrder;
		this.listShow = listShow;
		this.imgPath = imgPath;
		this.tshopGoodses = tshopGoodses;
		this.tshopGoodsCategories = tshopGoodsCategories;
	}

	// Property accessors
//	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
//	@GeneratedValue(generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		if(!StringUtils.isBlank(this.id)){
			return this.id;
		}
		return UUID.randomUUID().toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GOODS_TYPE_ID")
	public TshopGoodsType getTshopGoodsType() {
		return this.tshopGoodsType;
	}

	public void setTshopGoodsType(TshopGoodsType tshopGoodsType) {
		this.tshopGoodsType = tshopGoodsType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	public TshopGoodsCategory getTshopGoodsCategory() {
		return this.tshopGoodsCategory;
	}

	public void setTshopGoodsCategory(TshopGoodsCategory tshopGoodsCategory) {
		this.tshopGoodsCategory = tshopGoodsCategory;
	}

	@Column(name = "NOTE")
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "CATEGORY_PATH", length = 2000)
	public String getCategoryPath() {
		return this.categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	@Column(name = "GOODS_COUNT", precision = 22, scale = 0)
	public BigDecimal getGoodsCount() {
		return this.goodsCount;
	}

	public void setGoodsCount(BigDecimal goodsCount) {
		this.goodsCount = goodsCount;
	}

	@Column(name = "CATEGORY_ORDER", precision = 22, scale = 0)
	public BigDecimal getCategoryOrder() {
		return this.categoryOrder;
	}

	public void setCategoryOrder(BigDecimal categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	@Column(name = "LIST_SHOW", length = 1)
	public String getListShow() {
		return this.listShow;
	}

	public void setListShow(String listShow) {
		this.listShow = listShow;
	}

	@Column(name = "IMAGE")
	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tshopGoodsCategory")
	public Set<TshopGoods> getTshopGoodses() {
		return this.tshopGoodses;
	}

	public void setTshopGoodses(Set<TshopGoods> tshopGoodses) {
		this.tshopGoodses = tshopGoodses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tshopGoodsCategory")
	public Set<TshopGoodsCategory> getTshopGoodsCategories() {
		return this.tshopGoodsCategories;
	}

	public void setTshopGoodsCategories(
			Set<TshopGoodsCategory> tshopGoodsCategories) {
		this.tshopGoodsCategories = tshopGoodsCategories;
	}

}