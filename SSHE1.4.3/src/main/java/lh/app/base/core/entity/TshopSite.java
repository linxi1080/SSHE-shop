package lh.app.base.core.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

/**
 * EopSite entity. @author MyEclipse Persistence Tools
 * 购物站点表实体
 */
@Entity
@Table(name = "tshop_site")
public class TshopSite implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 7729561759785255142L;
	private String id; 
	private Integer userid;
	private String sitename;
	private String productid;
	private String descript;
	private String icofile;
	private String logofile;
	private Integer deleteflag;
	private String keywords;
	private String themepath;
	private Integer adminthemeid;
	private Integer themeid;
	private Integer point;
	private Date createtime; //创建时间
	private Date lastlogin;  //最后登录时间
	private long lastgetpoint;
	private Integer logincount; //登录次数
	private String bkloginpicfile;
	private String bklogofile;
	private long sumpoint;
	private long sumaccess;
	private String title;
	private String username;
	private Integer usersex;
	private String usertel;
	private String usermobile;
	private String usertel1;
	private String useremail;
	private Integer state;
	private String qqlist;
	private String msnlist;
	private String wwlist;
	private String tellist;
	private String worktime;
	private Integer siteon;
	private String closereson;
	private String copyright;
	private String icp;
	private String address;
	private String zipcode;
	private Integer qq;
	private Integer msn;
	private Integer ww;
	private Integer tel;
	private Integer wt;
	private String linkman;
	private String linktel;
	private String email;
	private short multiSite;
	private String relid;
	private short sitestate;
	private short isimported;
	private Integer imptype;
	private short mobilesite;

	// Constructors

	/** default constructor */
	public TshopSite() {
	}

	/** full constructor */
	public TshopSite(Integer userid, String sitename, String productid,
			String descript, String icofile, String logofile,
			Integer deleteflag, String keywords, String themepath,
			Integer adminthemeid, Integer themeid, Integer point,
			Date createtime, Date lastlogin, long lastgetpoint,
			Integer logincount, String bkloginpicfile, String bklogofile,
			long sumpoint, long sumaccess, String title, String username,
			Integer usersex, String usertel, String usermobile,
			String usertel1, String useremail, Integer state, String qqlist,
			String msnlist, String wwlist, String tellist, String worktime,
			Integer siteon, String closereson, String copyright, String icp,
			String address, String zipcode, Integer qq, Integer msn,
			Integer ww, Integer tel, Integer wt, String linkman,
			String linktel, String email, short multiSite, String relid,
			short sitestate, short isimported, Integer imptype, short mobilesite) {
		this.userid = userid;
		this.sitename = sitename;
		this.productid = productid;
		this.descript = descript;
		this.icofile = icofile;
		this.logofile = logofile;
		this.deleteflag = deleteflag;
		this.keywords = keywords;
		this.themepath = themepath;
		this.adminthemeid = adminthemeid;
		this.themeid = themeid;
		this.point = point;
		this.createtime = createtime;
		this.lastlogin = lastlogin;
		this.lastgetpoint = lastgetpoint;
		this.logincount = logincount;
		this.bkloginpicfile = bkloginpicfile;
		this.bklogofile = bklogofile;
		this.sumpoint = sumpoint;
		this.sumaccess = sumaccess;
		this.title = title;
		this.username = username;
		this.usersex = usersex;
		this.usertel = usertel;
		this.usermobile = usermobile;
		this.usertel1 = usertel1;
		this.useremail = useremail;
		this.state = state;
		this.qqlist = qqlist;
		this.msnlist = msnlist;
		this.wwlist = wwlist;
		this.tellist = tellist;
		this.worktime = worktime;
		this.siteon = siteon;
		this.closereson = closereson;
		this.copyright = copyright;
		this.icp = icp;
		this.address = address;
		this.zipcode = zipcode;
		this.qq = qq;
		this.msn = msn;
		this.ww = ww;
		this.tel = tel;
		this.wt = wt;
		this.linkman = linkman;
		this.linktel = linktel;
		this.email = email;
		this.multiSite = multiSite;
		this.relid = relid;
		this.sitestate = sitestate;
		this.isimported = isimported;
		this.imptype = imptype;
		this.mobilesite = mobilesite;
	}

	// Property accessors
//	@GenericGenerator(name = "generator", strategy = "uuid.string")
//	@Id
//	@GeneratedValue(generator = "generator")
//	@Column(name = "id", unique = true, nullable = false)
//	public Integer getId() {
//		return this.id;
//	}
	// Property accessors
//		@GenericGenerator(name = "generator", strategy = "uuid")
		@Id
//		@GeneratedValue(generator = "generator")
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

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "sitename")
	public String getSitename() {
		return this.sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	@Column(name = "productid", length = 50)
	public String getProductid() {
		return this.productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	@Column(name = "descript")
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "icofile")
	public String getIcofile() {
		return this.icofile;
	}

	public void setIcofile(String icofile) {
		this.icofile = icofile;
	}

	@Column(name = "logofile")
	public String getLogofile() {
		return this.logofile;
	}

	public void setLogofile(String logofile) {
		this.logofile = logofile;
	}

	@Column(name = "deleteflag")
	public Integer getDeleteflag() {
		return this.deleteflag;
	}

	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

	@Column(name = "keywords")
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "themepath", length = 50)
	public String getThemepath() {
		return this.themepath;
	}

	public void setThemepath(String themepath) {
		this.themepath = themepath;
	}

	@Column(name = "adminthemeid")
	public Integer getAdminthemeid() {
		return this.adminthemeid;
	}

	public void setAdminthemeid(Integer adminthemeid) {
		this.adminthemeid = adminthemeid;
	}

	@Column(name = "themeid")
	public Integer getThemeid() {
		return this.themeid;
	}

	public void setThemeid(Integer themeid) {
		this.themeid = themeid;
	}

	@Column(name = "point")
	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME", length = 7)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastlogin",length=7)
	public Date getLastlogin() {
		return this.lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	@Column(name = "lastgetpoint")
	public long getLastgetpoint() {
		return this.lastgetpoint;
	}

	public void setLastgetpoint(long lastgetpoint) {
		this.lastgetpoint = lastgetpoint;
	}

	@Column(name = "logincount")
	public Integer getLogincount() {
		return this.logincount;
	}

	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}

	@Column(name = "bkloginpicfile")
	public String getBkloginpicfile() {
		return this.bkloginpicfile;
	}

	public void setBkloginpicfile(String bkloginpicfile) {
		this.bkloginpicfile = bkloginpicfile;
	}

	@Column(name = "bklogofile")
	public String getBklogofile() {
		return this.bklogofile;
	}

	public void setBklogofile(String bklogofile) {
		this.bklogofile = bklogofile;
	}

	@Column(name = "sumpoint")
	public long getSumpoint() {
		return this.sumpoint;
	}

	public void setSumpoint(long sumpoint) {
		this.sumpoint = sumpoint;
	}

	@Column(name = "sumaccess")
	public long getSumaccess() {
		return this.sumaccess;
	}

	public void setSumaccess(long sumaccess) {
		this.sumaccess = sumaccess;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "usersex")
	public Integer getUsersex() {
		return this.usersex;
	}

	public void setUsersex(Integer usersex) {
		this.usersex = usersex;
	}

	@Column(name = "usertel", length = 50)
	public String getUsertel() {
		return this.usertel;
	}

	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}

	@Column(name = "usermobile", length = 50)
	public String getUsermobile() {
		return this.usermobile;
	}

	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}

	@Column(name = "usertel1", length = 50)
	public String getUsertel1() {
		return this.usertel1;
	}

	public void setUsertel1(String usertel1) {
		this.usertel1 = usertel1;
	}

	@Column(name = "useremail", length = 50)
	public String getUseremail() {
		return this.useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "qqlist")
	public String getQqlist() {
		return this.qqlist;
	}

	public void setQqlist(String qqlist) {
		this.qqlist = qqlist;
	}

	@Column(name = "msnlist")
	public String getMsnlist() {
		return this.msnlist;
	}

	public void setMsnlist(String msnlist) {
		this.msnlist = msnlist;
	}

	@Column(name = "wwlist")
	public String getWwlist() {
		return this.wwlist;
	}

	public void setWwlist(String wwlist) {
		this.wwlist = wwlist;
	}

	@Column(name = "tellist")
	public String getTellist() {
		return this.tellist;
	}

	public void setTellist(String tellist) {
		this.tellist = tellist;
	}

	@Column(name = "worktime")
	public String getWorktime() {
		return this.worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	@Column(name = "siteon")
	public Integer getSiteon() {
		return this.siteon;
	}

	public void setSiteon(Integer siteon) {
		this.siteon = siteon;
	}

	@Column(name = "closereson")
	public String getClosereson() {
		return this.closereson;
	}

	public void setClosereson(String closereson) {
		this.closereson = closereson;
	}

	@Column(name = "copyright", length = 1000)
	public String getCopyright() {
		return this.copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	@Column(name = "icp")
	public String getIcp() {
		return this.icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}

	@Column(name = "address")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "zipcode", length = 50)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "qq")
	public Integer getQq() {
		return this.qq;
	}

	public void setQq(Integer qq) {
		this.qq = qq;
	}

	@Column(name = "msn")
	public Integer getMsn() {
		return this.msn;
	}

	public void setMsn(Integer msn) {
		this.msn = msn;
	}

	@Column(name = "ww")
	public Integer getWw() {
		return this.ww;
	}

	public void setWw(Integer ww) {
		this.ww = ww;
	}

	@Column(name = "tel")
	public Integer getTel() {
		return this.tel;
	}

	public void setTel(Integer tel) {
		this.tel = tel;
	}

	@Column(name = "wt")
	public Integer getWt() {
		return this.wt;
	}

	public void setWt(Integer wt) {
		this.wt = wt;
	}

	@Column(name = "linkman")
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "linktel")
	public String getLinktel() {
		return this.linktel;
	}

	public void setLinktel(String linktel) {
		this.linktel = linktel;
	}

	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "multi_site")
	public short getMultiSite() {
		return this.multiSite;
	}

	public void setMultiSite(short multiSite) {
		this.multiSite = multiSite;
	}

	@Column(name = "relid")
	public String getRelid() {
		return this.relid;
	}

	public void setRelid(String relid) {
		this.relid = relid;
	}

	@Column(name = "sitestate")
	public short getSitestate() {
		return this.sitestate;
	}

	public void setSitestate(short sitestate) {
		this.sitestate = sitestate;
	}

	@Column(name = "isimported")
	public short getIsimported() {
		return this.isimported;
	}

	public void setIsimported(short isimported) {
		this.isimported = isimported;
	}

	@Column(name = "imptype")
	public Integer getImptype() {
		return this.imptype;
	}

	public void setImptype(Integer imptype) {
		this.imptype = imptype;
	}

	@Column(name = "mobilesite")
	public short getMobilesite() {
		return this.mobilesite;
	}

	public void setMobilesite(short mobilesite) {
		this.mobilesite = mobilesite;
	}

}