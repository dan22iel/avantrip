package ar.com.avantrip.exception;

public class ValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException(String detalles) {
		super("Wrong expression: ".concat(detalles));

	}

}
