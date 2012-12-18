package lector.client.reader;

import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.File;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.reader.PanelTextComent.CatalogTipo;
import lector.share.model.Language;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.TypeClient;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TextComentEdit extends DialogBox {

	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private MenuItem mntmGuardar;
	private MenuItem mntmClear;
	private MenuItem mntmCancelar;
	private AnnotationClient annotation;
	private MenuItemSeparator separator;
	private MenuItem mntmDeleteAnnootation;
	private Language ActualLang;
	private PanelTextComent PanelTexto;
	private ArrayList<TypeClient> ListaASalvar;
	private ArrayList<SelectorPanel> SE;

	public TextComentEdit(AnnotationClient E, ArrayList<SelectorPanel> sE) {
		
		super(false);
		setAnimationEnabled(true);
		annotation = E;
		SE=sE;
		setHTML(annotation.getCreator().getFirstName()
				+ " " + +annotation.getCreator().getLastName().charAt(0)+ ".  -  " + annotation.getCreatedDate().toGMTString());
		CommentPanel.setEstado(true);
		ActualLang = ActualState.getLanguage();
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("883px", "337px");

		MenuBar menuBar = new MenuBar(false);
		verticalPanel.add(menuBar);

		mntmGuardar = new MenuItem(ActualLang.getSave(), false, new Command() {


			public void execute() {
				annotation.setComment(PanelTexto.getRichTextArea().getHTML());
				annotation.setVisibility(false);
				if (PanelTexto.getComboBox().getItemText(
						PanelTexto.getComboBox().getSelectedIndex()).equals(
						Constants.ANNOTATION_PUBLIC)) {
					annotation.setVisibility(true);
					annotation
							.setUpdatability(PanelTexto.getChckbxNewCheckBox()
									.getValue());
				}
				LoadingPanel.getInstance().setLabelTexto(ActualLang.getSaving());
				LoadingPanel.getInstance().center();

				if (!moreThanone())
					{
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
								checkAndSave();
								
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
				
				else {
					ListaASalvar=new ArrayList<TypeClient>();
					for (int i = 0; i < PanelTexto.getPenelBotonesTipo().getWidgetCount(); i++) {
						ListaASalvar.add((TypeClient) ((ButtonTipo)PanelTexto.getPenelBotonesTipo().getWidget(i)).getEntidad().getEntry());
					}
					checkAndSave();
					

					

			}
			}

			private void checkAndSave() {

				annotation.setTags(ListaASalvar);
				annotation.setVisibility(false);
				annotation.setActivity(ActualState.getReadingactivity().getId());
				if (PanelTexto
						.getComboBox()
						.getItemText(
								PanelTexto.getComboBox().getSelectedIndex())
						.equals(Constants.ANNOTATION_PUBLIC)) {
					annotation.setVisibility(true);
					annotation.setUpdatability(PanelTexto
							.getChckbxNewCheckBox().getValue());
				}

				boolean IsInCatalog = false;
				for (int i = 0; i < ListaASalvar.size(); i++)

					if ((ActualState.getReadingactivity().getCloseCatalogo().getId()
							.equals(ListaASalvar.get(i).getCatalog().getId())))
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
								hide();
								
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
				hide();
				for (SelectorPanel SP : SE) {
					SP.hide();
				}
				CommentPanel.setEstado(false);
				LoadingPanel.getInstance().hide();

			}

			

			private boolean moreThanone() {
				return ((PanelTexto.getPenelBotonesTipo().getWidgetCount())>0);
			}

			private void saveAnnotacion() {

				AsyncCallback<Void> callback2 = new AsyncCallback<Void>() {
					//
					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						MainEntryPoint.refreshP();

					}

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ActualLang.getE_Saving() + "Annotation");

					}
				};
						bookReaderServiceHolder.saveAnnotation(annotation,
								callback2);

			}

		});

		menuBar.addItem(mntmGuardar);

		mntmClear = new MenuItem(ActualLang.getClearAnot(), false,
				new Command() {

					public void execute() {
						PanelTexto.getRichTextArea().setText("");
					}
				});

		menuBar.addItem(mntmClear);

		mntmCancelar = new MenuItem(ActualLang.getCancel(), false,
				new Command() {

					public void execute() {
						CommentPanel.setEstado(false);
						hide();
						for (SelectorPanel SP : SE) {
							SP.hide();
						}
					}
				});

		menuBar.addItem(mntmCancelar);

		separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		mntmDeleteAnnootation = new MenuItem(ActualLang.getDeleteAnnotation(), false,
				new Command() {

					public void execute() {

						LoadingPanel.getInstance().setLabelTexto(ActualLang.getDeleting());
						LoadingPanel.getInstance().center();
						AsyncCallback<Void> callback = new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
								Window.alert(ActualLang.getE_deleting()+ " Annotation");
								LoadingPanel.getInstance().hide();
							}

							public void onSuccess(Void result) {

								LoadingPanel.getInstance().hide();
								MainEntryPoint.refreshP();
							}
						};
						bookReaderServiceHolder.deleteAnnotation(annotation.getId(),
								callback);
						CommentPanel.setEstado(false);
						hide();
						for (SelectorPanel SP : SE) {
							SP.hide();
						}
					}
				});
		menuBar.addItem(mntmDeleteAnnootation);

		
		PanelTexto= new PanelTextComent(ActualLang);
		verticalPanel.add(PanelTexto);
		PanelTexto.setSize("100%", "100%");
		
		PanelTexto.getRichTextArea().setHTML(annotation.getComment().toString());
		
//
//		bookReaderServiceHolder.getFilesByIds(annotation.getFileIds(),
//				new AsyncCallback<ArrayList<FileDB>>() {
//
//					public void onFailure(Throwable caught) {
//
//					}
//
//					public void onSuccess(ArrayList<FileDB> result) {
		List<TypeClient> result =annotation.getTags();
		for (int i = 0; i < result.size(); i++) {
			TypeClient resulttmp=result.get(i);
			File F=new File(resulttmp, resulttmp.getCatalog(),null);
			if (F.getCatalogo().getId().equals(ActualState.getReadingactivity().getCloseCatalogo().getId()))
						{
							ButtonTipo B=new ButtonTipo(F,CatalogTipo.Catalog1.getTexto(),PanelTexto.getPenelBotonesTipo());
							B.addClickHandler(new ClickHandler() {
								
								public void onClick(ClickEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
									
								}
							});
						
				        	B.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
								}
							});
							

				        	B.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
							}
						});
							

				        	B.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									
									((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
								
							}
						});

				        	B.setStyleName("gwt-ButtonCenter");
//							if (annotation.isEditable()) {
								B.addClickHandler(new ClickHandler() {
								
								public void onClick(ClickEvent event) {
									ButtonTipo Yo=(ButtonTipo)event.getSource();
									Yo.getPertenezco().remove(Yo);
									
								}
								});
//							}
							PanelTexto.getPenelBotonesTipo().add(B);
						}
							else{
								ButtonTipo B=new ButtonTipo(F,CatalogTipo.Catalog2.getTexto(),PanelTexto.getPenelBotonesTipo());
//								if (annotation.isEditable()) {
								
								B.addClickHandler(new ClickHandler() {
									
									public void onClick(ClickEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
										
									}
								});
							
					        	B.addMouseDownHandler(new MouseDownHandler() {
									public void onMouseDown(MouseDownEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
									}
								});
								

					        	B.addMouseOutHandler(new MouseOutHandler() {
									public void onMouseOut(MouseOutEvent event) {
										((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
								}
							});
								

					        	B.addMouseOverHandler(new MouseOverHandler() {
									public void onMouseOver(MouseOverEvent event) {
										
										((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
									
								}
							});

					        	B.setStyleName("gwt-ButtonCenter");
									B.addClickHandler(new ClickHandler() {

										public void onClick(ClickEvent event) {
											ButtonTipo Yo = (ButtonTipo) event
													.getSource();
											Yo.getPertenezco().remove(Yo);

										}
									});
//								}
								PanelTexto.getPenelBotonesTipo().add(B);
							}
						}
//					}
//				});



		if (annotation.isUpdatability())
			PanelTexto.getChckbxNewCheckBox().setValue(true);
		else
			PanelTexto.getChckbxNewCheckBox().setValue(false);

		if (!annotation.isVisibility()) {
			PanelTexto.getComboBox().setSelectedIndex(1);
			PanelTexto.getChckbxNewCheckBox().setVisible(false);
		} else if (annotation.isVisibility()) {
			PanelTexto.getComboBox().setSelectedIndex(0);
			PanelTexto.getChckbxNewCheckBox().setVisible(true);
		} 

//		if (!annotation.isEditable()) {
//			mntmGuardar.setVisible(false);
//			mntmDeleteAnnootation.setVisible(false);
//			PanelTexto.getRichTextArea().setEnabled(false);
//			PanelTexto.getComboBox().setVisible(false);
//			PanelTexto.getToolbar().setVisible(false);
//			PanelTexto.getChckbxNewCheckBox().setVisible(false);
//			PanelTexto.getBotonSelectType().setVisible(false);
//			PanelTexto.getLabelPrivPub().setVisible(false);
//			PanelTexto.getBotonSelectTypePublic().setVisible(false);
//			PanelTexto.getRichTextArea().setEnabled(false);
//			mntmClear.setVisible(false);
//
//		}
		

	}
	
	

}
