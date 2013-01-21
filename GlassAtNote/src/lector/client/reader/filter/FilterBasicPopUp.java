package lector.client.reader.filter;

import java.util.ArrayList;


import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.catalogo.client.Folder;
import lector.client.controler.ActualState;
import lector.client.controler.Constants;
import lector.client.controler.Controlador;
import lector.client.controler.EntitdadObject;
import lector.client.reader.MainEntryPoint;
import lector.share.model.Language;
import lector.share.model.UserApp;
import lector.share.model.client.CatalogoClient;
import lector.share.model.client.EntryClient;
import lector.share.model.client.GroupClient;
import lector.share.model.client.StudentClient;
import lector.share.model.client.TypeCategoryClient;
import lector.share.model.client.TypeClient;
import lector.share.model.client.UserClient;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;

public class FilterBasicPopUp extends PopupPanel {
	private HorizontalPanel horizontalPanel;
	private Button All; 
	private static Boolean AllBoolean;
	private PopupPanel Me=this;
	private Language Lang;
	private static TextBox textBox;
	private GWTServiceAsync bookReaderServiceHolder = GWT
	.create(GWTService.class);
	private ArrayList<TypeClient> filtro = new ArrayList<TypeClient>();
	private Button Advance;
	private ArrayList<String> Types;
	private ArrayList<UserApp> GroupUser;
	
	public FilterBasicPopUp() {
		super(true);
		setAnimationEnabled(true);
		AllBoolean=false;
		filtro = new ArrayList<TypeClient>();
		VerticalPanel verticalPanel = new VerticalPanel();
		setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		SimplePanel simplePanel = new SimplePanel();
		verticalPanel.add(simplePanel);
		simplePanel.setSize("100%", "100%");
		
		if (textBox==null) textBox = new TextBox();
		textBox.setVisibleLength(100);
		simplePanel.setWidget(textBox);
		textBox.setSize("98%", "100%");
		Lang=ActualState.getLanguage();
	
	horizontalPanel = new HorizontalPanel();
	verticalPanel.add(horizontalPanel);
	
	Button btnNewButton = new Button(Lang.getFilterButton());
	horizontalPanel.add(btnNewButton);
	horizontalPanel.setCellHorizontalAlignment(btnNewButton, HasHorizontalAlignment.ALIGN_CENTER);
	
	All = new Button(Lang.getSelect_All());
	All.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			AllBoolean=!AllBoolean;
			if (AllBoolean){
				textBox.setEnabled(false);
				textBox.setReadOnly(true);
				textBox.setText(Lang.getAllSelected());
				
			}
			else 
			{
				textBox.setEnabled(true);
				textBox.setReadOnly(false);
				textBox.setText("");
			}
		}
	});
	horizontalPanel.add(All);
	All.setSize("100%", "100%");
	All.setStyleName("gwt-ButtonCenter");
	All.addMouseOutHandler(new MouseOutHandler() {
		public void onMouseOut(MouseOutEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
	All.addMouseOverHandler(new MouseOverHandler() {
		public void onMouseOver(MouseOverEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
		}
	});
	All.addMouseDownHandler(new MouseDownHandler() {
		public void onMouseDown(MouseDownEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
		}
	});
	
	Advance = new Button(Lang.getAdvance());
	Advance.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent event) {
			Controlador.change2FilterAdvance();
		}
	});
	Advance.setSize("100%", "100%");
	Advance.setStyleName("gwt-ButtonCenter");
	Advance.addMouseOutHandler(new MouseOutHandler() {
		public void onMouseOut(MouseOutEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenter");
		}
	});
	Advance.addMouseOverHandler(new MouseOverHandler() {
		public void onMouseOver(MouseOverEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterOver");
		}
	});
	Advance.addMouseDownHandler(new MouseDownHandler() {
		public void onMouseDown(MouseDownEvent event) {
			((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
		}
	});
	horizontalPanel.add(Advance);

	btnNewButton.setSize("100%", "100%");
		btnNewButton.setStyleName("gwt-ButtonCenter");
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
		btnNewButton.addMouseDownHandler(new MouseDownHandler() {
			public void onMouseDown(MouseDownEvent event) {
				((Button)event.getSource()).setStyleName("gwt-ButtonCenterPush");
			}
		});
		btnNewButton.addClickHandler(new ClickHandler() {


			public void onClick(ClickEvent event) {
				filtro=new ArrayList<TypeClient>();
				if (!AllBoolean){
				String Token=textBox.getText();
				String[] Words= Token.split(",");
				if (Words.length>0){
					Types=MakeWords(Words);
					filtro=FindTypes(Types,ActualState.getReadingActivityCloseCatalog());
					ArrayList<UserClient> Usuarios=FindUsers(Types,ActualState.getReadingactivity().getGroup());
					MainEntryPoint.setFiltro(filtro, Usuarios,Types,new ArrayList<Long>());
					Me.hide();
				}
				}else {
				MainEntryPoint.CleanFilter();
				Me.hide();
				}
			
		}

			private ArrayList<UserClient> FindUsers(ArrayList<String> types,
					GroupClient group) {
				ArrayList<UserClient> salida=new ArrayList<UserClient>();
				for (UserClient studentUnit : group.getParticipatingUsers()) {
					testUserIn(studentUnit,types,salida);
				}
				testUserIn(group.getProfessor(),types,salida);
				return salida;
			}

			private void testUserIn(UserClient studentUnit,
					ArrayList<String> types, ArrayList<UserClient> salida) {
				for (String typocandidato : types) {
					if (studentUnit.getFirstName().toUpperCase().contains(typocandidato.toUpperCase()))
						AddSalida(studentUnit,salida);
					if (studentUnit.getLastName().toUpperCase().contains(typocandidato.toUpperCase()))
						AddSalida(studentUnit,salida);
					if (studentUnit.getId().toString().toUpperCase().contains(typocandidato.toUpperCase()))
						AddSalida(studentUnit,salida);
					if (studentUnit.getEmail().toUpperCase().contains(typocandidato.toUpperCase()))
						AddSalida(studentUnit,salida);
				}
				
			}

			private void AddSalida(UserClient studentUnit,
					ArrayList<UserClient> salida) {
				if(!salida.contains(studentUnit))
					salida.add(studentUnit);
				
			}

			private ArrayList<TypeClient> FindTypes(ArrayList<String> types,
					CatalogoClient catalogo) {
				ArrayList<TypeClient> Salida=new ArrayList<TypeClient>();
				for (EntryClient entryUni : catalogo.getEntries()) {
					EvaluaEntry(entryUni,types,Salida);
				}
				return Salida;
			}

			private void EvaluaEntry(EntryClient entryUni,
					ArrayList<String> types, ArrayList<TypeClient> salida) {
				if (entryUni instanceof TypeClient)
					EvaluaType((TypeClient) entryUni,types,salida);
				else if (entryUni instanceof TypeCategoryClient)
					EvaluaTypeCategory((TypeCategoryClient) entryUni,types,salida);
				
			}

			private void EvaluaTypeCategory(TypeCategoryClient entryUni,
					ArrayList<String> types, ArrayList<TypeClient> salida) {
				if (existeEn(entryUni, types))
					AddHijos(entryUni,salida);
				
			}

			private void AddHijos(TypeCategoryClient entryUni,
					ArrayList<TypeClient> salida) {
				for (EntryClient candidato : entryUni.getChildren()) {
					if (candidato instanceof TypeClient)
						AddASalida((TypeClient) candidato,salida);
					else if (candidato instanceof TypeCategoryClient)
						AddHijos((TypeCategoryClient) candidato,salida);
				}
				
			}



			private void EvaluaType(TypeClient entryUni,
					ArrayList<String> types, ArrayList<TypeClient> salida) {
				if (existeEn(entryUni,types))
					AddASalida(entryUni,salida);
				
			}
			

			private void AddASalida(TypeClient entryUni,
					ArrayList<TypeClient> salida) {
				if (!salida.contains(entryUni))
					salida.add(entryUni);
				
			}

			private boolean existeEn(EntryClient entryUni,
					ArrayList<String> types) {
				for (String candidato : types) {
					if (candidato.toUpperCase().contains(entryUni.getName().toUpperCase()))
						return true;
				}
				return false;
			}

			private ArrayList<String> MakeWords(String[] words) {
				ArrayList<String> Salida=new ArrayList<String>();
				for (String SS : words) {
					if (!SS.isEmpty()) Salida.add(SS);
				} 
				return Salida;
			}


			
				
		});
	
		}
	
}
