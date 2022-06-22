package br.inatel.quotationmanagement.exceptions;

public class InvalidQuoteDateException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidQuoteDateException(String msg) {
		super(msg);
	}
}
