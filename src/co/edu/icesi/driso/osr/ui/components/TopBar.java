package co.edu.icesi.driso.osr.ui.components;

import co.edu.icesi.driso.osr.presenters.SearchPresenter;
import co.edu.icesi.driso.osr.presenters.ShoppingCartPresenter;
import co.edu.icesi.driso.osr.presenters.SignInPresenter;
import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.views.HomeView;
import co.edu.icesi.driso.osr.ui.views.ShoppingCartView;
import co.edu.icesi.driso.osr.ui.views.SignUpView;
import co.edu.icesi.driso.osr.util.OSRUtilities;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.PopupView;

public class TopBar extends CustomComponent implements ShoppingCartPresenter.Collaborator {

	private static final long serialVersionUID = 1L;
	private HorizontalLayout wrapper;
	private HorizontalLayout mainLayout;
	private CssLayout shoppingCartLayout;
	private CssLayout signInUpLayout;
	private CssLayout leftElementsLayout;
	private CssLayout rightElementsLayout;
	private Link logo;
	
	private SearchBar searchBar;
	
	private Button shoppingCart;
	private Button checkOut;
	private PopupView loginPopup;
	private Button signup;
	private SignInForm loginForm;

	public TopBar() {
		buildMainLayout();
		assignPresenters();
		bindEvents();
		setCompositionRoot(wrapper);
	}

	private void buildMainLayout() {
		
		wrapper = new HorizontalLayout();
		wrapper.setWidth(100, Unit.PERCENTAGE);
		wrapper.setStyleName("top-bar-wrapper");
		
		mainLayout = new HorizontalLayout();
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		mainLayout.setWidth(Application.pageWidth, Application.pageWidthUnit);
		
		leftElementsLayout = new CssLayout();
		rightElementsLayout = new CssLayout();
		
		shoppingCartLayout = new CssLayout();
		shoppingCartLayout.setStyleName("v-component-group");
		rightElementsLayout.addComponent(shoppingCartLayout);
		
		signInUpLayout = new CssLayout();
		signInUpLayout.setStyleName("v-component-group small-left-padding");
		rightElementsLayout.addComponent(signInUpLayout);
		
		// Logo
		logo = new Link(null, new ExternalResource("#!" + HomeView.NAME));
		logo.setTargetName("_self");
		logo.setIcon(new ThemeResource("images/logo.png"));
		leftElementsLayout.addComponent(logo);
		
		// Search bar
		searchBar = new SearchBar();
		searchBar.setStyleName("small-left-padding one-pixel-top-margin");
		searchBar.setWidth(90, Unit.PERCENTAGE);
		leftElementsLayout.addComponent(searchBar);
		
		// Shopping cart
		shoppingCart = new Button("", FontAwesome.SHOPPING_CART);
		shoppingCart.setStyleName("v-menubar-menuitem-icon-only");
		shoppingCartLayout.addComponent(shoppingCart);
		
		checkOut = new Button("");
		checkOut.setEnabled(false);
		shoppingCartLayout.addComponent(checkOut);
				
		// Sign up & Sign in	
		signup = new Button("Sign up");
		signup.setStyleName("primary");
		signInUpLayout.addComponent(signup);
		
		// Login form
		loginForm = new SignInForm();
		loginPopup = new PopupView(loginForm);
		loginPopup.setHideOnMouseOut(false);
		signInUpLayout.addComponent(loginPopup);

		// Wrapper & mainLayout
		mainLayout.addComponent(leftElementsLayout);
		mainLayout.setComponentAlignment(leftElementsLayout, Alignment.MIDDLE_LEFT);
		
		mainLayout.addComponent(rightElementsLayout);
		mainLayout.setComponentAlignment(rightElementsLayout, Alignment.MIDDLE_RIGHT);
		
		wrapper.addComponent(mainLayout);
		wrapper.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);
	}
	
	private void assignPresenters(){
		
		// Search presenter
		SearchPresenter searchPresenter = new SearchPresenter(searchBar);
		searchPresenter.init();
		
		// Login form presenter
		SignInPresenter loginPresenter = new SignInPresenter(loginForm);
		loginPresenter.init();
	}
	
	private void bindEvents(){
		shoppingCart.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Application.navigator.navigateTo(ShoppingCartView.NAME);
			}
		});
		
		signup.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Application.navigator.navigateTo(SignUpView.NAME);
			}
		});
	}

	@Override
	public void onShoppingCartUpdated(double totalToPay) {
		checkOut.setCaption(OSRUtilities.formatCurrency(String.valueOf(totalToPay)));
	}

}
