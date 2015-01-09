package co.edu.icesi.driso.osr.ui.components;


import co.edu.icesi.driso.osr.presenters.SubscriptionPresenter;
import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.views.client.HomeView;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;

public class Footer extends CustomComponent {

	private static final long serialVersionUID = 1L;
	private HorizontalLayout wrapper;
	private HorizontalLayout mainLayout;
	private CssLayout linksLayout;
	private CssLayout internalLinksLayout;
	private CssLayout externalLinksLayout;
	private SubscriptionForm subscriptionForm;

	public Footer() {
		buildMainLayout();
		assignPresenters();
		setCompositionRoot(wrapper);
	}

	private void buildMainLayout() {
		// Layouts configuration
		wrapper = new HorizontalLayout();
		wrapper.setWidth(100, Unit.PERCENTAGE);
		wrapper.setStyleName("footer-wrapper");
		
		mainLayout = new HorizontalLayout();
		mainLayout.setWidth(Application.pageWidth, Application.pageWidthUnit);
		
		linksLayout = new CssLayout();
		
		internalLinksLayout = new CssLayout();
		internalLinksLayout.setStyleName("footer-links");
		
		externalLinksLayout = new CssLayout();
		externalLinksLayout.setStyleName("footer-links");
		
		// Internal links
		Link homeLink = new Link("Home", new ExternalResource("#!" + HomeView.NAME));
		homeLink.setIcon(FontAwesome.HOME);
		homeLink.setTargetName("_self");
		internalLinksLayout.addComponent(homeLink);
		
		Link aboutLink = new Link("About the Online Store Retailer", 
				new ExternalResource("#!" + Application.ABOUT_VIEW));
		aboutLink.setIcon(FontAwesome.INFO_CIRCLE);
		aboutLink.setTargetName("_self");
		internalLinksLayout.addComponent(aboutLink);
		
		// External links
		Link icesiLink = new Link("Icesi University", 
				new ExternalResource("http://www.icesi.edu.co/"));
		icesiLink.setIcon(FontAwesome.UNIVERSITY);
		icesiLink.setTargetName("_blank");
		externalLinksLayout.addComponent(icesiLink);
		
		Link DRISOLink = new Link("DRISO", 
				new ExternalResource("http://www.icesi.edu.co/i2t/driso/"));
		DRISOLink.setIcon(FontAwesome.EXTERNAL_LINK_SQUARE);
		DRISOLink.setTargetName("_blank");
		externalLinksLayout.addComponent(DRISOLink);
		
		Link SHIFTLink = new Link("SHIFT", new ExternalResource("#"));
		SHIFTLink.setIcon(FontAwesome.EXTERNAL_LINK_SQUARE);
		SHIFTLink.setTargetName("_blank");
		externalLinksLayout.addComponent(SHIFTLink);
		
		linksLayout.addComponent(internalLinksLayout);
		linksLayout.addComponent(externalLinksLayout);
		mainLayout.addComponent(linksLayout);
		
		// Subscription form
		subscriptionForm = new SubscriptionForm();
		subscriptionForm.setStyleName("subscription-form");
		mainLayout.addComponent(subscriptionForm);
		mainLayout.setComponentAlignment(subscriptionForm, Alignment.MIDDLE_RIGHT);
		
		// Wrapper & mainLayout
		wrapper.addComponent(mainLayout);
		wrapper.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);
	}
	
	private void assignPresenters(){
		// Subscription presenter
		SubscriptionPresenter subscriptionPresenter = 
				new SubscriptionPresenter(subscriptionForm);
		subscriptionPresenter.init();
	}

}