package lector.server;

import java.util.ArrayList;
import java.util.List;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.client.TemplateCategoryClient;
import lector.share.model.client.TemplateClient;

public class TemplateGenerator {

	private static List<TemplateCategoryClient> listTemplateCategoriesClients = new ArrayList<TemplateCategoryClient>();

	public static void Start(TemplateClient tClient, Template template) {

		List<TemplateCategory> children = template.getCategories();
		for (TemplateCategory templateCategory : children) {
			TemplateCategoryClient templateCategoryClient = ServiceManagerUtils
					.produceTemplateCategoryClient(templateCategory, tClient,
							null);
			if (templateCategory.getSubCategories() != null
					&& !templateCategory.getSubCategories().isEmpty()) {
				deepSearch(templateCategoryClient, templateCategory);
			}
			listTemplateCategoriesClients.add(templateCategoryClient);
		}
		tClient.setCategories(listTemplateCategoriesClients);
	}

	public static void deepSearch(
			TemplateCategoryClient templateCategoryClient,
			TemplateCategory templateCategory) {
		TemplateCategoryClient son;
		List<TemplateCategoryClient> sons = new ArrayList<TemplateCategoryClient>();
		for (TemplateCategory child : templateCategory.getSubCategories()) {
			son = ServiceManagerUtils.produceTemplateCategoryClient(child,
					templateCategoryClient.getTemplate(),
					templateCategoryClient);
			sons.add(son);
			if (child.getSubCategories() != null) {
				deepSearch(son, child);
			}
		}
		templateCategoryClient.setSubCategories(sons);
	}
}
