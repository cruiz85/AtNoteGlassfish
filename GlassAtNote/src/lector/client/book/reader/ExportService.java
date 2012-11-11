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
import lector.share.model.client.TemplateCategoryClient;
import lector.share.model.client.TemplateClient;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/exportservice")
public interface ExportService extends RemoteService {

	// Templates

	public void saveTemplate(TemplateClient templateClient) throws GeneralException;

	public TemplateClient loadTemplateById(Long Id) throws TemplateNotFoundException, GeneralException;

	public List<TemplateClient> getTemplates() throws TemplateNotFoundException, GeneralException;

	public void deleteTemplate(Long templateId) throws GeneralException;

	// Template Category

	public void saveTemplateCategory(TemplateCategoryClient templateCategoryClient) throws GeneralException;

	/* mueve alguna categoria */
	public void moveCategory(Long toFatherId,
			Long categoryId, Long templateId);

	public void deleteTemplateCategory(Long templateCategoryId)throws GeneralException;

	public Template removeCategoriesFromTemplate(Long templateId,
			List<Long> categoriesIds);

	public void swapCategoryWeight(Long movingCategoryId, Long staticCategoryId);

	public List<TemplateClient> getTemplatesByIds(List<Long> ids);
	//Exportacion 	
	
	public String loadHTMLStringForExport(List<ExportObject> exportObjects);

	public String loadHTMLStringForExportUni(ExportObject exportObject);

	public String loadRTFStringForExportUni(ExportObject exportObject);

}
