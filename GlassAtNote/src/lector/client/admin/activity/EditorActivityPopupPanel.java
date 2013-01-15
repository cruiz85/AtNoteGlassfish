package lector.client.admin.activity;

import java.util.List;

import lector.client.admin.activity.buttons.BotonTemplates;
import lector.client.admin.activity.buttons.Botonbooks;
import lector.client.admin.activity.buttons.Botoncatalogo;
import lector.client.admin.activity.buttons.Botongroups;
import lector.client.admin.activity.buttons.Botonlanguage;
import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.logger.Logger;
import lector.client.reader.LoadingPanel;
import lector.share.model.Language;
import lector.share.model.client.BookClient;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.GroupClient;
import lector.share.model.client.ProfessorClient;
import lector.share.model.client.ReadingActivityClient;
import lector.share.model.client.TemplateClient;
import lector.share.model.client.TypeClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;

public class EditorActivityPopupPanel extends PopupPanel {
	
	private static final String EDITORACTIVITY_NAME = "Editor Activity Popup";
	
	private static final int NCampos=21;
	
	private static String MENU_WELLCOME_TEXT = "Activity Editor :";
	private static String MENU_SAVE_BUTTON = "Save";
	private static String MENU_CANCEL_BUTTON = "Cancel";
	private static String TAB_PANEL_LANGUAGE = "Language";
	private static String TAB_PANEL_CATALOG = "Catalog";
	private static String ALLOW_DEFAULT_TYPE="Allow Default Type";
	private static String BOTON_SELECT_DEFAULT_TYPE = "Select Default Type";
	private static String TAB_PANEL_BOOK="Book";
	private static String TAB_PANEL_GROUPS="Groups";
	private static String TAB_PANEL_TEMPLATES="Templates";
	private static String ALLOW_BLANK_TEMPLATE="Allow blank template";
	private static String TAB_PANEL_VISUALIZACION="Visualization";
	private static String LANGUAGE_LABEL="Language : ";
	private static String PRIVATE_CATALOG_LABEL = "Teacher Catalog : ";
	private static String PUBLIC_CATALOG_LABEL= "Open Catalog : ";
	private static String DEFAUL_TYPE_LABEL = "Default Type : ";
	private static String BOOK_LABEL= "Book : ";
	private static String GROUPS_LABEL="Groups : ";
	private static String TEMPLATE_LABEL="Template : ";
	private static String BLANK_TEMPLATE_ALLOWED="Blank template Allowed";
	private static String VISUALIZACION_LABEL = "Visualizacion : ";
	
	private static final String MENU_WELLCOME_TEXT_RESET = "Activity Editor :";
	private static final String MENU_SAVE_BUTTON_RESET = "Save";
	private static final String MENU_CANCEL_BUTTON_RESET = "Cancel";
	private static final String TAB_PANEL_LANGUAGE_RESET = "Language";
	private static final String TAB_PANEL_CATALOG_RESET = "Catalog";
	private static final String ALLOW_DEFAULT_TYPE_RESET = "Allow Default Type";
	private static final String BOTON_SELECT_DEFAULT_TYPE_RESET = "Select Default Type";
	private static final String TAB_PANEL_BOOK_RESET = "Book";
	private static final String TAB_PANEL_GROUPS_RESET = "Groups";
	private static final String TAB_PANEL_TEMPLATES_RESET = "Templates";
	private static final String ALLOW_BLANK_TEMPLATE_RESET = "Allow blank template";
	private static final String TAB_PANEL_VISUALIZACION_RESET = "Visualization";
	private static final String LANGUAGE_LABEL_RESET = "Language : ";
	private static final String PRIVATE_CATALOG_LABEL_RESET = "Teacher Catalog : ";
	private static final String PUBLIC_CATALOG_LABEL_RESET = "Open Catalog : ";
	private static final String DEFAUL_TYPE_LABEL_RESET = "Default Type : ";
	private static final String BOOK_LABEL_RESET = "Book : ";
	private static final String GROUPS_LABEL_RESET = "Groups : ";
	private static final String TEMPLATE_LABEL_RESET = "Template : ";
	private static final String BLANK_TEMPLATE_ALLOWED_RESET = "Blank template Allowed";
	private static final String VISUALIZACION_LABEL_RESET = "Visualizacion : ";

	private MenuItem WellcomeMenuItem;
	private MenuItem SaveMenuItem;
	private MenuItem CancelMenuItem;
	private TabPanel TabPanelGeneral;
	private ScrollPanel LanguageTabPanel;
	private ScrollPanel CatalogTabPanel;
	private CheckBox AllowDefaulTypeCheckBox;
	private Button BotonSelectDefaultType;
	private ScrollPanel BooksTabPanel;
	private ScrollPanel GroupsTabPanel;
	private SimplePanel TemplatesTabPanel;
	private CheckBox AllowFreeTemplateCheckBox;
	private ScrollPanel VisualizacionTabPanel;
	private Label LanguageLabel;
	private Label PrivateCatalogLabel;
	private Label PublicCatalogLabel;
	private Label DefaultTypeLabel;
	private Label BooksLabel;
	private Label GroupsLabel;
	private Label TemplateLabel;
	private Label BlankTemplateAllowedLabel;
	private Label VisualizacionLabel;
	
	private TextBox WellcomeMenuItemTextBox ;
	private TextBox SaveMenuItemTextBox ;
	private TextBox CancelMenuItemTextBox ;
	private TextBox LanguageTabPanelTextBox ;
	private TextBox CatalogTabPanelTextBox ;
	private TextBox AllowDefaulTypeCheckBoxTextBox ;
	private TextBox BotonSelectDefaultTypeTextBox ;
	private TextBox BooksTabPanelTextBox ;
	private TextBox GroupsTabPanelTextBox ;
	private TextBox TemplatesTabPanelTextBox ;
	private TextBox AllowFreeTemplateCheckBoxTextBox ;
	private TextBox VisualizacionTabPanelTextBox ;
	private TextBox LanguageLabelTextBox ;
	private TextBox PrivateCatalogLabelTextBox ;
	private TextBox PublicCatalogLabelTextBox ;
	private TextBox DefaultTypeLabelTextBox ;
	private TextBox BooksLabelTextBox ;
	private TextBox GroupsLabelTextBox ;
	private TextBox TemplateLabelTextBox ;
	private TextBox BlankTemplateAllowedLabelTextBox ;
	private TextBox VisualizacionLabelTextBox ;
	
	
	
	private static String IMAGEN_ARBOL="EditImages/Arbol.jpg";
	private static String IMAGEN_KEY="EditImages/Key.jpg";
	private Image imageVisualization;
	
	private static final int DecoradorWidth = 2;

	private static final int AnchuraOutElements = 150;

	private static final int MargenAlto = 15;
	
	private EditorActivityPopupPanel Yo;
	
	private ReadingActivityClient ActualActivity;
	private CatalogoClient SelectedCatalog = null;
	private CatalogoClient SelectedCatalogOld = null;
	private CatalogoClient SelectedCatalogPublic = null;
	private CatalogoClient SelectedCatalogOldPublic = null;
	private BookClient SelectedBookOld = null;
	private TemplateClient Template = null;
	private Language SelectedLanguage = null;
	private GroupClient SelectedGroup = null;
	private BookClient SelectedBook = null;
	private TypeClient DefaultType;
	private TypeClient DefaultTypeOld;
	
	private VerticalPanel CatalogPanel;
	private HorizontalPanel PanelSelecionDefault;
	private VerticalPanel LanguagePanel;
	private VerticalPanel BooksPanel;
	private VerticalPanel GroupsPanel;
	private VerticalPanel TemplatePanel;
	private ListBox SeleccionVisualizacionComboBox;
	
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private AbsolutePanel PanelEdicion;
	private FlowPanel flowPanel;

	private AbsolutePanel GeneralPanel;

	private SimplePanel EditorZone;
	

	public EditorActivityPopupPanel(ReadingActivityClient RA) {

		super(false);
		SelectedCatalog = null;
		SelectedCatalogOld = null;
		SelectedLanguage = null;
		SelectedGroup = null;
		SelectedBook = null;
		SelectedCatalogPublic = null;
		SelectedCatalogOldPublic = null;
		SelectedBookOld = null;
		setGlassEnabled(true);
		ActualActivity = RA;
		Yo = this;
		GeneralPanel=new AbsolutePanel();
		GeneralPanel.setSize("794px", "590px");
		flowPanel = new FlowPanel();
		setWidget(GeneralPanel);
		GeneralPanel.add(flowPanel, 0, 0);
		flowPanel.setSize("794px", "590px");
		EditorZone=new SimplePanel();
		EditorZone.setHeight(Constants.TAMANO_PANEL_EDICION);
	//	EditorZone.add(new Button("hola"));
		flowPanel.add(EditorZone);
		MenuBar menuBar = new MenuBar(false);
		flowPanel.add(menuBar);

		WellcomeMenuItem = new MenuItem(EditorActivityPopupPanel.MENU_WELLCOME_TEXT,
				false, (Command) null);
		WellcomeMenuItem.setEnabled(false);
		WellcomeMenuItem.setHTML(EditorActivityPopupPanel.MENU_WELLCOME_TEXT
				+ ActualActivity.getName());
		menuBar.addItem(WellcomeMenuItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		SaveMenuItem = new MenuItem(EditorActivityPopupPanel.MENU_SAVE_BUTTON, false,
				new Command() {
					public void execute() {

						if (SelectedBook == null || SelectedCatalog == null
								|| SelectedGroup == null
								|| SelectedLanguage == null
								|| SelectedCatalogPublic == null)
							if (Window
									.confirm(InformationConstants.BLANK_ATRIBUTES))
								saveActivity();
							else {
							}
						else
							saveActivity();

					}

					private void saveActivity() {
						if (checkcatalog()) {
							if (Window
									.confirm(InformationConstants.ARE_YOU_SURE_TO_SWAP_ONE_ORE_BOTH_CATALOGS)) {

								//Window.alert("Under Developer");
								StringBuffer SB = new StringBuffer();
								SB.append("Change Catalog or both and remove activities");
								if (SelectedCatalogOld != null) {
									SB.append(" - Old Private: ");
									SB.append(SelectedCatalogOld
											.getCatalogName());
									SB.append(":");
									SB.append(SelectedCatalogOld.getId());
								}
								if (SelectedCatalog != null) {
									SB.append("- New Private: ");
									SB.append(SelectedCatalog.getCatalogName());
									SB.append(":");
									SB.append(SelectedCatalog.getId());
								}
								if (SelectedCatalogOldPublic != null) {
									SB.append("- Old Public: ");
									SB.append(SelectedCatalogOldPublic
											.getCatalogName());
									SB.append(":");
									SB.append(SelectedCatalogOldPublic.getId());
								}
								if (SelectedCatalogPublic != null) {
									SB.append("- New Private: ");
									SB.append(SelectedCatalogPublic
											.getCatalogName());
									SB.append(":");
									SB.append(SelectedCatalogPublic.getId());
								}
								Logger.GetLogger().info(
										Yo.getClass().toString(),
										ActualState.getUser().toString(),
										SB.toString());
								// LoadingPanel.getInstance().center();
								// LoadingPanel.getInstance().setLabelTexto("Deleting...");
								// TODO Reparar
								// bookReaderServiceHolder
								// .removeReadingActivityFromAnnotations(
								// ActualActivity.getId(),
								// new AsyncCallback<Integer>() {
								//
								// public void onFailure(
								// Throwable caught) {
								// Window.alert("The annotations could not be deleted");
								// LoadingPanel.getInstance()
								// .hide();
								//
								// }
								//
								// public void onSuccess(Integer result) {
								 SaveacActivitytoServer();
								// LoadingPanel.getInstance()
								// .hide();
								//
								// }
								// });
								//
							}
						} else if (checkbook()) {
							if (Window
									.confirm(InformationConstants.ARE_YOU_SURE_TO_SWAP_BOOK)) {
								//Window.alert("Under Developer");
								StringBuffer SB = new StringBuffer();
								SB.append("Change Book and remove activities");
								if (SelectedBookOld != null) {
									SB.append(" - Old Book: ");
									SB.append(SelectedBookOld.getTitle());
									SB.append(":");
									SB.append(SelectedBookOld.getId());
								}
								if (SelectedBook != null) {
									SB.append("- New Private: ");
									SB.append(SelectedBook.getTitle());
									SB.append(":");
									SB.append(SelectedBook.getId());
								}
								Logger.GetLogger().info(
										Yo.getClass().toString(),
										ActualState.getUser().toString(),
										SB.toString());
								// LoadingPanel.getInstance().center();
								// LoadingPanel.getInstance().setLabelTexto("Deleting...");
								// TODO Reparar
								// bookReaderServiceHolder
								// .removeReadingActivityFromAnnotations(
								// ActualActivity.getId(),
								// new AsyncCallback<Integer>() {
								//
								// public void onFailure(
								// Throwable caught) {
								// Window.alert("The annotations could not be deleted");
								// LoadingPanel.getInstance()
								// .hide();
								//
								// }
								//
								// public void onSuccess(Integer result) {
								 SaveacActivitytoServer();
								// LoadingPanel.getInstance()
								// .hide();
								//
								// }
								// });
								//
							}
						} else {
							// if
							// (SelectedCatalog.getId()==SelectedCatalogPublic.getId())
							// Window.alert("The open catalog and the teachers catalog can't be the same");
							// else
							SaveacActivitytoServer();
						}

					}

					private boolean checkbook() {
						// return false;
						return ((SelectedBook != null)
								&& (SelectedBookOld != null) && !(SelectedBook
								.equals(SelectedBookOld)));
					}

					private boolean checkcatalog() {
						return (((SelectedCatalogOld != null)
								&& (SelectedCatalog != null) && !(SelectedCatalog
								.getId().equals(SelectedCatalogOld.getId()))) || ((SelectedCatalogOldPublic != null)
								&& (SelectedCatalogPublic != null) && !(SelectedCatalogPublic
								.getId().equals(SelectedCatalogOldPublic
								.getId()))));

					}

					private void SaveacActivitytoServer() {

						ActualActivity.setBook(SelectedBook);
						if (SelectedCatalog != null)
							ActualActivity.setCloseCatalogo(SelectedCatalog);
						if (SelectedGroup != null)
							ActualActivity.setGroup(SelectedGroup);
						if (SelectedLanguage != null)
							ActualActivity.setLanguage(SelectedLanguage);
						if (SelectedCatalogPublic != null)
							ActualActivity
									.setOpenCatalogo(SelectedCatalogPublic);
						if (Template != null)
							ActualActivity.setTemplate(Template);
						ActualActivity.setIsFreeTemplateAllowed(AllowFreeTemplateCheckBox
								.getValue());
						ActualActivity.setVisualization(SeleccionVisualizacionComboBox
								.getItemText(SeleccionVisualizacionComboBox.getSelectedIndex()));
						if (DefaultType == null || !AllowDefaulTypeCheckBox.getValue())
							ActualActivity.setDefaultType(null);
						else
							ActualActivity.setDefaultType(DefaultType.getId());
						LoadingPanel.getInstance().center();
						LoadingPanel.getInstance().setLabelTexto(
								InformationConstants.SAVING);
						bookReaderServiceHolder.saveReadingActivity(
								ActualActivity, new AsyncCallback<Void>() {

									public void onFailure(Throwable caught) {
										Window.alert(ErrorConstants.ERROR_SAVING_ACTIVITY);
										Logger.GetLogger().severe(Yo.getClass().toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_SAVING_ACTIVITY);
										LoadingPanel.getInstance().hide();

									}

									public void onSuccess(Void result) {
										LoadingPanel.getInstance().hide();
										Yo.hide();

									}
								});

					}
				});
		SaveMenuItem.setHTML(EditorActivityPopupPanel.MENU_SAVE_BUTTON);
		menuBar.addItem(SaveMenuItem);

		CancelMenuItem = new MenuItem(EditorActivityPopupPanel.MENU_CANCEL_BUTTON, false,
				new Command() {
					public void execute() {
						Yo.hide();
					}
				});
		CancelMenuItem.setHTML(EditorActivityPopupPanel.MENU_CANCEL_BUTTON);
		menuBar.addItem(CancelMenuItem);

		VerticalSplitPanel verticalSplitPanel = new VerticalSplitPanel();
		flowPanel.add(verticalSplitPanel);
		verticalSplitPanel.setSize("100%", "100%");

		TabPanelGeneral = new TabPanel();
		verticalSplitPanel.setTopWidget(TabPanelGeneral);
		TabPanelGeneral.setSize("100%", "100%");

		LanguageTabPanel = new ScrollPanel();
		TabPanelGeneral.add(LanguageTabPanel, EditorActivityPopupPanel.TAB_PANEL_LANGUAGE, false);
		LanguageTabPanel.setSize("100%", "226px");

		LanguagePanel = new VerticalPanel();
		LanguageTabPanel.setWidget(LanguagePanel);
		LanguagePanel.setSize("100%", "100%");

		CatalogTabPanel = new ScrollPanel();
		TabPanelGeneral.add(CatalogTabPanel, EditorActivityPopupPanel.TAB_PANEL_CATALOG, false);
		CatalogTabPanel.setSize("100%", "226px");

		HorizontalPanel horizontalPanel_4 = new HorizontalPanel();
		horizontalPanel_4
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		CatalogTabPanel.setWidget(horizontalPanel_4);
		horizontalPanel_4.setSize("100%", "100%");

		CatalogPanel = new VerticalPanel();
		horizontalPanel_4.add(CatalogPanel);
		CatalogPanel.setWidth("400px");

		PanelSelecionDefault = new HorizontalPanel();
		horizontalPanel_4.add(PanelSelecionDefault);
		PanelSelecionDefault.setSpacing(10);
		PanelSelecionDefault
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		PanelSelecionDefault.setSize("280px", "");
		PanelSelecionDefault.setVisible(false);

		AllowDefaulTypeCheckBox = new CheckBox(EditorActivityPopupPanel.ALLOW_DEFAULT_TYPE);
		AllowDefaulTypeCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

					public void onValueChange(ValueChangeEvent<Boolean> event) {
						if (AllowDefaulTypeCheckBox.getValue()) {
							if (DefaultTypeOld != null)
								DefaultTypeLabel
										.setText(EditorActivityPopupPanel.DEFAUL_TYPE_LABEL
												+ DefaultTypeOld);
							BotonSelectDefaultType.setEnabled(true);
						} else {
							DefaultType = null;
							DefaultTypeOld = DefaultType;
							DefaultTypeLabel
									.setText(EditorActivityPopupPanel.DEFAUL_TYPE_LABEL);
							BotonSelectDefaultType.setEnabled(false);
						}
					}
				});
		PanelSelecionDefault.add(AllowDefaulTypeCheckBox);

		BotonSelectDefaultType = new Button(EditorActivityPopupPanel.BOTON_SELECT_DEFAULT_TYPE);
		PanelSelecionDefault.add(BotonSelectDefaultType);
		BotonSelectDefaultType.setWidth("119px");
		BotonSelectDefaultType.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterPush");
			}
		});

		BotonSelectDefaultType.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});

		BotonSelectDefaultType.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {

				((Button) event.getSource())
						.setStyleName("gwt-ButtonCenterOver");

			}
		});

		BotonSelectDefaultType.setStyleName("gwt-ButtonCenter");
		BotonSelectDefaultType.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				FinderDefaultTypePopupPanel FDT = new FinderDefaultTypePopupPanel(Yo,
						SelectedCatalog);
				FDT.center();
			}
		});
		BotonSelectDefaultType.setEnabled(false);

		BooksTabPanel = new ScrollPanel();
		TabPanelGeneral.add(BooksTabPanel, EditorActivityPopupPanel.TAB_PANEL_BOOK, false);
		BooksTabPanel.setSize("100%", "226px");

		BooksPanel = new VerticalPanel();
		BooksTabPanel.setWidget(BooksPanel);
		BooksPanel.setSize("100%", "100%");

		GroupsTabPanel = new ScrollPanel();
		TabPanelGeneral.add(GroupsTabPanel, EditorActivityPopupPanel.TAB_PANEL_GROUPS, false);
		GroupsTabPanel.setSize("100%", "226px");

		GroupsPanel = new VerticalPanel();
		GroupsTabPanel.setWidget(GroupsPanel);
		GroupsPanel.setSize("100%", "100%");

		TemplatesTabPanel = new SimplePanel();
		TabPanelGeneral.add(TemplatesTabPanel, EditorActivityPopupPanel.TAB_PANEL_TEMPLATES, false);
		TemplatesTabPanel.setSize("100%", "226px");

		VerticalPanel verticalPanel = new VerticalPanel();
		TemplatesTabPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");

		ScrollPanel scrollPanel = new ScrollPanel();
		verticalPanel.add(scrollPanel);
		scrollPanel.setSize("100%", "169px");

		TemplatePanel = new VerticalPanel();
		scrollPanel.setWidget(TemplatePanel);
		TemplatePanel.setSize("100%", "100%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "45px");

		AllowFreeTemplateCheckBox = new CheckBox(EditorActivityPopupPanel.ALLOW_BLANK_TEMPLATE);
		AllowFreeTemplateCheckBox.setValue(true);
		AllowFreeTemplateCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (AllowFreeTemplateCheckBox.getValue())
					BlankTemplateAllowedLabel.setVisible(true);
				else if (Template != null)
					BlankTemplateAllowedLabel.setVisible(false);
				else {
					Window.alert(ErrorConstants.TEMPLATES_CAT_BE_EMPTY);
					AllowFreeTemplateCheckBox.setValue(true, false);
				}
			}
		});
		horizontalPanel.add(AllowFreeTemplateCheckBox);

		VisualizacionTabPanel = new ScrollPanel();
		TabPanelGeneral.add(VisualizacionTabPanel, EditorActivityPopupPanel.TAB_PANEL_VISUALIZACION, false);
		VisualizacionTabPanel.setSize("100%", "226px");

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		VisualizacionTabPanel.setWidget(horizontalPanel_1);
		horizontalPanel_1.setSize("100%", "224px");

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.add(verticalPanel_1);
		verticalPanel_1.setSize("225px", "100%");

		SeleccionVisualizacionComboBox = new ListBox();
		SeleccionVisualizacionComboBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				if (SeleccionVisualizacionComboBox.getItemText(SeleccionVisualizacionComboBox.getSelectedIndex()).equals(
						Constants.VISUAL_ARBOL)) {
					VisualizacionLabel.setText(EditorActivityPopupPanel.VISUALIZACION_LABEL 
							+ Constants.VISUAL_ARBOL);
					imageVisualization.setUrl(EditorActivityPopupPanel.IMAGEN_ARBOL);
				} else {
					VisualizacionLabel.setText(EditorActivityPopupPanel.VISUALIZACION_LABEL
							+ Constants.VISUAL_KEY);
					imageVisualization.setUrl(EditorActivityPopupPanel.IMAGEN_KEY);
				}
			}
		});
		SeleccionVisualizacionComboBox.addItem(Constants.VISUAL_ARBOL);
		SeleccionVisualizacionComboBox.addItem(Constants.VISUAL_KEY);
		SeleccionVisualizacionComboBox.setSelectedIndex(0);
		verticalPanel_1.add(SeleccionVisualizacionComboBox);
		SeleccionVisualizacionComboBox.setWidth("199px");

		VerticalPanel verticalPanel_3 = new VerticalPanel();
		verticalPanel_3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_3
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.add(verticalPanel_3);
		verticalPanel_3.setSize("545px", "100%");

		imageVisualization = new Image(EditorActivityPopupPanel.IMAGEN_ARBOL);
		verticalPanel_3.add(imageVisualization);
		imageVisualization.setSize("503px", "151px");

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setSpacing(6);
		verticalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalSplitPanel.setBottomWidget(verticalPanel_2);
		verticalPanel_2.setSize("100%", "236px");

		LanguageLabel = new Label(EditorActivityPopupPanel.LANGUAGE_LABEL);
		verticalPanel_2.add(LanguageLabel);

		PrivateCatalogLabel = new Label(EditorActivityPopupPanel.PRIVATE_CATALOG_LABEL);
		verticalPanel_2.add(PrivateCatalogLabel);

		PublicCatalogLabel = new Label(EditorActivityPopupPanel.PUBLIC_CATALOG_LABEL);
		verticalPanel_2.add(PublicCatalogLabel);

		DefaultTypeLabel = new Label(EditorActivityPopupPanel.DEFAUL_TYPE_LABEL);
		verticalPanel_2.add(DefaultTypeLabel);

		BooksLabel = new Label(EditorActivityPopupPanel.BOOK_LABEL);
		verticalPanel_2.add(BooksLabel);

		GroupsLabel = new Label(EditorActivityPopupPanel.GROUPS_LABEL);
		verticalPanel_2.add(GroupsLabel);

		VerticalPanel verticalPanel_4 = new VerticalPanel();
		verticalPanel_2.add(verticalPanel_4);

		TemplateLabel = new Label(EditorActivityPopupPanel.TEMPLATE_LABEL);
		verticalPanel_4.add(TemplateLabel);

		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		verticalPanel_4.add(horizontalPanel_2);
		horizontalPanel_2.setSpacing(3);

		SimplePanel simplePanel = new SimplePanel();
		horizontalPanel_2.add(simplePanel);
		simplePanel.setSize("35px", "18px");

		BlankTemplateAllowedLabel = new Label(EditorActivityPopupPanel.BLANK_TEMPLATE_ALLOWED);
		horizontalPanel_2.add(BlankTemplateAllowedLabel);
		BlankTemplateAllowedLabel.setStyleName("gwt-LabelRedactivity");

		VisualizacionLabel = new Label(EditorActivityPopupPanel.VISUALIZACION_LABEL
				+ Constants.VISUAL_ARBOL);
		verticalPanel_2.add(VisualizacionLabel);

		generateOldCampsAndPanels();

		TabPanelGeneral.selectTab(0);
		
		PanelEdicion=new AbsolutePanel();

	}

	private void closeEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion,flowPanel.getOffsetWidth()-40, 0);
		PanelEdicion.setSize("40px","50px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("");
		Button Boton=new Button();
		PanelEdicion.add(Boton,0,0);
		Boton.setHTML(InformationConstants.EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				OpenEditPanel();
				
			}
		});
		
	}

	public void OpenEditPanel() {
		GeneralPanel.remove(PanelEdicion);
		GeneralPanel.add(PanelEdicion,0,0);
		PanelEdicion.setSize(flowPanel.getOffsetWidth()+"px",flowPanel.getOffsetHeight()+"px");
		PanelEdicion.clear();
		PanelEdicion.setStyleName("BlancoTransparente");
		Button Boton=new Button();
		Boton.setHTML(InformationConstants.END_EDIT_BOTTON);
		Boton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				closeEditPanel();
				
				if (!WellcomeMenuItemTextBox.getText().isEmpty())
					MENU_WELLCOME_TEXT=WellcomeMenuItemTextBox.getText();
				else MENU_WELLCOME_TEXT=MENU_WELLCOME_TEXT_RESET;

				if (!SaveMenuItemTextBox.getText().isEmpty())
				MENU_SAVE_BUTTON=SaveMenuItemTextBox.getText();
			else MENU_SAVE_BUTTON=MENU_SAVE_BUTTON_RESET;
			
				if (!CancelMenuItemTextBox.getText().isEmpty())
					MENU_CANCEL_BUTTON=CancelMenuItemTextBox.getText();
				else MENU_CANCEL_BUTTON=MENU_CANCEL_BUTTON_RESET;
				
				if (!LanguageTabPanelTextBox.getText().isEmpty())
					TAB_PANEL_LANGUAGE=LanguageTabPanelTextBox.getText();
				else TAB_PANEL_LANGUAGE=TAB_PANEL_LANGUAGE_RESET;
				
				if (!CatalogTabPanelTextBox.getText().isEmpty())
					TAB_PANEL_CATALOG=CatalogTabPanelTextBox.getText();
				else TAB_PANEL_CATALOG=TAB_PANEL_CATALOG_RESET;
				
				if (!AllowDefaulTypeCheckBoxTextBox.getText().isEmpty())
					ALLOW_DEFAULT_TYPE=AllowDefaulTypeCheckBoxTextBox.getText();
				else ALLOW_DEFAULT_TYPE=ALLOW_DEFAULT_TYPE_RESET;
				
				if (!BotonSelectDefaultTypeTextBox.getText().isEmpty())
					BOTON_SELECT_DEFAULT_TYPE=BotonSelectDefaultTypeTextBox.getText();
				else BOTON_SELECT_DEFAULT_TYPE=BOTON_SELECT_DEFAULT_TYPE_RESET;
				
				if (!BooksTabPanelTextBox.getText().isEmpty())
					TAB_PANEL_BOOK=BooksTabPanelTextBox.getText();
				else TAB_PANEL_BOOK=TAB_PANEL_BOOK_RESET;
				
				if (!GroupsTabPanelTextBox.getText().isEmpty())
					TAB_PANEL_GROUPS=GroupsTabPanelTextBox.getText();
				else TAB_PANEL_GROUPS=TAB_PANEL_GROUPS_RESET;
				
				if (!TemplatesTabPanelTextBox.getText().isEmpty())
					TAB_PANEL_TEMPLATES=TemplatesTabPanelTextBox.getText();
				else TAB_PANEL_TEMPLATES=TAB_PANEL_TEMPLATES_RESET;
				
				if (!AllowFreeTemplateCheckBoxTextBox.getText().isEmpty())
					ALLOW_BLANK_TEMPLATE=AllowFreeTemplateCheckBoxTextBox.getText();
				else ALLOW_BLANK_TEMPLATE=ALLOW_BLANK_TEMPLATE_RESET;
				
				if (!VisualizacionTabPanelTextBox.getText().isEmpty())
					TAB_PANEL_VISUALIZACION=VisualizacionTabPanelTextBox.getText();
				else TAB_PANEL_VISUALIZACION=TAB_PANEL_VISUALIZACION_RESET;
				
				if (!LanguageLabelTextBox.getText().isEmpty())
					LANGUAGE_LABEL=LanguageLabelTextBox.getText();
				else LANGUAGE_LABEL=LANGUAGE_LABEL_RESET;
				
				if (!PrivateCatalogLabelTextBox.getText().isEmpty())
					PRIVATE_CATALOG_LABEL=PrivateCatalogLabelTextBox.getText();
				else PRIVATE_CATALOG_LABEL=PRIVATE_CATALOG_LABEL_RESET;
				
				if (!PublicCatalogLabelTextBox.getText().isEmpty())
					PUBLIC_CATALOG_LABEL=PublicCatalogLabelTextBox.getText();
				else PUBLIC_CATALOG_LABEL=PUBLIC_CATALOG_LABEL_RESET;
				
				
				if (!DefaultTypeLabelTextBox.getText().isEmpty())
				DEFAUL_TYPE_LABEL=DefaultTypeLabelTextBox.getText();
			else DEFAUL_TYPE_LABEL=DEFAUL_TYPE_LABEL_RESET;
			
				
				
				if (!BooksLabelTextBox.getText().isEmpty())
				BOOK_LABEL=BooksLabelTextBox.getText();
			else BOOK_LABEL=BOOK_LABEL_RESET;
			
				
				
				if (!GroupsLabel.getText().isEmpty())
				GROUPS_LABEL=GroupsLabel.getText();
			else GROUPS_LABEL=GROUPS_LABEL_RESET;
			
				
				
				if (!TemplateLabelTextBox.getText().isEmpty())
				TEMPLATE_LABEL=TemplateLabelTextBox.getText();
			else TEMPLATE_LABEL=TEMPLATE_LABEL_RESET;
			
				
				
				if (!BlankTemplateAllowedLabelTextBox.getText().isEmpty())
				BLANK_TEMPLATE_ALLOWED=BlankTemplateAllowedLabelTextBox.getText();
			else BLANK_TEMPLATE_ALLOWED=BLANK_TEMPLATE_ALLOWED_RESET;
			
				
				
				if (!VisualizacionLabelTextBox.getText().isEmpty())
					VISUALIZACION_LABEL=VisualizacionLabelTextBox.getText();
			else VISUALIZACION_LABEL=VISUALIZACION_LABEL_RESET;
			
								
				ParsearFieldsAItems();
				SaveChages();
			}
		});
		WellcomeMenuItemTextBox=new TextBox();
		WellcomeMenuItemTextBox.setText(EditorActivityPopupPanel.MENU_WELLCOME_TEXT);
		WellcomeMenuItemTextBox.setSize(WellcomeMenuItem.getOffsetWidth()+"px", WellcomeMenuItem.getOffsetHeight()+"px");
		PanelEdicion.add(WellcomeMenuItemTextBox, WellcomeMenuItem.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, WellcomeMenuItem.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		SaveMenuItemTextBox=new TextBox();
		SaveMenuItemTextBox.setText(MENU_SAVE_BUTTON);
		SaveMenuItemTextBox.setSize(SaveMenuItem.getOffsetWidth()+"px", SaveMenuItem.getOffsetHeight()+"px");
		PanelEdicion.add(SaveMenuItemTextBox, SaveMenuItem.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, SaveMenuItem.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		
		CancelMenuItemTextBox=new TextBox();
		CancelMenuItemTextBox.setText(MENU_CANCEL_BUTTON);
		CancelMenuItemTextBox.setSize(CancelMenuItem.getOffsetWidth()+"px", CancelMenuItem.getOffsetHeight()+"px");
		PanelEdicion.add(CancelMenuItemTextBox, CancelMenuItem.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, CancelMenuItem.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		int NTabs=TabPanelGeneral.getWidgetCount();
		int Dist=TabPanelGeneral.getTabBar().getOffsetWidth()/NTabs;
		int WTabCounter=0;
		int SeparacionExtaPorRoun=1;
		int HExtraTabCounter=1;
		
		LanguageTabPanelTextBox=new TextBox();
		LanguageTabPanelTextBox.setText(TAB_PANEL_LANGUAGE);
		LanguageTabPanelTextBox.setSize(Dist+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(LanguageTabPanelTextBox, (TabPanelGeneral.getTabBar().getAbsoluteLeft()+Dist*WTabCounter)+SeparacionExtaPorRoun-flowPanel.getAbsoluteLeft()-DecoradorWidth,TabPanelGeneral.getTabBar().getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		WTabCounter++;
		
		CatalogTabPanelTextBox=new TextBox();
		CatalogTabPanelTextBox.setText(TAB_PANEL_CATALOG);
		CatalogTabPanelTextBox.setSize(Dist+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(CatalogTabPanelTextBox,  (TabPanelGeneral.getTabBar().getAbsoluteLeft()+Dist*WTabCounter)+SeparacionExtaPorRoun-flowPanel.getAbsoluteLeft()-DecoradorWidth,TabPanelGeneral.getTabBar().getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		WTabCounter++;
		
		
		AllowDefaulTypeCheckBoxTextBox=new TextBox();
		AllowDefaulTypeCheckBoxTextBox.setText(ALLOW_DEFAULT_TYPE);
		AllowDefaulTypeCheckBoxTextBox.setSize(AnchuraOutElements+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(AllowDefaulTypeCheckBoxTextBox,TabPanelGeneral.getTabBar().getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, TabPanelGeneral.getAbsoluteTop() + TabPanelGeneral.getTabBar().getOffsetHeight()*HExtraTabCounter -flowPanel.getAbsoluteTop()+MargenAlto);
		HExtraTabCounter++;
		
		BotonSelectDefaultTypeTextBox=new TextBox();
		BotonSelectDefaultTypeTextBox.setText(BOTON_SELECT_DEFAULT_TYPE);
		BotonSelectDefaultTypeTextBox.setSize(AnchuraOutElements+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(BotonSelectDefaultTypeTextBox, TabPanelGeneral.getTabBar().getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, TabPanelGeneral.getAbsoluteTop() + TabPanelGeneral.getTabBar().getOffsetHeight()*HExtraTabCounter -flowPanel.getAbsoluteTop()+MargenAlto);
		HExtraTabCounter++;
		
		BooksTabPanelTextBox=new TextBox();
		BooksTabPanelTextBox.setText(TAB_PANEL_BOOK);
		BooksTabPanelTextBox.setSize(Dist+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(BooksTabPanelTextBox,  (TabPanelGeneral.getTabBar().getAbsoluteLeft()+Dist*WTabCounter)+SeparacionExtaPorRoun-flowPanel.getAbsoluteLeft()-DecoradorWidth,TabPanelGeneral.getTabBar().getAbsoluteTop() -flowPanel.getAbsoluteTop()-DecoradorWidth);
		WTabCounter++;
		
		GroupsTabPanelTextBox=new TextBox();
		GroupsTabPanelTextBox.setText(TAB_PANEL_GROUPS);
		GroupsTabPanelTextBox.setSize(Dist+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(GroupsTabPanelTextBox,(TabPanelGeneral.getTabBar().getAbsoluteLeft()+Dist*WTabCounter)+SeparacionExtaPorRoun-flowPanel.getAbsoluteLeft()-DecoradorWidth,TabPanelGeneral.getTabBar().getAbsoluteTop() -flowPanel.getAbsoluteTop()-DecoradorWidth);
		WTabCounter++;
		
		TemplatesTabPanelTextBox=new TextBox();
		TemplatesTabPanelTextBox.setText(TAB_PANEL_TEMPLATES);
		TemplatesTabPanelTextBox.setSize(Dist+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(TemplatesTabPanelTextBox, (TabPanelGeneral.getTabBar().getAbsoluteLeft()+Dist*WTabCounter)+SeparacionExtaPorRoun-flowPanel.getAbsoluteLeft()-DecoradorWidth,TabPanelGeneral.getTabBar().getAbsoluteTop() -flowPanel.getAbsoluteTop()-DecoradorWidth);
		WTabCounter++;
		
		AllowFreeTemplateCheckBoxTextBox=new TextBox();
		AllowFreeTemplateCheckBoxTextBox.setText(ALLOW_BLANK_TEMPLATE);
		AllowFreeTemplateCheckBoxTextBox.setSize(AnchuraOutElements+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(AllowFreeTemplateCheckBoxTextBox, TabPanelGeneral.getTabBar().getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, TabPanelGeneral.getAbsoluteTop() + TabPanelGeneral.getTabBar().getOffsetHeight()*HExtraTabCounter-flowPanel.getAbsoluteTop()+MargenAlto);
		HExtraTabCounter++;
		
		VisualizacionTabPanelTextBox=new TextBox();
		VisualizacionTabPanelTextBox.setText(TAB_PANEL_VISUALIZACION);
		VisualizacionTabPanelTextBox.setSize(Dist+"px", TabPanelGeneral.getTabBar().getOffsetHeight()+"px");
		PanelEdicion.add(VisualizacionTabPanelTextBox,(TabPanelGeneral.getTabBar().getAbsoluteLeft()+Dist*WTabCounter)+SeparacionExtaPorRoun-flowPanel.getAbsoluteLeft()-DecoradorWidth,TabPanelGeneral.getTabBar().getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		LanguageLabelTextBox=new TextBox();
		LanguageLabelTextBox.setText(LANGUAGE_LABEL);
		LanguageLabelTextBox.setSize(LanguageLabel.getOffsetWidth()+"px", LanguageLabel.getOffsetHeight()+"px");
		PanelEdicion.add(LanguageLabelTextBox, LanguageLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, LanguageLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		PrivateCatalogLabelTextBox=new TextBox();
		PrivateCatalogLabelTextBox.setText(PRIVATE_CATALOG_LABEL);
		PrivateCatalogLabelTextBox.setSize(PrivateCatalogLabel.getOffsetWidth()+"px", PrivateCatalogLabel.getOffsetHeight()+"px");
		PanelEdicion.add(PrivateCatalogLabelTextBox, PrivateCatalogLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, PrivateCatalogLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		PublicCatalogLabelTextBox=new TextBox();
		PublicCatalogLabelTextBox.setText(PUBLIC_CATALOG_LABEL);
		PublicCatalogLabelTextBox.setSize(PublicCatalogLabel.getOffsetWidth()+"px", PublicCatalogLabel.getOffsetHeight()+"px");
		PanelEdicion.add(PublicCatalogLabelTextBox, PublicCatalogLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, PublicCatalogLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		DefaultTypeLabelTextBox=new TextBox();
		DefaultTypeLabelTextBox.setText(DEFAUL_TYPE_LABEL);
		DefaultTypeLabelTextBox.setSize(DefaultTypeLabel.getOffsetWidth()+"px", DefaultTypeLabel.getOffsetHeight()+"px");
		PanelEdicion.add(DefaultTypeLabelTextBox, DefaultTypeLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, DefaultTypeLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		BooksLabelTextBox=new TextBox();
		BooksLabelTextBox.setText(BOOK_LABEL);
		BooksLabelTextBox.setSize(BooksLabel.getOffsetWidth()+"px", BooksLabel.getOffsetHeight()+"px");
		PanelEdicion.add(BooksLabelTextBox, BooksLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, BooksLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		GroupsLabelTextBox=new TextBox();
		GroupsLabelTextBox.setText(GROUPS_LABEL);
		GroupsLabelTextBox.setSize(GroupsLabel.getOffsetWidth()+"px", GroupsLabel.getOffsetHeight()+"px");
		PanelEdicion.add(GroupsLabelTextBox, GroupsLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, GroupsLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		TemplateLabelTextBox=new TextBox();
		TemplateLabelTextBox.setText(TEMPLATE_LABEL);
		TemplateLabelTextBox.setSize(TemplateLabel.getOffsetWidth()+"px", TemplateLabel.getOffsetHeight()+"px");
		PanelEdicion.add(TemplateLabelTextBox, TemplateLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, TemplateLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		BlankTemplateAllowedLabelTextBox=new TextBox();
		BlankTemplateAllowedLabelTextBox.setText(BLANK_TEMPLATE_ALLOWED);
		BlankTemplateAllowedLabelTextBox.setSize(BlankTemplateAllowedLabel.getOffsetWidth()+"px", BlankTemplateAllowedLabel.getOffsetHeight()+"px");
		PanelEdicion.add(BlankTemplateAllowedLabelTextBox, BlankTemplateAllowedLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, BlankTemplateAllowedLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		
		VisualizacionLabelTextBox=new TextBox();
		VisualizacionLabelTextBox.setText(VISUALIZACION_LABEL);
		VisualizacionLabelTextBox.setSize(VisualizacionLabel.getOffsetWidth()+"px", VisualizacionLabel.getOffsetHeight()+"px");
		PanelEdicion.add(VisualizacionLabelTextBox, VisualizacionLabel.getAbsoluteLeft()-flowPanel.getAbsoluteLeft()-DecoradorWidth, VisualizacionLabel.getAbsoluteTop()-flowPanel.getAbsoluteTop()-DecoradorWidth);
		PanelEdicion.add(Boton,PanelEdicion.getOffsetWidth()-70, 0);
		
	}

	protected void SaveChages() {
		Language LanguageActual = ActualState.getActualLanguage();
		String EditorActivityLanguageConfiguration=toFile();
		LanguageActual.setEditorActivityLanguageConfiguration(EditorActivityLanguageConfiguration);
		ActualState.saveLanguageActual(LanguageActual);
		
	}

	public String toFile() {
		StringBuffer SB=new StringBuffer();
		SB.append(MENU_WELLCOME_TEXT + '\n');
		SB.append( MENU_SAVE_BUTTON + '\n' );
		SB.append( MENU_CANCEL_BUTTON + '\n' );
		SB.append( TAB_PANEL_LANGUAGE + '\n' );
		SB.append( TAB_PANEL_CATALOG + '\n' );
		SB.append( ALLOW_DEFAULT_TYPE + '\n' );
		SB.append( BOTON_SELECT_DEFAULT_TYPE + '\n' );
		SB.append( TAB_PANEL_BOOK + '\n' );
		SB.append( TAB_PANEL_GROUPS + '\n' );
		SB.append( TAB_PANEL_TEMPLATES + '\n' );
		SB.append( ALLOW_BLANK_TEMPLATE + '\n' );
		SB.append( TAB_PANEL_VISUALIZACION + '\n' );
		SB.append( LANGUAGE_LABEL + '\n' );
		SB.append( PRIVATE_CATALOG_LABEL + '\n' );
		SB.append( PUBLIC_CATALOG_LABEL + '\n' );
		SB.append( DEFAUL_TYPE_LABEL + '\n' );
		SB.append( BOOK_LABEL + '\n' );
		SB.append( GROUPS_LABEL + '\n' );
		SB.append( TEMPLATE_LABEL + '\n' );
		SB.append( BLANK_TEMPLATE_ALLOWED + '\n' );
		SB.append( VISUALIZACION_LABEL + '\n' );
		return SB.toString();
	}

	public static void FromFile(String Entrada) {
		String[] Lista = Entrada.split("\n");
		if (Lista.length >= NCampos) {
			if (!Lista[0].isEmpty())
				MENU_WELLCOME_TEXT = Lista[0];
			else MENU_WELLCOME_TEXT=MENU_WELLCOME_TEXT_RESET;
			if (!Lista[1].isEmpty())
				MENU_SAVE_BUTTON = Lista[1];
			else MENU_SAVE_BUTTON=MENU_SAVE_BUTTON_RESET;
			if (!Lista[2].isEmpty())
				MENU_CANCEL_BUTTON = Lista[2];
			else MENU_CANCEL_BUTTON=MENU_CANCEL_BUTTON_RESET;
			if (!Lista[3].isEmpty())
				TAB_PANEL_LANGUAGE = Lista[3];
			else TAB_PANEL_LANGUAGE=TAB_PANEL_LANGUAGE_RESET;
			if (!Lista[4].isEmpty())
				TAB_PANEL_CATALOG = Lista[4];
			else TAB_PANEL_CATALOG=TAB_PANEL_CATALOG_RESET;
			if (!Lista[5].isEmpty())
				ALLOW_DEFAULT_TYPE = Lista[5];
			else ALLOW_DEFAULT_TYPE=ALLOW_DEFAULT_TYPE_RESET;
			if (!Lista[6].isEmpty())
				BOTON_SELECT_DEFAULT_TYPE = Lista[6];
			else BOTON_SELECT_DEFAULT_TYPE=BOTON_SELECT_DEFAULT_TYPE_RESET;
			if (!Lista[7].isEmpty())
				TAB_PANEL_BOOK = Lista[7];
			else TAB_PANEL_BOOK=TAB_PANEL_BOOK_RESET;
			if (!Lista[8].isEmpty())
				TAB_PANEL_GROUPS = Lista[8];
			else TAB_PANEL_GROUPS=TAB_PANEL_GROUPS_RESET;
			if (!Lista[9].isEmpty())
				TAB_PANEL_TEMPLATES = Lista[9];
			else TAB_PANEL_TEMPLATES=TAB_PANEL_TEMPLATES_RESET;
			if (!Lista[10].isEmpty())
				ALLOW_BLANK_TEMPLATE = Lista[10];
			else ALLOW_BLANK_TEMPLATE=ALLOW_BLANK_TEMPLATE_RESET;
			if (!Lista[11].isEmpty())
				TAB_PANEL_VISUALIZACION = Lista[11];
			else TAB_PANEL_VISUALIZACION=TAB_PANEL_VISUALIZACION_RESET;
			if (!Lista[12].isEmpty())
				LANGUAGE_LABEL = Lista[12];
			else LANGUAGE_LABEL=LANGUAGE_LABEL_RESET;
			if (!Lista[13].isEmpty())
				PRIVATE_CATALOG_LABEL = Lista[13];
			else PRIVATE_CATALOG_LABEL=PRIVATE_CATALOG_LABEL_RESET;
			if (!Lista[14].isEmpty())
				PUBLIC_CATALOG_LABEL = Lista[14];
			else PUBLIC_CATALOG_LABEL=PUBLIC_CATALOG_LABEL_RESET;
			if (!Lista[14].isEmpty())
				DEFAUL_TYPE_LABEL = Lista[15];
			else DEFAUL_TYPE_LABEL=DEFAUL_TYPE_LABEL_RESET;
			if (!Lista[14].isEmpty())
				BOOK_LABEL = Lista[16];
			else BOOK_LABEL=BOOK_LABEL_RESET;
			if (!Lista[14].isEmpty())
				GROUPS_LABEL = Lista[17];
			else GROUPS_LABEL=GROUPS_LABEL_RESET;
			if (!Lista[14].isEmpty())
				TEMPLATE_LABEL = Lista[18];
			else TEMPLATE_LABEL=TEMPLATE_LABEL_RESET;
			if (!Lista[14].isEmpty())
				BLANK_TEMPLATE_ALLOWED = Lista[19];
			else BLANK_TEMPLATE_ALLOWED=BLANK_TEMPLATE_ALLOWED_RESET;
			if (!Lista[14].isEmpty())
				VISUALIZACION_LABEL = Lista[20];
			else VISUALIZACION_LABEL=VISUALIZACION_LABEL_RESET;
		}
		else 
			Logger.GetLogger().severe(EditorActivityPopupPanel.class.toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_LOADING_LANGUAGE_IN  + EDITORACTIVITY_NAME);	
	}
	
	protected void ParsearFieldsAItems() {

		WellcomeMenuItem.setHTML(MENU_WELLCOME_TEXT);	
		SaveMenuItem.setHTML(MENU_SAVE_BUTTON);
		CancelMenuItem.setHTML(MENU_CANCEL_BUTTON);
		TabPanelGeneral.clear();
		TabPanelGeneral.add(LanguageTabPanel, EditorActivityPopupPanel.TAB_PANEL_LANGUAGE, false);
		TabPanelGeneral.add(CatalogTabPanel, EditorActivityPopupPanel.TAB_PANEL_CATALOG, false);
		AllowDefaulTypeCheckBox.setHTML(ALLOW_DEFAULT_TYPE);
		BotonSelectDefaultType.setHTML(BOTON_SELECT_DEFAULT_TYPE);
		TabPanelGeneral.add(BooksTabPanel, EditorActivityPopupPanel.TAB_PANEL_BOOK, false);
		TabPanelGeneral.add(GroupsTabPanel, EditorActivityPopupPanel.TAB_PANEL_GROUPS, false);
		TabPanelGeneral.add(TemplatesTabPanel, EditorActivityPopupPanel.TAB_PANEL_TEMPLATES, false);
		AllowFreeTemplateCheckBox.setHTML(ALLOW_BLANK_TEMPLATE);
		TabPanelGeneral.add(VisualizacionTabPanel, EditorActivityPopupPanel.TAB_PANEL_VISUALIZACION, false);
		LanguageLabel.setText(LANGUAGE_LABEL);
		PrivateCatalogLabel.setText(PRIVATE_CATALOG_LABEL);
		PublicCatalogLabel.setText(PUBLIC_CATALOG_LABEL);
		DefaultTypeLabel.setText(DEFAUL_TYPE_LABEL);
		BooksLabel.setText(BOOK_LABEL);
		GroupsLabel.setText(GROUPS_LABEL);
		TemplateLabel.setText(TEMPLATE_LABEL);
		BlankTemplateAllowedLabel.setText(BLANK_TEMPLATE_ALLOWED);
		VisualizacionLabel.setText(VISUALIZACION_LABEL);
		
	}

	private void generateOldCampsAndPanels() {
		if (ActualActivity.getCloseCatalogo() != null) {

			SelectedCatalog = ActualActivity.getCloseCatalogo();
			SelectedCatalogOld = ActualActivity.getCloseCatalogo();
			PrivateCatalogLabel.setText(EditorActivityPopupPanel.PRIVATE_CATALOG_LABEL
					+ SelectedCatalog.getCatalogName());
			PanelSelecionDefault.setVisible(true);
			if (ActualActivity.getDefaultType() != null) {
				LoadDefType(ActualActivity.getDefaultType());
				//
				// DefaultType=generaTypeAntiguo(ActualActivity.getDefaultType());
				// if (DefaultType!=null)
				// DefaultTypeLabel.setText(DEFAUL_TYPE_LABEL +
				// DefaultType.getName());
				// else
				// Window.alert(ErrorConstants.ERROR_RETRIVING_DEFAULT_TYPE_FROM_CATALOG);
			} else {
				generalangaugeOld();
			}

		} else
			generalangaugeOld();

	}

	private void LoadDefType(Long defaultType2) {
		bookReaderServiceHolder.loadTypeById(defaultType2,
				new AsyncCallback<TypeClient>() {

					@Override
					public void onSuccess(TypeClient result) {
						generalangaugeOld();
						DefaultType = result;
						DefaultTypeLabel.setText(EditorActivityPopupPanel.DEFAUL_TYPE_LABEL
								+ DefaultType.getName());
					}

					@Override
					public void onFailure(Throwable caught) {
						generalangaugeOld();
						Window.alert(ErrorConstants.ERROR_RETRIVING_DEFAULT_TYPE_FROM_CATALOG);
						Logger.GetLogger().severe(Yo.getClass().toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_RETRIVING_DEFAULT_TYPE_FROM_CATALOG);
					}
				});

	}

	private void generalangaugeOld() {
		if (ActualActivity.getLanguage() != null) {

			SelectedLanguage = ActualActivity.getLanguage();
			LanguageLabel.setText(EditorActivityPopupPanel.LANGUAGE_LABEL + SelectedLanguage.getName());
			generabookOld();

		} else
			generabookOld();

	}

	private void generabookOld() {
		if (ActualActivity.getBook() != null) {
			SelectedBook = ActualActivity.getBook();
			SelectedBookOld = SelectedBook;
			BooksLabel.setText(EditorActivityPopupPanel.BOOK_LABEL + SelectedBook.getTitle());
			generagroupOld();
		} else
			generagroupOld();

	}

	private void generagroupOld() {
		if (ActualActivity.getGroup() != null) {
			GroupsLabel.setText(EditorActivityPopupPanel.GROUPS_LABEL
					+ ActualActivity.getGroup().getName());
			SelectedGroup = ActualActivity.getGroup();
			generatecatalogPublicOld();

		} else
			generatecatalogPublicOld();
		;

	}

	private void generatecatalogPublicOld() {
		if (ActualActivity.getOpenCatalogo() != null) {
			SelectedCatalogPublic = ActualActivity.getOpenCatalogo();
			SelectedCatalogOldPublic = ActualActivity.getOpenCatalogo();
			PublicCatalogLabel.setText(EditorActivityPopupPanel.PUBLIC_CATALOG_LABEL
					+ SelectedCatalogPublic.getCatalogName());
			generateTemplateOld();

		} else
			generateTemplateOld();
	}

	private void generateTemplateOld() {
		if (ActualActivity.getTemplate() != null) {

			Template = ActualActivity.getTemplate();
			TemplateLabel.setText(EditorActivityPopupPanel.TEMPLATE_LABEL + Template.getName());
			generateBlancTemplateOld();

		} else
			generateBlancTemplateOld();

	}

	private void generateBlancTemplateOld() {
		if (ActualActivity.getIsFreeTemplateAllowed()) {
			BlankTemplateAllowedLabel.setVisible(true);
			AllowFreeTemplateCheckBox.setValue(true, false);
		} else {
			AllowFreeTemplateCheckBox.setValue(false, false);
			BlankTemplateAllowedLabel.setVisible(false);
		}

		generateFinderOld();

	}

	private void generateFinderOld() {
		if (ActualActivity.getVisualization() != null) {
			if (ActualActivity.getVisualization()
					.equals(Constants.VISUAL_ARBOL)) {
				SeleccionVisualizacionComboBox.setSelectedIndex(0);
				VisualizacionLabel.setText(EditorActivityPopupPanel.VISUALIZACION_LABEL
						+ Constants.VISUAL_ARBOL);
				imageVisualization.setUrl(EditorActivityPopupPanel.IMAGEN_ARBOL);

			} else {
				SeleccionVisualizacionComboBox.setSelectedIndex(1);
				VisualizacionLabel.setText(EditorActivityPopupPanel.VISUALIZACION_LABEL
						+ Constants.VISUAL_KEY);
				imageVisualization.setUrl(EditorActivityPopupPanel.IMAGEN_KEY);
			}
			Generatepanels();

		} else
			Generatepanels();
	}

	private void Generatepanels() {
		bookReaderServiceHolder.getVisbibleCatalogsByProfessorId(ActualState
				.getUser().getId(), new AsyncCallback<List<CatalogoClient>>() {

			public void onSuccess(List<CatalogoClient> result) {
				for (int i = 0; i < result.size() - 1; i++) {

					CatalogoClient catalog = result.get(i);
					Botoncatalogo BC = new Botoncatalogo(catalog);
					BC.setSize("100%", "100%");
					CatalogPanel.add(BC);
					BC.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							Botoncatalogo BCE = (Botoncatalogo) event
									.getSource();
							PanelSeleccionCatalogo PSC = new PanelSeleccionCatalogo(
									BCE.getCatalogo(), PrivateCatalogLabel,
									PublicCatalogLabel, Yo);
							PSC.showRelativeTo(BCE);

						}
					});
					BC.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonPush");
						}
					});
					BC.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonTOP");
						}
					});
					BC.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonTOPOver");
						}
					});
					BC.setStyleName("gwt-ButtonTOP");
				}
				if (!result.isEmpty()) {
					CatalogoClient catalog = result.get(result.size() - 1);
					Botoncatalogo BC = new Botoncatalogo(catalog);
					BC.setSize("100%", "100%");
					CatalogPanel.add(BC);
					BC.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							Botoncatalogo BCE = (Botoncatalogo) event
									.getSource();
							PanelSeleccionCatalogo PSC = new PanelSeleccionCatalogo(
									BCE.getCatalogo(), PrivateCatalogLabel,
									PublicCatalogLabel, Yo);
							PSC.showRelativeTo(BCE);

						}
					});
					BC.setStyleName("gwt-ButtonBotton");
					BC.addMouseOutHandler(new MouseOutHandler() {
						public void onMouseOut(MouseOutEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonBotton");
						}
					});
					BC.addMouseOverHandler(new MouseOverHandler() {
						public void onMouseOver(MouseOverEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonBottonOver");
						}
					});
					BC.addMouseDownHandler(new MouseDownHandler() {
						public void onMouseDown(MouseDownEvent event) {
							((Button) event.getSource())
									.setStyleName("gwt-ButtonPushBotton");
						}
					});
				}
				GeneratepanelsLang();

			}

			public void onFailure(Throwable caught) {
				Window.alert(ErrorConstants.ERROR_RETRIVING_CATALOG);
				Logger.GetLogger().severe(Yo.getClass().toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_RETRIVING_CATALOG);
				GeneratepanelsLang();

			}
		});

	}

	private void GeneratepanelsLang() {
		bookReaderServiceHolder
				.getLanguages(new AsyncCallback<List<Language>>() {

					public void onSuccess(List<Language> result) {
						for (int i = 0; i < result.size() - 1; i++) {
							Botonlanguage BC = new Botonlanguage(result.get(i));
							BC.setSize("100%", "100%");
							LanguagePanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botonlanguage BCE = (Botonlanguage) event
											.getSource();
									LanguageLabel.setText(EditorActivityPopupPanel.LANGUAGE_LABEL
											+ BCE.getLanguage().getName());
									SelectedLanguage = BCE.getLanguage();

								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPush");
								}
							});
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOP");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOPOver");
								}
							});
							BC.setStyleName("gwt-ButtonTOP");

						}
						if (!result.isEmpty()) {
							Botonlanguage BC = new Botonlanguage(result
									.get(result.size() - 1));
							BC.setSize("100%", "100%");
							LanguagePanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botonlanguage BCE = (Botonlanguage) event
											.getSource();
									LanguageLabel.setText(EditorActivityPopupPanel.LANGUAGE_LABEL
											+ BCE.getLanguage().getName());
									SelectedLanguage = BCE.getLanguage();

								}
							});
							BC.setStyleName("gwt-ButtonBotton");
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBotton");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBottonOver");
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPushBotton");
								}
							});

						}
						Generatepanelsbooks();

					}

					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_RETRIVING_LANGUAGES);
						Logger.GetLogger().severe(Yo.getClass().toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_RETRIVING_LANGUAGES);
						Generatepanelsbooks();

					}
				});

	}

	private void Generatepanelsbooks() {

		List<Long> result = ((ProfessorClient) ActualState.getUser())
				.getBooks();
		bookReaderServiceHolder.getBookClientsByIds(result,
				new AsyncCallback<List<BookClient>>() {

					@Override
					public void onSuccess(List<BookClient> result) {

						for (int i = 0; i < result.size() - 1; i++) {
							Botonbooks BC = new Botonbooks(result.get(i));
							BC.setSize("100%", "100%");
							BooksPanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botonbooks BCE = (Botonbooks) event
											.getSource();
									BooksLabel.setText(EditorActivityPopupPanel.BOOK_LABEL
											+ BCE.getBook().getTitle());
									SelectedBook = BCE.getBook();

								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPush");
								}
							});
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOP");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOPOver");
								}
							});
							BC.setStyleName("gwt-ButtonTOP");
						}
						if (!result.isEmpty()) {
							Botonbooks BC = new Botonbooks(result.get(result
									.size() - 1));
							BC.setSize("100%", "100%");
							BooksPanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botonbooks BCE = (Botonbooks) event
											.getSource();
									BooksLabel.setText(EditorActivityPopupPanel.BOOK_LABEL
											+ BCE.getBook().getTitle());
									SelectedBook = BCE.getBook();

								}
							});
							BC.setStyleName("gwt-ButtonBotton");
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBotton");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBottonOver");
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPushBotton");
								}
							});
						}
						Generatepanelsgroup();
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_RETRIVING_THE_BOOKS);
						Logger.GetLogger().severe(Yo.getClass().toString(), ActualState.getUser().toString(), ErrorConstants.ERROR_RETRIVING_LANGUAGES);
						Generatepanelsgroup();
					}

				});

	}

	private void Generatepanelsgroup() {
		bookReaderServiceHolder.getGroupsByUserId(
				ActualState.getUser().getId(),
				new AsyncCallback<List<GroupClient>>() {

					public void onSuccess(List<GroupClient> result) {
						for (int i = 0; i < result.size() - 1; i++) {
							Botongroups BC = new Botongroups(result.get(i));
							BC.setSize("100%", "100%");
							GroupsPanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botongroups BCE = (Botongroups) event
											.getSource();
									GroupsLabel.setText(EditorActivityPopupPanel.GROUPS_LABEL
											+ BCE.getGrupo().getName());
									SelectedGroup = BCE.getGrupo();
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPush");
								}
							});
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOP");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOPOver");
								}
							});
							BC.setStyleName("gwt-ButtonTOP");
						}
						if (!result.isEmpty()) {
							Botongroups BC = new Botongroups(result.get(result
									.size() - 1));
							BC.setSize("100%", "100%");
							GroupsPanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									Botongroups BCE = (Botongroups) event
											.getSource();
									GroupsLabel.setText(EditorActivityPopupPanel.GROUPS_LABEL
											+ BCE.getGrupo().getName());
									SelectedGroup = BCE.getGrupo();
								}
							});
							BC.setStyleName("gwt-ButtonBotton");
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBotton");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBottonOver");
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPushBotton");
								}
							});
						}
						GenerateTemplates();

					}

					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_RETRIVING_GROUPS);
						GenerateTemplates();

					}
				});

	}

	private void GenerateTemplates() {

		exportServiceHolder
				.getTemplates(new AsyncCallback<List<TemplateClient>>() {

					public void onSuccess(List<TemplateClient> result) {
						for (int i = 0; i < result.size() - 1; i++) {
							BotonTemplates BC = new BotonTemplates(result
									.get(i));
							BC.setSize("100%", "100%");
							TemplatePanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									BotonTemplates BCE = (BotonTemplates) event
											.getSource();
									Template = BCE.getTemplate();
									TemplateLabel.setText(EditorActivityPopupPanel.TEMPLATE_LABEL
											+ Template.getName());
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPush");
								}
							});
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOP");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonTOPOver");
								}
							});
							BC.setStyleName("gwt-ButtonTOP");
						}
						if (!result.isEmpty()) {
							BotonTemplates BC = new BotonTemplates(result
									.get(result.size() - 1));
							BC.setSize("100%", "100%");
							TemplatePanel.add(BC);
							BC.addClickHandler(new ClickHandler() {

								public void onClick(ClickEvent event) {
									BotonTemplates BCE = (BotonTemplates) event
											.getSource();
									Template = BCE.getTemplate();
									TemplateLabel.setText(EditorActivityPopupPanel.TEMPLATE_LABEL
											+ Template.getName());
								}
							});
							BC.setStyleName("gwt-ButtonBotton");
							BC.addMouseOutHandler(new MouseOutHandler() {
								public void onMouseOut(MouseOutEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBotton");
								}
							});
							BC.addMouseOverHandler(new MouseOverHandler() {
								public void onMouseOver(MouseOverEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonBottonOver");
								}
							});
							BC.addMouseDownHandler(new MouseDownHandler() {
								public void onMouseDown(MouseDownEvent event) {
									((Button) event.getSource())
											.setStyleName("gwt-ButtonPushBotton");
								}
							});
						}

					}

					public void onFailure(Throwable caught) {
						Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATES);

					}
				});
		

	}

	public CatalogoClient getSelectedCatalog() {
		return SelectedCatalog;
	}

	public void setSelectedCatalog(CatalogoClient selectedCatalog) {
		if ((SelectedCatalog != null)
				&& (!SelectedCatalog.getId().equals(selectedCatalog.getId()))) {
			DefaultType = null;
			DefaultTypeOld = null;
			DefaultTypeLabel.setText(DEFAUL_TYPE_LABEL);
		}
		SelectedCatalog = selectedCatalog;
	}

	public CatalogoClient getSelectedCatalogPublic() {
		return SelectedCatalogPublic;
	}

	public void setSelectedCatalogPublic(CatalogoClient selectedCatalogPublic) {
		SelectedCatalogPublic = selectedCatalogPublic;
	}

	public void setPanel_Selecion_Default_Visibility(boolean State) {
		PanelSelecionDefault.setVisible(State);
	}

	public void setTypeDefault(TypeClient eC) {
		if (AllowDefaulTypeCheckBox.getValue()) {
			DefaultType = eC;
			DefaultTypeLabel.setText(DEFAUL_TYPE_LABEL + eC.getName());
		}

	}
	
	@Override
	public void center() {
		super.center();
		EditorZone.setVisible(false);
		if (ActualState.isLanguageActive())
			{
			EditorZone.setVisible(true);
			closeEditPanel();
			}
	}

}
