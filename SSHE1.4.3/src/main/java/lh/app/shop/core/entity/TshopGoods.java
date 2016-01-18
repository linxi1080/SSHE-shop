package lh.app.shop.core.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * TshopGoods entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TSHOP_GOODS")
public class TshopGoods implements java.io.Serializable {

	// Fields

	private String goodsId;
	private TshopGoodsType tshopGoodsType;
	private TshopGoodsBrand tshopGoodsBrand;
	private TshopGoodsCategory tshopGoodsCategory;
	private String adjuncts;
	private String big;
	private String brief;
	private long buyCount;
	private double cost;
	private Date createTime;
	private Integer disabled;
	private String goodsComment;
	private String goodsType;
	private long grade;
	private Integer haveField;
	private Integer haveSpec;
	private String intro;
	private Integer isPack;
	private Date lastModify;
	private Integer marketEnable;
	private String metaDescription;
	private String metaKeywords;
	private double mktprice;
	private String name;
	private String original;
	private String p1;
	private String p10;
	private String p11;
	private String p12;
	private String p13;
	private String p14;
	private String p15;
	private String p16;
	private String p17;
	private String p18;
	private String p19;
	private String p2;
	private String p20;
	private String p3;
	private String p4;
	private String p5;
	private String p6;
	private String p7;
	private String p8;
	private String p9;
	private String pageTitle;
	private String params;
	private long point;
	private double price;
	private String small;
	private String sn;
	private long sord;
	private String specs;
	private long store;
	private String thumbnail;
	private String unit;
	private long viewCount;
	private double weight;

	// Constructors

	/** default constructor */
	public TshopGoods() {
	}

	/** full constructor */
	public TshopGoods(TshopGoodsType tshopGoodsType,
			TshopGoodsBrand tshopGoodsBrand,
			TshopGoodsCategory tshopGoodsCategory, String adjuncts, String big,
			String brief, long buyCount, double cost, Date createTime,
			Integer disabled, String goodsComment, String goodsType,
			long grade, Integer haveField, Integer haveSpec, String intro,
			Integer isPack, Date lastModify, Integer marketEnable,
			String metaDescription, String metaKeywords, double mktprice,
			String name, String original, String p1, String p10, String p11,
			String p12, String p13, String p14, String p15, String p16,
			String p17, String p18, String p19, String p2, String p20,
			String p3, String p4, String p5, String p6, String p7, String p8,
			String p9, String pageTitle, String params, long point,
			double price, String small, String sn, long sord, String specs,
			long store, String thumbnail, String unit, long viewCount,
			double weight) {
		this.tshopGoodsType = tshopGoodsType;
		this.tshopGoodsBrand = tshopGoodsBrand;
		this.tshopGoodsCategory = tshopGoodsCategory;
		this.adjuncts = adjuncts;
		this.big = big;
		this.brief = brief;
		this.buyCount = buyCount;
		this.cost = cost;
		this.createTime = createTime;
		this.disabled = disabled;
		this.goodsComment = goodsComment;
		this.goodsType = goodsType;
		this.grade = grade;
		this.haveField = haveField;
		this.haveSpec = haveSpec;
		this.intro = intro;
		this.isPack = isPack;
		this.lastModify = lastModify;
		this.marketEnable = marketEnable;
		this.metaDescription = metaDescription;
		this.metaKeywords = metaKeywords;
		this.mktprice = mktprice;
		this.name = name;
		this.original = original;
		this.p1 = p1;
		this.p10 = p10;
		this.p11 = p11;
		this.p12 = p12;
		this.p13 = p13;
		this.p14 = p14;
		this.p15 = p15;
		this.p16 = p16;
		this.p17 = p17;
		this.p18 = p18;
		this.p19 = p19;
		this.p2 = p2;
		this.p20 = p20;
		this.p3 = p3;
		this.p4 = p4;
		this.p5 = p5;
		this.p6 = p6;
		this.p7 = p7;
		this.p8 = p8;
		this.p9 = p9;
		this.pageTitle = pageTitle;
		this.params = params;
		this.point = point;
		this.price = price;
		this.small = small;
		this.sn = sn;
		this.sord = sord;
		this.specs = specs;
		this.store = store;
		this.thumbnail = thumbnail;
		this.unit = unit;
		this.viewCount = viewCount;
		this.weight = weight;
	}

	// Property accessors
//	@GenericGenerator(name = "generator", strategy = "uuid.string")
	@Id
//	@GeneratedValue(generator = "generator")
	@Column(name = "GOODS_ID", unique = true, nullable = false, length = 50)
	public String getGoodsId() {
		if(!StringUtils.isBlank(this.goodsId)){
			//非空返回id
			return this.goodsId;
		}
		//为空返回UUID
		return UUID.randomUUID().toString();
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPE_ID")
	public TshopGoodsType getTshopGoodsType() {
		return this.tshopGoodsType;
	}

	public void setTshopGoodsType(TshopGoodsType tshopGoodsType) {
		this.tshopGoodsType = tshopGoodsType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRAND_ID")
	public TshopGoodsBrand getTshopGoodsBrand() {
		return this.tshopGoodsBrand;
	}

	public void setTshopGoodsBrand(TshopGoodsBrand tshopGoodsBrand) {
		this.tshopGoodsBrand = tshopGoodsBrand;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAT_ID")
	public TshopGoodsCategory getTshopGoodsCategory() {
		return this.tshopGoodsCategory;
	}

	public void setTshopGoodsCategory(TshopGoodsCategory tshopGoodsCategory) {
		this.tshopGoodsCategory = tshopGoodsCategory;
	}

	@Column(name = "ADJUNCTS")
	public String getAdjuncts() {
		return this.adjuncts;
	}

	public void setAdjuncts(String adjuncts) {
		this.adjuncts = adjuncts;
	}

	@Column(name = "BIG")
	public String getBig() {
		return this.big;
	}

	public void setBig(String big) {
		this.big = big;
	}

	@Column(name = "BRIEF")
	public String getBrief() {
		return this.brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	@Column(name = "BUY_COUNT", precision = 10, scale = 0)
	public long getBuyCount() {
		return this.buyCount;
	}

	public void setBuyCount(long buyCount) {
		this.buyCount = buyCount;
	}

	@Column(name = "COST", precision = 22, scale = 6)
	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "DISABLED", precision = 5, scale = 0)
	public Integer getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	@Column(name = "GOODS_COMMENT")
	public String getGoodsComment() {
		return this.goodsComment;
	}

	public void setGoodsComment(String goodsComment) {
		this.goodsComment = goodsComment;
	}

	@Column(name = "GOODS_TYPE", length = 6)
	public String getGoodsType() {
		return this.goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	@Column(name = "GRADE", precision = 10, scale = 0)
	public long getGrade() {
		return this.grade;
	}

	public void setGrade(long grade) {
		this.grade = grade;
	}

	@Column(name = "HAVE_FIELD", precision = 5, scale = 0)
	public Integer getHaveField() {
		return this.haveField;
	}

	public void setHaveField(Integer haveField) {
		this.haveField = haveField;
	}

	@Column(name = "HAVE_SPEC", precision = 5, scale = 0)
	public Integer getHaveSpec() {
		return this.haveSpec;
	}

	public void setHaveSpec(Integer haveSpec) {
		this.haveSpec = haveSpec;
	}

	@Column(name = "INTRO")
	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Column(name = "IS_PACK", precision = 5, scale = 0)
	public Integer getIsPack() {
		return this.isPack;
	}

	public void setIsPack(Integer isPack) {
		this.isPack = isPack;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFY", length = 7)
	public Date getLastModify() {
		return this.lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	@Column(name = "MARKET_ENABLE", precision = 5, scale = 0)
	public Integer getMarketEnable() {
		return this.marketEnable;
	}

	public void setMarketEnable(Integer marketEnable) {
		this.marketEnable = marketEnable;
	}

	@Column(name = "META_DESCRIPTION", length = 1000)
	public String getMetaDescription() {
		return this.metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@Column(name = "META_KEYWORDS", length = 1000)
	public String getMetaKeywords() {
		return this.metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(name = "MKTPRICE", precision = 22, scale = 6)
	public double getMktprice() {
		return this.mktprice;
	}

	public void setMktprice(double mktprice) {
		this.mktprice = mktprice;
	}

	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ORIGINAL")
	public String getOriginal() {
		return this.original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	@Column(name = "P1")
	public String getP1() {
		return this.p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	@Column(name = "P10")
	public String getP10() {
		return this.p10;
	}

	public void setP10(String p10) {
		this.p10 = p10;
	}

	@Column(name = "P11")
	public String getP11() {
		return this.p11;
	}

	public void setP11(String p11) {
		this.p11 = p11;
	}

	@Column(name = "P12")
	public String getP12() {
		return this.p12;
	}

	public void setP12(String p12) {
		this.p12 = p12;
	}

	@Column(name = "P13")
	public String getP13() {
		return this.p13;
	}

	public void setP13(String p13) {
		this.p13 = p13;
	}

	@Column(name = "P14")
	public String getP14() {
		return this.p14;
	}

	public void setP14(String p14) {
		this.p14 = p14;
	}

	@Column(name = "P15")
	public String getP15() {
		return this.p15;
	}

	public void setP15(String p15) {
		this.p15 = p15;
	}

	@Column(name = "P16")
	public String getP16() {
		return this.p16;
	}

	public void setP16(String p16) {
		this.p16 = p16;
	}

	@Column(name = "P17")
	public String getP17() {
		return this.p17;
	}

	public void setP17(String p17) {
		this.p17 = p17;
	}

	@Column(name = "P18")
	public String getP18() {
		return this.p18;
	}

	public void setP18(String p18) {
		this.p18 = p18;
	}

	@Column(name = "P19")
	public String getP19() {
		return this.p19;
	}

	public void setP19(String p19) {
		this.p19 = p19;
	}

	@Column(name = "P2")
	public String getP2() {
		return this.p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	@Column(name = "P20")
	public String getP20() {
		return this.p20;
	}

	public void setP20(String p20) {
		this.p20 = p20;
	}

	@Column(name = "P3")
	public String getP3() {
		return this.p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	@Column(name = "P4")
	public String getP4() {
		return this.p4;
	}

	public void setP4(String p4) {
		this.p4 = p4;
	}

	@Column(name = "P5")
	public String getP5() {
		return this.p5;
	}

	public void setP5(String p5) {
		this.p5 = p5;
	}

	@Column(name = "P6")
	public String getP6() {
		return this.p6;
	}

	public void setP6(String p6) {
		this.p6 = p6;
	}

	@Column(name = "P7")
	public String getP7() {
		return this.p7;
	}

	public void setP7(String p7) {
		this.p7 = p7;
	}

	@Column(name = "P8")
	public String getP8() {
		return this.p8;
	}

	public void setP8(String p8) {
		this.p8 = p8;
	}

	@Column(name = "P9")
	public String getP9() {
		return this.p9;
	}

	public void setP9(String p9) {
		this.p9 = p9;
	}

	@Column(name = "PAGE_TITLE")
	public String getPageTitle() {
		return this.pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	@Column(name = "PARAMS")
	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Column(name = "POINT", precision = 10, scale = 0)
	public long getPoint() {
		return this.point;
	}

	public void setPoint(long point) {
		this.point = point;
	}

	@Column(name = "PRICE", precision = 22, scale = 6)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "SMALL")
	public String getSmall() {
		return this.small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	@Column(name = "SN", length = 200)
	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "SORD", precision = 10, scale = 0)
	public long getSord() {
		return this.sord;
	}

	public void setSord(long sord) {
		this.sord = sord;
	}

	@Column(name = "SPECS")
	public String getSpecs() {
		return this.specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	@Column(name = "STORE", precision = 10, scale = 0)
	public long getStore() {
		return this.store;
	}

	public void setStore(long store) {
		this.store = store;
	}

	@Column(name = "THUMBNAIL")
	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Column(name = "UNIT", length = 20)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "VIEW_COUNT", precision = 10, scale = 0)
	public long getViewCount() {
		return this.viewCount;
	}

	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}

	@Column(name = "WEIGHT", precision = 22, scale = 6)
	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}