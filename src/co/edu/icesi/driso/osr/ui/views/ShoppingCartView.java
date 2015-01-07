package co.edu.icesi.driso.osr.ui.views;

import java.util.List;

import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.ShoppingCartPresenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.ui.components.ShoppingCartProductSummary;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;

public class ShoppingCartView extends VerticalLayout implements View, ViewComponent<List<ShoppingCartProductSummary>> {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "shopping-cart";
	private ShoppingCartPresenter presenter;
	
	private VerticalLayout productsLayout;
	private Button checkOutButton;
	private ViewWrapper wrapper;
	
	public ShoppingCartView(){
		buildUI();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Shopping cart - Online Store Retailer");
	}
	
	private void buildUI(){
		// Layout configuration
		initProductsLayout();
		
		// CheckOut button
		checkOutButton = new Button("Check Out", FontAwesome.CHECK_SQUARE_O);
		
		wrapper = new ViewWrapper(productsLayout);
		addComponent(wrapper);
	}
	
	private void initProductsLayout(){
		productsLayout = new VerticalLayout();
		productsLayout.setWidth(100, Unit.PERCENTAGE);
	}
	
	private void buildProductsLayout(List<ShoppingCartProductSummary> productSummaries){
		for (ShoppingCartProductSummary productSummary : productSummaries) {
			productsLayout.addComponent(productSummary);
		}
		
		productsLayout.addComponent(checkOutButton);
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
		initProductsLayout();
	}

	@Override
	public void setData(String key, List<ShoppingCartProductSummary> data) {
		if(key.equalsIgnoreCase("products")){
			buildProductsLayout(data);
		}
	}

}
