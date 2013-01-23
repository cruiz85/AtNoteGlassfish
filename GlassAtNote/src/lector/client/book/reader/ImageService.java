/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;
import java.util.List;

import lector.share.model.Book;
import lector.share.model.BookNotFoundException;
import lector.share.model.ExportObject;
import lector.share.model.LocalBook;
import lector.share.model.TextSelector;
import lector.share.model.client.BookClient;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author Cesar y Joaquin
 */
@RemoteServiceRelativePath("book.reader/imageservice")
public interface ImageService extends RemoteService {

	// Carga de Libros en Blob // Se modificara completamente

	public BookClient loadBookById(Long id) throws BookNotFoundException;

	public List<BookClient> getBookByUserId(Long userAppId);

	public String loadHTMLStringForExport(ArrayList<ExportObject> exportObjects);

	public String loadHTMLStringForExportUni(ExportObject exportObject);

	public String loadRTFStringForExportUni(ExportObject exportObject);

	// public LocalBook loadBookBlobById(Long id);
	//
	// public void saveBookBlob(LocalBook bookBlob);

}
