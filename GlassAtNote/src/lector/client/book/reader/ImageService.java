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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/imageservice")
public interface ImageService extends RemoteService {

	//Carga de Libros en Blob  // Se modificara completamente
	
	public Book loadBookById(Long id);

	public void saveBook(Book book);

	public String getBlobstoreUploadUrl();

	public List<Book> getBookByUserId(Long userAppId);


//	public LocalBook loadBookBlobById(Long id);
//
//	public void saveBookBlob(LocalBook bookBlob);

}
