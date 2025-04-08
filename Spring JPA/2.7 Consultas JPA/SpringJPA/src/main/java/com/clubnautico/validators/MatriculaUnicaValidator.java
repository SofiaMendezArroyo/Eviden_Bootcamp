package com.clubnautico.validators;

import com.clubnautico.repositories.BarcoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class MatriculaUnicaValidator implements ConstraintValidator<MatriculaUnica, String> {
	@Autowired
	private BarcoRepository barcoRepository;

	@Override
	public boolean isValid(String matricula, ConstraintValidatorContext context) {
		if (matricula == null) {
			return true;
		}
		return !barcoRepository.existsById(matricula);
	}
}
