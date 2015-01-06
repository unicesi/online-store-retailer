package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.ProductSummary;
import co.edu.icesi.driso.osr.util.OSRException;

public class ProductPresenter implements Presenter {
	
	private final ProductSummary viewComponent;
	
	public ProductPresenter(ProductSummary viewComponent){
		this.viewComponent = viewComponent;
	}
	
	public void onAddingToCart(int productId, int quantity) {
		// TODO
		// Perform the adding process
		// ...
	}
	
	public void onProductRating(int productId, double ratingValue) throws OSRException {
		// TODO
		// Perform the rating process
		// ...
	}

	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
		
		// TODO
		// Update rating information
		// int productId = viewComponent.getProductId();
		
		viewComponent.setData("rating", 4.5);
	}

}
