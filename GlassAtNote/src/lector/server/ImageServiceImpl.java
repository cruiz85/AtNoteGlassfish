package lector.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.codec.binary.Base64;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.ImageService;
import lector.client.reader.Book;
import lector.client.reader.ExportObject;
import lector.share.model.LocalBook;
import lector.share.model.TextSelector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ImageServiceImpl extends RemoteServiceServlet implements
		ImageService {
	GWTService generalAppService = new GWTServiceImpl();

	@Override
	public Book loadBookById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveBook(Book book) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBlobstoreUploadUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBookByUserId(Long userAppId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
