package com.tradez.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tradez.models.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		PasswordValidator validator = new PasswordValidator(Arrays.asList(
				// at least 8 characters
				new LengthRule(5, 20),

				// at least one upper-case character
				new CharacterRule(EnglishCharacterData.UpperCase, 1),

				// at least one lower-case character
				new CharacterRule(EnglishCharacterData.LowerCase, 1),

				// at least one digit character
				new CharacterRule(EnglishCharacterData.Digit, 1),

				// at least one symbol (special character)
				new CharacterRule(EnglishCharacterData.Special, 1),

				// no whitespace
				new WhitespaceRule()

		));
		RuleResult result = validator.validate(new PasswordData(user.getPassword()));
		if (!result.isValid()) {
			List<String> messages = validator.getMessages(result);
			errors.rejectValue("password", null, messages.stream().collect(Collectors.joining(",")));
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("password", null, "the password and confirmPassword are not the same.");
		}
	}
}
