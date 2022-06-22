package br.inatel.quotationmanagement.validation;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuoteFormat implements ConstraintValidator<QuoteValidation, Map<String, String>> {
//	@Override
//	public boolean isValid(Map<String, String> values, ConstraintValidatorContext context) {
//		
//		context.disableDefaultConstraintViolation();
//
//		try {
//			for (Map.Entry<String, String> value : values.entrySet()) {
//
//				if (!value.getKey().matches("^[0-3]?[0-9]-[0-3]?[0-9]-(?:[0-9]{2})?[0-9]{2}$")) {
//					context.buildConstraintViolationWithTemplate(
//							"Data inválida ou fora do padrão dd-mm-aaaa: (" + value.getKey() + ")")
//							.addConstraintViolation();
//					return false;
//				}
//
//				if (!value.getValue().matches("^[0-9]*([\\\\.,]{1}[0-9]{0,2}){0,1}$") || value.getValue().isEmpty()) {
//					context.buildConstraintViolationWithTemplate(
//							"Valor inválido: (" + value.getValue() + ") O valor deve ser numérico, positivo e não vazio")
//							.addConstraintViolation();
//					return false;
//				}
//			}
//		} catch (Exception e) {
//			return false;
//		}
//		return true;
//	}
	@Override
	public boolean isValid(Map<String, String> values, ConstraintValidatorContext context) {

		context.disableDefaultConstraintViolation();
		boolean validData = true;

		try {
			for (Map.Entry<String, String> value : values.entrySet()) {

				if (!value.getKey().matches("^[0-3]?[0-9]-[0-3]?[0-9]-(?:[0-9]{2})?[0-9]{2}$")) {
					context.buildConstraintViolationWithTemplate(
							"Data (" + value.getKey() + ") inválida ou fora do padrão dd-mm-aaaa")

							.addConstraintViolation();
					validData = false;
				}

				if (!value.getValue().matches("^[0-9]*([\\\\.,]{1}[0-9]{0,2}){0,1}$") || value.getValue().isEmpty()) {
					context.buildConstraintViolationWithTemplate("Valor inválido: (" + value.getValue()
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
