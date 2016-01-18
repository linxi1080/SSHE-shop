package lh.entity;

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
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import lh.entity.Tmenu;

/**
 * Resourcetype entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TRESOURCETYPE", uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class TresourceType implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String description;
	private Set<Tmenu> tmenus = new HashSet<Tmenu>(0);
	// Constructors

	/** default constructor */
	public TresourceType() {
	}

	/** full constructor 
	 * @return */
	public TresourceType(String name, Set<Tmenu> tmenus) {
		this.name = name;
		this.tmenus = tmenus;
	}

	// Property accessors
//	@GenericGenerator(name = "generator", strategy = "uuid.string")
	@Id
//	@GeneratedValue(generator = "generator")
	@Column(name = "ID", nullable = false, length = 50)
	public String getId() {
		if(!StringUtils.isBlank(this.id)){//非空返回id
			return this.id;
		}
		return UUID.randomUUID().toString();//为空返回UUID
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "description", length = 200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tresourceType")
	public Set<Tmenu> getTmenus() {
		return tmenus;
	}

	public void setTmenus(Set<Tmenu> tmenus) {
		this.tmenus = tmenus;
	}

}