package br.inatel.quotationmanagement.exceptions;

public class StockNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public StockNotFoundException(String msg) {
		super(msg);
	}
}
