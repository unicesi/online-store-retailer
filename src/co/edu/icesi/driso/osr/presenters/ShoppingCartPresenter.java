package co.edu.icesi.driso.osr.presenters;

import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.components.ShoppingCartProductSummary;
import co.edu.icesi.driso.osr.ui.views.HomeView;
import co.edu.icesi.driso.osr.ui.views.ShoppingCartView;

import com.google.gwt.dev.util.collect.HashMap;
import com.vaadin.ui.Notification;

public class ShoppingCartPresenter implements Presenter, 
												SquaredProductPresenter.Collaborator,
												ProductPresenter.Collaborator,
												ShoppingCartProductPresenter.Collaborator {

	private final ShoppingCartView viewComponent;
	private double totalToPay;
	private final HashMap<ShoppingCartProductSummary, Integer> products;

	public ShoppingCartPresenter(ShoppingCartView viewComponent){
		this.viewComponent = viewComponent;
		totalToPay = 0;
		products = new HashMap<ShoppingCartProductSummary, Integer>();
	}

	public void onCheckOut(){
		Application.navigator.navigateTo(HomeView.NAME);
		Notification.show("Mensaje");
	}

	@Override
	public void onQuantityChange(String[] product, int quantity){
		double newTotal = 0;
		
		for (ShoppingCartProductSummary tempProductSummary : products.keySet()) {
			if(tempProductSummary.getProduct().equals(product)){
				products.put(tempProductSummary, quantity);
			}
			
			newTotal += 
					Double.valueOf(tempProductSummary.getProduct()[3]) 
					* products.get(tempProductSummary);
		}
				
		totalToPay = newTotal;
		viewComponent.setData("total", String.valueOf(totalToPay));
	}
	
	@Override
	public void onRemovingProduct(String[] product) {
		double newTotal = 0;
		
		for (ShoppingCartProductSummary tempProductSummary : products.keySet()) {
			if(tempProductSummary.getProduct().equals(product)){
				products.remove(tempProductSummary);
			}
			
			newTotal += 
					Double.valueOf(tempProductSummary.getProduct()[3]) 
					* products.get(tempProductSummary);
		}
		
		totalToPay = newTotal;
		viewComponent.setData("products", products.keySet());
		viewComponent.setData("total", String.valueOf(totalToPay));
	}

	@Override
	public void onAddingToCart(String[] product, int quantity){

		ShoppingCartProductSummary productSummary = new ShoppingCartProductSummary(product);
		ShoppingCartProductPresenter productPresenter = new ShoppingCartProductPresenter(productSummary, quantity, this);
		productPresenter.init();

		products.put(productSummary, quantity);
		totalToPay += Double.valueOf(product[3]);

		viewComponent.setData("products", products.keySet());
		viewComponent.setData("total", String.valueOf(totalToPay));
	}

	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();

		// TODO
		// Perform query (active session) to retrieve the shopping cart items
	}

}
