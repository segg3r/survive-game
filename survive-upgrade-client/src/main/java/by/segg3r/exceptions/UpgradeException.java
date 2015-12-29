package by.segg3r.exceptions;

public class UpgradeException extends Exception {

	private static final long serialVersionUID = -7980389080510498781L;

	public UpgradeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UpgradeException(String message) {
		super(message);
	}
	
}
