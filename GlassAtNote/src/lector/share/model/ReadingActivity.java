package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;

import lector.share.model.Book;

@Entity
@Table(name = "reading_activity")
public class ReadingActivity implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne
	Professor professor;

	private Language language;
	private Book book;

	private GroupApp group;
	private Catalogo closeCatalogo;
	private Catalogo openCatalogo;

	private String Visualization;
	private Template template;
	private short isFreeTemplateAllowed = 0;
	@OneToMany(mappedBy = "activity", cascade = CascadeType.ALL)
	private List<Annotation> annotations = new ArrayList<Annotation>();

	private Tag defultTag;

	public ReadingActivity() {
	}

	public ReadingActivity(String name, Professor professor, Language language,
			Book book, GroupApp group, Catalogo closeCatalogo,
			Catalogo openCatalogo, String visualization, Template template,
			short isFreeTemplateAllowed) {
		super();
		this.name = name;
		this.professor = professor;
		this.language = language;
		this.book = book;
		this.group = group;
		this.closeCatalogo = closeCatalogo;
		this.openCatalogo = openCatalogo;
		Visualization = visualization;
		this.template = template;
		this.isFreeTemplateAllowed = isFreeTemplateAllowed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Tag getDefultTag() {
		return defultTag;
	}

	public void setDefultTag(Tag defultTag) {
		this.defultTag = defultTag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Annotation> annotations) {
		this.annotations = annotations;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public GroupApp getGroup() {
		return group;
	}

	public void setGroup(GroupApp group) {
		this.group = group;
	}

	public Catalogo getCloseCatalogo() {
		return closeCatalogo;
	}

	public void setCloseCatalogo(Catalogo closeCatalogo) {
		this.closeCatalogo = closeCatalogo;
	}

	public Catalogo getOpenCatalogo() {
		return openCatalogo;
	}

	public void setOpenCatalogo(Catalogo openCatalogo) {
		this.openCatalogo = openCatalogo;
	}

	public String getVisualization() {
		return Visualization;
	}

	public void setVisualization(String visualization) {
		Visualization = visualization;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public short getIsFreeTemplateAllowed() {
		return isFreeTemplateAllowed;
	}

	public void setIsFreeTemplateAllowed(short isFreeTemplateAllowed) {
		this.isFreeTemplateAllowed = isFreeTemplateAllowed;
	}

}
