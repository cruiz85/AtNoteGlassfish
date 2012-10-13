package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TemplateClient implements IsSerializable{


	private Long id;
	private String name;
	private boolean modifyable = false;
	private List<TemplateCategoryClient> categories = new ArrayList<TemplateCategoryClient>();
	private Long professor;

	public TemplateClient() {
	}

	public TemplateClient(String name, boolean modifyable,
			List<TemplateCategoryClient> categories, Long professor) {
		this();
		this.name = name;
		this.modifyable = modifyable;
		this.categories = categories;
		this.professor = professor;
	}

	public TemplateClient(String name, boolean modifyable, Long professor) {
		super();
		this.name = name;
		this.modifyable = modifyable;
		this.professor = professor;
	}

	public Long getProfessor() {
		return professor;
	}

	public void setProfessor(Long professor) {
		this.professor = professor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getModifyable() {
		return modifyable;
	}

	public void setModifyable(boolean modifyable) {
		this.modifyable = modifyable;
	}

	public List<TemplateCategoryClient> getCategories() {
		return categories;
	}

	public void setCategories(List<TemplateCategoryClient> categories) {
		this.categories = categories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
