package smartHomeManagement.exceptions;

public class MailException extends Exception{

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
