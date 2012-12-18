package lector.client.reader.filter.advance;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import lector.client.controler.ActualState;
import lector.client.reader.LoadingPanel;
import lector.share.model.client.AnnotationClient;


public class FilterAsyncSystem {

	private static ArrayList<Rule> ReglasEvaluar;
	private static int actualRule=0;
	private static ArrayList<AnnotationClient> Anotaciones;
	private static ArrayList<Long> filtroResidual;
	
	public FilterAsyncSystem(ArrayList<Rule> Reglas) {
		//TODO Mensajes Generales
		LoadingPanel.getInstance().center();
		LoadingPanel.getInstance().setLabelTexto(ActualState.getLanguage().getFiltering());
		ReglasEvaluar=Reglas;
		actualRule=0;
		Anotaciones=new ArrayList<AnnotationClient>();
		filtroResidual=new ArrayList<Long>();
		nextRule();
	}
	
	public static void nextRule()
	{
		if (actualRule<ReglasEvaluar.size())
		{
			ReglasEvaluar.get(actualRule).evaluarReglas();
		}else flitrado();
		actualRule++;
	}
	
	private static void flitrado() {
		generafiltroResidual();
		VerticalPanel resultado=new VerticalPanel();
		AnotationFilterResultPanel Panel=new AnotationFilterResultPanel(resultado);
		for (AnnotationClient AIndiv : Anotaciones) {
			resultado.add(new CommentPanelFAdvance(AIndiv, new Image(ActualState.getBook().getWebLinks().get(AIndiv.getPageNumber())),Panel.getHeight()));
		}	
		Panel.center();
		LoadingPanel.getInstance().hide();
		
	}

	private static void generafiltroResidual() {
		filtroResidual=new ArrayList<Long>();
		for (AnnotationClient AIndiv : Anotaciones)
		{
			filtroResidual.add(AIndiv.getId());
		}
		
	}

	public static ArrayList<AnnotationClient> getAnotaciones() {
		return Anotaciones;
	}
	
	public static void setAnotaciones(ArrayList<AnnotationClient> anotaciones) {
		Anotaciones = anotaciones;
	}

	public static ArrayList<Long> getFiltroResidual() {
		return filtroResidual;
	}
}
