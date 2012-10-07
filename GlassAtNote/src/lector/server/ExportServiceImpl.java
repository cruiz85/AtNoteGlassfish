package lector.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import lector.client.book.reader.ExportService;
import lector.client.controler.Constants;
import lector.share.model.ExportObject;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ExportServiceImpl extends RemoteServiceServlet implements
		ExportService {

	@Override
	public void saveTemplate(Template template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Template loadTemplateById(Long Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Template> getTemplatesByProfessorId(Long professorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getTemplates() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long deleteTemplate(Long templateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTemplateCategory(TemplateCategory templateCategory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<TemplateCategory> getTemplateCategoriesByIds(
			ArrayList<Long> categoriesIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveCategory(Long fromFatherId, Long toFatherId,
			Long categoryId, Long templateId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long deleteTemplateCategory(Long templateCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Template removeCategoriesFromTemplate(Long templateId,
			List<Long> categoriesIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void swapCategoryWeight(Long movingCategoryId, Long staticCategoryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Template> getTemplatesByIds(ArrayList<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadHTMLStringForExport(List<ExportObject> exportObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadHTMLStringForExportUni(ExportObject exportObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadRTFStringForExportUni(ExportObject exportObject) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
