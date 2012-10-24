package lector.share.model.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TemplateCategoryClient implements IsSerializable{

	private Long id;
	private String name;
	private TemplateCategoryClient father;
	private List<TemplateCategoryClient> subCategories = new ArrayList<TemplateCategoryClient>();
	private ArrayList<Long> annotationsIds = new ArrayList<Long>();

	private TemplateClient template;
	private Integer order = 0;

	public TemplateCategoryClient() {
		annotationsIds = new ArrayList<Long>();
	}

	public TemplateCategoryClient(String name,
			ArrayList<TemplateCategoryClient> subCategories,
			ArrayList<Long> annotationsIds, TemplateCategoryClient father,
			TemplateClient template) {
		super();
		this.name = name;
		this.subCategories = subCategories;
		this.annotationsIds = annotationsIds;
		this.father = father;
		this.template = template;
	}

	public TemplateCategoryClient(Long id, String name,
			ArrayList<Long> annotationsIds, TemplateClient template,
			Integer order) {
		super();
		this.id = id;
		this.name = name;
		this.annotationsIds = annotationsIds;
		this.template = template;
		this.order = order;
	}

	public TemplateCategoryClient(String name, TemplateCategoryClient father,
			TemplateClient template) {
		super();
		this.name = name;
		this.father = father;
		this.template = template;
	}

	public ArrayList<Long> getAnnotationsIds() {
		return annotationsIds;
	}

	public void setAnnotationsIds(ArrayList<Long> annotationsIds) {
		this.annotationsIds = annotationsIds;
	}

	public TemplateClient getTemplate() {
		return template;
	}

	public void setTemplate(TemplateClient template) {
		this.template = template;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TemplateCategoryClient getFather() {
		return father;
	}

	public void setFather(TemplateCategoryClient father) {
		this.father = father;
	}

	public List<TemplateCategoryClient> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<TemplateCategoryClient> subCategories) {
		this.subCategories = subCategories;
	}

}
