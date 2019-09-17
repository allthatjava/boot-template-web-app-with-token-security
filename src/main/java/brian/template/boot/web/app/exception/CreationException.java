package brian.template.boot.web.app.exception;

public class CreationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1323040521845611469L;

	public CreationException() {
		super();
	}

	public CreationException(String message) {
		super(message);
	}

	public CreationException(String message, Exception ex) {
		super(message, ex);
	}
}
