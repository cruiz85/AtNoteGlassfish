package lector.client.catalogo;

import lector.client.controler.EntitdadObject;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class BotonesStackPanelMio extends Button {
	
	
	protected VerticalPanel Actual;
	protected EntitdadObject Entidad;
	protected String Text;
	
	public BotonesStackPanelMio(EntitdadObject Entidad) {
		super(Entidad.getName());
		Text=Entidad.getName();
		this.Entidad=Entidad;
	}
	
	public BotonesStackPanelMio(String HTML) {
		super(HTML);
		Text=HTML;
	}

	public abstract BotonesStackPanelMio Clone();
	
	
	
	public void setActual(VerticalPanel actual) {
		if (Actual!=null)Actual.remove(this);
		Actual = actual;
		Actual.add(this);
	}
	
	public VerticalPanel getActual() {
		return Actual;
	}
	
	public EntitdadObject getEntidad() {
		return Entidad;
	}
	
	public void setEntidad(EntitdadObject entidad) {
		Entidad = entidad;
	}
	
	public void setIcon(String S,String Text)
	{
		this.Text=Text;
		setHTML("<img src=\""+ S +"\">"+ Text);
	}
	
	@Override
	public String getHTML() {
		
		return Text;
	}

	@Override
	public String getText() {
	
		return Text;
	}
	
	
}
