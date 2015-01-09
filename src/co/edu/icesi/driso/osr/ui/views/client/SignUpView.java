package co.edu.icesi.driso.osr.ui.views.client;


import co.edu.icesi.driso.osr.presenters.SignUpPresenter;
import co.edu.icesi.driso.osr.ui.components.FeaturedProducts;
import co.edu.icesi.driso.osr.ui.components.SignUpForm;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.VerticalLayout;

public class SignUpView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "signup";
	private ViewWrapper wrapper;
	
	private SignUpForm signUpForm;
	private FeaturedProducts featuredProducts;

	public SignUpView(){
		buildUI();
		assignPresenters();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Sign Up - Online Store Retailer");
	}
	
	public void buildUI(){
		// Registration form
		signUpForm = new SignUpForm();	
		
		// Top selling products showcase
		featuredProducts = new FeaturedProducts(false);
		
		wrapper = new ViewWrapper(signUpForm, featuredProducts);
		addComponent(wrapper);
	}
	
	public void assignPresenters(){
		// Sign up presenter
		SignUpPresenter signUpPresenter = new SignUpPresenter(signUpForm);
		signUpPresenter.init();
	}

}
