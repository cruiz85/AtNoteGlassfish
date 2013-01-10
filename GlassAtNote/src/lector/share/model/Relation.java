package lector.share.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name = "relation")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Relation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "FATHER_ID")
	private FolderDB father;
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "CHILD_ID")
	private Entry child;
	private Long catalogId;

	public Relation() {

	}

	public Relation(FolderDB father, Entry child) {
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

	public FolderDB getFather() {
		return father;
	}

	public void setFather(FolderDB father) {
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
