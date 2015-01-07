package co.edu.icesi.driso.osr.ui;


import co.edu.icesi.driso.osr.presenters.ShoppingCartPresenter;
import co.edu.icesi.driso.osr.ui.views.CategoryView;
import co.edu.icesi.driso.osr.ui.views.ErrorView;
import co.edu.icesi.driso.osr.ui.views.HomeView;
import co.edu.icesi.driso.osr.ui.views.ProductView;
import co.edu.icesi.driso.osr.ui.views.RecoveryPasswordView;
import co.edu.icesi.driso.osr.ui.views.SearchResultsView;
import co.edu.icesi.driso.osr.ui.views.ShoppingCartView;
import co.edu.icesi.driso.osr.ui.views.SignUpView;
import co.edu.icesi.driso.osr.ui.views.TermsAndConditionsView;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("onlinestoreretailer")
@PreserveOnRefresh
@Title("Online Store Retailer")
public class Application extends UI {
	
	private static final long serialVersionUID = 1L;
	public static ShoppingCartPresenter shoppingCartPresenter;

	// page width configuration
	public static final int pageWidth = 960;
	public static final Unit pageWidthUnit = Unit.PIXELS;
	
	public static final String ABOUT_VIEW = "about";
	public static Navigator navigator;
	
	public Application(){
		// Create a navigator to control the application views
		navigator = new Navigator(this, this);
		navigator.setErrorView(new ErrorView());
		
		ShoppingCartView shoppingCartView = new ShoppingCartView();
		shoppingCartPresenter = new ShoppingCartPresenter(shoppingCartView);
		shoppingCartPresenter.init();
		
		// Create and register the application views
		// To create a new instance each time: navigator.addView(CountView.NAME, CountView.class);
		navigator.addView("", new HomeView());
		navigator.addView(HomeView.NAME, new HomeView());
		navigator.addView(SignUpView.NAME, new SignUpView());
		navigator.addView(RecoveryPasswordView.NAME, new RecoveryPasswordView());
		navigator.addView(CategoryView.NAME, new CategoryView());
		navigator.addView(ProductView.NAME, ProductView.class);
		navigator.addView(TermsAndConditionsView.NAME, TermsAndConditionsView.class);
		navigator.addView(ShoppingCartView.NAME, shoppingCartView);
		navigator.addView(SearchResultsView.NAME, SearchResultsView.class);
	}

	@Override
	protected void init(VaadinRequest request) {	
		
	}

}