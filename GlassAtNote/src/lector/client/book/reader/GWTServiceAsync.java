/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;

import lector.share.model.Annotation;
import lector.share.model.AnnotationThread;
import lector.share.model.Book;
import lector.share.model.Catalogo;
import lector.share.model.Entry;
import lector.share.model.FolderDB;
import lector.share.model.Professor;
import lector.share.model.Student;
import lector.share.model.Tag;
import lector.share.model.GroupApp;
import lector.share.model.Language;
import lector.share.model.ReadingActivity;
import lector.share.model.UserApp;

/**
 * 
 * @author Cesar y Gayoso.
 */
public interface GWTServiceAsync {


	void getBooks(String query, AsyncCallback<List<Book>> asyncCallback);

	void getBooks(String query, int start,
			AsyncCallback<List<Book>> asyncCallback);

	void deleteAnnotation(Long annotationId, AsyncCallback<Void> asyncCallback);

	public void loadFullBookInGoogle(String query,
			AsyncCallback<Book> asyncCallback);

	void saveAnnotation(Annotation annotation, AsyncCallback<Void> asyncCallback);

	void saveCatalog(Catalogo catalog, AsyncCallback<Void> callback);

	public void login(String requestUri, AsyncCallback<UserApp> asyncCallback);

	void saveUser(UserApp user, AsyncCallback<Void> asyncCallback);

	public void loadUserById(Long userId, AsyncCallback<UserApp> asyncCallback);

	public void loadGroupById(Long groupId,
			AsyncCallback<GroupApp> asyncCallback);

	public void saveGroup(GroupApp groupApp, AsyncCallback<Void> asyncCallback);

	void deleteGroup(Long groupId, AsyncCallback<Void> asyncCallback);

	void getGroupsByUserId(Long userId,
			AsyncCallback<List<GroupApp>> asyncCallback);

	void loginAuxiliar(String userEmail, AsyncCallback<UserApp> callback);

	void loadUserByEmail(String email, AsyncCallback<UserApp> callback);

	void getCatalogs(AsyncCallback<Void> asyncCallback);

	void deleteCatalog(Long catalogId, AsyncCallback<Void> callback);

	void deleteReadingActivity(Long readingActivityId,
			AsyncCallback<Void> callback);

	void saveLanguage(Language language, AsyncCallback<Void> callback);

	void deleteLanguage(Long languageId, AsyncCallback<Long> callback);

	void getLanguagesNames(AsyncCallback<List<String>> callback);

	void getLanguages(AsyncCallback<List<Language>> callback);

	void getReadingActivityByUserId(Long userId,
			AsyncCallback<ReadingActivity> callback);

	void saveReadingActivity(ReadingActivity readingActivity,
			AsyncCallback<Void> callback);

	void loadCatalogById(Long catalogId, AsyncCallback<Catalogo> callback);

	void getVisbibleCatalogsByProfessorId(Long professorId,
			AsyncCallback<List<Catalogo>> callback);

	void getAnnotationsByPageNumber(Integer pageNumber, String bookId,
			Long readingActivityId, AsyncCallback<List<Annotation>> callback);

	void loadReadingActivityById(Long id,
			AsyncCallback<ReadingActivity> callback);

	void getEntriesIdsByNames(ArrayList<String> names, Long catalogTeacher,
			Long catalogOpen, AsyncCallback<List<Long>> callback);

	void getAnnotationsByIds(List<Long> ids,
			AsyncCallback<List<Annotation>> callback);

	void getAnnotationsByIdsAndAuthorsTeacher(List<Long> ids,
			List<Long> authorIds, Long Activity,
			AsyncCallback<List<Annotation>> callback);

	void getAnnotationsByIdsAndAuthorsStudent(List<Long> ids,
			List<Long> authorIds, Long Activity, Long Student,
			AsyncCallback<List<Annotation>> callback);

	void fusionFolder(Long fFromId, Long fToId, AsyncCallback<Void> callback);

	void saveAnnotationThread(AnnotationThread annotationThread,
			AsyncCallback<Void> callback);

	void deleteAnnotationThread(Long annotationThreadId,
			AsyncCallback<Void> callback);

	void getAnnotationThreadsByItsFather(Long threadFatherId,
			AsyncCallback<List<AnnotationThread>> callback);

	void getJSONServiceTODrawGraph(String url, String body,
			AsyncCallback<String> callback);

//	void getSchemaByCatalogId(Long catalogId,
//			AsyncCallback<AnnotationSchema> callback);

	void deleteBook(String bookId, Long userId, AsyncCallback<Void> callback);

	void updateReadingActivities(AsyncCallback<Void> callback);

	void getStudentsByGroupId(Long groupId,
			AsyncCallback<List<Student>> callback);

	void getStudents(AsyncCallback<List<Student>> callback);

	void deleteStudentById(Long studentId, AsyncCallback<Void> callback);

	void getProfessors(AsyncCallback<List<Professor>> callback);

	void deleteProfessorById(Long professorId, AsyncCallback<Void> callback);

	void getAnnotationsByPageNumbertAndUserId(Integer pageNumber,
			String bookId, Long studentId, Long readingActivityId,
			AsyncCallback<List<Annotation>> callback);

	void getAnnotationsByTeacherIds(List<Long> ids, Long readingActivityId,
			AsyncCallback<List<Annotation>> callback);

	void getAnnotationsByStudentIds(List<Long> ids, Long Student,
			Long readingActivityId, AsyncCallback<List<Annotation>> callback);

	void loadBookById(Long id, AsyncCallback<Book> callback);

	void getEntriesByIdsRecursiveIfFolder(ArrayList<Long> Ids,
			AsyncCallback<List<Entry>> callback);

	void loadTagById(Long typeId, AsyncCallback<Tag> callback);

	void loadTagByNameAndCatalogId(String typeName, Long catalogId,
			AsyncCallback<Tag> callback);

	void saveTag(Tag typesys, Long typeCategoryId, AsyncCallback<Void> callback);

	void deleteTag(Long typeId, Long typeCategoryId,
			AsyncCallback<Long> callback);

	void getAnnotationsNumberByTagName(String annotationTagName,
			AsyncCallback<Integer> callback);

	void fusionTags(Long typeFromId, Long typeToId, AsyncCallback<Long> callback);

	void moveTag(Long typeCategoryFromId, Long typeId, Long typeCategoryToId,
			AsyncCallback<Void> callback);

	void getTagsByNameAndCatalogId(ArrayList<String> typeNames, Long catalogId,
			AsyncCallback<List<Tag>> callback);

	void getTagsByIds(ArrayList<Long> typeIds, AsyncCallback<List<Tag>> callback);

	void getTagNamesByIds(ArrayList<Long> typeIds,
			AsyncCallback<List<String>> callback);

	void renameTag(Long typeIds, String newTagName, AsyncCallback<Void> callback);

	void addTagToFolderDB(Long typeIds, Long fatherFolderDBId,
			AsyncCallback<Void> callback);

	void loadFolderDBById(Long typeCategoryId, AsyncCallback<FolderDB> callback);

	void deleteFolderDB(Long typeCategoryId, Long fatherFolderDBId,
			AsyncCallback<Void> callback);

	void getSonsFromFolderDB(Long typeCategoryId,
			AsyncCallback<List<Entry>> callback);

	void renameFolderDB(Long typeCategoryId, String newFolderDBName,
			AsyncCallback<Void> callback);

	void saveFolderDB(FolderDB typeCategory, Long typeCategoryFatherId,
			AsyncCallback<Void> callback);

	void moveFolderDB(Long typeCategoryId, Long typeCategoryFromId,
			Long typeCategoryToId, AsyncCallback<Void> callback);

	void loadFolderDBByNameAndCatalogId(String FolderDBName, Long catalogId,
			AsyncCallback<FolderDB> callback);

	void removeReadingActivityAnnotations(Long readingActivity,
			AsyncCallback<Integer> callback);

	void loadReadingActivityByProfessorId(Long professorId,
			AsyncCallback<List<ReadingActivity>> callback);

	void loadLanguageById(Long languageId, AsyncCallback<Language> callback);

	void addStudentToBeValidated(Long userId, Long groupId,
			AsyncCallback<Void> callback);

	void validStudentToBeInGroup(Long userId, Long groupId,
			AsyncCallback<Void> callback);

	void getAnnotationsByBookId(Long activityId, String bookId,
			AsyncCallback<List<Annotation>> callback);

}
