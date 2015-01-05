package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.SignInForm;

public class SignInPresenter implements Presenter {
	
	private final SignInForm viewComponent;
	
	public SignInPresenter(SignInForm viewComponent){
		this.viewComponent = viewComponent;
	}

	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
	}
	
	public boolean onLogin(String email, String password){
		boolean ok = true;
		// TODO
		// Perform the login process
		// ...

		// And then, answer
		if(ok)
			viewComponent.reset();
		
		return ok;
	}

}
