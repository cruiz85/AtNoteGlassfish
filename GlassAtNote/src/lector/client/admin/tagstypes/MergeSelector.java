package lector.client.admin.tagstypes;

import java.util.ArrayList;

import lector.client.admin.generalPanels.BotonesStackPanelAdministracionMio;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.EntitdadObject;
import lector.client.controler.catalogo.client.File;
import lector.client.controler.catalogo.client.Folder;
import lector.client.reader.LoadingPanel;
import lector.share.model.IlegalFolderFusionException;
import lector.share.model.client.CatalogoClient;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class MergeSelector extends PopupPanel {

	private ListBox comboBox;
	private ArrayList<BotonesStackPanelAdministracionMio> ListaCombo;
	static GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	private PopupPanel Yo;
	private static CatalogoClient catalog;

	public MergeSelector(
			ArrayList<BotonesStackPanelAdministracionMio> ListaComboIn) {
		super(true);
		setGlassEnabled(true);
		this.ListaCombo = ListaComboIn;
		Yo = this;
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		setWidget(verticalPanel);
		verticalPanel.setSize("235px", "136px");

		Label lblNewLabel = new Label("Select the destination of the Merge");
		verticalPanel.add(lblNewLabel);

		comboBox = new ListBox();
		comboBox.setSelectedIndex(1);
		verticalPanel.add(comboBox);
		comboBox.setWidth("149px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		
				Button btnNewButton = new Button("Select");
				horizontalPanel.add(btnNewButton);
				btnNewButton.addMouseDownHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						((Button) event.getSource())
								.setStyleName("gwt-ButtonCenterPush");
					}
				});
				btnNewButton.addMouseOutHandler(new MouseOutHandler() {
					public void onMouseOut(MouseOutEvent event) {
						((Button) event.getSource()).setStyleName("gwt-ButtonCenter");
					}
				});
				btnNewButton.addMouseOverHandler(new MouseOverHandler() {
					public void onMouseOver(MouseOverEvent event) {
						((Button) event.getSource())
								.setStyleName("gwt-ButtonCenterOver");
					}
				});
				btnNewButton.setStyleName("gwt-ButtonCenter");
				btnNewButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						Yo.hide();
						String Destino = comboBox.getItemText(comboBox
								.getSelectedIndex());
						int counter = 0;
						boolean found = false;
						while (counter < ListaCombo.size() && !found) {
							found = ListaCombo.get(counter).getText().equals(Destino);
							counter++;
						}
						counter--;
						EntitdadObject DestinoEn = ListaCombo.get(counter).getEntidad();
						for (BotonesStackPanelAdministracionMio texto : ListaCombo) {
							if (!(texto.getText().equals(Destino))) {
								UnirTypos(texto.getEntidad(), DestinoEn);

							}
						}

					}

					private void UnirTypos(EntitdadObject entity, EntitdadObject destinoEn) {
						AsyncCallback<Void> callback = new AsyncCallback<Void>() {

							public void onFailure(Throwable caught) {
							
								if (caught instanceof IlegalFolderFusionException) {
									Window.alert(((IlegalFolderFusionException) caught)
											.getErrorMessage());
								} else {
									Window.alert("Error in Merge");
								}
								// lo a�adi
								LoadingPanel.getInstance().hide();
							//	Yo.hide();
							}

							public void onSuccess(Void result) {
								LoadingPanel.getInstance().hide();
								EditorTagsAndTypes.LoadBasicTypes();
							}
						};
						AsyncCallback<Void> callback2 = new AsyncCallback<Void>() {

							public void onSuccess(Void result) {
								LoadingPanel.getInstance().hide();
								EditorTagsAndTypes.LoadBasicTypes();

							}

							public void onFailure(Throwable caught) {
								Window.alert("Error in Merge");

							}
						};

						LoadingPanel.getInstance().setLabelTexto("Saving...");
						LoadingPanel.getInstance().center();
						if ((entity instanceof Folder) && (destinoEn instanceof Folder))
							bookReaderServiceHolder.fusionFolder(((Folder)entity).getEntry().getId(),
									((Folder)destinoEn).getEntry().getId(), callback);
						else if ((entity instanceof File)
								&& (destinoEn instanceof File))
							bookReaderServiceHolder.fusionTypes(((File)entity).getEntry().getId(),
									((File)destinoEn).getEntry().getId(), callback2);
					}

				});

		for (BotonesStackPanelAdministracionMio texto : ListaCombo) {
			comboBox.addItem(texto.getText());
		}
	}

	public static void setCatalog(CatalogoClient catalog) {
		MergeSelector.catalog = catalog;
	}

	public static CatalogoClient getCatalog() {
		return catalog;
	}

}
