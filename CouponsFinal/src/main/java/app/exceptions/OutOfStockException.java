package app.exceptions;

public class OutOfStockException extends Exception {

	public OutOfStockException() {
		super("Error! item is out of stock");
	}
	
	

}
