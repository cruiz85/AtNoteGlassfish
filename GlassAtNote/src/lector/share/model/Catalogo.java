package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.persistence.Table;

@Entity
@Table(name = "catalogo")
public class Catalogo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(orphanRemoval=true)
	private List<Entry> entries = new ArrayList<Entry>();
	private List<Long> orders = new ArrayList<Long>();
	// NUEVO
	private short isPrivate = 1;
	private Long professorId;
	private String catalogName;

	public Catalogo() {

	}

	public Catalogo(short isPrivate, Long professorId, String catalogName) {
		super();
		this.isPrivate = isPrivate;
		this.professorId = professorId;
		this.catalogName = catalogName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public short getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(short isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public List<Long> getOrders() {
		return orders;
	}

	public void setOrders(List<Long> orders) {
		this.orders = orders;
	}

}
