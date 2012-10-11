/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;
import java.util.List;


import lector.share.model.ExportObject;
import lector.share.model.GeneralException;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.TemplateNotFoundException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/exportservice")
public interface ExportService extends RemoteService {

	// Templates

	public Long saveTemplate(Template template) throws GeneralException;

	public Template loadTemplateById(Long Id) throws TemplateNotFoundException, GeneralException;

	public List<Template> getTemplates() throws GeneralException, TemplateNotFoundException;

	public void deleteTemplate(Long templateId) throws GeneralException;

	// Template Category

	public void saveTemplateCategory(TemplateCategory templateCategory) throws GeneralException;

	public List<TemplateCategory> getTemplateCategoriesByIds(
			ArrayList<Long> categoriesIds);

	/* mueve alguna categoria */
	public void moveCategory(Long fromFatherId, Long toFatherId,
			Long categoryId, Long templateId);

	public Long deleteTemplateCategory(Long templateCategoryId);

	public Template removeCategoriesFromTemplate(Long templateId,
			List<Long> categoriesIds);

	public void swapCategoryWeight(Long movingCategoryId, Long staticCategoryId);

	public List<Template> getTemplatesByIds(ArrayList<Long> ids);
	//Exportacion 	
	
	public String loadHTMLStringForExport(List<ExportObject> exportObjects);

	public String loadHTMLStringForExportUni(ExportObject exportObject);

	public String loadRTFStringForExportUni(ExportObject exportObject);

}
