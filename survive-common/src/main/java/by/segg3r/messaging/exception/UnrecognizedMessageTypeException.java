package by.segg3r.messaging.exception;

public class UnrecognizedMessageTypeException extends Exception {

	private static final long serialVersionUID = -7710389374806154399L;

	public UnrecognizedMessageTypeException(String message) {
		super(message);
	}

	public UnrecognizedMessageTypeException(String message, Throwable cause) {
		super(message, cause);
	}

}
