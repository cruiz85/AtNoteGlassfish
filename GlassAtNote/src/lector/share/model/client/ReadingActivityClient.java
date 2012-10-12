package lector.share.model.client;

public class ReadingActivityClient{

	private Long id;
	private String name;

	ProfessorClient professor;

	private LanguageClient language;
	private BookClient book;
	
	private GroupClient group;
	private CatalogoClient closeCatalogo;
	private CatalogoClient openCatalogo;

	private String Visualization;
	private TemplateClient template;
	private boolean isFreeTemplateAllowed = false;


	public ReadingActivityClient() {
	}

	public ReadingActivityClient(String name, ProfessorClient professor, LanguageClient language,
			BookClient book, GroupClient group, CatalogoClient closeCatalogo,
			CatalogoClient openCatalogo, String visualization, TemplateClient template,
			boolean isFreeTemplateAllowed) {
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

	public void setName(String name) {
		this.name = name;
	}


	public ProfessorClient getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorClient professor) {
		this.professor = professor;
	}

	public LanguageClient getLanguage() {
		return language;
	}

	public void setLanguage(LanguageClient language) {
		this.language = language;
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

}
