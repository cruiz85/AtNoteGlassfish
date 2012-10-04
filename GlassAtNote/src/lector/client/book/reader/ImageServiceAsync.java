/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;
import java.util.List;

import lector.client.reader.Book;
import lector.client.reader.ExportObject;
import lector.share.model.LocalBook;
import lector.share.model.TextSelector;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Cesar y Gayoso.
 */
public interface ImageServiceAsync {

	void getBlobstoreUploadUrl(AsyncCallback<String> callback);

	void loadBookById(Long id, AsyncCallback<Book> callback);

	void saveBook(Book book, AsyncCallback<Void> callback);

	void getBookByUserId(Long userAppId, AsyncCallback<List<Book>> callback);

}
