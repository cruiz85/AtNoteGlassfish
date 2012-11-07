package lector.client.search;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.CalendarNow;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.login.ActualUser;
import lector.client.login.UserEdition;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.GoogleBookClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.UserClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.TextBox;

public class VisorSearcher extends PopupPanel {

	public String BookId="8topAAAAYAAJ";
	public VisorSearcher Yo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	public GoogleBookClient Entrada;
	
	public VisorSearcher(GoogleBookClient entrada) {
		super(false);
		BookId=entrada.getWebLinks().get(0);
		entrada.setImagesPath(BookId);
		Yo=this;
		Entrada=entrada;
		String[] Booksplit=BookId.split("&");
		BookId=Booksplit[0];
		setGlassEnabled(true);
		SimplePanel simplePanel = new SimplePanel();
		setWidget(simplePanel);
		//simplePanel.setSize("100%", "100%");
		simplePanel.setSize("846px", "608px");
		
		FlowPanel verticalPanel = new FlowPanel();
		simplePanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);
		
		MenuItem mntmClose = new MenuItem("Close", false, new Command() {
			public void execute() {
			Yo.hide();
			}
		});
		menuBar.addItem(mntmClose);
		
		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		
		MenuItem mntmSave = new MenuItem("Add to My Books", false, new Command() {
			public void execute() {
				
				//Window.alert("Works");
				ProfessorClient PC=(ProfessorClient) ActualUser.getUser();
				LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(InformationConstants.SAVING);
				bookReaderServiceHolder.addBookToUser(Entrada,PC.getId(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						realoadUser();
						
						Yo.hide();
						Logger.GetLogger()
						.info(Yo.getClass().toString(),
								InformationConstants.ADDED_BOOK1+Entrada.getTitle()+InformationConstants.ADDED_BOOK1+ActualUser.getUser().getEmail() + " at " + CalendarNow.GetDateNow());
						
					}
					
					private void realoadUser() {
						
						bookReaderServiceHolder.loadUserById(ActualUser.getUser().getId(), new AsyncCallback<UserClient>() {
							
							@Override
							public void onSuccess(UserClient result) {
								LoadingPanel.getInstance().hide();
								ActualUser.setUser(result);
								Controlador.change2BookAdminstrator();

								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								LoadingPanel.getInstance().hide();
								Window.alert(ErrorConstants.ERROR_RELOADING_USER_REFRESH);
								Window.Location.reload();
								
							}
						});
						
					}

					@Override
					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ErrorConstants.ERROR_SAVING_BOOK1+Entrada.getTitle()+ErrorConstants.ERROR_SAVING_BOOK2+ActualUser.getUser().getEmail());
						Logger.GetLogger()
						.severe(Yo.getClass().toString(),
								ErrorConstants.ERROR_SAVING_BOOK1+Entrada.getTitle()+ErrorConstants.ERROR_SAVING_BOOK2+ActualUser.getUser().getEmail() + " at " + CalendarNow.GetDateNow());
					}
				});
//				ActualUser.getUser().getBookIds().add(Entrada.getTitle()+" "+Constants.BREAKER+" "+Entrada.getId());
//				bookReaderServiceHolder.saveUser(ActualUser.getUser(),
//						new AsyncCallback<Boolean>() {
//
//							public void onFailure(Throwable caught) {
//								Window.alert("Sorry, the book could not be saved, try again later");
//								Controlador.change2BookAdminstrator();
//								Yo.hide();
//							}
//
//							public void onSuccess(Boolean result) {
//								Controlador.change2BookAdminstrator();
//								Yo.hide();
//							}
//						});
			}
		});
		menuBar.addItem(mntmSave);
		
		String Direccion=BookId +"&printsec=frontcover&output=embed";
		
		TextBox textBox = new TextBox();
		textBox.setVisibleLength(180);
		textBox.setReadOnly(true);
		textBox.setText(Direccion);
		verticalPanel.add(textBox);
		textBox.setWidth("839px");
		Frame frame = new Frame(Direccion);
		verticalPanel.add(frame);
		frame.setSize("842px", "556px");
		
	}



}
