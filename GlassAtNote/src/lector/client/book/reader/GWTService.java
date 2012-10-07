/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;
import java.util.List;




import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import lector.share.model.Annotation;
import lector.share.model.AnnotationNotFoundException;
import lector.share.model.AnnotationThread;
import lector.share.model.Book;
import lector.share.model.BookNotFoundException;
import lector.share.model.Catalogo;
import lector.share.model.DecendanceException;
import lector.share.model.Entry;
import lector.share.model.FolderDB;
import lector.share.model.GeneralException;
import lector.share.model.IlegalFolderFusionException;
import lector.share.model.LanguageNotFoundException;
import lector.share.model.NullParameterException;
import lector.share.model.Professor;
import lector.share.model.Student;
import lector.share.model.Tag;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.UserApp;
import lector.share.model.UserNotFoundException;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/gwtservice")
public interface GWTService extends RemoteService {

	/**
	 * 
	 * @gwt.typeArgs bookId <java.lang.String>
	 * @gwt.typeArgs <org.yournamehere.client.Annotation>
	 */

	UserApp login(String requestUri) throws UserNotFoundException;

	// Usuarios

	public void saveUser(UserApp user);

	public UserApp loadUserById(Long userId) throws UserNotFoundException;

	public UserApp loadUserByEmail(String email) throws UserNotFoundException;

	// Student

	public List<Student> getStudentsByGroupId(Long groupId);

	public List<Student> getStudents();

	public Long deleteStudentById(Long studentId);

	// Professor

	public List<Professor> getProfessors();

	public Long deleteProfessorById(Long professorId);

	void deleteBookFromUser(String bookId, Long userId);

	// Groups
	public void saveGroup(GroupApp groupApp);

	public GroupApp loadGroupById(Long groupId);

	public List<GroupApp> getGroupsByUserId(Long userId);

	public Long deleteGroup(Long groupId);

	/* Metodo Nuevo, para añadir usuarios a un grupo, en la lista para validar */
	public void addUserAndGroupRelation(Long userId, Long groupId);

	/*
	 * Valida al usuario en el grupo pasandolo de la lista de en espera a
	 * validos
	 */
	public void validUserAndGroupRelation(Long userId, Long groupId);

	// Annotations

	public void saveAnnotation(Annotation annotation);

	public List<Annotation> getAnnotationsByBookId(String bookId)
			throws GeneralException, AnnotationNotFoundException,
			NullParameterException, BookNotFoundException;

	public List<Annotation> getAnnotationsByPageNumbertAndUserId(
			Integer pageNumber, String bookId, Long studentId,
			Long readingActivityId);

	public List<Annotation> getAnnotationsByPageNumber(Integer pageNumber,
			String bookId, Long readingActivityId) throws GeneralException,
			AnnotationNotFoundException, NullParameterException,
			BookNotFoundException;;

	public List<Annotation> getAnnotationsByIds(List<Long> ids);

	public List<Annotation> getAnnotationsByIdsAndAuthorsTeacher(
			List<Long> ids, List<Long> authorIds, Long Activity);

	public List<Annotation> getAnnotationsByTeacherIds(List<Long> ids,
			Long readingActivityId);

	public List<Annotation> getAnnotationsByStudentIds(List<Long> ids,
			Long Student, Long readingActivityId);

	public List<Annotation> getAnnotationsByIdsAndAuthorsStudent(
			List<Long> ids, List<Long> authorIds, Long Activity, Long Student);

	public Long deleteAnnotation(Annotation annotation)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException;

	// Annotations Threads

	public void saveAnnotationThread(AnnotationThread annotationThread);

	public Long deleteAnnotationThread(AnnotationThread annotationThread)
			throws GeneralException;

	public List<AnnotationThread> getAnnotationThreadsByItsFather(
			Long annotationId, Long threadFatherId);

	// Books

	public Book loadBookById(Long id);

	// Carga un libro desde la factoria.

	public Book loadFullBookInGoogle(String query);

	// Recupera un libro de google, se vuelve interno

	public List<Book> getBooks(String query);

	// Recupera un libro de la libreria de google, se combertira interno de la
	// factotia de de carga)

	public List<Book> getBooks(String query, int start);

	// Recupera los libros de goole para la busqueda, se combertira en un
	// proceso interno de la carga)

	// Catalog

	public void saveCatalog(Catalogo catalog);

	public Catalogo loadCatalogById(Long catalogId);

	public void getCatalogs();

	public List<Catalogo> getVisbibleCatalogsByProfessorId(Long professorId);

	void deleteCatalog(Long catalogId);

	// Entity

	public List<Long> getEntriesIdsByNames(ArrayList<String> names,
			Long catalogTeacher, Long catalogOpen);

	public List<Entry> getEntriesByIdsRecursiveIfFolder(ArrayList<Long> Ids);

	// Type

	public Tag loadTagById(Long typeId);

	public Tag loadTagByNameAndCatalogId(String typeName, Long catalogId);

	public void saveTag(Tag typesys, Long typeCategoryId);

	public Long deleteTag(Long typeId, Long typeCategoryId)
			throws GeneralException;;

	public Integer getAnnotationsNumberByTagName(String annotationTagName)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException;

	public Long fusionTags(Long typeFromId, Long typeToId)
			throws GeneralException, NullParameterException;

	public void moveTag(Long typeCategoryFromId, Long typeId,
			Long typeCategoryToId) throws GeneralException;;

	public List<Tag> getTagsByNameAndCatalogId(ArrayList<String> typeNames,
			Long catalogId);

	public List<Tag> getTagsByIds(ArrayList<Long> typeIds);

	public List<String> getTagNamesByIds(ArrayList<Long> typeIds);

	public void renameTag(Long typeIds, String newTagName);

	public void addTagToFolderDB(Long typeIds, Long fatherFolderDBId);

	// TypeCategory

	public FolderDB loadFolderDBById(Long typeCategoryId);

	public FolderDB loadFolderDBByNameAndCatalogId(String FolderDBName,
			Long catalogId);

	public void deleteFolderDB(Long typeCategoryId, Long fatherFolderDBId)
			throws GeneralException;;

	public List<Entry> getSonsFromFolderDB(Long typeCategoryId);

	public void renameFolderDB(Long typeCategoryId, String newFolderDBName);

	public void saveFolderDB(FolderDB typeCategory, Long typeCategoryFatherId);

	public void moveFolderDB(Long typeCategoryId, Long typeCategoryFromId,
			Long typeCategoryToId) throws GeneralException, DecendanceException;;

	public void fusionFolder(Long typeCategoryFromId, Long typeCategoryToId)
			throws IlegalFolderFusionException, GeneralException;

	// ReadingActivity

	public ReadingActivity loadReadingActivityById(Long readingActivityId);

	public void deleteReadingActivity(Long readingActivityId)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException;

	public Integer removeReadingActivityAnnotations(Long readingActivity);

	public ReadingActivity getReadingActivityByUserId(Long userId);

	public List<ReadingActivity> loadReadingActivityByProfessorId(
			Long professorId);

	public void saveReadingActivity(ReadingActivity readingActivity);

	public void updateReadingActivities();

	// Language

	public void saveLanguage(Language language);

	public Long deleteLanguage(Long languageId);

	public List<String> getLanguagesNames() throws GeneralException,
			LanguageNotFoundException, NullParameterException;;

	public List<Language> getLanguages() throws GeneralException,
			LanguageNotFoundException, NullParameterException;

	public Language loadLanguageById(Long languageId);
	
	//Procesos Internos
	
			//Generacion del Grafo // Se debe de implementar internamente
		
	String getJSONServiceTODrawGraph(String url,String body);

//	AnnotationSchema getSchemaByCatalogId(Long catalogId);


//	public ArrayList<Tag> getEntriesIdsByIdsRec(ArrayList<Long> Ids);

//	public Long saveFile(File filesys, Long fatherId) throws FileException;
//
//	public Long saveFolder(Folder folderSys, Long fatherId)
//			throws FolderException;
//
//	public ArrayList<Entity> getSons(Long fatherId, Long catalogId);


	// TODO: RETIRAR CUANDO SE HAGA EL LOGIN CON GOOGLE
	public UserApp loginAuxiliar(String userEmail) throws UserNotFoundException;




	public void deleteBook(String bookId, Long userId);

	//public ArrayList<String> getFileNamesByIds(ArrayList<Long> ids);

	
}
