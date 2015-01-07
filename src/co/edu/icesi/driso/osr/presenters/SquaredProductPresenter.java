package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.SquaredProductSummary;

public class SquaredProductPresenter implements Presenter {
	
	public interface Collaborator {
		public void onAddingToCart(String[] product, int quantity);
	}
	
	private final SquaredProductSummary viewComponent;
	private final Collaborator[] collaborators;

	public SquaredProductPresenter(SquaredProductSummary viewComponent, 
			Collaborator ... collaborators) {
		
		this.viewComponent = viewComponent;
		this.collaborators = collaborators;
	}
	
	public void onAddingToCart(String[] product, int quantity) {
		// TODO
		// Perform the adding process
		// ...
		
		for (int i = 0; i < collaborators.length; i++) {
			collaborators[i].onAddingToCart(product, quantity);
		}
	}

	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
		
		viewComponent.setData("quantity", 1);
	}

}
