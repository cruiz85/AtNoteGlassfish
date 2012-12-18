package lector.client.reader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Entity;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.share.model.Annotation;
import lector.share.model.Language;
import lector.share.model.TextSelector;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.BookClient;
import lector.share.model.client.TextSelectorClient;
import lector.share.model.client.TypeClient;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TextComment extends DialogBox {

	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ArrayList<TextSelectorClient> textSelector;
	private MenuItem mntmGuardar;
	private MenuItem mntmClear;
	private MenuItem mntmCancelar;
	private Language ActualLang;
	private AnnotationClient annotation;
	private PanelTextComent PanelTexto;
	private BookClient bookRef;
	
	
	public TextComment(ArrayList<TextSelectorClient> textSelectorin, BookClient book) {

		super(false);
		setAnimationEnabled(true);
		CommentPanel.setEstado(true);
		Date now = new Date();
		setHTML(ActualState.getUser().getFirstName()
				+ " " + ActualState.getUser().getLastName().charAt(0)+ ".  -  " + now.toGMTString());
		setSize("100%", "100%");
		bookRef = book;
		this.textSelector = textSelectorin;
		ActualLang=ActualState.getLanguage();
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("883px", "329px");
		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);

		mntmGuardar = new MenuItem(ActualLang.getSave(), false, new Command() {

			private ArrayList<TypeClient> ListaASalvar;


			public void execute() {
				
				LoadingPanel.getInstance().setLabelTexto(ActualLang.getSaving());
				LoadingPanel.getInstance().center();
				if (moreThanone()) {
					
					ListaASalvar=new ArrayList<TypeClient>();
					for (int i = 0; i < PanelTexto.getPenelBotonesTipo().getWidgetCount(); i++) {
						ListaASalvar.add((TypeClient) ((ButtonTipo)PanelTexto.getPenelBotonesTipo().getWidget(i)).getEntidad().getEntry());
					}
					saveDataAnnotacion();
					

				} else {
					if (ActualState.getReadingactivity().getDefaultType() != null) {
						bookReaderServiceHolder.loadTypeById(ActualState.getReadingactivity().getDefaultType(),new AsyncCallback<TypeClient>(){

							@Override
							public void onFailure(Throwable caught) {
								LoadingPanel.getInstance().hide();
								Window.alert(ActualLang.getE_Saving() + "Annotation");
								
							}

							@Override
							public void onSuccess(TypeClient result) {
								ListaASalvar=new ArrayList<TypeClient>();
								for (int i = 0; i < PanelTexto.getPenelBotonesTipo().getWidgetCount(); i++) {
									ListaASalvar.add((TypeClient) ((ButtonTipo)PanelTexto.getPenelBotonesTipo().getWidget(i)).getEntidad().getEntry());
								}
								ListaASalvar.add(result);
								saveDataAnnotacion();
								
							}});
					} else {
						LoadingPanel.getInstance().hide();
						Window.alert(ActualLang.getE_Need_to_select_a_type()
								+ ActualState.getCatalogo().getCatalogName()
								+ " : "
								+ ActualLang.getSetTypes()
								+ "("
								+ (PanelTexto.getPenelBotonesTipo()
										.getWidgetCount()) + ")");
					}
					
				}

				
			}

			private void saveDataAnnotacion() {
				Text comment = new Text(PanelTexto.getRichTextArea().getHTML());
//				LoadingPanel.getInstance().setLabelTexto(ActualLang.getSaving());
//				LoadingPanel.getInstance().center();
				boolean visivility;
				boolean update;
				if (PanelTexto.getComboBox().getItemText(
						PanelTexto.getComboBox().getSelectedIndex()).equals(
						Constants.ANNOTATION_PUBLIC)) {
					visivility=true;
					update=PanelTexto.getChckbxNewCheckBox()
							.getValue();
				}else
				{
					visivility=false;
					update=false;
				}

				annotation = new AnnotationClient();
				annotation.setCreator(ActualState.getUser());
				annotation.setActivity(ActualState.getReadingactivity()
						.getId());
				annotation.setTextSelectors(textSelector);
				annotation.setComment(comment.toString());
				annotation.setBookId(bookRef.getId());
				annotation.setVisibility(visivility);
				annotation.setUpdatability(update);
				annotation.setPageNumber(MainEntryPoint
						.getCurrentPageNumber());
				annotation.setTags(ListaASalvar);
				boolean IsInCatalog = false;
				for (int i = 0; i < ListaASalvar.size(); i++)

					if ((ActualState.getReadingactivity().getCloseCatalogo().getId()
							.equals(ListaASalvar.get(i).getCatalog()
									.getId())))
						IsInCatalog = true;

				if (IsInCatalog) {
					saveAnnotacion();
					hide();
				} else {

					if (ActualState.getReadingactivity().getDefaultType() != null) {
						bookReaderServiceHolder.loadTypeById(ActualState.getReadingactivity().getDefaultType(),new AsyncCallback<TypeClient>(){

							@Override
							public void onFailure(Throwable caught) {
								LoadingPanel.getInstance().hide();
								Window.alert(ActualLang.getE_Saving() + "Annotation");
								
							}

							@Override
							public void onSuccess(TypeClient result) {
								ListaASalvar=new ArrayList<TypeClient>();
								for (int i = 0; i < PanelTexto.getPenelBotonesTipo().getWidgetCount(); i++) {
									ListaASalvar.add((TypeClient) ((ButtonTipo)PanelTexto.getPenelBotonesTipo().getWidget(i)).getEntidad().getEntry());
								}
								ListaASalvar.add(result);
								saveAnnotacion();
								
							}});
					} else {
						LoadingPanel.getInstance().hide();
						Window.alert(ActualLang.getE_Need_to_select_a_type()
								+ ActualState.getCatalogo().getCatalogName()
								+ " : "
								+ ActualLang.getSetTypes()
								+ "("
								+ (PanelTexto.getPenelBotonesTipo()
										.getWidgetCount()) + ")");
					}
				}
				
			}

			private boolean moreThanone() {
				return ((PanelTexto.getPenelBotonesTipo().getWidgetCount())>0);
			}


			private void saveAnnotacion() {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						Window.alert(ActualLang.getE_Saving()+ " Annotation");
						MainEntryPoint.hidePopUpSelector();
						LoadingPanel.getInstance().hide();
					}

					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						MainEntryPoint.hidePopUpSelector();
						MainEntryPoint.refreshP();
					}
				};
				
				bookReaderServiceHolder
						.saveAnnotation(annotation, callback);
				CommentPanel.setEstado(false);
				
			}

		});

		menuBar.addItem(mntmGuardar);

		mntmClear = new MenuItem(ActualLang.getClearAnot(), false, new Command() {

			public void execute() {
				PanelTexto.getRichTextArea().setText("");
			}
		});

		menuBar.addItem(mntmClear);

		mntmCancelar = new MenuItem(ActualLang.getCancel(), false, new Command() {

			public void execute() {
				CommentPanel.setEstado(false);
				hide();
				MainEntryPoint.hidePopUpSelector();
			}
		});

		menuBar.addItem(mntmCancelar);

		PanelTexto=new PanelTextComent(ActualLang);
		verticalPanel.add(PanelTexto);
		PanelTexto.setSize("100%", "100%");

	}

	

	
}
