package lector.client.reader.hilocomentarios;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.reader.MainEntryPoint;
import lector.client.reader.RichTextToolbar;
import lector.share.model.AnnotationThread;
import lector.share.model.Language;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.AnnotationThreadClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;

import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

public class ReplyDialog extends DialogBox {

	private RichTextArea richTextArea;
	private RichTextToolbar toolbar;
	private VerticalPanel verticalPanel;
	private MenuBar menuBar;
	private MenuItem Guardar;
	private MenuItem Clear;
	private MenuItem Cancel;
	private AnnotationThreadClient Padre;
	private AnnotationClient AnotPadre;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);

	public ReplyDialog(AnnotationThreadClient Padrein, AnnotationClient AnotPadrein) {
		super(false);
		Padre = Padrein;
		AnotPadre = AnotPadrein;
		setAnimationEnabled(true);
		Date now = new Date();
		setHTML(ActualState.getUser().getFirstName()
				+ " " + ActualState.getUser().getLastName().charAt(0)+ ".  -  " + now.toGMTString());
		richTextArea = new RichTextArea();
		toolbar = new RichTextToolbar(richTextArea);
		verticalPanel = new VerticalPanel();

		menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);

		Language ActualLang = ActualState.getLanguage();

		Guardar = new MenuItem(ActualLang.getSave(), false, new Command() {
			public void execute() {
				Date now = new Date();
				AnnotationThreadClient AT = new AnnotationThreadClient(Padre, new ArrayList<AnnotationThreadClient>(),AnotPadre,
						richTextArea.getHTML(), ActualState.getUser()
								.getId(), ActualState.getUser().getFirstName()
								+ " " + ActualState.getUser().getLastName().charAt(0)+ ".",now);
				bookReaderServiceHolder.saveAnnotationThread(AT,
						new AsyncCallback<Void>() {

							public void onSuccess(Void result) {
								MainEntryPoint.refreshP();
								hide();

							}

							public void onFailure(Throwable caught) {
								Window.alert("Se ha producido un error al salvar el hilo-comentario");
								// TODO: Mensaje.

							}
						});
				
			}
		});
		menuBar.addItem(Guardar);
		Guardar.setHTML(ActualLang.getSave());

		Clear = new MenuItem(ActualLang.getClearAnot(), false, new Command() {
			public void execute() {
				richTextArea.setText("");
			}
		});
		menuBar.addItem(Clear);
		Clear.setHTML(ActualLang.getClearAnot());

		Cancel = new MenuItem(ActualLang.getCancel(), false, new Command() {
			public void execute() {
				hide();
			}
		});
		Cancel.setHTML(ActualLang.getCancel());
		menuBar.addItem(Cancel);
		verticalPanel.add(toolbar);
		verticalPanel.add(richTextArea);
		richTextArea.setWidth("99%");
		setWidget(verticalPanel);

	}

}
