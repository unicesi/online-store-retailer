package co.edu.icesi.driso.osr.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.VerticalLayout;

public class ShoppingCartView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "shopping-cart";
	private ViewWrapper wrapper;
	
	public ShoppingCartView(){
		buildUI();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Shopping cart - Online Store Retailer");
	}
	
	private void buildUI(){
		
		
		wrapper = new ViewWrapper();
		addComponent(wrapper);
	}

}
