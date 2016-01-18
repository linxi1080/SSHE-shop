package lh.app.shop.core.pageModel;

import java.io.File;
import java.math.BigDecimal;

import com.alibaba.fastjson.annotation.JSONField;

public class Category {
	private String id;
	private String typeId;
	private String typeName;
	private String text;
	private String categoryPath;
	private BigDecimal goodsCount;
	private BigDecimal categoryOrder;
	private String listShow;
	private File   image;
	private String goodsIds;
	private String goodsNames;
	private String pid;
	private String parentName;
	private String categoryIds;
	private String categoryNames;
	private String _parentId;
	private String imageFileName;
	private String imgPath;
	private String[] ids;
	private BigDecimal[] categoryOrders;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCategoryPath() {
		return categoryPath;
	}
	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}
	public BigDecimal getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(BigDecimal goodsCount) {
		this.goodsCount = goodsCount;
	}
	public BigDecimal getCategoryOrder() {
		if (categoryOrder == null){
			return new BigDecimal(0);
		}
		return categoryOrder;
	}
	public void setCategoryOrder(BigDecimal categoryOrder) {
		if (categoryOrder ==null){
			this.categoryOrder = new BigDecimal(0);
		}else{
			this.categoryOrder = categoryOrder;
		}
	}
	public String getListShow() {
		return listShow;
	}
	public void setListShow(String listShow) {
		this.listShow = listShow;
	}
	public String getGoodsIds() {
		return goodsIds;
	}
	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}
	public String getCategoryNames() {
		return categoryNames;
	}
	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}
	//定义序列化Json后的名称，如果不定义，名称会把下划线去掉
	@JSONField(name="_parentId")
	public String get_parentId() {
		return _parentId;
	}
	public void set_parentId(String _parentId) {
		this._parentId = _parentId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getGoodsNames() {
		return goodsNames;
	}
	public void setGoodsNames(String goodsNames) {
		this.goodsNames = goodsNames;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public BigDecimal[] getCategoryOrders() {
		return categoryOrders;
	}
	public void setCategoryOrders(BigDecimal[] categoryOrders) {
		this.categoryOrders = categoryOrders;
	}
	
}
