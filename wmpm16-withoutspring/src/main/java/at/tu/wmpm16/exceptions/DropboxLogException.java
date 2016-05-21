package at.tu.wmpm16.exceptions;

public class DropboxLogException extends Exception {

	public DropboxLogException() {
		super();
	}

	public DropboxLogException(String message) {
		super(message);

	}

	public DropboxLogException(String message, Throwable ex) {
		super(message, ex);

	}
}
