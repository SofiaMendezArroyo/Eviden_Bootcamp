package com.clubnautico.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MatriculaUnicaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MatriculaUnica {
	String message() default "La matrícula ya está registrada";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
