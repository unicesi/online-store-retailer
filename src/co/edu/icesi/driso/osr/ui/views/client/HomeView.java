package co.edu.icesi.driso.osr.ui.views.client;


import co.edu.icesi.driso.osr.ui.components.CategoriesSummary;
import co.edu.icesi.driso.osr.ui.components.FeaturedProducts;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.VerticalLayout;

public class HomeView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "index";
	private ViewWrapper wrapper;

	public HomeView(){
		buildUI();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Home - Online Store Retailer");
	}
	
	public void buildUI(){
		// Top selling products showcase
		FeaturedProducts featuredProducts = new FeaturedProducts();
						
		// Top selling categories showcase
		CategoriesSummary categories1 = 
				new CategoriesSummary(CategoriesSummary.CategoriesPanelType.HALF_SIZE_ITEM, 
						CategoriesSummary.CategoriesPanelStyle.RUBY);
		CategoriesSummary categories2 = 
				new CategoriesSummary(CategoriesSummary.CategoriesPanelType.THIRD_SIZE_ITEM, 
						CategoriesSummary.CategoriesPanelStyle.MARLEY);
				
		wrapper = new ViewWrapper(featuredProducts, categories1, categories2);
		addComponent(wrapper);
	}
}
