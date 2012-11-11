package lector.client.admin.tagstypes;

import java.util.ArrayList;

import lector.client.admin.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.Finder;
import lector.client.catalogo.FinderKeys;
import lector.client.catalogo.StackPanelMio;
import lector.client.catalogo.client.Entity;
import lector.client.catalogo.client.EntityCatalogElements;
import lector.client.catalogo.client.File;
import lector.client.catalogo.client.Folder;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.controler.InformationConstants;
import lector.client.reader.LoadingPanel;
import lector.share.model.DecendanceException;
import lector.share.model.FileException;
import lector.share.model.GeneralException;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

		MenuItem mntmNewItem_2 = new MenuItem("Avariable", false,
				(Command) null);
		mntmNewItem_2.setEnabled(false);
		mntmNewItem_2.setHTML("Avariable");
		menuBar_2.addItem(mntmNewItem_2);

		menuBar_3 = new MenuBar(false);
		label.add(menuBar_3);
		menuBar_3.setWidth("100%");

		MenuItem mntmSelected = new MenuItem("Selected", false, (Command) null);
		mntmSelected.setHTML("Selected");
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
		RootPanel RootMenu = RootPanel.get("Menu");
		RootTXOriginal.setSize("100%", "100%");
		RootMenu.setStyleName("Root");
		RootTXOriginal.setStyleName("Root");

		finder.setCatalogo(catalogo);
		Selected.clear();
		
		MenuBar menuBar = new MenuBar(false);
		RootMenu.add(menuBar);

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
					Window.alert(ErrorConstants.TYPES_CANNOT_HAVE_SUBTYPES);
			}

		});
		mntmNewItem_3.setHTML("Create");
		menuBar.addItem(mntmNewItem_3);

		MenuItem mntmMerge = new MenuItem("Merge", false, new Command() {


			public void execute() {
				int Unir = Selected.getWidgetCount();
				if (Unir >= 3) {

					ArrayList<BotonesStackPanelAdministracionMio> ListaUnir = new ArrayList<BotonesStackPanelAdministracionMio>();
					for (int i = 0; i < Unir; i++) {
						ListaUnir
								.add(((BotonesStackPanelAdministracionMio) Selected
										.getWidget(i)));
					}

					MergeSelector.setCatalog(catalogo);
					MergeSelector MS = new MergeSelector(ListaUnir);
					MS.center();
					MS.setModal(true);

				} else {
					Window.alert("There are less than two elements to merge");
				}

			}

		});
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

			private void moverTypos(Entity entidad) {
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
				//TODO Hacer
//				if (entidad instanceof File)
//					bookReaderServiceHolder.moveFile(entidad.getActualFather()
//							.getID(), entidad.getID(), finder.getTopPath()
//							.getID(), callback);
//				else
//					bookReaderServiceHolder.moveFolder(entidad
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
						Window.alert(ErrorConstants.ERROR_DELETING_TYPE);

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
				LoadingPanel.getInstance().setLabelTexto(InformationConstants.DELETING);
				
					bookReaderServiceHolder.deleteTypeCategory(delete.getEntry().getId(), delete.getFatherIdCreador().getId(), callback);
				}else{
					/*
					 * if(finder.getTopPath()== null)
					 * bookReaderServiceHolder.deleteFile(delete.getID(),null,
					 * callback); else
					 */
					LoadingPanel.getInstance().center();
				LoadingPanel.getInstance().setLabelTexto(InformationConstants.DELETING);
					bookReaderServiceHolder.deleteTag(delete.getEntry().getId(), delete.getFatherIdCreador().getId(), callback);
				}
			}

		});
		menuBar.addItem(mntmNewItem);

		MenuItemSeparator separator = new MenuItemSeparator();
		menuBar.addSeparator(separator);
		// MenuBar menuBar_1 = new MenuBar(true);

		// mntmMenuTipos = new MenuItem("Types", false, menuBar_1);
		// mntmMenuTipos.setEnabled(false);
		// mntmMenuTipos.setHTML("Types");
		//
		// mntmTypes = new MenuItem("Types", false, new Command() {
		//
		// public void execute() {
		// myState = state.Typos;
		// mntmMenuTipos.setHTML("Types");
		// LoadBasicTypes();
		// mntmNewItem_1.setEnabled(true);
		// mntmTypes.setEnabled(false);
		// }
		// });
		//
		// menuBar_1.addItem(mntmTypes);
		// mntmTypes.setEnabled(false);
		//
		// mntmNewItem_1 = new MenuItem("Tags", false, new Command() {
		//
		// public void execute() {
		// myState = state.Tags;
		// mntmMenuTipos.setHTML("Tags");
		// LoadBasicTags();
		// mntmNewItem_1.setEnabled(false);
		// mntmTypes.setEnabled(true);
		// }
		// });
		//
		// menuBar_1.addItem(mntmNewItem_1);
		// menuBar.addItem(mntmMenuTipos);

		MenuItem mntmSearcher = new MenuItem("Back", false, new Command() {

			public void execute() {
				Controlador.change2CatalogAdmin();
			}
		});
		mntmSearcher.setHTML("Back");
		menuBar.addItem(mntmSearcher);

		SimplePanel simplePanel_2 = new SimplePanel();
		simplePanel_2.setStyleName("fondoLogo");
		RootTXOriginal.add(simplePanel_2, 0, 20);
		// RootTXOriginal.add(simplePanel_2);
		simplePanel_2.setSize("100%", "97%");

		SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
		simplePanel_2.setWidget(splitLayoutPanel);
		splitLayoutPanel.setSize("100%", "100%");
		
				SimplePanel simplePanel_1 = new SimplePanel();
				splitLayoutPanel.addEast(simplePanel_1, 300.0);
				simplePanel_1.setSize("100%", "100%");
				
				HorizontalPanel inicial = new HorizontalPanel();
				simplePanel_1.setWidget(inicial);
				inicial.setSize("100%", "100%");
						
						
						inicial.add(label);
						

		splitLayoutPanel.add(Actual);

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
