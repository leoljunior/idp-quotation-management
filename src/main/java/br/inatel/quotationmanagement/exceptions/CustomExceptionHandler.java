package br.inatel.quotationmanagement.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class CustomExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ExceptionResponse> handle(MethodArgumentNotValidException ex) {
		List<ExceptionResponse> responseErrors = new ArrayList<>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ExceptionResponse error = new ExceptionResponse(e.getField(), message);
			responseErrors.add(error);
		});
		return responseErrors;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidQuoteDateException.class)
	public ExceptionResponse handle(InvalidQuoteDateException ex) {		
		ExceptionResponse responseErrors = new ExceptionResponse("quotes.date", ex.getMessage());		
		return responseErrors;
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResponseStatusException.class)
	public ExceptionResponse handle(ResponseStatusException ex) {
		ExceptionResponse responseErrors = new ExceptionResponse("stockId", ex.getReason());		
		return responseErrors;
	}
}
