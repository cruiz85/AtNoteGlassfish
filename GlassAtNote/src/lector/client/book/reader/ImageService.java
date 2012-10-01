/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lector.client.book.reader;

import java.util.ArrayList;

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

	public String getBlobstoreUploadUrl();

	public ArrayList<LocalBook> getBookBlobsByUserId(Long userAppId);

	public LocalBook loadBookBlobById(Long id);

	public void saveBookBlob(LocalBook bookBlob);

	public String loadHTMLStringForExport(ArrayList<ExportObject> exportObjects);
	
	public String loadHTMLStringForExportUni(ExportObject exportObject);
	
	public String loadRTFStringForExportUni(ExportObject exportObject);
}
