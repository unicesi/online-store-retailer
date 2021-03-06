package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.components.ShoppingCartProductSummary;
import co.edu.icesi.osr.dtos.ProductoDTO;

public class ShoppingCartProductPresenter implements Presenter {
	
	public interface Collaborator {
		public void onQuantityChange(ProductoDTO product, int quantity);
		
		public void onRemovingProduct(ProductoDTO product);
	}
	
	private final ShoppingCartProductSummary viewComponent;
	private final Collaborator[] collaborators;
	private final int initialQuantity;

	public ShoppingCartProductPresenter(ShoppingCartProductSummary viewComponent, 
			int initialQuantity, Collaborator ... collaborators){
		this.viewComponent = viewComponent;
		this.initialQuantity = initialQuantity;
		this.collaborators = collaborators;
	}
	
	public void onRemovingProduct(ProductoDTO product){
		for (int i = 0; i < collaborators.length; i++) {
			collaborators[i].onRemovingProduct(product);
		}
	}
	
	public void onQuantityChange(ProductoDTO product, int quantity){
		
		for (int i = 0; i < collaborators.length; i++) {
			collaborators[i].onQuantityChange(product, quantity);
		}
		
		viewComponent.setData("quantity", quantity);
	}
	
	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
		
		// TODO
		// Perform query (active session) to know product quantity
		viewComponent.setData("quantity", initialQuantity);
	}

}
