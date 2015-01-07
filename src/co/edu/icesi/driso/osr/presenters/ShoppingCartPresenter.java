package co.edu.icesi.driso.osr.presenters;

import java.util.ArrayList;
import java.util.List;

import co.edu.icesi.driso.osr.ui.components.ShoppingCartProductSummary;
import co.edu.icesi.driso.osr.ui.views.ShoppingCartView;

public class ShoppingCartPresenter implements Presenter {
	
	private final ShoppingCartView viewComponent;
	
	public ShoppingCartPresenter(ShoppingCartView viewComponent){
		this.viewComponent = viewComponent;
	}
	
	public void onCheckOut(){
		
	}
	
	@Override
	public void init() {
		viewComponent.setPresenter(this);
		viewComponent.reset();
		
		// TODO
		// Perform query (active session) to retrieve the shopping cart items
		List<ShoppingCartProductSummary> items = new ArrayList<ShoppingCartProductSummary>();
		
		ShoppingCartProductSummary a = new ShoppingCartProductSummary(getProductInformation(1));
		items.add(a);
		
		viewComponent.setData("products", items);
	}
	
	private String[] getProductInformation(int productId){
		return new String[]{
				"Sony Xperia Z3", 
				"It’s the details that make the difference " +
				"between a good smartphone and a great one. " +
				"Our premium Xperia™ smartphones and tablets " +
				"bring together the best of Sony technologies, " +
				"crafted using the finest quality materials in " +
				"a waterproof body, for a design that can " +
				"withstand the test of time. So don’t settle " +
				"for good when you can have great.", 
				"545000",
				"599000",
				"htc_one.png"
		};
	}

}
