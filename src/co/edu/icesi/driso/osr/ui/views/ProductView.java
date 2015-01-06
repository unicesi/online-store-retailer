package co.edu.icesi.driso.osr.ui.views;

import java.util.Random;

import co.edu.icesi.driso.osr.presenters.ProductPresenter;
import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.components.FeaturedProducts;
import co.edu.icesi.driso.osr.ui.components.ProductSummary;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class ProductView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "product";
	private int productId;
	private ViewWrapper wrapper;
	
	private ProductSummary productSummary;
	private FeaturedProducts relatedProducts;
	
	public ProductView(){
		productId = -1;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() != null){
			// split at "/", add each part as a label
			// Structure: vars[0] = categoryId
			String[] vars = event.getParameters().split("/");
			String nre = "[0-9]{1,}";
			
			if(vars[0].matches(nre)){
				productId = Integer.parseInt(vars[0]);
				
				Page.getCurrent()
				.setTitle(getProductInfo(productId)[1] + " - Online Store Retailer");
				
				// Build the UI and assign respective presenters
				buildUI();
				assignPresenters();
				
			}else{
				Notification.show("Unknown error", "An unknown error has occurred, please"
						+ " continue your navigation", 
						Notification.Type.ERROR_MESSAGE);
				
				try {
					Thread.sleep(1000);
					Application.navigator.navigateTo(HomeView.NAME);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}   
	}
	
	public void buildUI(){

		productSummary = new ProductSummary(productId, true);
		
		// Top-selling related-products showcase
		relatedProducts = new FeaturedProducts(false, 1);
		
		wrapper = new ViewWrapper(productSummary, relatedProducts);
		addComponent(wrapper);
	}
	
	public void assignPresenters(){
		ProductPresenter productPresenter = new ProductPresenter(productSummary);
		productPresenter.init();
	}
	
	public String[] getProductInfo(int productId){
		Random gen = new Random();
		String v = gen.nextBoolean() ? "11000" : "10000";
		return new String[]{
				"1",
				"Shampoo Redken",
				"htc_one.png",
				"11000",
				v
		};
	}

}
