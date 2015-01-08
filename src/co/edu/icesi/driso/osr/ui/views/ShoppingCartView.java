package co.edu.icesi.driso.osr.ui.views;

import java.util.Set;

import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.ShoppingCartPresenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.ui.components.ShoppingCartProductSummary;
import co.edu.icesi.driso.osr.util.OSRUtilities;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ShoppingCartView extends VerticalLayout implements View, ViewComponent<Object> {
	
	private static final long serialVersionUID = 1L;
	public static final String NAME = "shopping-cart";
	private ShoppingCartPresenter presenter;
	
	private VerticalLayout productsLayout;
	private Label title;
	private Label totalToPayLabel;
	private Button checkOutButton;
	private ViewWrapper wrapper;
	
	public ShoppingCartView(){
		buildUI();
		bindEvents();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Shopping cart - Online Store Retailer");
	}
	
	private void buildUI(){
	
		// Title
		title = new Label("<h1>Shopping Cart</h1>", ContentMode.HTML);
		
		// Total to pay
		totalToPayLabel = new Label(OSRUtilities.formatCurrency("0"));
		
		// CheckOut button
		checkOutButton = new Button("Check Out", FontAwesome.CHECK_SQUARE_O);
		checkOutButton.setStyleName("primary right");
		
		// Layout configuration
		initProductsLayout();
		
		wrapper = new ViewWrapper(productsLayout);
		addComponent(wrapper);
	}
	
	private void initProductsLayout(){
		productsLayout = new VerticalLayout();
		productsLayout.setStyleName("content-layout shopping-cart");
	}
	
	private void buildProductsLayout(Set<ShoppingCartProductSummary> productSummaries){
		VerticalLayout oldLayout = productsLayout;
		initProductsLayout();
		
		// Page title
		productsLayout.addComponent(title);
		productsLayout.addComponent(new Label("<hr />", ContentMode.HTML));
				
		for (ShoppingCartProductSummary productSummary : productSummaries) {
			productsLayout.addComponent(productSummary);
		}
		
		productsLayout.addComponent(new Label("<hr />", ContentMode.HTML));
		productsLayout.addComponent(totalToPayLabel);
		productsLayout.addComponent(new Label("<hr />", ContentMode.HTML));
		
		// CheckOut button
		productsLayout.addComponent(checkOutButton);
		
		// Update the layout
		wrapper.replaceComponent(oldLayout, productsLayout);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (ShoppingCartPresenter) presenter;
	}

	@Override
	public void bindEvents() {
		checkOutButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				presenter.onCheckOut();
			}
		});
	}

	@Override
	public void reset() {
		if(productsLayout.getComponentCount() > 0){
			initProductsLayout();
		}else{
			productsLayout.addComponent(
					new Label("<br /><p>This shopping cart is empty</p><br />", 
							ContentMode.HTML));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setData(String key, Object data) {
		if(key.equalsIgnoreCase("products")){
			buildProductsLayout((Set<ShoppingCartProductSummary>) data);
		}if(key.equalsIgnoreCase("total")){
			
			String total = (String) data;
			Label oldTotalToPayLabel = totalToPayLabel;
			totalToPayLabel = new Label("Total: " + OSRUtilities.formatCurrency(total));
			
			productsLayout.replaceComponent(oldTotalToPayLabel, totalToPayLabel);
		}
	}

}
