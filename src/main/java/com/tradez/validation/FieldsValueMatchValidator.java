package com.tradez.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tradez.models.User;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, User> {

	@Override
	public void initialize(FieldsValueMatch constraintAnnotation) {

	}

	@Override
	public boolean isValid(final User value, final ConstraintValidatorContext context) {

		User user = value;
		boolean isValid = user.getPlainPassword().equals(user.getPlainConfirmPassword());
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("plainPassword").addConstraintViolation();
		}
		return isValid;
	}
}