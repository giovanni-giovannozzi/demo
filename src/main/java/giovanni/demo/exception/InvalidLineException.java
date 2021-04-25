package giovanni.demo.exception;

public class InvalidLineException extends Exception {
	private static final long serialVersionUID = 2899231929515641683L;

	public InvalidLineException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidLineException(String message) {
		super(message);
	}

}
