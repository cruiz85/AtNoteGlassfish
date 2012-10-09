package lector.client.login;



import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.share.model.Professor;
import lector.share.model.UserApp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Generador_Basico implements EntryPoint {

	static GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	
	public void onModuleLoad() {
//		Professor adminUser = new Professor();
//		adminUser.setEmail("root@gmail.com");
//		bookReaderServiceHolder.saveUser(adminUser, new AsyncCallback<Void>() {
//
//			public void onSuccess(Void result) {
//
//			}
//
//			public void onFailure(Throwable caught) {
//				Window.alert("Ha fallado La carga de root");
//
//			}
//		});
	}

}
