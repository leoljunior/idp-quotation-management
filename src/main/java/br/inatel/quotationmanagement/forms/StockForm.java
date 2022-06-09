package br.inatel.quotationmanagement.forms;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.inatel.quotationmanagement.validation.QuoteValidation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockForm {
		
	@NotNull @NotEmpty
	private String stockId;
	
	@QuoteValidation
	private Map<String, String> quotes;
	
}
