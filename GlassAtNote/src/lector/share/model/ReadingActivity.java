package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.PrivateOwned;

import lector.share.model.Book;

@Entity
@Table(name = "reading_activity")
public class ReadingActivity implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "PROFESSOR_ID")
	private Professor professor;

	private Language language;

	@ManyToOne
	@JoinColumn(name = "BOOK_ID")
	private Book book;

	private GroupApp group;
	private Catalogo closeCatalogo;
	private Catalogo openCatalogo;

	private String visualization;
	private Template template;
	private short isFreeTemplateAllowed = 0;
	@PrivateOwned
	@OneToMany(mappedBy = "activity", orphanRemoval = true)
	private List<Annotation> annotations = new ArrayList<Annotation>();

	private short privacy = 1;
	private Tag defultTag;

	public ReadingActivity() {
	}

	public ReadingActivity(String name, Professor professor, Language language,
			Book book, GroupApp group, Catalogo closeCatalogo,
			Catalogo openCatalogo, String visualization, Template template,
			short isFreeTemplateAllowed, short privacy, Tag defultTag) {
		super();
		this.name = name;
		this.professor = professor;
		this.language = language;
		this.book = book;
		this.group = group;
		this.closeCatalogo = closeCatalogo;
		this.openCatalogo = openCatalogo;
		this.visualization = visualization;
		this.template = template;
		this.isFreeTemplateAllowed = isFreeTemplateAllowed;
		this.privacy = privacy;
		this.defultTag = defultTag;
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
		return visualization;
	}

	public void setVisualization(String visualization) {
		this.visualization = visualization;
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

	public short getPrivacy() {
		return privacy;
	}

	public void setPrivacy(short privacy) {
		this.privacy = privacy;
	}

	// @PrePersist
	// @PreUpdate
	// public void prePersist() {
	// if(!book.getReadingActivities().contains(this)){
	// book.getReadingActivities().add(this);
	// }
	// }

}
