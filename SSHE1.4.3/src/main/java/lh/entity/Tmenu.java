package lh.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import lh.entity.TresourceType;

/**
 * Tmenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TMENU")
public class Tmenu implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3521741163824092409L;
	private String id;
	private TresourceType tresourceType;
	private Tmenu tmenu;
	private String iconCls;
	private String url;
	private String text;
	private String useFlag;
	private long seqNo;
	private String target;
	private String openMode;
	private Set<Trole> troles = new HashSet<Trole>(0);
	private Set<Tmenu> tmenus = new HashSet<Tmenu>(0);
	private String notes;


	// Constructors

	/** default constructor */
	public Tmenu() {
	}

	/** full constructor */
	public Tmenu(Tmenu tmenu, String iconCls, String url, String text,
			String useFlag, long seqNo, Set<Trole> troles, Set<Tmenu> tmenus,String notes,TresourceType tresourceType) {
		this.tmenu = tmenu;
		this.iconCls = iconCls;
		this.url = url;
		this.text = text;
		this.useFlag = useFlag;
		this.seqNo = seqNo;
		this.troles = troles;
		this.tmenus = tmenus;
		this.notes = notes;
		this.tresourceType = tresourceType;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public Tmenu getTmenu() {
		return this.tmenu;
	}

	public void setTmenu(Tmenu tmenu) {
		this.tmenu = tmenu;
	}

	@Column(name = "ICONCLS", length = 100)
	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Column(name = "URL", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "TEXT", length = 200)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "USE_FLAG", length = 1)
	public String getUseFlag() {
		return this.useFlag;
	}

	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	@Column(name = "SEQ_NO", precision = 10, scale = 0)
	public long getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmenus")
	public Set<Trole> getTroles() {
		return this.troles;
	}

	public void setTroles(Set<Trole> troles) {
		this.troles = troles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmenu")
	public Set<Tmenu> getTmenus() {
		return this.tmenus;
	}

	public void setTmenus(Set<Tmenu> tmenus) {
		this.tmenus = tmenus;
	}
	@Column(name = "NOTES", length = 200)
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCE_TYPE")
	public TresourceType getTresourceType() {
		return tresourceType;
	}
	
	public void setTresourceType(TresourceType tresourceType) {
		this.tresourceType = tresourceType;
	}
	@Column(name = "TARGET", length = 100)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	@Column(name = "open_mode", length = 100)
	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}
	
}