package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.SubscriptionForm;

public class SubscriptionPresenter implements Presenter {
	
	private final SubscriptionForm viewComponent;
	
	public SubscriptionPresenter(SubscriptionForm viewComponent){
		this.viewComponent = viewComponent;
	}
	
	public void onSubscribe(String email){
		// TODO
		// Perform the subscription process
		// ...	
		
		viewComponent.reset();
	}

	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
	}

}
