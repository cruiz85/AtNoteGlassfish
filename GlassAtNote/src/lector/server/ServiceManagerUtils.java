package lector.server;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.persistence.EntityTransaction;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import lector.share.model.Annotation;
import lector.share.model.AnnotationThread;
import lector.share.model.Book;
import lector.share.model.Catalogo;
import lector.share.model.FolderDB;
import lector.share.model.RemoteBook;
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
import lector.share.model.client.RemoteBookClient;
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
	static Rectangle clip;
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
				a.getBook().getId(), visibility, updatability, a.getPageNumber(),
				produceTypeClientsLazy(a.getTags()), a.isEditable(),
				a.getCreatedDate(), getIdsOfThreds(a));
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
		return new TypeClient(t.getId(), null, t.getName(), new CatalogoClient(
				t.getCatalog().getId(), true, null, "Catalog"));
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
		boolean privacy = t.getPrivacy() == 1;
		BookClient Book = null;
		GroupClient Group = null;
		CatalogoClient CloseCatalog = null;
		CatalogoClient OpenCatalog = null;
		TemplateClient Template = null;

		if (t.getBook() != null)
			Book = produceBookClient(t.getBook());

		if (t.getGroup() != null)
			Group = produceGroupClient(t.getGroup());

		if (t.getCloseCatalogo() != null)
			CloseCatalog = produceCatalogoClient(t.getCloseCatalogo());

		if (t.getOpenCatalogo() != null)
			OpenCatalog = produceCatalogoClient(t.getOpenCatalogo());

		if (t.getTemplate() != null)
			Template = produceTemplateClient(t.getTemplate());
		ReadingActivityClient readingActivityClient = new ReadingActivityClient(
				t.getName(), produceProfessorClient(t.getProfessor()),
				t.getLanguage(), Book, Group, CloseCatalog, OpenCatalog,
				t.getVisualization(), Template, isFreeTemplateAllowed, privacy);
		readingActivityClient.setId(t.getId());
		if (t.getDefultTag() != null) {
			readingActivityClient.setDefaultType(t.getDefultTag().getId());
		}

		return readingActivityClient;
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
		atc.setSubThreads(produceAnnotationThreadClients(a.getSubThreads(), atc));
		return atc;
	}

	public static LocalBookClient produceLocalBookClient(LocalBook lb) {
		return new LocalBookClient(lb.getId(), lb.getProfessor().getId(),
				lb.getAuthor(), lb.getISBN(), lb.getPagesCount(),
				lb.getPublishedYear(), lb.getTitle(),
				produceListString(lb.getWebLinks()));
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
				getReadingActivityIds((List<ReadingActivity>)p.getReadingActivities()),
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
		if (g != null) {
			for (ReadingActivity readingActivity : g) {
				ids.add(readingActivity.getId());
			}
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
		GoogleBookClient g = new GoogleBookClient(gb.getAuthor(), gb.getISBN(),
				gb.getPagesCount(), gb.getPublishedYear(), gb.getTitle(),
				gb.getTbURL(), gb.getUrl(), gb.getWebLinks());
		g.setId(gb.getId());
		return g;
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

	public static List<UserClient> produceUserClients(List<UserApp> s) {
		List<UserClient> studentClients = new ArrayList<UserClient>();
		for (UserApp student : s) {
			studentClients.add(produceUserClient(student));
		}
		return studentClients;
	}

	// EXPORTSERVICE - HANDLER

	public static TemplateClient produceTemplateClient(Template template) {
		boolean modifyable = true;
		if (template.getModifyable() == 0) {
			modifyable = false;
		}
		return new TemplateClient(template.getId(), template.getName(),
				modifyable, template.getProfessor().getId());

	}

	public static TemplateCategoryClient produceTemplateCategoryClient(
			TemplateCategory templateCategory, TemplateClient templateClient,
			TemplateCategoryClient father) {

		TemplateCategoryClient templateCategoryClient = new TemplateCategoryClient(
				templateCategory.getId(), templateCategory.getName(),
				templateCategory.getAnnotationsIds(), templateClient);
		templateCategoryClient.setFather(father);

		return templateCategoryClient;
	}
	
	
	
// PROCESAMIENTO DE IMAGENES
	
	public static BufferedImage cropMyImage(BufferedImage img, int cropWidth,
			int cropHeight, int cropStartX, int cropStartY) throws Exception {
		BufferedImage clipped = null;
		Dimension size = new Dimension(cropWidth, cropHeight);

		createClip(img, size, cropStartX, cropStartY);

		try {
			int w = clip.width;
			int h = clip.height;
			clipped = img.getSubimage(clip.x, clip.y, w, h);

			// System.out.println("Image Cropped. <span id="IL_AD3" class="IL_AD">New Image</span> Dimension: "+
			// clipped.getWidth() + "w X " + clipped.getHeight() + "h");
		} catch (RasterFormatException rfe) {
			System.out.println("Raster format error: " + rfe.getMessage());
			return null;
		}
		return clipped;
	}

	private static void createClip(BufferedImage img, Dimension size,
			int clipX, int clipY) throws Exception {
		
		boolean isClipAreaAdjusted = false;

		/** Checking for negative X Co-ordinate **/
		if (clipX < 0) {
			clipX = 0;
			isClipAreaAdjusted = true;
		}
		/** Checking for negative Y Co-ordinate **/
		if (clipY < 0) {
			clipY = 0;
			isClipAreaAdjusted = true;
		}

		/** Checking if the clip area lies outside the rectangle **/
		if ((size.width + clipX) <= img.getWidth()
				&& (size.height + clipY) <= img.getHeight()) {

			/**
			 * <span id="IL_AD10" class="IL_AD">Setting up a</span> clip
			 * rectangle when clip area lies within the image.
			 */

			clip = new Rectangle(size);
			clip.x = clipX;
			clip.y = clipY;
		} else {

			/**
			 * Checking if the width of the clip area lies outside the image. If
			 * so, making the image width boundary as the clip width.
			 */
			if ((size.width + clipX) > img.getWidth())
				size.width = img.getWidth() - clipX;

			/**
			 * Checking if the height of the clip area lies outside the image.
			 * If so, making the image height boundary as the clip height.
			 */
			if ((size.height + clipY) > img.getHeight())
				size.height = img.getHeight() - clipY;

			/** Setting up the clip are based on our clip area size adjustment **/
			clip = new Rectangle(size);
			clip.x = clipX;
			clip.y = clipY;

			isClipAreaAdjusted = true;

		}
		if (isClipAreaAdjusted)
			System.out.println("Crop Area Lied Outside The Image."
					+ " Adjusted The Clip Rectangle\n");
	}

	public static BufferedImage readImageFromURL(String urlLocation) {
		URLConnection yc = null;
		BufferedImage bufferedImage = null;
		try {
			URL url = new URL(urlLocation);
			yc = url.openConnection();
			yc.addRequestProperty("GET", url.toString());
			yc.addRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.4 (KHTML, like Gecko) Chrome/5.0.375.70 Safari/533.4");
			yc.addRequestProperty(
					"Accept",
					"application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
			yc.addRequestProperty("Accept-Charset",
					"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
			yc.addRequestProperty("Accept-Language",
					"es-es,es;q=0.8,en-us;q=0.5,en;q=0.3");
			// yc.addRequestProperty("Cookie","PREF=ID=893be4845a4c5aec:TM=1271533549:LM=12715335 49:S=KCvKQcyJS2SjNm-5");
			yc.addRequestProperty("Cookie",
					"PREF=ID=28face613556316e:TM=1184620070:LM=1184620070:S=DkEaab7_F7PtM3ZX");
			yc.addRequestProperty("Host", "books.google.com");
			yc.addRequestProperty("Connection", "keep-alive");
			// yc.setConnectTimeout(3000);
			InputStream is = yc.getInputStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
			byte[] array = new byte[1000];
			int leido = is.read(array);
			while (leido > 0) {
				outputStream.write(array, 0, leido);
				leido = is.read(array);
			}

			InputStream in = new ByteArrayInputStream(
					outputStream.toByteArray());
			bufferedImage = ImageIO.read(in);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}

	public static BufferedImage imageToBufferedImage(Image im) {
		BufferedImage bi = new BufferedImage(im.getWidth(null),
				im.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}

	public static BufferedImage readImage(String fileLocation) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileLocation));
			System.out.println("Image Read. Image Dimension: " + img.getWidth()
					+ "w X " + img.getHeight() + "h");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static void writeImage(BufferedImage img, String fileLocation,
			String extension) {
		try {
			BufferedImage bi = img;
			File outputfile = new File(fileLocation);
			ImageIO.write(bi, extension, outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage resizeImage(BufferedImage originalImage,
			int width, int height, int type) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}
	

}
