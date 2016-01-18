package lh.common.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * EsOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tshop_goods_order")
public class TshopGoodsOrder implements java.io.Serializable {

	// Fields

	private String orderId;
	private String sn;
	private Integer memberId;
	private short status;
	private short payStatus;
	private short shipStatus;
	private Integer shippingId;
	private String shippingType;
	private String shippingArea;
	private Integer paymentId;
	private String paymentName;
	private String paymentType;
	private double paymoney;
	private String goods;
	private Date createTime;
	private String shipName;
	private String shipAddr;
	private String shipZip;
	private String shipEmail;
	private String shipMobile;
	private String shipTel;
	private String shipDay;
	private String shipTime;
	private String isProtect;
	private double protectPrice;
	private double goodsAmount;
	private double shippingAmount;
	private double orderAmount;
	private double weight;
	private Integer goodsNum;
	private Integer gainedpoint;
	private Integer consumepoint;
	private String remark;
	private String disabled;
	private double discount;
	private short imported;
	private short pimported;
	private Integer completeTime;
	private String cancelReason;
	private Integer signingTime;
	private String theSign;
	private Integer allocationTime;
	private Integer shipProvinceid;
	private Integer shipCityid;
	private Integer shipRegionid;
	private short saleCmpl;
	private Integer saleCmplTime;
	private Integer depotid;
	private String adminRemark;
	private double needPayMoney;
	private String shipNo;
	private Integer addressId;

	// Constructors

	/** default constructor */
	public TshopGoodsOrder() {
	}

	/** full constructor */
	public TshopGoodsOrder(String sn, Integer memberId, short status, short payStatus,
			short shipStatus, Integer shippingId, String shippingType,
			String shippingArea, Integer paymentId, String paymentName,
			String paymentType, double paymoney, String goods, Date createTime,
			String shipName, String shipAddr, String shipZip, String shipEmail,
			String shipMobile, String shipTel, String shipDay, String shipTime,
			String isProtect, double protectPrice, double goodsAmount,
			double shippingAmount, double orderAmount, double weight,
			Integer goodsNum, Integer gainedpoint, Integer consumepoint,
			String remark, String disabled, double discount, short imported,
			short pimported, Integer completeTime, String cancelReason,
			Integer signingTime, String theSign, Integer allocationTime,
			Integer shipProvinceid, Integer shipCityid, Integer shipRegionid,
			short saleCmpl, Integer saleCmplTime, Integer depotid,
			String adminRemark, double needPayMoney, String shipNo,
			Integer addressId) {
		this.sn = sn;
		this.memberId = memberId;
		this.status = status;
		this.payStatus = payStatus;
		this.shipStatus = shipStatus;
		this.shippingId = shippingId;
		this.shippingType = shippingType;
		this.shippingArea = shippingArea;
		this.paymentId = paymentId;
		this.paymentName = paymentName;
		this.paymentType = paymentType;
		this.paymoney = paymoney;
		this.goods = goods;
		this.createTime = createTime;
		this.shipName = shipName;
		this.shipAddr = shipAddr;
		this.shipZip = shipZip;
		this.shipEmail = shipEmail;
		this.shipMobile = shipMobile;
		this.shipTel = shipTel;
		this.shipDay = shipDay;
		this.shipTime = shipTime;
		this.isProtect = isProtect;
		this.protectPrice = protectPrice;
		this.goodsAmount = goodsAmount;
		this.shippingAmount = shippingAmount;
		this.orderAmount = orderAmount;
		this.weight = weight;
		this.goodsNum = goodsNum;
		this.gainedpoint = gainedpoint;
		this.consumepoint = consumepoint;
		this.remark = remark;
		this.disabled = disabled;
		this.discount = discount;
		this.imported = imported;
		this.pimported = pimported;
		this.completeTime = completeTime;
		this.cancelReason = cancelReason;
		this.signingTime = signingTime;
		this.theSign = theSign;
		this.allocationTime = allocationTime;
		this.shipProvinceid = shipProvinceid;
		this.shipCityid = shipCityid;
		this.shipRegionid = shipRegionid;
		this.saleCmpl = saleCmpl;
		this.saleCmplTime = saleCmplTime;
		this.depotid = depotid;
		this.adminRemark = adminRemark;
		this.needPayMoney = needPayMoney;
		this.shipNo = shipNo;
		this.addressId = addressId;
	}

	// Property accessors
//	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
//	@GeneratedValue(generator = "generator")
	@Column(name = "order_id", unique = true, nullable = false,length=50)
	public String getOrderId() {
		if(!StringUtils.isBlank(this.orderId)){
			return this.orderId;//非空返回ID
		}
		return UUID.randomUUID().toString();//如果为空返回UUID
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "sn", length = 50)
	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "member_id")
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "status")
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	@Column(name = "pay_status")
	public short getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(short payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "ship_status")
	public short getShipStatus() {
		return this.shipStatus;
	}

	public void setShipStatus(short shipStatus) {
		this.shipStatus = shipStatus;
	}

	@Column(name = "shipping_id")
	public Integer getShippingId() {
		return this.shippingId;
	}

	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}

	@Column(name = "shipping_type")
	public String getShippingType() {
		return this.shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	@Column(name = "shipping_area")
	public String getShippingArea() {
		return this.shippingArea;
	}

	public void setShippingArea(String shippingArea) {
		this.shippingArea = shippingArea;
	}

	@Column(name = "payment_id")
	public Integer getPaymentId() {
		return this.paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	@Column(name = "payment_name", length = 50)
	public String getPaymentName() {
		return this.paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	@Column(name = "payment_type", length = 50)
	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "paymoney", precision = 20)
	public double getPaymoney() {
		return this.paymoney;
	}

	public void setPaymoney(double paymoney) {
		this.paymoney = paymoney;
	}

	@Column(name = "goods")
	public String getGoods() {
		return this.goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time",length=7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "ship_name")
	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column(name = "ship_addr")
	public String getShipAddr() {
		return this.shipAddr;
	}

	public void setShipAddr(String shipAddr) {
		this.shipAddr = shipAddr;
	}

	@Column(name = "ship_zip", length = 20)
	public String getShipZip() {
		return this.shipZip;
	}

	public void setShipZip(String shipZip) {
		this.shipZip = shipZip;
	}

	@Column(name = "ship_email", length = 50)
	public String getShipEmail() {
		return this.shipEmail;
	}

	public void setShipEmail(String shipEmail) {
		this.shipEmail = shipEmail;
	}

	@Column(name = "ship_mobile", length = 50)
	public String getShipMobile() {
		return this.shipMobile;
	}

	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}

	@Column(name = "ship_tel", length = 50)
	public String getShipTel() {
		return this.shipTel;
	}

	public void setShipTel(String shipTel) {
		this.shipTel = shipTel;
	}

	@Column(name = "ship_day")
	public String getShipDay() {
		return this.shipDay;
	}

	public void setShipDay(String shipDay) {
		this.shipDay = shipDay;
	}

	@Column(name = "ship_time")
	public String getShipTime() {
		return this.shipTime;
	}

	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}

	@Column(name = "is_protect", length = 1)
	public String getIsProtect() {
		return this.isProtect;
	}

	public void setIsProtect(String isProtect) {
		this.isProtect = isProtect;
	}

	@Column(name = "protect_price", precision = 20)
	public double getProtectPrice() {
		return this.protectPrice;
	}

	public void setProtectPrice(double protectPrice) {
		this.protectPrice = protectPrice;
	}

	@Column(name = "goods_amount", precision = 20)
	public double getGoodsAmount() {
		return this.goodsAmount;
	}

	public void setGoodsAmount(double goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	@Column(name = "shipping_amount", precision = 20)
	public double getShippingAmount() {
		return this.shippingAmount;
	}

	public void setShippingAmount(double shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	@Column(name = "order_amount", precision = 20)
	public double getOrderAmount() {
		return this.orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Column(name = "weight", precision = 20)
	public double getWeight() {
		return this.weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Column(name = "goods_num")
	public Integer getGoodsNum() {
		return this.goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	@Column(name = "gainedpoint")
	public Integer getGainedpoint() {
		return this.gainedpoint;
	}

	public void setGainedpoint(Integer gainedpoint) {
		this.gainedpoint = gainedpoint;
	}

	@Column(name = "consumepoint")
	public Integer getConsumepoint() {
		return this.consumepoint;
	}

	public void setConsumepoint(Integer consumepoint) {
		this.consumepoint = consumepoint;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "disabled", length = 1)
	public String getDisabled() {
		return this.disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	@Column(name = "discount", precision = 20)
	public double getDiscount() {
		return this.discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Column(name = "imported")
	public short getImported() {
		return this.imported;
	}

	public void setImported(short imported) {
		this.imported = imported;
	}

	@Column(name = "pimported")
	public short getPimported() {
		return this.pimported;
	}

	public void setPimported(short pimported) {
		this.pimported = pimported;
	}

	@Column(name = "complete_time")
	public Integer getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(Integer completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "cancel_reason")
	public String getCancelReason() {
		return this.cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	@Column(name = "signing_time")
	public Integer getSigningTime() {
		return this.signingTime;
	}

	public void setSigningTime(Integer signingTime) {
		this.signingTime = signingTime;
	}

	@Column(name = "the_sign")
	public String getTheSign() {
		return this.theSign;
	}

	public void setTheSign(String theSign) {
		this.theSign = theSign;
	}

	@Column(name = "allocation_time")
	public Integer getAllocationTime() {
		return this.allocationTime;
	}

	public void setAllocationTime(Integer allocationTime) {
		this.allocationTime = allocationTime;
	}

	@Column(name = "ship_provinceid")
	public Integer getShipProvinceid() {
		return this.shipProvinceid;
	}

	public void setShipProvinceid(Integer shipProvinceid) {
		this.shipProvinceid = shipProvinceid;
	}

	@Column(name = "ship_cityid")
	public Integer getShipCityid() {
		return this.shipCityid;
	}

	public void setShipCityid(Integer shipCityid) {
		this.shipCityid = shipCityid;
	}

	@Column(name = "ship_regionid")
	public Integer getShipRegionid() {
		return this.shipRegionid;
	}

	public void setShipRegionid(Integer shipRegionid) {
		this.shipRegionid = shipRegionid;
	}

	@Column(name = "sale_cmpl")
	public short getSaleCmpl() {
		return this.saleCmpl;
	}

	public void setSaleCmpl(short saleCmpl) {
		this.saleCmpl = saleCmpl;
	}

	@Column(name = "sale_cmpl_time")
	public Integer getSaleCmplTime() {
		return this.saleCmplTime;
	}

	public void setSaleCmplTime(Integer saleCmplTime) {
		this.saleCmplTime = saleCmplTime;
	}

	@Column(name = "depotid")
	public Integer getDepotid() {
		return this.depotid;
	}

	public void setDepotid(Integer depotid) {
		this.depotid = depotid;
	}

	@Column(name = "admin_remark", length = 1000)
	public String getAdminRemark() {
		return this.adminRemark;
	}

	public void setAdminRemark(String adminRemark) {
		this.adminRemark = adminRemark;
	}

	@Column(name = "need_pay_money", precision = 20)
	public double getNeedPayMoney() {
		return this.needPayMoney;
	}

	public void setNeedPayMoney(double needPayMoney) {
		this.needPayMoney = needPayMoney;
	}

	@Column(name = "ship_no")
	public String getShipNo() {
		return this.shipNo;
	}

	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	@Column(name = "address_id")
	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

}