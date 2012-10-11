package lector.share.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "template")
public class Template implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private short modifyable = 0;
	@OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
	private List<TemplateCategory> categories = new ArrayList<TemplateCategory>();
	@ManyToOne
	private Professor professor;

	public Template() {
	}

	public Template(String name, short modifyable,
			List<TemplateCategory> categories, Professor professor) {
		this();
		this.name = name;
		this.modifyable = modifyable;
		this.categories = categories;
		this.professor = professor;
	}

	public Template(String name, short modifyable, Professor professor) {
		super();
		this.name = name;
		this.modifyable = modifyable;
		this.professor = professor;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getModifyable() {
		return modifyable;
	}

	public void setModifyable(short modifyable) {
		this.modifyable = modifyable;
	}

	public List<TemplateCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<TemplateCategory> categories) {
		this.categories = categories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
