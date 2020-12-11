package app.exceptions;

public class WrongClientException extends Exception {

	public WrongClientException() {
		super("Error! incorrect client type entered");
	}
	

}
