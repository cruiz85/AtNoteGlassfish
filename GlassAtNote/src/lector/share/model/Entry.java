package lector.share.model;


import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import javax.persistence.Table;

@Entity
@Table(name = "entry")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Entry implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	@ManyToOne
	@JoinColumn(name = "catalogId")
	private Catalogo catalog;
	@OneToMany
	private List<Entry> parents = new ArrayList<Entry>();

	public Entry() {

	}


	public Entry(List<Entry> parents, String name, Catalogo catalog,
			String uppercaseName) {
		super();
		this.parents = parents;
		this.name = name;
		this.catalog = catalog;
		this.uppercaseName = uppercaseName;
	}


	public Entry(String name) {
		this();
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<Entry> getParents() {
		return parents;
	}


	public void setParents(List<Entry> parents) {
		this.parents = parents;
	}


	public Catalogo getCatalog() {
		return catalog;
	}


	public void setCatalog(Catalogo catalog) {
		this.catalog = catalog;
	}



	private String uppercaseName;

	@PrePersist
	@PreUpdate
	public void prePersist() {
		if (name != null) {
			uppercaseName = name.toUpperCase();
		} else {
			uppercaseName = null;
		}
	}
}
