package lector.share.model;

public class NotAuthenticatedException extends Exception {

	public NotAuthenticatedException() {
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Texto;
	
	public NotAuthenticatedException(String Textoin) {
		Texto=Textoin;
	}
	@Override
	public String getMessage() {
		return Texto;
	}
}
