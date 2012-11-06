package lector.client.reader;

import java.util.ArrayList;
import java.util.List;

import lector.client.book.reader.ExportService;
import lector.client.book.reader.ExportServiceAsync;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.book.reader.ImageService;
import lector.client.book.reader.ImageServiceAsync;
import lector.client.controler.Controlador;
import lector.client.controler.ErrorConstants;
import lector.client.login.ActualUser;
import lector.client.reader.export.EnvioExportacion;
import lector.client.reader.export.ExportResult;
import lector.client.reader.export.PanelSeleccionExportacion;
import lector.client.reader.export.arbitroLlamadas;
import lector.share.model.client.TemplateCategoryClient;
import lector.share.model.client.TemplateClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;

public class PopUPEXportacion extends PopupPanel {

	private VerticalPanel verticalPanel;
	private ListBox comboBox;
	private static final int Longitud = 473;
	static ImageServiceAsync imageServiceHolder = GWT
			.create(ImageService.class);
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	static ExportServiceAsync exportServiceHolder = GWT
			.create(ExportService.class);
	private TemplateClient Template;
	private static VerticalPanel Actual;
	private static ElementoExportacionTemplate EET;


	public PopUPEXportacion() {
		super(false);

		DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
		dockLayoutPanel.setStyleName("BlancoTransparente");
		setWidget(dockLayoutPanel);
		dockLayoutPanel.setSize(Longitud + "px", Window.getClientHeight() - 48
				+ "px");
		// dockLayoutPanel.setSize( Longitud +"px",
		// Window.getClientHeight()-48+"px");
		MenuBar menuBar = new MenuBar(false);
		dockLayoutPanel.addNorth(menuBar, 1.9);

		MenuItem Close = new MenuItem(ActualUser.getLanguage().getClose(),
				false, new Command() {
					public void execute() {
						hide();
					}
				});
		Close.setHTML("Close");
		menuBar.addItem(Close);

		MenuItem mntmNewItem_1 = new MenuItem("Export", false, new Command() {
			public void execute() {
				if (verticalPanel.getWidgetCount() > 0) {
					ArrayList<ExportObject> list = new ArrayList<ExportObject>();
					// Controlador.change2ExportResult();
					for (Widget W : verticalPanel) {
						
						if (W instanceof ElementoExportacionTemplate)
							{
							ElementoExportacionTemplate EET=(ElementoExportacionTemplate)W;
							list.add(new ExportObjectTemplate(EET.getProfundidad(), EET.getTExt()));
							EET.Export(list);
							}
						else if (W instanceof ElementoExportacion)
							{
							ElementoExportacion EE=(ElementoExportacion)W;
							list.add(new ExportObject(EE.getAnnotation(), 
														 EE.getImagen().getUrl(),
														 EE.getImagen().getWidth(),
														 EE.getImagen().getHeight(),
														 EE.getAnnotation().getCreator().getFirstName()+ " " + 
																 EE.getAnnotation().getCreator().getLastName().charAt(0)+".",
														 EE.getAnnotation().getCreatedDate().toGMTString()));
							}
						

					}
					PanelSeleccionExportacion PSE=new PanelSeleccionExportacion(list);
					PSE.center();
					

				}
			}
		});
		// TODO Anadir a Lenguaje
		menuBar.addItem(mntmNewItem_1);

		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize(Longitud + "px", Window.getClientHeight() - 48
				+ "px");
		dockLayoutPanel.add(scrollPanel);

		VerticalPanel verticalPanel_1 = new VerticalPanel();
		scrollPanel.setWidget(verticalPanel_1);
		verticalPanel_1.setSize("100%", "100%");

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel);

		if (ActualUser.getReadingactivity().getIsFreeTemplateAllowed()
				&& ActualUser.getReadingactivity().getTemplate() != null) {
			comboBox = new ListBox();
			TemplateClient IDTemplate = ActualUser.getReadingactivity()
					.getTemplate();
			if (ActualUser.getReadingactivity().getIsFreeTemplateAllowed())
				comboBox.addItem("Blank Template");

			comboBox.addItem(IDTemplate.getName());
			Template = IDTemplate;
			comboBox.setSelectedIndex(0);
			comboBox.addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					verticalPanel.clear();
					ListBox CB = (ListBox) event.getSource();
					if (CB.getSelectedIndex() != 0)
						LoadTemplate();
					else {
						Actual = verticalPanel;
						if (EET != null)
							EET.ResetButton();
					}
				}

			});
			horizontalPanel.add(comboBox);
			comboBox.setWidth("245px");
		} else if (ActualUser.getReadingactivity().getTemplate() != null) {

			Template = ActualUser.getReadingactivity().getTemplate();
			verticalPanel.clear();
			if (EET != null)
				EET.ResetButton();
			Actual = verticalPanel;
			LoadTemplate();
			// }
			//
			// public void onFailure(Throwable caught) {
			//
			// Window.alert(ErrorConstants.ERROR_RETRIVING_TEMPLATE_MASTER_EXPORT_PANEL);
			// }
			// });
		}

		verticalPanel = new VerticalPanel();
		verticalPanel_1.add(verticalPanel);
		Actual = verticalPanel;
		if (EET != null)
			EET.ResetButton();

	}

	protected void LoadTemplate() {
		if (!Template.getCategories().isEmpty())
		{
			List<TemplateCategoryClient> Lista=Template.getCategories();
			for (TemplateCategoryClient templateCategory : Lista) {
				ElementoExportacionTemplate E=new ElementoExportacionTemplate(templateCategory, 1, Template.getModifyable());
				Actual.add(E);
				E.addCliker(Actual);
				procesaHijo(templateCategory,E);
			}
			
			setActual((ElementoExportacionTemplate) Actual.getWidget(0));
			
		}
	
		
	}

	private void procesaHijo(TemplateCategoryClient templateCategory, ElementoExportacionTemplate ElementoExportPadre) {
		if (!templateCategory.getSubCategories().isEmpty())
		{
			List<TemplateCategoryClient> Lista=templateCategory.getSubCategories();
			for (TemplateCategoryClient templateSubCategory : Lista) {
				ElementoExportacionTemplate E=new ElementoExportacionTemplate(templateSubCategory, 1, Template.getModifyable());
				ElementoExportPadre.getFondo().add(E);
				E.addCliker(ElementoExportPadre.getFondo());
				procesaHijo(templateSubCategory,E);
			}
			
		}
		
	}

	public static int getLongitud() {
		return Longitud;
	}

	public void addAlement(ElementoExportacion elementoExportacion) {
		Actual.add(elementoExportacion);
		elementoExportacion.addCliker(Actual);
	}
	
	public static void setActual(ElementoExportacionTemplate actual) {
		if (EET!=null)
			EET.ResetButton();
		EET=actual;
		EET.selectedButton();
		Actual = actual.getFondo();
	}
	

	public void Refresh() {
		verticalPanel.clear();
		if (Template!=null&&(comboBox==null||comboBox.getSelectedIndex()!=0))
			LoadTemplate();
		
	}
	

	
}
