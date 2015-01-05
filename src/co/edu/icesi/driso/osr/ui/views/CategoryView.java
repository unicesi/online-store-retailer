package co.edu.icesi.driso.osr.ui.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import co.edu.icesi.driso.osr.ui.components.ProductGrid;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class CategoryView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "category";
	private VerticalLayout contentLayout;
	private Label titleLabel;
	private ViewWrapper wrapper;
	
	private int categoryId;

	public CategoryView(){
		categoryId = -1;
		
		buildUI();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() != null){
			// split at "/", add each part as a label
			// Structure: vars[0] = categoryId
			String[] vars = event.getParameters().split("/");
			String nre = "[0-9]{1,}";
			
			if(vars[0].matches(nre)){
				categoryId = Integer.parseInt(vars[0]);
				Page.getCurrent()
				.setTitle(getCategoryInfo(categoryId)[1] + " - Online Store Retailer");
			}else{
				Notification.show("Unknown error", "An unknown error has occurred, "
						+ "please reload the page. If this error continues "
						+ "contact the system administrator", 
						Notification.Type.ERROR_MESSAGE);
			}
		}   
	}

	public void buildUI(){
		
		// Layout configuration
		contentLayout = new VerticalLayout();
		contentLayout.setStyleName("content-layout");
		titleLabel = new Label("<h1>" + getCategoryInfo(categoryId)[1] + "</h1>", ContentMode.HTML);
		
		// Product grid
		List<String[]> products = getElements(100);
		ProductGrid productGrid = new ProductGrid(products, 8, 4);
		
		contentLayout.addComponent(titleLabel);
		contentLayout.addComponent(productGrid);
		
		wrapper = new ViewWrapper(contentLayout);
		addComponent(wrapper);
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

	public String[] getCategoryInfo(int categoryId){
		return new String[]{
				"" + categoryId,
				"Animals & Pet Supplies"
		};
	}

	public List<String[]> getElements(int n) {
		List<String[]> products = new ArrayList<String[]>();
		
		for (int i = 0; i < n; i++) {
			String[] tempProduct = getProductInfo(i);
			products.add(tempProduct);
		}
		
		return products;
	}

}
