package lector.server;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

import lector.client.book.reader.ExportService;
import lector.share.model.ExportObject;
import lector.share.model.GeneralException;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.TemplateNotFoundException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ExportServiceImpl extends RemoteServiceServlet implements
		ExportService {

	private EntityManager em;
	private String PERSISTENCE_UNIT_NAME = "System";
	private EntityManagerFactory emf = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	@Resource
	UserTransaction userTransaction;

	@Override
	public Long saveTemplate(Template template) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (template.getId() == null) {
				entityManager.persist(template);
			} else {
				entityManager.merge(template);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		return template.getId();
	}

	@Override
	public Template loadTemplateById(Long id) throws TemplateNotFoundException,
			GeneralException {
		EntityManager entityManager = emf.createEntityManager();
		List<Template> list;

		String sql = "SELECT r FROM Template r WHERE r.id=" + id;
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadTemplateById: ", e)
			throw new GeneralException("Exception in method loadTemplateById:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadTemplateById: ", e)
			throw new TemplateNotFoundException(
					"Template not found in method loadTemplateById");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}
		ServiceManagerUtils.cleanTemplate(list.get(0));
		return list.get(0);
	}

	@Override
	public List<Template> getTemplates() throws GeneralException,
			TemplateNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		List<Template> list;

		String sql = "SELECT r FROM Template r";
		try {
			list = entityManager.createQuery(sql).getResultList();
		} catch (Exception e) {
			// logger.error ("Exception in method loadGroupByEmail: ", e)
			throw new GeneralException("Exception in method getTemplates:"
					+ e.getMessage(), e.getStackTrace());

		}
		if (list == null || list.isEmpty()) {
			// logger.error ("Exception in method loadGroupById: ", e)
			throw new TemplateNotFoundException(
					"Template not found in method getTemplates");

		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

		return list;
	}

	@Override
	public void deleteTemplate(Long templateId) throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			entityManager.createQuery(
					"DELETE FROM Template s WHERE s.id=" + templateId)
					.executeUpdate();
			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction);
			throw new GeneralException("Exception in method deleteTemplateById"
					+ e.getMessage(), e.getStackTrace());
		}
	}

	@Override
	public void saveTemplateCategory(TemplateCategory templateCategory)
			throws GeneralException {
		EntityManager entityManager = emf.createEntityManager();

		try {
			userTransaction.begin();
			if (templateCategory.getId() == null) {
				entityManager.persist(templateCategory);
			} else {
				entityManager.merge(templateCategory);
			}

			userTransaction.commit();
		} catch (Exception e) {
			ServiceManagerUtils.rollback(userTransaction); // TODO utilizar
															// método de
															// logger
		}
		if (entityManager.isOpen()) {
			entityManager.close();
		}

	}

	@Override
	public List<TemplateCategory> getTemplateCategoriesByIds(
			ArrayList<Long> categoriesIds) {
		List<TemplateCategory> templateCategorys = new ArrayList<TemplateCategory>();
		for (int i = 0; i < categoriesIds.size(); i++) {
			TemplateCategory templateCategory = quickFind(categoriesIds.get(i));
			if (templateCategory != null) {
				templateCategorys.add(templateCategory);
			}

		}
		return templateCategorys;
	}

	private TemplateCategory quickFind(Long id) {
		EntityManager entityManager = emf.createEntityManager();
		TemplateCategory a = entityManager.find(TemplateCategory.class, id);
		return a;
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
		List<Template> templates = new ArrayList<Template>();
		for (int i = 0; i < ids.size(); i++) {
			Template template = quickFindTemplate(ids.get(i));
			if (template != null) {
				templates.add(template);
			}

		}
		return templates;
	}

	private Template quickFindTemplate(Long id) {
		EntityManager entityManager = emf.createEntityManager();
		Template a = entityManager.find(Template.class, id);
		return a;
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
