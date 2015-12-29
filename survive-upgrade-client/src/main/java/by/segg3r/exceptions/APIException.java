package by.segg3r.exceptions;

public class APIException extends Exception {

	private static final long serialVersionUID = 9197289508221368176L;

	public APIException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public APIException(Throwable cause) {
		super(cause);
	}

	public APIException(String message) {
		super(message);
	}
	
}
