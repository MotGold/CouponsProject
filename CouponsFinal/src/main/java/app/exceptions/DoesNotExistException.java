package app.exceptions;

public class DoesNotExistException extends Exception {

	public DoesNotExistException() {
		super("Error! The entity you are trying to reach does not exist.");
	}

}
