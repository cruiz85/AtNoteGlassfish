/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import lector.share.model.AnnotationNotFoundException;
import lector.share.model.AnnotationThreadNotFoundException;
import lector.share.model.BookNotFoundException;
import lector.share.model.CatalogoNotFoundException;
import lector.share.model.DecendanceException;
import lector.share.model.Entry;
import lector.share.model.FolderDBNotFoundException;
import lector.share.model.GeneralException;
import lector.share.model.GroupNotFoundException;
import lector.share.model.IlegalFolderFusionException;
import lector.share.model.LanguageNotFoundException;
import lector.share.model.NotAuthenticatedException;
import lector.share.model.NullParameterException;
import lector.share.model.Professor;
import lector.share.model.ProfessorNotFoundException;
import lector.share.model.ReadingActivityNotFoundException;
import lector.share.model.Student;
import lector.share.model.StudentNotFoundException;
import lector.share.model.Tag;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.TagNotFoundException;
import lector.share.model.TwinBrotherException;
import lector.share.model.UserApp;
import lector.share.model.UserNotFoundException;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.AnnotationThreadClient;
import lector.share.model.client.BookClient;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.GoogleBookClient;
import lector.share.model.client.GroupClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.ReadingActivityClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;
import lector.share.model.client.UserClient;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/gwtservice")
public interface GWTService extends RemoteService {

	/**
	 * 
	 * @throws GeneralException
	 * @gwt.typeArgs bookId <java.lang.String>
	 * @gwt.typeArgs <org.yournamehere.client.Annotation>
	 */

	public UserClient login(String userName, String password)
			throws UserNotFoundException, NotAuthenticatedException,
			GeneralException;

	// Usuarios

	public void saveUser(UserClient userClient) throws GeneralException,
			UserNotFoundException;

	public UserClient loadUserById(Long userId) throws UserNotFoundException,
			GeneralException;

	public boolean updateUser(Long userId, String password) throws GeneralException;

	public boolean deleteUserWithPassword(Long userId, String password) throws GeneralException;

	// public UserClient loadUserByEmail(String email) throws
	// UserNotFoundException, GeneralException;

	// EMAIL CONFIRM

	// public void sendEmail(String email);

	// Student

	public List<StudentClient> getUsersByGroupId(Long groupId)
			throws GeneralException, GroupNotFoundException;

	public List<StudentClient> getStudents() throws GeneralException,
			StudentNotFoundException;

	public void deleteStudentById(Long studentId) throws GeneralException;

	// Professor

	public List<ProfessorClient> getProfessors() throws GeneralException,
			ProfessorNotFoundException;

	public List<ProfessorClient> getProfessorsAnnotatorsByActivityId(
			Long activityId) throws GeneralException, UserNotFoundException;

	public void deleteProfessorById(Long professorId) throws GeneralException;

	// void deleteBookFromUser(String bookId, Long userId); no debe ser
	// necesario, eliminarlo de la lista y save

	// Groups
	public void saveGroup(GroupClient groupClient) throws GeneralException;

	public GroupClient loadGroupById(Long groupId)
			throws GroupNotFoundException, GeneralException;

	public List<GroupClient> getGroupsByUserId(Long userId)
			throws GroupNotFoundException, GeneralException;

	public List<GroupClient> getGroupsByIds(List<Long> ids)
			throws GroupNotFoundException, GeneralException;

	public void deleteGroup(Long groupId) throws GeneralException;

	/* Metodo Nuevo, para a�adir usuarios a un grupo, en la lista para validar */
	public void addStudentToBeValidated(Long userId, Long groupId)
			throws GeneralException;

	public void removeStudentsToBeValidated(List<Long> userIds, Long groupId)
			throws GeneralException;

	public void removeUserParticipatingInGroup(Long userId, Long groupId)
			throws GeneralException;

	/*
	 * Valida al usuario en el grupo pasandolo de la lista de en espera a
	 * validos
	 */
	public void validStudentToBeInGroup(Long userId, Long groupId)
			throws GeneralException;

	public void validateStudentsToBeInGroup(List<Long> userIds, Long groupId)
			throws GeneralException;

	// Annotations

	public void saveAnnotation(AnnotationClient annotationClient)
			throws GeneralException;

	public List<AnnotationClient> getAnnotationsByBookId(Long activityId,
			String bookId) throws GeneralException,
			AnnotationNotFoundException, NullParameterException,
			BookNotFoundException;

	public List<AnnotationClient> getAnnotationsByPageNumbertAndUserId(
			Integer pageNumber, String bookId, Long studentId,
			Long readingActivityId) throws GeneralException,
			AnnotationNotFoundException;

	public List<AnnotationClient> getAnnotationsByPageNumber(
			Integer pageNumber, String bookId, Long readingActivityId)
			throws GeneralException, AnnotationNotFoundException,
			NullParameterException, BookNotFoundException;;

	public List<AnnotationClient> getAnnotationsByIds(List<Long> ids)
			throws GeneralException, GroupNotFoundException;

	public List<AnnotationClient> getAnnotationsByIdsAndAuthorsTeacher(
			List<Long> ids, List<Long> authorIds, Long Activity);

	public List<AnnotationClient> getAnnotationsByTeacherIds(List<Long> ids,
			Long readingActivityId);

	public List<AnnotationClient> getAnnotationsByTypeClientIdsForProfessor(
			List<Long> ids, Long readingActivityId, Long professorId)
			throws GeneralException, TagNotFoundException;

	public List<AnnotationClient> getAnnotationsByTypeClientIdsForStudent(
			List<Long> ids, Long readingActivityId, Long studentId)
			throws GeneralException, TagNotFoundException;

	public List<AnnotationClient> getAnnotationsForStudentId(List<Long> ids,
			Long Student) throws GeneralException, AnnotationNotFoundException;

	public void deleteAnnotation(Long annotationId) throws GeneralException,
			AnnotationNotFoundException;

	// Annotations Threads

	public void saveAnnotationThread(
			AnnotationThreadClient annotationThreadClient)
			throws GeneralException;

	public void deleteAnnotationThread(Long annotationThreadId)
			throws GeneralException;

	public List<AnnotationThreadClient> getAnnotationThreadsByItsAnnotation(
			Long annotationId) throws GeneralException,
			AnnotationThreadNotFoundException;

	// Books

	public BookClient loadBookClientById(Long id) throws BookNotFoundException,
			GeneralException;

	public List<BookClient> getBookClientsByIds(List<Long> ids)
			throws BookNotFoundException, GeneralException;

	public void addBookToUser(BookClient bookClient, Long userId)
			throws GeneralException;

	// Carga un libro desde la factoria.

	public GoogleBookClient loadFullBookInGoogle(String query);

	// Recupera un libro de google, se vuelve interno

	public List<GoogleBookClient> getGoogleBookClients(String query);

	// Recupera un libro de la libreria de google, se combertira interno de la
	// factotia de de carga)

	public List<GoogleBookClient> getBookClients(String query, int start);

	// Recupera los libros de goole para la busqueda, se combertira en un
	// proceso interno de la carga)

	// Catalog

	public void saveCatalog(CatalogoClient catalogClient)
			throws GeneralException;

	public void updateCatalog(CatalogoClient catalogClient)
			throws CatalogoNotFoundException, GeneralException;

	public CatalogoClient loadCatalogById(Long catalogId);

	public List<CatalogoClient> getCatalogs() throws GeneralException,
			CatalogoNotFoundException;

	public List<CatalogoClient> getVisbibleCatalogsByProfessorId(
			Long professorId) throws GeneralException,
			CatalogoNotFoundException;

	public void deleteCatalog(Long catalogId) throws GeneralException;

	public void addChildToCatalog(EntryClient entryClient, Long catalogId)
			throws TwinBrotherException, GeneralException;

	// Entity

	public List<Long> getEntriesIdsByNames(ArrayList<String> names,
			Long catalogTeacher, Long catalogOpen);

	public List<Entry> getEntriesByIdsRecursiveIfFolder(ArrayList<Long> Ids);

	// Type

	public TypeClient loadTypeById(Long typeId) throws TagNotFoundException,
			GeneralException;

	public TypeClient loadTypeByNameAndCatalogId(String typeName, Long catalogId)
			throws TagNotFoundException, GeneralException;

	public void saveType(TypeClient typesys, Long fatherEntry)
			throws GeneralException;

	public void updateType(TypeClient typesys) throws TagNotFoundException;

	public void deleteTag(Long typeId, Long fatherId) throws GeneralException;;

	public Integer getAnnotationsNumberByTagName(String annotationTagName)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException;

	public void fusionTypes(Long typeFromId, Long typeToId)
			throws GeneralException, NullParameterException;

	public void moveType(Long typeCategoryFromId, Long typeId,
			Long typeCategoryToId) throws GeneralException;;

	public List<TypeClient> getTypesByNameAndCatalogId(List<String> typeNames,
			Long catalogId) throws GeneralException;

	public List<TypeClient> getTypesByIds(List<Long> typeIds)
			throws GeneralException;

	// public List<String> getTypesNamesByIds(List<Long> typeIds)
	// throws GeneralException, TagNotFoundException;

	public void addChildEntry(Long entryId, Long fatherTypeCategory)
			throws TwinBrotherException, GeneralException;

	// TypeCategory

	public TypeCategoryClient loadTypeCategoryById(Long typeCategoryId)
			throws GeneralException, FolderDBNotFoundException;

	public TypeCategoryClient loadTypeCategoryByNameAndCatalogId(
			String typeCategoryName, Long catalogId) throws GeneralException,
			FolderDBNotFoundException;

	public void deleteTypeCategory(Long typeCategoryId, Long fatherFolderDBId)
			throws GeneralException;;

	public List<EntryClient> getSonsFromFolderDB(Long typeCategoryId);

	public void renameTypeCategory(Long typeCategoryId, String newFolderDBName);

	public void saveTypeCategory(TypeCategoryClient typeCategoryClient,
			Long fatherEntry) throws GeneralException;

	public void updateTypeCategory(TypeCategoryClient typeCategoryClient)
			throws FolderDBNotFoundException;

	public void moveFolderDB(Long typeCategoryId, Long typeCategoryFromId,
			Long typeCategoryToId) throws GeneralException, DecendanceException;;

	public void fusionFolder(Long typeCategoryFromId, Long typeCategoryToId)
			throws IlegalFolderFusionException, GeneralException;

	// ReadingActivity

	public ReadingActivityClient loadReadingActivityById(Long readingActivityId)
			throws ReadingActivityNotFoundException, GeneralException;

	public void deleteReadingActivity(Long readingActivityId)
			throws GeneralException, NullParameterException,
			AnnotationNotFoundException;

	public List<ReadingActivityClient> getReadingActivitiesByStudentId(
			Long studentId) throws GeneralException, UserNotFoundException,
			GroupNotFoundException;

	public List<ReadingActivityClient> getReadingActivitiesByProfessorId(
			Long professorId) throws GeneralException,
			ReadingActivityNotFoundException;

	public void saveReadingActivity(ReadingActivityClient readingActivityClient)
			throws GeneralException;

	// Language

	public void saveLanguage(Language language) throws GeneralException;

	public void deleteLanguage(Long languageId) throws GeneralException;

	public List<String> getLanguagesNames() throws GeneralException,
			LanguageNotFoundException, NullParameterException;;

	public List<Language> getLanguages() throws GeneralException,
			LanguageNotFoundException, NullParameterException;

	public Language loadLanguageById(Long languageId)
			throws LanguageNotFoundException, GeneralException;

	// Procesos Internos

	// Generacion del Grafo // Se debe de implementar internamente

	String getJSONServiceTODrawGraph(String query, String data);

	// AnnotationSchema getSchemaByCatalogId(Long catalogId);

	// public ArrayList<Tag> getEntriesIdsByIdsRec(ArrayList<Long> Ids);

	// public Long saveFile(File filesys, Long fatherId) throws FileException;
	//
	// public Long saveFolder(Folder folderSys, Long fatherId)
	// throws FolderException;
	//
	// public ArrayList<Entity> getSons(Long fatherId, Long catalogId);

	// TODO: RETIRAR CUANDO SE HAGA EL LOGIN CON GOOGLE
	public void deleteBookById(Long id) throws GeneralException;

	void renameType(Long typeIds, String newTagName);

	// public ArrayList<String> getFileNamesByIds(ArrayList<Long> ids);

}
