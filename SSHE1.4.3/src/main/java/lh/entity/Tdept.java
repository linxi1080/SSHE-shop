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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.StringUtils;

/**
 * Tdept entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TDEPT", uniqueConstraints = @UniqueConstraint(columnNames = "DEPT_NO"))
public class Tdept implements java.io.Serializable {

	// Fields

	private String id;
	private Tdept tdept;
	private String deptNo;
	private String name;
	private String notes;
	private String stateId;
	private String address;
	private String iconCls;
	private Set<Tuser> tusers = new HashSet<Tuser>(0);
	private Set<Tdept> tdepts = new HashSet<Tdept>(0);

	// Constructors

	/** default constructor */
	public Tdept() {
	}

	/** full constructor */
	public Tdept(Tdept tdept, String name, String notes, String stateId,
			String deptNo, Set<Tuser> tusers, Set<Tdept> tdepts) {
		this.tdept = tdept;
		this.name = name;
		this.notes = notes;
		this.stateId = stateId;
		this.deptNo = deptNo;
		this.tusers = tusers;
		this.tdepts = tdepts;
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
	@JoinColumn(name = "PARENT_ID")
	public Tdept getTdept() {
		return this.tdept;
	}

	public void setTdept(Tdept tdept) {
		this.tdept = tdept;
	}
	
	@Column(name = "NAME", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NOTES", length = 1000)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name = "STATE", length = 10)
	public String getStateId() {
		return this.stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tdept")
	public Set<Tuser> getTusers() {
		return this.tusers;
	}

	public void setTusers(Set<Tuser> tusers) {
		this.tusers = tusers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tdept")
	public Set<Tdept> getTdepts() {
		return this.tdepts;
	}

	public void setTdepts(Set<Tdept> tdepts) {
		this.tdepts = tdepts;
	}
	@Column(name = "dept_no", length = 50)
	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	@Column(name = "addree", length = 200)
	public String getAddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}
	@Column(name = "iconcls", length = 100)
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}