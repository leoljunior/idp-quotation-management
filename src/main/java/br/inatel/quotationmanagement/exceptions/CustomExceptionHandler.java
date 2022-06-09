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
	
	
	
	
	
	
	
	
//	@Autowired
//	private MessageSource messageSource;
//	
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		List<ExceptionResponse.Field> campos = new ArrayList<>();
//		
//		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
//			String nome = ((FieldError) error).getField(); 
//			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
//			
//			campos.add(new ExceptionResponse.Field(nome, mensagem));
//		}
//		
//		ExceptionResponse response = new ExceptionResponse();
//		response.setStatus(status.value());
//		response.setTitle("Um ou mais campos estão inválidos. Preencha corretamente e tente novamente.");
//		response.setFields(campos);
//		
//		return handleExceptionInternal(ex, response, headers, status, request);
//	}
	
//	@org.springframework.web.bind.annotation.ExceptionHandler(NegocioException.class) 
//	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
//		
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//		
//		ExceptionResponse response = new ExceptionResponse();
//		response.setStatus(status.value()); 
//		response.setTitle(ex.getMessage());
//		
//		return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
//	}
	
//	@org.springframework.web.bind.annotation.ExceptionHandler(StockNotFoundException.class)
//	public ResponseEntity<Object> handleNegocio(StockNotFoundException ex, WebRequest request) {
//		
//		HttpStatus status = HttpStatus.NOT_FOUND;
//		
//		ExceptionResponse response = new ExceptionResponse();
//		response.setStatus(status.value());
//		response.setTitle(ex.getMessage()); 
//		
//		return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
//	}
	
//	@ExceptionHandler(StockNotFoundException.class)
//	  public final ResponseEntity<ExceptionResponse> handleNotFoundException(StockNotFoundException ex, WebRequest request) {
//	    ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(),
//	        request.getDescription(false),HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
//
//	    return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
//	  }
}
