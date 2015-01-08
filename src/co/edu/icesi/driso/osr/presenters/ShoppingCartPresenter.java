package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.components.ShoppingCartProductSummary;
import co.edu.icesi.driso.osr.ui.views.HomeView;
import co.edu.icesi.driso.osr.ui.views.ShoppingCartView;
import co.edu.icesi.osr.dtos.ProductoDTO;

import com.google.gwt.dev.util.collect.HashMap;

public class ShoppingCartPresenter implements Presenter, 
												SquaredProductPresenter.Collaborator,
												ProductPresenter.Collaborator,
												ShoppingCartProductPresenter.Collaborator {
	
	public interface Collaborator {
		public void onShoppingCartUpdated(double totalToPay);
	}

	private final ShoppingCartView viewComponent;
	private double totalToPay;
	private final HashMap<ShoppingCartProductSummary, Integer> products;
	private final Collaborator[] collaborators;

	public ShoppingCartPresenter(ShoppingCartView viewComponent, 
			Collaborator ... collaborators){
		this.viewComponent = viewComponent;
		totalToPay = 0;
		products = new HashMap<ShoppingCartProductSummary, Integer>();
		this.collaborators = collaborators;
	}

	public void onCheckOut(){
		Application.navigator.navigateTo(HomeView.NAME);
	}

	@Override
	public void onQuantityChange(ProductoDTO product, int quantity){
		double newTotal = 0;
		
		for (ShoppingCartProductSummary tempProductSummary : products.keySet()) {
			if(tempProductSummary.getProduct().equals(product)){
				products.put(tempProductSummary, quantity);
			}
			
			newTotal += 
					Double.valueOf(tempProductSummary.getProduct().getPrecio()) 
					* products.get(tempProductSummary);
		}
				
		totalToPay = newTotal;
		viewComponent.setData("total", String.valueOf(totalToPay));
		
		for (Collaborator collaborator : collaborators) {
			collaborator.onShoppingCartUpdated(totalToPay);
		}
	}
	
	@Override
	public void onRemovingProduct(ProductoDTO product) {
		double newTotal = 0;
		boolean removed = false;
		
		for (ShoppingCartProductSummary tempProductSummary : products.keySet()) {
			if(!removed && tempProductSummary.getProduct().equals(product)){
				products.remove(tempProductSummary);
				removed = true;
			}else{
				newTotal += 
						Double.valueOf(tempProductSummary.getProduct().getPrecio()) 
						* products.get(tempProductSummary);
			}
		}
		
		totalToPay = newTotal;
		viewComponent.setData("products", products.keySet());
		viewComponent.setData("total", String.valueOf(totalToPay));
		
		for (Collaborator collaborator : collaborators) {
			collaborator.onShoppingCartUpdated(totalToPay);
		}
	}

	@Override
	public void onAddingToCart(ProductoDTO product, int quantity){
		
		boolean added = false;

		for (ShoppingCartProductSummary tempProductSummary : products.keySet()) {
			if(!added && tempProductSummary.getProduct().equals(product)){
				int newQuantity = products.get(tempProductSummary) + quantity;
				
				products.put(tempProductSummary, newQuantity);
				totalToPay += Double.valueOf(tempProductSummary.getProduct().getPrecio()) * quantity;
				
				tempProductSummary.setData("quantity", newQuantity);
				
				added = true;
			}
		}
		
		if(!added){
			ShoppingCartProductSummary productSummary = 
					new ShoppingCartProductSummary(product);
			ShoppingCartProductPresenter productPresenter = 
					new ShoppingCartProductPresenter(productSummary, quantity, this);
			productPresenter.init();

			products.put(productSummary, quantity);
			totalToPay += Double.valueOf(product.getPrecio()) * quantity;
		}
		
		viewComponent.setData("products", products.keySet());
		viewComponent.setData("total", String.valueOf(totalToPay));
		
		for (Collaborator collaborator : collaborators) {
			collaborator.onShoppingCartUpdated(totalToPay);
		}
	}

	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();

		// TODO
		// Perform query (active session) to retrieve the shopping cart items
	}

}
