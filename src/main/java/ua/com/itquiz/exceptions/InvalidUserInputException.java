package ua.com.itquiz.exceptions;

/**
 * 
 * @author Artur Meshcheriakov
 */
public class InvalidUserInputException extends Exception {

    private static final long serialVersionUID = -8613227902296912305L;

    public InvalidUserInputException(String message) {
	super(message);
    }

    public InvalidUserInputException(Throwable cause) {
	super(cause);
    }

    public InvalidUserInputException(String message, Throwable cause) {
	super(message, cause);
    }

}
