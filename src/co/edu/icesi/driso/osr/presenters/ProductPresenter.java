package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.ProductSummary;
import co.edu.icesi.driso.osr.util.OSRException;

public class ProductPresenter implements Presenter {
	
	public interface Collaborator {
		
		public void onAddingToCart(String[] product, int quantity);
	}
	
	private final ProductSummary viewComponent;
	private final Collaborator[] collaborators;
	
	public ProductPresenter(ProductSummary viewComponent, 
			Collaborator ... collaborators){
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
	
	public void onProductRating(String[] product, double ratingValue) throws OSRException {
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
