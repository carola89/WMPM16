package at.tu.wmpm16.exceptions;

public class MailException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailException() {
		super();
	}

	public MailException(String message, Throwable ex) {
		super(message, ex);
		
	}

	public MailException(String message) {
		super(message);
	
	}


}
