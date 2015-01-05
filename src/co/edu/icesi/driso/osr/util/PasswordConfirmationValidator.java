package co.edu.icesi.driso.osr.util;

import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractField;

public class PasswordConfirmationValidator implements Validator {

	private static final long serialVersionUID = 1L;
	private final AbstractField<String> passwordField;
	private final String errorMessage;
	
	public PasswordConfirmationValidator(AbstractField<String> passwordField, 
			String errorMessage){
		this.passwordField = passwordField;
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		String passwordConfirmation = (String) value;
		if(!passwordField.getValue().equals(passwordConfirmation)){
			throw new InvalidValueException(errorMessage);
		}
	}

}
