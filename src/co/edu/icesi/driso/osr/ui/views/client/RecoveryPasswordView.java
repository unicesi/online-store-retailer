package co.edu.icesi.driso.osr.ui.views.client;


import co.edu.icesi.driso.osr.ui.components.RecoveryPasswordForm;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class RecoveryPasswordView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "recover";
	private ViewWrapper wrapper;
	private VerticalLayout contentLayout;
	private Label titleLabel;
	private Label descriptionLabel;
	private RecoveryPasswordForm recoveryForm;

	public RecoveryPasswordView(){
		buildUI();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Password recovery - Online Store Retailer");
	}
	
	public void buildUI(){
		// Layout configuration
		contentLayout = new VerticalLayout();
		contentLayout.setStyleName("content-layout");
		contentLayout.setWidth(100, Unit.PERCENTAGE);
		contentLayout.setMargin(true);
		contentLayout.setSpacing(true);
		
		recoveryForm = new RecoveryPasswordForm();
		
		// Form title and description
		titleLabel = new Label("<h1>Password Recovery</h1>", ContentMode.HTML);
		descriptionLabel = new Label("<p>Please specify the email attached to " +
				"your account. Once you press button \"Recover\" you will " +
				"receive an email containing a new password, then you will be " +
				"able to change it whenever you want.</p>", ContentMode.HTML);
				
		// Page's content
		contentLayout.addComponent(titleLabel);
		contentLayout.addComponent(descriptionLabel);
		contentLayout.addComponent(recoveryForm);
		
		// View wrapper
		wrapper = new ViewWrapper(contentLayout);
		addComponent(wrapper);
	}
	
}
