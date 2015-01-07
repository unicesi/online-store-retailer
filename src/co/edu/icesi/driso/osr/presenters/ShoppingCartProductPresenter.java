package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.ShoppingCartProductSummary;

public class ShoppingCartProductPresenter implements Presenter {
	
	private final ShoppingCartProductSummary viewComponent;

	public ShoppingCartProductPresenter(ShoppingCartProductSummary viewComponent){
		this.viewComponent = viewComponent;
	}
	
	public void onProductRemoving(int productId){
		
	}
	
	public void onQuantityChange(int productId, int quantity){
		
	}
	
	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
		
		// TODO
		// Perform query (active session) to know product quantity
		viewComponent.setData("quantity", 1);
	}

}
