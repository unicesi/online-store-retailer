package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.SquaredProductSummary;

public class SquaredProductPresenter implements Presenter {
	
	private SquaredProductSummary viewComponent;

	public SquaredProductPresenter(SquaredProductSummary viewComponent) {
		this.viewComponent = viewComponent;
	}
	
	public void onAddingToCart(int productId, int quantity) {
		// TODO
		// Perform the adding process
		// ...
	}

	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
		
		viewComponent.setData("quantity", 1);
	}

}
