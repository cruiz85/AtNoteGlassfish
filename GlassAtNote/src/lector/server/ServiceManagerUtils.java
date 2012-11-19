package lector.server;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityTransaction;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import lector.share.model.Annotation;
import lector.share.model.AnnotationThread;
import lector.share.model.Book;
import lector.share.model.Catalogo;
import lector.share.model.FolderDB;
import lector.share.model.Tag;
import lector.share.model.GoogleBook;
import lector.share.model.GroupApp;
import lector.share.model.LocalBook;
import lector.share.model.Professor;
import lector.share.model.ReadingActivity;
import lector.share.model.Student;
import lector.share.model.Template;
import lector.share.model.TemplateCategory;
import lector.share.model.TextSelector;
import lector.share.model.UserApp;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.AnnotationThreadClient;
import lector.share.model.client.BookClient;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.GoogleBookClient;
import lector.share.model.client.GroupClient;
import lector.share.model.client.LocalBookClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.ReadingActivityClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TemplateCategoryClient;
import lector.share.model.client.TemplateClient;
import lector.share.model.client.TextSelectorClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;
import lector.share.model.client.UserClient;

public class ServiceManagerUtils {
	// public static void rollback(EntityTransaction entityTransaction,
	// Logger logger) {
	// try {
	// entityTransaction.rollback();
	// } catch (Exception e) {
	// logger.warn("Error making a rollback:", e);
	// }
	//
	// }

	public static void rollback(UserTransaction userTransaction) {
		try {
			userTransaction.rollback();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s, " ", false);
		String t = "";
		while (st.hasMoreElements()) {
			t += st.nextElement();
		}
		return t;
	}

	/* Los tags son lazy */
	public static AnnotationClient produceAnnotationClient(Annotation a) {
		boolean visibility = false;
		boolean updatability = false;
		if (a.getVisibility() == 1) {
			visibility = true;
		}
		if (a.getUpdatability() == 1) {
			updatability = true;
		}
		return new AnnotationClient(a.getId(),
				produceUserClient(a.getCreator()), a.getActivity().getId(),
				produceTextSelectors(a.getTextSelectors()), a.getComment(),
				a.getBookId(), visibility, updatability, a.getPageNumber(),
				produceTypeClientsLazy(a.getTags()), a.isEditable(), a.getCreatedDate(), getIdsOfThreds(a));
	}

	private static List<Long> getIdsOfThreds(Annotation a) {
		List<Long> ids = new ArrayList<Long>();
		for (AnnotationThread thread : a.getThreads()) {
			ids.add(thread.getId());
		}
		return ids;
	}

	public static List<TypeClient> produceTypeClientsLazy(List<Tag> tags) {
		List<TypeClient> typeClients = new ArrayList<TypeClient>();
		for (Tag tag : tags) {
			typeClients.add(produceTypeClientLazy(tag));
		}
		return typeClients;
	}

	public static TypeClient produceTypeClientLazy(Tag t) {
		return new TypeClient(t.getId(), null, t.getName(),
				new CatalogoClient(t.getCatalog().getId(), true, null, "Catalog"));
	}

	public static CatalogoClient produceCatalogoClient(Catalogo catalog) {
		boolean isPrivate = catalog.getIsPrivate() > 0;
		return new CatalogoClient(catalog.getId(), isPrivate,
				catalog.getProfessorId(), catalog.getCatalogName());
	}

	public static List<CatalogoClient> produceCatalogoClients(List<Catalogo> s) {
		List<CatalogoClient> catalogoClients = new ArrayList<CatalogoClient>();
		for (Catalogo catalogo : s) {
			catalogoClients.add(produceCatalogoClient(catalogo));
		}
		return catalogoClients;
	}

	public static TypeClient produceTypeClientEager(Tag t,
			CatalogoClient catalogClient) {
		return new TypeClient(t.getId(), new ArrayList<EntryClient>(),
				t.getName(), catalogClient);
	}

	public static TypeCategoryClient produceTypeCategoryClient(FolderDB fb,
			CatalogoClient catalogClient) {
		return new TypeCategoryClient(fb.getId(), new ArrayList<EntryClient>(),
				fb.getName(), catalogClient, new ArrayList<EntryClient>());
	}

	public static ReadingActivityClient produceReadingActivityClient(
			ReadingActivity t) {
		boolean isFreeTemplateAllowed = t.getIsFreeTemplateAllowed() == 1;
		BookClient Book=null;
		GroupClient Group=null;
		CatalogoClient CloseCatalog=null;
		CatalogoClient OpenCatalog=null;	
		TemplateClient Template=null;
		
		if (t.getBook()!=null)
			Book=produceBookClient(t.getBook());
		
		if (t.getGroup()!=null)
			Group=produceGroupClient(t.getGroup());
		
		
		if (t.getCloseCatalogo()!=null)
			CloseCatalog=produceCatalogoClient(t.getCloseCatalogo());
		
		if (t.getOpenCatalogo()!=null)
			OpenCatalog=produceCatalogoClient(t.getOpenCatalogo());
		
		if (t.getTemplate()!=null)
			Template= produceTemplateClient(t.getTemplate());
		ReadingActivityClient Salida= new ReadingActivityClient(t.getName(),
				produceProfessorClient(t.getProfessor()), t.getLanguage(),
				Book,
				Group,
				CloseCatalog,
				OpenCatalog,
				t.getVisualization(),Template,
				isFreeTemplateAllowed);
		Salida.setId(t.getId());
		return Salida;
	}

	public static List<ReadingActivityClient> produceReadingActivityClients(
			List<ReadingActivity> a) {
		List<ReadingActivityClient> readingActivityClients = new ArrayList<ReadingActivityClient>();
		for (ReadingActivity readingActivity : a) {
			readingActivityClients
					.add(produceReadingActivityClient(readingActivity));
		}
		return readingActivityClients;
	}

	public static List<AnnotationClient> produceAnnotationClients(
			List<Annotation> a) {
		List<AnnotationClient> annotationClients = new ArrayList<AnnotationClient>();
		for (Annotation annotation : a) {
			annotationClients.add(produceAnnotationClient(annotation));
		}
		return annotationClients;
	}

	public static List<AnnotationThreadClient> produceAnnotationThreadClients(
			List<AnnotationThread> at, AnnotationThreadClient father) {
		List<AnnotationThreadClient> annotationThreadClients = new ArrayList<AnnotationThreadClient>();
		for (AnnotationThread annotationThread : at) {
			annotationThreadClients.add(produceAnnotationThreadClient(
					annotationThread, father));
		}
		return annotationThreadClients;
	}

	public static AnnotationThreadClient produceAnnotationThreadClient(
			AnnotationThread a, AnnotationThreadClient father) {
		AnnotationThreadClient atc = new AnnotationThreadClient(a.getId(),
				produceAnnotationClient(a.getAnnotation()), a.getComment(),
				a.getUserId(), a.getUserName(), a.getCreatedDate());
		atc.setFather(father);
		produceAnnotationThreadClients(a.getSubThreads(), atc);
		return atc;
	}

	public static LocalBookClient produceLocalBookClient(LocalBook lb) {
		return new LocalBookClient(lb.getProfessor().getId(), lb.getAuthor(),
				lb.getISBN(), lb.getPagesCount(), lb.getPublishedYear(),
				lb.getTitle(), produceListString(lb.getWebLinks()));
	}

	public static GroupClient produceGroupClient(GroupApp g) {
		return new GroupClient(g.getId(), g.getName(),
				produceProfessorClient(g.getProfessor()),
				produceStudentClients(g.getParticipatingStudents()),
				produceStudentClients(g.getRemainingStudents()));
	}

	public static List<GroupClient> produceGroupClients(List<GroupApp> g) {
		List<GroupClient> groupClients = new ArrayList<GroupClient>();
		for (GroupApp group : g) {
			groupClients.add(produceGroupClient(group));
		}
		return groupClients;
	}

	public static ProfessorClient produceProfessorClient(Professor p) {

		boolean confirmed = (p.getIsConfirmed() > 0);
		return new ProfessorClient(p.getId(), p.getFirstName(),
				p.getLastName(), p.getEmail(), p.getPassword(),
				p.getCreatedDate(), confirmed,
				getReadingActivityIds(p.getReadingActivities()),
				getBooksIds(p.getBooks()), getTemplatesIds(p.getTemplates()),
				getGroupsIds(p.getGroups()));
	}

	private static List<Long> getGroupsIds(List<GroupApp> g) {
		List<Long> ids = new ArrayList<Long>();
		for (GroupApp group : g) {
			ids.add(group.getId());
		}
		return ids;
	}

	private static List<Long> getReadingActivityIds(List<ReadingActivity> g) {
		List<Long> ids = new ArrayList<Long>();
		for (ReadingActivity readingActivity : g) {
			ids.add(readingActivity.getId());
		}
		return ids;
	}

	private static List<Long> getBooksIds(List<Book> g) {
		List<Long> ids = new ArrayList<Long>();
		for (Book book : g) {
			ids.add(book.getId());
		}
		return ids;
	}

	private static List<Long> getTemplatesIds(List<Template> g) {
		List<Long> ids = new ArrayList<Long>();
		for (Template template : g) {
			ids.add(template.getId());
		}
		return ids;
	}

	public static List<StudentClient> produceStudentClients(List<Student> s) {
		List<StudentClient> studentClients = new ArrayList<StudentClient>();
		for (Student student : s) {
			studentClients.add(produceStudentClient(student));
		}
		return studentClients;
	}

	public static List<ProfessorClient> produceProfessorClients(
			List<Professor> s) {
		List<ProfessorClient> professorClients = new ArrayList<ProfessorClient>();
		for (Professor professor : s) {
			professorClients.add(produceProfessorClient(professor));
		}
		return professorClients;
	}

	public static StudentClient produceStudentClient(Student student) {
		boolean confirmed = (student.getIsConfirmed() > 0);
		return new StudentClient(student.getId(), student.getFirstName(),
				student.getLastName(), student.getEmail(),
				student.getPassword(), student.getCreatedDate(), confirmed);
	}

	public static GoogleBookClient produceGoogleBookClient(GoogleBook gb) {
		GoogleBookClient G=new GoogleBookClient(gb.getAuthor(), gb.getISBN(),
				gb.getPagesCount(), gb.getPublishedYear(), gb.getTitle(),
				gb.getTbURL(), gb.getUrl(), gb.getWebLinks());
		G.setId(gb.getId());
		return G;
	}

	public static List<GoogleBookClient> produceGoogleBookClients(
			List<GoogleBook> s) {
		List<GoogleBookClient> googleBookClients = new ArrayList<GoogleBookClient>();
		for (GoogleBook googleBook : s) {
			googleBookClients.add(produceGoogleBookClient(googleBook));
		}
		return googleBookClients;
	}

	public static BookClient produceBookClient(Book b) {
		if (b instanceof GoogleBook) {
			return produceGoogleBookClient((GoogleBook) b);
		} else {
			return produceLocalBookClient((LocalBook) b);
		}

	}

	public static List<BookClient> produceBookClients(List<Book> s) {
		List<BookClient> bookClients = new ArrayList<BookClient>();
		for (Book book : s) {
			bookClients.add(produceBookClient(book));
		}
		return bookClients;
	}

	private static List<String> produceListString(List<String> strings) {
		List<String> cleanStrings = new ArrayList<String>();
		for (String string : strings) {
			cleanStrings.add(string);
		}
		return cleanStrings;
	}

	public static List<TextSelectorClient> produceTextSelectors(
			List<TextSelector> ts) {
		List<TextSelectorClient> textSelectorClients = new ArrayList<TextSelectorClient>();
		for (TextSelector textSelector : ts) {
			textSelectorClients.add(produceTextSelector(textSelector));
		}
		return textSelectorClients;
	}

	private static TextSelectorClient produceTextSelector(TextSelector tx) {
		return new TextSelectorClient(tx.getX(), tx.getY(), tx.getWidth(),
				tx.getHeight());
	}

	public static UserClient produceUserClient(UserApp u) {
		if (u instanceof Professor) {
			return produceProfessorClient((Professor) u);
		} else {
			return produceStudentClient((Student) u);
		}

	}

	// EXPORTSERVICE - HANDLER

	public static TemplateClient produceTemplateClient(Template template) {
		boolean modifyable = true;
		if (template.getModifyable() == 0) {
			modifyable = false;
		}
		return new TemplateClient(template.getId(), template.getName(), modifyable, template
				.getProfessor().getId());

	}

	public static TemplateCategoryClient produceTemplateCategoryClient(
			TemplateCategory templateCategory, TemplateClient templateClient,
			TemplateCategoryClient father) {

		TemplateCategoryClient templateCategoryClient = new TemplateCategoryClient(
				templateCategory.getId(), templateCategory.getName(),
				templateCategory.getAnnotationsIds(), templateClient);
		templateCategoryClient.setFather(father);

		return null;
	}

}
