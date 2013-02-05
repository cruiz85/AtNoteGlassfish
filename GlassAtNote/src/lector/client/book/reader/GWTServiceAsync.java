/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;
import lector.share.model.Entry;
import lector.share.model.Language;
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
 * @author Cesar y Gayoso.
 */
public interface GWTServiceAsync {

	void deleteAnnotation(Long annotationId, AsyncCallback<Void> asyncCallback);

	void saveAnnotation(AnnotationClient annotationClient,
			AsyncCallback<Void> asyncCallback);

	void saveCatalog(CatalogoClient catalogClient, AsyncCallback<Void> callback);

	void login(String userName, String password,
			AsyncCallback<UserClient> asyncCallback);

	void saveUser(UserClient userClient, AsyncCallback<Void> asyncCallback);

	void saveGroup(GroupClient groupClient, AsyncCallback<Void> asyncCallback);

	void deleteGroup(Long groupId, AsyncCallback<Void> asyncCallback);

	void deleteCatalog(Long catalogId, AsyncCallback<Void> callback);

	void deleteReadingActivity(Long readingActivityId,
			AsyncCallback<Void> callback);

	void saveLanguage(Language language, AsyncCallback<Void> callback);

	void deleteLanguage(Long languageId, AsyncCallback<Void> callback);

	void saveReadingActivity(ReadingActivityClient readingActivityClient,
			AsyncCallback<Void> callback);

	void getEntriesIdsByNames(ArrayList<String> names, Long catalogTeacher,
			Long catalogOpen, AsyncCallback<List<Long>> callback);

	void fusionFolder(Long fFromId, Long fToId, AsyncCallback<Void> callback);

	void saveAnnotationThread(AnnotationThreadClient annotationThreadClient,
			AsyncCallback<Void> callback);

	void deleteAnnotationThread(Long annotationThreadId,
			AsyncCallback<Void> callback);

	void getJSONServiceTODrawGraph(String query, String data,
			AsyncCallback<String> callback);

	// void getSchemaByCatalogId(Long catalogId,
	// AsyncCallback<AnnotationSchema> callback);

	void deleteProfessorById(Long professorId, AsyncCallback<Void> callback);

	void getEntriesByIdsRecursiveIfFolder(ArrayList<Long> Ids,
			AsyncCallback<List<Entry>> callback);

	void deleteTag(Long typeId, Long fatherId, AsyncCallback<Void> callback);

	void getAnnotationsNumberByTagName(String annotationTagName,
			AsyncCallback<Integer> callback);

	void renameType(Long typeIds, String newTagName,
			AsyncCallback<Void> callback);

	void moveFolderDB(Long typeCategoryId, Long typeCategoryFromId,
			Long typeCategoryToId, AsyncCallback<Void> callback);

	void addStudentToBeValidated(Long userId, Long groupId,
			AsyncCallback<Void> callback);

	void validStudentToBeInGroup(Long userId, Long groupId,
			AsyncCallback<Void> callback);

	void loadUserById(Long userId, AsyncCallback<UserClient> callback);

	// void loadUserByEmail(String email, AsyncCallback<UserClient> callback);

	void deleteStudentById(Long studentId, AsyncCallback<Void> callback);

	void getAnnotationsByBookId(Long activityId, String bookId,
			AsyncCallback<List<AnnotationClient>> callback);

	void getAnnotationsByIds(List<Long> ids,
			AsyncCallback<List<AnnotationClient>> callback);

	void getStudents(AsyncCallback<List<StudentClient>> callback);

	void getProfessors(AsyncCallback<List<ProfessorClient>> callback);

	void loadGroupById(Long groupId, AsyncCallback<GroupClient> callback);

	void getGroupsByUserId(Long userId,
			AsyncCallback<List<GroupClient>> callback);

	void getAnnotationsByPageNumbertAndUserId(Integer pageNumber,
			String bookId, Long studentId, Long readingActivityId,
			AsyncCallback<List<AnnotationClient>> callback);

	void getAnnotationsByIdsAndAuthorsTeacher(List<Long> ids,
			List<Long> authorIds, Long Activity,
			AsyncCallback<List<AnnotationClient>> callback);

	void getAnnotationsByTeacherIds(List<Long> ids, Long readingActivityId,
			AsyncCallback<List<AnnotationClient>> callback);

	void getAnnotationsByPageNumber(Integer pageNumber, String bookId,
			Long readingActivityId,
			AsyncCallback<List<AnnotationClient>> callback);

	void loadFullBookInGoogle(String query,
			AsyncCallback<GoogleBookClient> callback);

	void loadCatalogById(Long catalogId, AsyncCallback<CatalogoClient> callback);

	void getCatalogs(AsyncCallback<List<CatalogoClient>> callback);

	void getVisbibleCatalogsByProfessorId(Long professorId,
			AsyncCallback<List<CatalogoClient>> callback);

	void getSonsFromFolderDB(Long typeCategoryId,
			AsyncCallback<List<EntryClient>> callback);

	void loadReadingActivityById(Long readingActivityId,
			AsyncCallback<ReadingActivityClient> callback);

	void getReadingActivitiesByStudentId(Long studentId,
			AsyncCallback<List<ReadingActivityClient>> callback);

	void getReadingActivitiesByProfessorId(Long professorId,
			AsyncCallback<List<ReadingActivityClient>> callback);

	void getLanguagesNames(AsyncCallback<List<String>> callback);

	void getLanguages(AsyncCallback<List<Language>> callback);

	void loadLanguageById(Long languageId, AsyncCallback<Language> callback);

	void getGoogleBookClients(String query,
			AsyncCallback<List<GoogleBookClient>> callback);

	void getBookClients(String query, int start,
			AsyncCallback<List<GoogleBookClient>> callback);

	void deleteBookById(Long id, AsyncCallback<Void> callback);

	void loadBookClientById(Long id, AsyncCallback<BookClient> callback);

	void getBookClientsByIds(List<Long> ids,
			AsyncCallback<List<BookClient>> callback);

	void getGroupsByIds(List<Long> ids,
			AsyncCallback<List<GroupClient>> callback);

	void addChildEntry(Long entryId, Long fatherTypeCategory,
			AsyncCallback<Void> callback);

	void saveTypeCategory(TypeCategoryClient typeCategoryClient, Long father,
			AsyncCallback<Void> callback);

	void saveType(TypeClient typesys, Long fatherEntry,
			AsyncCallback<Void> callback);

	void addBookToUser(BookClient bookClient, Long userId,
			AsyncCallback<Void> callback);

	void renameTypeCategory(Long typeCategoryId, String newFolderDBName,
			AsyncCallback<Void> callback);

	void deleteTypeCategory(Long typeCategoryId, Long fatherFolderDBId,
			AsyncCallback<Void> callback);

	void addChildToCatalog(EntryClient entryClient, Long catalogId,
			AsyncCallback<Void> callback);

	void loadTypeCategoryById(Long typeCategoryId,
			AsyncCallback<TypeCategoryClient> callback);

	void loadTypeCategoryByNameAndCatalogId(String FolderDBName,
			Long catalogId, AsyncCallback<TypeCategoryClient> callback);

	void loadTypeById(Long typeId, AsyncCallback<TypeClient> callback);

	void loadTypeByNameAndCatalogId(String typeName, Long catalogId,
			AsyncCallback<TypeClient> callback);

	void validateStudentsToBeInGroup(List<Long> userIds, Long groupId,
			AsyncCallback<Void> callback);

	void removeStudentsToBeValidated(List<Long> userIds, Long groupId,
			AsyncCallback<Void> callback);

	void fusionTypes(Long typeFromId, Long typeToId,
			AsyncCallback<Void> callback);

	void moveType(Long typeCategoryFromId, Long typeId, Long typeCategoryToId,
			AsyncCallback<Void> callback);

	void getTypesByNameAndCatalogId(List<String> typeNames, Long catalogId,
			AsyncCallback<List<TypeClient>> callback);

	void getTypesByIds(List<Long> typeIds,
			AsyncCallback<List<TypeClient>> callback);

	void updateCatalog(CatalogoClient catalogClient,
			AsyncCallback<Void> callback);

	void updateType(TypeClient typesys, AsyncCallback<Void> callback);

	void updateTypeCategory(TypeCategoryClient typeCategoryClient,
			AsyncCallback<Void> callback);

	void getAnnotationThreadsByItsAnnotation(Long annotationId,
			AsyncCallback<List<AnnotationThreadClient>> callback);

	void getUsersByGroupId(Long groupId,
			AsyncCallback<List<StudentClient>> callback);

	void removeUserParticipatingInGroup(Long userId, Long groupId,
			AsyncCallback<Void> callback);

	void getAnnotationsForStudentId(List<Long> ids, Long Student,
			AsyncCallback<List<AnnotationClient>> callback);

	void getAnnotationsByTypeClientIdsForProfessor(List<Long> ids,
			Long readingActivityId, Long userId,
			AsyncCallback<List<AnnotationClient>> callback);

	void getAnnotationsByTypeClientIdsForStudent(List<Long> ids,
			Long readingActivityId, Long studentId,
			AsyncCallback<List<AnnotationClient>> callback);

	void getProfessorsAnnotatorsByActivityId(Long activityId,
			AsyncCallback<List<ProfessorClient>> callback);

	void updateUser(Long userId, String password,
			AsyncCallback<Boolean> callback);

	void deleteUserWithPassword(Long userId, String password,
			AsyncCallback<Boolean> callback);


}
