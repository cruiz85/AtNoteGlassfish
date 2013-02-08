package lector.client.login.bookselec;


import lector.client.controler.ErrorConstants;
import lector.share.model.client.BookClient;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.IntegerBox;

public class Visor extends PopupPanel {

	public BookClient Book;
	public Visor Yo;
	private int actualpagina;
	private Image Pagina1;
	private Image Pagina2;
	private IntegerBox PaginaInt;
	private Button Adelante;
	private Button Atras;
	
	public Visor(BookClient bookClient) {
		super(false);
		actualpagina=0;
		Book=bookClient;
		Yo=this;
//		String[] Booksplit=Book.("&");
//		Book=Booksplit[0];
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
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("100%", "583px");
		
		HorizontalPanel horizontalPanel_2 = new HorizontalPanel();
		horizontalPanel_2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel_1.add(horizontalPanel_2);
		horizontalPanel_2.setHeight("100%");
		
		HorizontalPanel horizontalPanel_3 = new HorizontalPanel();
		horizontalPanel_2.add(horizontalPanel_3);
		
		Atras = new Button("<img src=\"BotonesTemplate/Izquierda.gif\" alt=\"<-\">");
		Atras.setStyleName("gwt-ButtonCenter");
		Atras.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		Atras.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		Atras.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		Atras.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		Atras.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actualpagina--;
				PaginaInt.setValue(actualpagina);
				if (actualpagina<=2)
				{
					Atras.setEnabled(false);
				}
				Adelante.setEnabled(true);	
				Pagina1.setUrl(Book.getWebLinks().get(actualpagina-2).replace("\\", "/"));
				Pagina2.setUrl(Book.getWebLinks().get(actualpagina-1).replace("\\", "/"));
			}
		});
		horizontalPanel_3.add(Atras);
		
		Adelante = new Button("<img src=\"BotonesTemplate/Derecha.gif\" alt=\"<-\">");
		Adelante.setStyleName("gwt-ButtonCenter");
		Adelante.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		Adelante.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
			}
		});
		Adelante.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		Adelante.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
			}
		});
		Adelante.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				actualpagina++;
				PaginaInt.setValue(actualpagina);
				if (actualpagina>=Book.getWebLinks().size())
					Adelante.setEnabled(false);	
				
				Atras.setEnabled(true);
				Pagina1.setUrl(Book.getWebLinks().get(actualpagina-2).replace("\\", "/"));
				Pagina2.setUrl(Book.getWebLinks().get(actualpagina-1).replace("\\", "/"));
			}
		});
		
		PaginaInt = new IntegerBox();
		PaginaInt.setVisibleLength(5);
		PaginaInt.setReadOnly(true);
		horizontalPanel_3.add(PaginaInt);
		PaginaInt.setHeight("100%");
		horizontalPanel_3.add(Adelante);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel_1.add(horizontalPanel);
		horizontalPanel.setHeight("100%");
		
		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		horizontalPanel.add(horizontalPanel_1);
		horizontalPanel_1.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		horizontalPanel_1.setHeight("100%");
		
		
		if (Book.getWebLinks().size()>0)
		{
			DecoratorPanel decoratorPanel = new DecoratorPanel();
			horizontalPanel_1.add(decoratorPanel);
			Pagina1 = new Image(Book.getWebLinks().get(0).replace("\\", "/"));
			decoratorPanel.setWidget(Pagina1);
			Pagina1.setHeight("528px");
			actualpagina=1;
			Pagina1.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) {
					Image I = (Image) event.getSource();
					float He = I.getHeight();
					float Wi = I.getWidth();
					float prop = He / 528;
					float Winew = (Wi / prop);
					
					if (Winew<410)
						Pagina1.setSize(Winew + "px", "528px");
					else{
						He = I.getHeight();
						Wi = I.getWidth();
						prop = Wi / 410;
						float Hinew = (He / prop);
						Pagina1.setSize("410px", Hinew + "px");
					}	
						
									

					
					// Window.alert("Altura: " + He + "Ancho: " + Wi );
				}
			});
		}else
		{
		Window.alert(ErrorConstants.ERROR_BOOK_EMPTY);	
		hide();
		}
		if (Book.getWebLinks().size()>1)
		{
			DecoratorPanel decoratorPanel_1 = new DecoratorPanel();
			horizontalPanel_1.add(decoratorPanel_1);
		
			Pagina2 = new Image(Book.getWebLinks().get(1).replace("\\", "/"));
			decoratorPanel_1.setWidget(Pagina2);
			Pagina2.setHeight("528px");
			actualpagina=2;
			Pagina2.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) {
					Image I = (Image) event.getSource();
					float He = I.getHeight();
					float Wi = I.getWidth();
					float prop = He / 528;
					float Winew = (Wi / prop);
					
					if (Winew<410)
						Pagina2.setSize(Winew + "px", "528px");
					else{
						He = I.getHeight();
						Wi = I.getWidth();
						prop = Wi / 410;
						float Hinew = (He / prop);
						Pagina2.setSize("410px", Hinew + "px");
					}				
				}
				
		});
		}

		if (Book.getWebLinks().size()<3)
			Adelante.setEnabled(false);	
		Atras.setEnabled(false);
		PaginaInt.setValue(actualpagina);		
	}



}
