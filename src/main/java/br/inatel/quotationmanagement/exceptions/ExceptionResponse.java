package br.inatel.quotationmanagement.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter @Setter
@AllArgsConstructor
public class ExceptionResponse {

	private String field;
	
	private String status;
		
}
