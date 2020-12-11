package app.exceptions;

public class IllegalModificationException extends Exception{

	public IllegalModificationException() {
		super("Error! One or more values you are trying to change cannot be modified.");
	}
	
	

}
