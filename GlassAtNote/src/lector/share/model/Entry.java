package lector.share.model;


import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Table(name = "entry")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ENTRY_TYPE")
public abstract class Entry implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	private String name;
	@ManyToOne
//	@JoinColumn(name = "catalogId")
	private Catalogo catalog;


	public Entry() {

	}

	public Entry(String name, Catalogo catalog) {
		super();
		this.name = name;
//		this.catalog = catalog;
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

	public Catalogo getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalogo catalog) {
		this.catalog = catalog;
	}

}
