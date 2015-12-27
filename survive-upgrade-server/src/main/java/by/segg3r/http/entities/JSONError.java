package by.segg3r.http.entities;

public class JSONError {

	public static JSONError of(String message) {
		return new JSONError(message);
	}
	
	private String message;

	public JSONError(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
