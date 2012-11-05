package lector.client.reader;

import lector.share.model.client.AnnotationClient;


public class AnnotationRanked {

	private AnnotationClient AnotacionRankeada;
	private float ranking;
	
	public AnnotationRanked(AnnotationClient A) {
		AnotacionRankeada=A;
				ranking=0f;
				
	}
	
	public void setAnotacionRankeada(AnnotationClient anotacionRankeada) {
		AnotacionRankeada = anotacionRankeada;
	}
	
	public void setRanking(float ranking) {
		this.ranking = ranking;
	}
	
	public AnnotationClient getAnotacionRankeada() {
		return AnotacionRankeada;
	}
	
	public float getRanking() {
		return ranking;
	}
}
