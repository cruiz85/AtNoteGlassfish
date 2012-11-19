package lector.client.reader.hilocomentarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import lector.client.book.reader.GWTService;
import lector.client.book.reader.GWTServiceAsync;
import lector.client.controler.ErrorConstants;
import lector.share.model.AnnotationThread;
import lector.share.model.client.AnnotationClient;
import lector.share.model.client.AnnotationThreadClient;
import lector.share.model.client.TextSelectorClient;

public class ArbitroThreads {

	
	private Stack<ParesLlamada> Llamadas;
	private GWTServiceAsync bookReaderServiceHolder = GWT
			.create(GWTService.class);
	
	public ArbitroThreads() {
		Llamadas=new Stack<ParesLlamada>();
	}

	public void addLlamada(ParesLlamada A)
	{
		Llamadas.add(A);
	}

	public void Start() {
		if (!Llamadas.isEmpty())
		{
			bookReaderServiceHolder.getAnnotationThreadsByItsAnnotation(Llamadas.peek().getIDPadre().getId(), new AsyncCallback<List<AnnotationThreadClient>>() {
				
				@Override
				public void onSuccess(List<AnnotationThreadClient> result) {
					ParesLlamada Success=Llamadas.pop();
					if (result==null) 
						result = new ArrayList<AnnotationThreadClient>();
					for (AnnotationThreadClient annotationThread : result) {
						Respuesta R=new Respuesta(annotationThread,Success.getIDPadre().getTextSelectors());
						Success.getVP().add(R);
						JeraquiaSimulada JS=new JeraquiaSimulada();
						ProcesaHijos(annotationThread,Success.getIDPadre().getTextSelectors(),JS);
						Success.getVP().add(JS);
//						MainEntryPoint.setPorcentScrollAnnotationsPanel();
					}
					
					Start();

				
				}
				
				private void ProcesaHijos(
						AnnotationThreadClient annotationThreadPadre,
						List<TextSelectorClient> textSelectors, JeraquiaSimulada jS) {
					List<AnnotationThreadClient> Hijos= annotationThreadPadre.getSubThreads();
					if (Hijos==null) 
						Hijos = new ArrayList<AnnotationThreadClient>();
					for (AnnotationThreadClient annotationThread : Hijos) {
						Respuesta R=new Respuesta(annotationThread,textSelectors);
						jS.getVerticalPanel().add(R);
						JeraquiaSimulada JS=new JeraquiaSimulada();
						ProcesaHijos(annotationThread,textSelectors,JS);
						jS.getVerticalPanel().add(JS);
//						MainEntryPoint.setPorcentScrollAnnotationsPanel();
					}
					
				}

				@Override
				public void onFailure(Throwable caught) {
					ParesLlamada error=Llamadas.pop();
					Window.alert(ErrorConstants.ERROR_LOADING_THREAD1 + error.getIDPadre().getId() + ErrorConstants.ERROR_LOADING_THREAD2 + error.getIDPadre());
					
				}
			});
			
		}
		
	}
	
	
}
