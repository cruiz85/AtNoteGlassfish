package lector.client.admin.tagstypes;

import java.util.ArrayList;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.EntitdadObject;
import lector.client.controler.ConstantsError;
import lector.client.controler.ConstantsInformation;
import lector.client.controler.catalogo.Finder;
import lector.client.controler.catalogo.FinderKeys;
import lector.client.controler.catalogo.StackPanelMio;
import lector.client.controler.catalogo.client.EntityCatalogElements;
import lector.client.controler.catalogo.client.File;
import lector.client.controler.catalogo.client.Folder;
import lector.client.reader.LoadingPanel;
import lector.share.model.DecendanceException;
import lector.share.model.FileException;
import lector.share.model.GeneralException;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class EditorTagsAndTypes implements EntryPoint {

	private static String MERGE_BUTTON="Merge";
	private static String AVAILABLE_SECCION="Available";
	private static String SELECTED_SECCION="Selected";
	
	private static VerticalPanel Selected = new VerticalPanel();
	private static AbsolutePanel Actual = new AbsolutePanel();
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private static MenuBar menuBar_3;
	private static MenuBar menuBar_2;
	private static FinderKeys finder;
	private static CatalogoClient catalogo;
	private VerticalPanel label = new VerticalPanel();

	public EditorTagsAndTypes() {
		menuBar_2 = new MenuBar(false);
		Actual.add(menuBar_2);
		menuBar_2.setWidth("100%");

		MenuItem mntmNewItem_2 = new MenuItem(EditorTagsAndTypes.AVAILABLE_SECCION, false,
				(Command) null);
		mntmNewItem_2.setEnabled(false);
		mntmNewItem_2.setHTML(EditorTagsAndTypes.AVAILABLE_SECCION);
		menuBar_2.addItem(mntmNewItem_2);

		menuBar_3 = new MenuBar(false);
		label.add(menuBar_3);
		menuBar_3.setWidth("100%");

		MenuItem mntmSelected = new MenuItem(EditorTagsAndTypes.SELECTED_SECCION, false, (Command) null);
		mntmSelected.setHTML(EditorTagsAndTypes.AVAILABLE_SECCION);
		mntmSelected.setEnabled(false);
		menuBar_3.addItem(mntmSelected);
		
		label.setWidth("100%");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		label.add(horizontalPanel_1);
		horizontalPanel_1.setSpacing(10);
		horizontalPanel_1.setStyleName("AzulTransparente");
		horizontalPanel_1.setWidth("100%");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setStyleName("BlancoTransparente");
		horizontalPanel_2.setSpacing(6);
		horizontalPanel_1.add(horizontalPanel_2);
		horizontalPanel_2.setWidth("100%");
		horizontalPanel_2.add(Selected);
		Selected.setWidth("100%");
		
				Selected.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		finder = new FinderKeys();
		SimplePanel S = new SimplePanel();
		S.setSize("100%", "97%");
		S.add(finder);
		FinderKeys.setButtonTipo(new BotonesStackPanelAdministracionMio(
				"prototipo", new VerticalPanel(), Selected, finder));
		
		FinderKeys.setBotonClick(new ClickHandlerMio());

		finder.setSize("100%", "100%");

		Actual.add(S);
	}

	public void onModuleLoad() {

		RootPanel RootTXOriginal = RootPanel.get();
		RootTXOriginal.setSize("100%", "100%");
		RootTXOriginal.setStyleName("Root");

		finder.setCatalogo(catalogo);
		Selected.clear();
		
		MenuBar menuBar = new MenuBar(false);
	//	RootMenu.add(menuBar);

		MenuItem mntmTypesTags = new MenuItem(
				"Types & Tags Administration for Catalog: "
						+ catalogo.getCatalogName(), false, (Command) null);
		mntmTypesTags.setEnabled(false);
		mntmTypesTags.setHTML("Types & Tags Administration\r\n");
		menuBar.addItem(mntmTypesTags);

		MenuItemSeparator separator_1 = new MenuItemSeparator();
		menuBar.addSeparator(separator_1);
		
		MenuItem mntmNewItem_1 = new MenuItem("New item", false, new Command() {
			public void execute() {
				VisualizeGraph VG=new VisualizeGraph(finder.getCatalogo());
				VG.center();
				VG.Lanza();
			}
		});
		mntmNewItem_1.setHTML("Show Graph");
		menuBar.addItem(mntmNewItem_1);

		MenuItem mntmNewItem_3 = new MenuItem("New", false, new Command() {

			public void execute() {
				if (finder.getTopPath() instanceof Folder) {

					PopUpNewOSelect PPP = new PopUpNewOSelect(catalogo,
							finder.getTopPath().getEntry());
					PPP.center();
//					 SelectBetweenFileOrFolderInNew.setCatalog(catalogo);
//					 SelectBetweenFileOrFolderInNew SBFF=new SelectBetweenFileOrFolderInNew(finder.getTopPath().getEntry());
//					 SBFF.center();
//					 SBFF.setModal(true);
				} else
					Window.alert(ConstantsError.TYPES_CANNOT_HAVE_SUBTYPES);
			}

		});
		mntmNewItem_3.setHTML("Create");
		menuBar.addItem(mntmNewItem_3);

		MenuItem mntmMerge = new MenuItem(EditorTagsAndTypes.MERGE_BUTTON, false, new Command() {

			private final int minimuunElements=2;

			public void execute() {
				int Unir = Selected.getWidgetCount();
				if (Unir >= minimuunElements) {

					ArrayList<BotonesStackPanelAdministracionMio> ListaUnir = new ArrayList<BotonesStackPanelAdministracionMio>();
					BotonesStackPanelAdministracionMio BSPAM=(BotonesStackPanelAdministracionMio) Selected.getWidget(0);
					ListaUnir.add((BSPAM));
					boolean Incompatible=false;
					for (int i = 1; i < Unir; i++) {
						BotonesStackPanelAdministracionMio BSPAM2=(BotonesStackPanelAdministracionMio) Selected.getWidget(i);
						ListaUnir
								.add((BSPAM2));
						if (((EntityCatalogElements)BSPAM.getEntidad()).getEntry().getId().equals(((EntityCatalogElements)BSPAM2.getEntidad()).getEntry().getId()))
							{
							Incompatible=true;
							break;
							}
							
					}

					if (!Incompatible)
					{
					MergeSelector.setCatalog(catalogo);
					MergeSelector MS = new MergeSelector(ListaUnir);
					MS.center();
					MS.setModal(true);
					}else {
						Window.alert(ConstantsInformation.ELEMENTS_SHOULD_BE_THE_SAME_TYPE);
					}
				} else {
					Window.alert(ConstantsInformation.SHOULD_ME_MORE_THAN1 + minimuunElements + ConstantsInformation.SHOULD_ME_MORE_THAN2);
				}

			}

		});
		mntmMerge.setHTML(EditorTagsAndTypes.MERGE_BUTTON);
		mntmMerge.setEnabled(true);
		menuBar.addItem(mntmMerge);

		MenuItem mntmMove = new MenuItem("Move", false, new Command() {
			public void execute() {
				if (!(finder.getTopPath() instanceof File))
				{
				int mover = Selected.getWidgetCount();
				for (int i = 0; i < mover; i++) {

					BotonesStackPanelAdministracionMio Delete = ((BotonesStackPanelAdministracionMio) Selected
							.getWidget(i));

					moverTypos(Delete.getEntidad());
				}
				}
				else Window.alert("Destiny cannot be a Type. It always has to be a Category");
			}

			private void moverTypos(EntitdadObject entidad) {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						if (caught instanceof DecendanceException) {
							Window.alert(((DecendanceException) caught)
									.getMessage());
						} else if (caught instanceof GeneralException) {
							Window.alert(((GeneralException) caught)
									.getMessage());
						} else if (caught instanceof FileException) {
							Window.alert(((FileException) caught).getMessage());
						} else {
							Window.alert("Error on move");
						}
					}

					public void onSuccess(Void result) {
						LoadBasicTypes();

					}
				};
				if (entidad instanceof File)
					bookReaderServiceHolder.moveType(((File) entidad).getFatherIdCreador().getId(), ((File) entidad).getEntry().getId(), finder.getTopPath().getEntry().getId(), callback);
				else
					bookReaderServiceHolder.moveFolderDB(((Folder) entidad).getFatherIdCreador().getId(), ((Folder) entidad).getEntry().getId(), finder.getTopPath().getEntry().getId(), callback);
//					(entidad
//							.getActualFather().getID(), entidad.getID(), finder
//							.getTopPath().getID(), callback);

			}
		});
		mntmMove.setHTML("Move");
		mntmMove.setEnabled(true);
		menuBar.addItem(mntmMove);

		MenuItem mntmRename = new MenuItem("Rename", false, new Command() {

			public void execute() {
				int renombrar = Selected.getWidgetCount();
				for (int i = 0; i < renombrar; i++) {

					BotonesStackPanelAdministracionMio Renombrar = ((BotonesStackPanelAdministracionMio) Selected
							.getWidget(i));
					RenameTypos(((EntityCatalogElements)Renombrar.getEntidad()).getEntry());

				}

			}

			private void RenameTypos(EntryClient entity) {

				NewTypeRename TR = new NewTypeRename(entity);
				TR.setModal(true);
				TR.center();
			}
		});
		mntmRename.setHTML("Rename");
		menuBar.addItem(mntmRename);

		MenuItem mntmNewItem = new MenuItem("Delete", false, new Command() {

			public void execute() {
				int Borrar = Selected.getWidgetCount();
				for (int i = 0; i < Borrar; i++) {

					BotonesStackPanelAdministracionMio Delete = ((BotonesStackPanelAdministracionMio) Selected
							.getWidget(i));

					BorrarTypos((EntityCatalogElements)Delete.getEntidad());

				}

			}

			private void BorrarTypos(EntityCatalogElements delete) {
				AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {
						LoadingPanel.getInstance().hide();
						Window.alert(ConstantsError.ERROR_DELETING_TYPE);

					}

					public void onSuccess(Void result) {
						LoadingPanel.getInstance().hide();
						LoadBasicTypes();

					}
				};
				if (delete instanceof Folder)
					/*
					 * if(finder.getTopPath()== null)
					 * bookReaderServiceHolder.deleteFolder(delete.getID(),null,
					 * callback); else
					 */
				{
					LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.DELETING);
				
					bookReaderServiceHolder.deleteTypeCategory(delete.getEntry().getId(), delete.getFatherIdCreador().getId(), callback);
				}else{
					/*
					 * if(finder.getTopPath()== null)
					 * bookReaderServiceHolder.deleteFile(delete.getID(),null,
					 * callback); else
					 */
					LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(ConstantsInformation.DELETING);
					bookReaderServiceHolder.deleteTag(delete.getEntry().getId(), delete.getFatherIdCreador().getId(), callback);
				}
			}

		});
		menuBar.addItem(mntmNewItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);

		MenuItem mntmSearcher = new MenuItem("Back", false, new Command() {

			public void execute() {
				Controlador.change2CatalogAdmin();
			}
		});
		mntmSearcher.setHTML("Back");
		menuBar.addItem(mntmSearcher);

		DockLayoutPanel simplePanel_2 = new DockLayoutPanel(Unit.PX);
		simplePanel_2.setStyleName("fondoLogo");
		
		RootTXOriginal.add(simplePanel_2, 0, 0);
		// RootTXOriginal.add(simplePanel_2);
		simplePanel_2.addNorth(menuBar, 25);
		simplePanel_2.setSize("100%", "100%");

		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		simplePanel_2.add(splitLayoutPanel);
		splitLayoutPanel.setSize("100%", "100%");
		
				SimplePanel simplePanel_1 = new SimplePanel();
				splitLayoutPanel.addEast(simplePanel_1, 300.0);
				simplePanel_1.setSize("100%", "100%");
				
				HorizontalPanel inicial = new HorizontalPanel();
				simplePanel_1.setWidget(inicial);
				inicial.setSize("100%", "100%");
						
						
						inicial.add(label);
						
//						ScrollPanel SP=new ScrollPanel();
//						SP.setSize(Constants.P100, Constants.P100);
		splitLayoutPanel.add(Actual);
//						SP.add(Actual);
//						SP.setAlwaysShowScrollBars(true);
//						splitLayoutPanel.add(SP);
		Actual.setStyleName("BlancoTransparente");
		Actual.setSize("100%", "100%");

		LoadBasicTypes();

	}

	public static void LoadBasicTypes() {
		//
		// Asincronino
		Selected.clear();
	//	Selected.add(menuBar_3);
		FinderKeys.setButtonTipo(new BotonesStackPanelAdministracionMio(
				"prototipo", new VerticalPanel(), Selected, finder));
		
		FinderKeys.setBotonClick(new ClickHandlerMio());
		finder.RefrescaLosDatos();
		// scrollPanel.setWidget(finder);
		finder.setSize("100%", "100%");
	}

	public static VerticalPanel getSelected() {
		return Selected;
	}

	public static CatalogoClient getCatalogo() {
		return catalogo;
	}

	public static void setCatalogo(CatalogoClient catalogo) {
		EditorTagsAndTypes.catalogo = catalogo;
	}
	
	public static void restoreHandle()
	{
		FinderKeys.setBotonClick(new ClickHandlerMio());
	}
}
