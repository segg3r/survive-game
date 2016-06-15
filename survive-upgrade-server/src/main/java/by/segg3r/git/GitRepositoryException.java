package by.segg3r.git;

public class GitRepositoryException extends Exception {

	private static final long serialVersionUID = 6365790134314336870L;

	public GitRepositoryException(String message) {
		super(message);
	}
	
	public GitRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

}
