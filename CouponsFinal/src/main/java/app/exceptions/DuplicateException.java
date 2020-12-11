package app.exceptions;

public class DuplicateException extends Exception{

	public DuplicateException() {
		super("Error! An entity with a key value identical to one of the values you have entered already exists.");
	}

}
