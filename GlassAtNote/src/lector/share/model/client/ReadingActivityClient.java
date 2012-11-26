package lector.share.model.client;

import com.google.gwt.user.client.rpc.IsSerializable;

import lector.share.model.Language;

public class ReadingActivityClient implements IsSerializable {

	private Long id;
	private String name;

	ProfessorClient professor;

	private Language language;
	private BookClient book;

	private GroupClient group;
	private CatalogoClient closeCatalogo;
	private CatalogoClient openCatalogo;

	private String Visualization;
	private TemplateClient template;
	private boolean isFreeTemplateAllowed = false;
	private Long defaultType;
	private boolean privacy = true;

	public ReadingActivityClient() {
	}

	public ReadingActivityClient(String name, ProfessorClient professor,
			Language language, BookClient book, GroupClient group,
			CatalogoClient closeCatalogo, CatalogoClient openCatalogo,
			String visualization, TemplateClient template,
			boolean isFreeTemplateAllowed, boolean privacy) {
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
		this.privacy = privacy;
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

	public void setName(String name) {
		this.name = name;
	}

	public ProfessorClient getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorClient professor) {
		this.professor = professor;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setFreeTemplateAllowed(boolean isFreeTemplateAllowed) {
		this.isFreeTemplateAllowed = isFreeTemplateAllowed;
	}

	public Long getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(Long defaultType) {
		this.defaultType = defaultType;
	}

	public BookClient getBook() {
		return book;
	}

	public void setBook(BookClient book) {
		this.book = book;
	}

	public GroupClient getGroup() {
		return group;
	}

	public void setGroup(GroupClient group) {
		this.group = group;
	}

	public CatalogoClient getCloseCatalogo() {
		return closeCatalogo;
	}

	public void setCloseCatalogo(CatalogoClient closeCatalogo) {
		this.closeCatalogo = closeCatalogo;
	}

	public CatalogoClient getOpenCatalogo() {
		return openCatalogo;
	}

	public void setOpenCatalogo(CatalogoClient openCatalogo) {
		this.openCatalogo = openCatalogo;
	}

	public String getVisualization() {
		return Visualization;
	}

	public void setVisualization(String visualization) {
		Visualization = visualization;
	}

	public TemplateClient getTemplate() {
		return template;
	}

	public void setTemplate(TemplateClient template) {
		this.template = template;
	}

	public boolean getIsFreeTemplateAllowed() {
		return isFreeTemplateAllowed;
	}

	public void setIsFreeTemplateAllowed(boolean isFreeTemplateAllowed) {
		this.isFreeTemplateAllowed = isFreeTemplateAllowed;
	}

	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

}
