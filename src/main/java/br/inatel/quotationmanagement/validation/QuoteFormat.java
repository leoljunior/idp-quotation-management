package br.inatel.quotationmanagement.validation;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuoteFormat implements ConstraintValidator<QuoteValidation, Map<String, String>> {
	
	public boolean isValid(Map<String, String> values, ConstraintValidatorContext constraintValidatorContext) {

		constraintValidatorContext.disableDefaultConstraintViolation();
		boolean validData = true;

		try {
			for (Map.Entry<String, String> value : values.entrySet()) {

				if (!value.getKey().matches("^[0-3]?[0-9]-[0-3]?[0-9]-(?:[0-9]{2})?[0-9]{2}$")) {
					constraintValidatorContext.buildConstraintViolationWithTemplate(
							"Data (" + value.getKey() + ") inválida ou fora do padrão dd-mm-aaaa")

							.addConstraintViolation();
					validData = false;
				}

				if (!value.getValue().matches("^[0-9]*([\\\\.,]{1}[0-9]{0,2}){0,1}$") || value.getValue().isEmpty()) {
					constraintValidatorContext.buildConstraintViolationWithTemplate("Valor inválido: (" + value.getValue()
							+ ") O valor deve ser numérico, positivo e não vazio").addConstraintViolation();
					validData = false;
				}
			}
		} catch (Exception e) {
			validData = false;
		}
		return validData;
	}
}
