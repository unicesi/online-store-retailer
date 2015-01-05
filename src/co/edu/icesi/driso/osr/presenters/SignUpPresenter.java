package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.SignUpForm;
import co.edu.icesi.driso.osr.util.OSRException;

public class SignUpPresenter implements Presenter {
	
	private final SignUpForm viewComponent;
	
	public SignUpPresenter(SignUpForm viewComponent){
		this.viewComponent = viewComponent;
	}

	public boolean onSignUp(String names, String surnames, String idNumber, 
			String email, String password, int genderOptionId, int 
			idDocumentTypeId) throws OSRException {
		
		boolean ok = true;
		// TODO
		// Perform the search process
		// ...
		
		// And then, redirect to the results page
		if(ok)
			viewComponent.reset();
			
		return ok;
	}

	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
	}

}
