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
import lector.share.model.Professor;
import lector.share.model.ProfessorNotFoundException;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.TemplateCategoryNotFoundException;
import lector.share.model.TemplateNotFoundException;
import lector.share.model.client.TemplateCategoryClient;
import lector.share.model.client.TemplateClient;

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
	public void saveTemplate(TemplateClient templateClient)
			throws GeneralException {
		try {
			saveTemplate(new Template(templateClient.getName(),
					templateClient.getModifyable() ? (short) 1 : 0,
					findProfessor(templateClient.getProfessor())));

		} catch (ProfessorNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Professor findProfessor(Long id) throws ProfessorNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Professor a = entityManager.find(Professor.class, id);
		if (a == null) {
			throw new ProfessorNotFoundException(
					"Professor not found in method loadProfessorById");
		}
		entityManager.close();
		return a;
	}

	private TemplateCategory findTemplateCategory(Long id)
			throws TemplateCategoryNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		TemplateCategory a = entityManager.find(TemplateCategory.class, id);
		if (a == null) {
			throw new TemplateCategoryNotFoundException(
					"TemplateCategory not found in method loadTemplateCategoryById");
		}
		entityManager.close();
		return a;
	}

	private Template findTemplate(Long id) throws TemplateNotFoundException {
		EntityManager entityManager = emf.createEntityManager();
		Template a = entityManager.find(Template.class, id);
		if (a == null) {
			throw new TemplateNotFoundException(
					"Template not found in method loadTemplateById");
		}
		entityManager.close();
		return a;
	}

	private void saveTemplate(Template template) throws GeneralException {
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
	}

	@Override
	public TemplateClient loadTemplateById(Long id)
			throws TemplateNotFoundException, GeneralException {
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
		Template template = list.get(0);
		TemplateClient tClient = ServiceManagerUtils
				.produceTemplateClient(template);
		TemplateGenerator.Start(tClient, template);
		return tClient;
	}

	@Override
	public List<TemplateClient> getTemplates() throws GeneralException,
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
		List<TemplateClient> templateClients = new ArrayList<TemplateClient>();
		for (int i = 0; i < list.size(); i++) {
			Template template = list.get(i);
			TemplateClient tClient = ServiceManagerUtils
					.produceTemplateClient(template);
			TemplateGenerator.Start(tClient, template);
			templateClients.add(tClient);
		}

		return templateClients;
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
	public void saveTemplateCategory(
			TemplateCategoryClient templateCategoryClient)
			throws GeneralException {
		TemplateCategory father = null;
		Template template;
		try {
			template = findTemplate(templateCategoryClient.getTemplate()
					.getId());

			if (templateCategoryClient.getFather() != null) {
				father = findTemplateCategory(templateCategoryClient
						.getFather().getId());
			}
			TemplateCategory templateCategory = new TemplateCategory(
					templateCategoryClient.getName(),
					templateCategoryClient.getOrder(), father, template);
			saveTemplateCategory(templateCategory);
		} catch (TemplateNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateCategoryNotFoundException tcnfe) {
			tcnfe.printStackTrace();
		}

	}

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
			ServiceManagerUtils.rollback(userTransaction);
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
	public List<TemplateClient> getTemplatesByIds(List<Long> ids) {
		List<TemplateClient> templateClients = new ArrayList<TemplateClient>();
		for (int i = 0; i < ids.size(); i++) {
			Template template;
			try {
				template = findTemplate(ids.get(i));
				TemplateClient tClient = ServiceManagerUtils
						.produceTemplateClient(template);
				TemplateGenerator.Start(tClient, template);
				templateClients.add(tClient);
			} catch (TemplateNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return templateClients;
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
