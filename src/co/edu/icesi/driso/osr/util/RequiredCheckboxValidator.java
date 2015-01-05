package co.edu.icesi.driso.osr.util;

import com.vaadin.data.Validator;

public class RequiredCheckboxValidator implements Validator {

	private static final long serialVersionUID = 1L;
	private final String errorMessage;
	
	public RequiredCheckboxValidator(String errorMessage){
		this.errorMessage = errorMessage;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		boolean bvalue = Boolean.valueOf((String) value);
		if(!bvalue){
			throw new InvalidValueException(errorMessage);
		}
	}

}
