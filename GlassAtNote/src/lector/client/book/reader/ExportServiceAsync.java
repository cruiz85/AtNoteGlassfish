package lector.client.book.reader;

import java.util.ArrayList;
import java.util.List;

import lector.share.model.ExportObject;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExportServiceAsync {

	void saveTemplate(Template template, AsyncCallback<Void> callback);

	void saveTemplateCategory(TemplateCategory templateCategory,
			AsyncCallback<Void> callback);

	void deleteTemplate(Long templateId, AsyncCallback<Long> callback);

	void deleteTemplateCategory(Long templateCategoryId,
			AsyncCallback<Long> callback);

	void getTemplates(AsyncCallback<Void> callback);

	void loadTemplateById(Long Id, AsyncCallback<Template> callback);

	void removeCategoriesFromTemplate(Long templateId,
			List<Long> categoriesIds, AsyncCallback<Template> callback);

	void getTemplateCategoriesByIds(ArrayList<Long> categoriesIds,
			AsyncCallback<List<TemplateCategory>> callback);

	void getTemplatesByIds(ArrayList<Long> ids,
			AsyncCallback<List<Template>> callback);

	void moveCategory(Long fromFatherId, Long toFatherId, Long categoryId,
			Long templateId, AsyncCallback<Void> callback);

	void swapCategoryWeight(Long movingCategoryId, Long staticCategoryId,
			AsyncCallback<Void> callback);

	void getTemplatesByProfessorId(Long professorId,
			AsyncCallback<List<Template>> callback);

	void loadHTMLStringForExport(
			List<lector.share.model.ExportObject> exportObjects,
			AsyncCallback<String> callback);

	void loadHTMLStringForExportUni(ExportObject exportObject,
			AsyncCallback<String> callback);

	void loadRTFStringForExportUni(ExportObject exportObject,
			AsyncCallback<String> callback);



}
