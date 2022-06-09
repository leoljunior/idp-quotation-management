package br.inatel.quotationmanagement.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QuoteFormat.class)
public @interface QuoteValidation {
	
	String message() default "Dados inválidos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
