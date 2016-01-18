package lh.app.shop.core.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

/**
 * TshopGoodsType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TSHOP_GOODS_TYPE")
public class TshopGoodsType implements java.io.Serializable {

	// Fields

	private String id;
	private String note;
	private String props;
	private String params;
	private String disabled_;
	private String haveProp;
	private String haveParm;
	private String joinBrand;
	private Set<TshopGoodsCategory> tshopGoodsCategories = new HashSet<TshopGoodsCategory>(
			0);
	private Set<TshopGoods> tshopGoodses = new HashSet<TshopGoods>(0);

	// Constructors

	/** default constructor */
	public TshopGoodsType() {
	}

	/** full constructor */
	public TshopGoodsType(String note, String props, String params,
			String disabled_, String haveProp, String haveParm,
			String joinBrand, Set<TshopGoodsCategory> tshopGoodsCategories,
			Set<TshopGoods> tshopGoodses) {
		this.note = note;
		this.props = props;
		this.params = params;
		this.disabled_ = disabled_;
		this.haveProp = haveProp;
		this.haveParm = haveParm;
		this.joinBrand = joinBrand;
		this.tshopGoodsCategories = tshopGoodsCategories;
		this.tshopGoodses = tshopGoodses;
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

	@Column(name = "NOTE")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "PROPS", length = 2000)
	public String getProps() {
		return this.props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	@Column(name = "PARAMS", length = 2000)
	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "DISABLED", length = 1)
	public String getDisabled_() {
		return this.disabled_;
	}

	public void setDisabled_(String disabled_) {
		this.disabled_ = disabled_;
	}

	@Column(name = "HAVE_PROP", length = 1)
	public String getHaveProp() {
		return this.haveProp;
	}

	public void setHaveProp(String haveProp) {
		this.haveProp = haveProp;
	}

	@Column(name = "HAVE_PARM", length = 1)
	public String getHaveParm() {
		return this.haveParm;
	}

	public void setHaveParm(String haveParm) {
		this.haveParm = haveParm;
	}

	@Column(name = "JOIN_BRAND", length = 1)
	public String getJoinBrand() {
		return this.joinBrand;
	}

	public void setJoinBrand(String joinBrand) {
		this.joinBrand = joinBrand;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tshopGoodsType")
	public Set<TshopGoodsCategory> getTshopGoodsCategories() {
		return this.tshopGoodsCategories;
	}

	public void setTshopGoodsCategories(
			Set<TshopGoodsCategory> tshopGoodsCategories) {
		this.tshopGoodsCategories = tshopGoodsCategories;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tshopGoodsType")
	public Set<TshopGoods> getTshopGoodses() {
		return this.tshopGoodses;
	}

	public void setTshopGoodses(Set<TshopGoods> tshopGoodses) {
		this.tshopGoodses = tshopGoodses;
	}

}