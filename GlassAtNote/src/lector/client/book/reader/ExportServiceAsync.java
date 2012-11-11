package lector.client.book.reader;

import java.util.ArrayList;
import java.util.List;

import lector.share.model.ExportObject;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.client.TemplateCategoryClient;
import lector.share.model.client.TemplateClient;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExportServiceAsync {

	void saveTemplate(TemplateClient templateClient,
			AsyncCallback<Void> callback);

	void saveTemplateCategory(TemplateCategoryClient templateCategoryClient,
			AsyncCallback<Void> callback);

	void deleteTemplateCategory(Long templateCategoryId,
			AsyncCallback<Void> callback);

	void getTemplates(AsyncCallback<List<TemplateClient>> callback);

	void loadTemplateById(Long Id, AsyncCallback<TemplateClient> callback);

	void removeCategoriesFromTemplate(Long templateId,
			List<Long> categoriesIds, AsyncCallback<Template> callback);

	void getTemplatesByIds(List<Long> ids,
			AsyncCallback<List<TemplateClient>> callback);

	void moveCategory(Long toFatherId, Long categoryId, Long templateId,
			AsyncCallback<Void> callback);

	void swapCategoryWeight(Long movingCategoryId, Long staticCategoryId,
			AsyncCallback<Void> callback);

	void loadHTMLStringForExport(
			List<lector.share.model.ExportObject> exportObjects,
			AsyncCallback<String> callback);

	void loadHTMLStringForExportUni(ExportObject exportObject,
			AsyncCallback<String> callback);

	void loadRTFStringForExportUni(ExportObject exportObject,
			AsyncCallback<String> callback);

	void deleteTemplate(Long templateId, AsyncCallback<Void> callback);



}
