package lector.share.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Table(name = "relation")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Relation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Entry father;
	private Entry child;
	private Long catalogId;

	public Relation() {

	}

	public Relation(Entry father, Entry child) {
		super();
		this.father = father;
		this.child = child;
		this.setCatalogId(child.getCatalog().getId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Entry getFather() {
		return father;
	}

	public void setFather(Entry father) {
		this.father = father;
	}

	public Entry getChild() {
		return child;
	}

	public void setChild(Entry child) {
		this.child = child;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

}
