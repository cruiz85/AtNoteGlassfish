package lector.client.controler.catalogo.graph;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;



public class ArbolgrafoVacio extends Composite implements ElementoGrafo {


private SimplePanel simplePanel;

public ArbolgrafoVacio() {
	
	simplePanel = new SimplePanel();
	initWidget(simplePanel);
	simplePanel.setSize("10px","20px");
}

public SimplePanel getSimplePanel() {
	return simplePanel;
}
}
