package lector.client.admin.tagstypes;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Grid;
import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.reader.LoadingPanel;
import lector.share.model.FileException;
import lector.share.model.FolderException;
import lector.share.model.client.EntryClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;


public class NewTypeRename extends PopupPanel {

    private GWTServiceAsync bookReaderServiceHolder = GWT.create(GWTService.class);
    private PopupPanel Yo;
    private  final TextBox textBox; 
    private final EntryClient Nombre;
    private ArrayList<EntryClient> Father;

    public NewTypeRename(final EntryClient renombrar) {
        super(false);
        setGlassEnabled(true);
        String Tipo;
        if (renombrar instanceof TypeClient) Tipo="Type"; else Tipo="Type Category";
        String Titulo = "Write the new "+ Tipo +" for rename the " + Tipo + " " + '"' + renombrar.getName() + '"';
        Yo = this;
        Nombre =renombrar;
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        setWidget(verticalPanel);
        verticalPanel.setSize("100%", "100%");

        Label lblNewLabel = new Label(Titulo);
        lblNewLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel.add(lblNewLabel);

        Grid grid = new Grid(1, 2);
        grid.setCellSpacing(2);
        grid.setCellPadding(2);
        verticalPanel.add(grid);
        grid.setSize("159px", "56px");

        Label lblType = new Label(Tipo);
        grid.setWidget(0, 0, lblType);
        
        VerticalPanel verticalPanel_1 = new VerticalPanel();
        verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        grid.setWidget(0, 1, verticalPanel_1);
        
        
        
        textBox = new TextBox();
        textBox.setVisibleLength(30);
        textBox.setMaxLength(100);
        verticalPanel_1.add(textBox);
        
        Grid grid_1 = new Grid(1, 2);
        verticalPanel.add(grid_1);
        
                Button btnNewButton = new Button("Save");
                btnNewButton.setSize("100%", "100%");
        		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
        			public void onMouseDown(MouseDownEvent event) {
        				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
        			}
        		});
        		btnNewButton.addMouseOutHandler(new MouseOutHandler() {
        			public void onMouseOut(MouseOutEvent event) {
        				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
        			}
        		});
        		btnNewButton.addMouseOverHandler(new MouseOverHandler() {
        			public void onMouseOver(MouseOverEvent event) {
        				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
        			}
        		});
        		btnNewButton.setStyleName("gwt-ButtonCenter");
                grid_1.setWidget(0, 0, btnNewButton);
                btnNewButton.addClickHandler(new ClickHandler() {

                    public void onClick(ClickEvent event) {
                    	
                        	if (!textBox.getText().isEmpty()) {
                        		                           
                        		
                        		AsyncCallback<Void> callback=new AsyncCallback<Void>(){

									public void onFailure(Throwable caught) {
										if (caught instanceof FileException) {
											Window.alert(((FileException) caught)   // Se ataja cuando se guarda un file con un nombre que ya existe.
													.getMessage());
										} else if (caught instanceof FolderException) {
											Window.alert(((FolderException) caught)   // Se ataja cuando se guarda un folder con un nombre que ya existe en su mismo nivel.
													.getMessage());
										} else {
											Window.alert("Error in Merge");
											}
										LoadingPanel.getInstance().hide();
                                        Yo.hide();
										
									}

									public void onSuccess(Void result) {
										LoadingPanel.getInstance().hide();
										EditorTagsAndTypes.LoadBasicTypes();
                                	 	Yo.hide();
										
									}};
									
                                LoadingPanel.getInstance().setLabelTexto("Saving...");
		    					LoadingPanel.getInstance().center();
                                if (Nombre instanceof TypeClient){
                                	bookReaderServiceHolder.renameType(((TypeClient)Nombre).getId(),textBox.getText(), callback);
                                }
                                else {
                                	bookReaderServiceHolder.renameTypeCategory(((TypeCategoryClient)Nombre).getId(),textBox.getText(),callback);
                                }
                        } else {
                            Window.alert("The new type is empty");
                            NewTypeRename NT = new NewTypeRename(renombrar);
                            NT.isModal();
                            NT.center();
                            Yo.hide();
                        }
                        
                    }
                  
                });
                verticalPanel.setCellHorizontalAlignment(btnNewButton, HasHorizontalAlignment.ALIGN_CENTER);
                verticalPanel.setCellVerticalAlignment(btnNewButton, HasVerticalAlignment.ALIGN_MIDDLE);
                
                Button btnNewButton_1 = new Button("Cancel");
                btnNewButton_1.setSize("100%", "100%");
                btnNewButton_1.addMouseDownHandler(new MouseDownHandler() {
        			public void onMouseDown(MouseDownEvent event) {
        				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
        			}
        		});
                btnNewButton_1.addMouseOutHandler(new MouseOutHandler() {
        			public void onMouseOut(MouseOutEvent event) {
        				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
        			}
        		});
                btnNewButton_1.addMouseOverHandler(new MouseOverHandler() {
        			public void onMouseOver(MouseOverEvent event) {
        				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
        			}
        		});
                btnNewButton_1.setStyleName("gwt-ButtonCenter");
                btnNewButton_1.addClickHandler(new ClickHandler() {
                	public void onClick(ClickEvent event) {
                		Yo.hide();
                	}
                });
                grid_1.setWidget(0, 1, btnNewButton_1);
    }

   

}
