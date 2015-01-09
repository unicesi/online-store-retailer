package co.edu.icesi.driso.osr.ui.components;


import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.SubscriptionPresenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.ui.views.client.TermsAndConditionsView;
import co.edu.icesi.driso.osr.util.RequiredCheckboxValidator;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SubscriptionForm extends CustomComponent implements ViewComponent<String> {

	private static final long serialVersionUID = 1L;
	private SubscriptionPresenter presenter;
	
	private VerticalLayout mainLayout;
	private FormLayout formLayout;
	private CssLayout secundaryLayout;
	private TextField emailTextField;
	private Button subscribeButton;
	private CssLayout conditionsCheckAndLink;
	private CheckBox conditionsCheckBox;
	private Link conditionsLink;

	public SubscriptionForm() {
		buildMainLayout();
		bindEvents();
		setCompositionRoot(formLayout);
	}

	private void buildMainLayout() {
		// Layout configuration
		mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);
		
		formLayout = new FormLayout();
		
		secundaryLayout = new CssLayout();
		secundaryLayout.setStyleName("v-component-group");
		
		conditionsCheckAndLink = new CssLayout();
		
		// Terms and conditions
		conditionsCheckBox = new CheckBox("I accept the ", false);
		conditionsCheckBox.addValidator(
				new RequiredCheckboxValidator(
						"You must accept our terms and conditions before "
						+ "subscribing to our email list"));
		conditionsCheckBox.setValidationVisible(false);
		conditionsCheckAndLink.addComponent(conditionsCheckBox);
		
		conditionsLink = new Link("terms and conditions", 
				new ExternalResource("#!" + TermsAndConditionsView.NAME));
		conditionsLink.setTargetName("_blank");
		conditionsLink.setId("terms-conditions-link");
		conditionsCheckAndLink.addComponent(conditionsLink);
		
		// TextBox for the user's email
		emailTextField = new TextField();
		emailTextField.setInputPrompt("Your email address");
		emailTextField.setColumns(26);
		emailTextField.addValidator(new EmailValidator("You need to provide a valid email"));
		emailTextField.setRequired(true);
		emailTextField.setRequiredError("Please provide your email");
		emailTextField.setValidationVisible(false);
		secundaryLayout.addComponent(emailTextField);
		
		// Subscribe button
		subscribeButton = new Button("Subscribe");
		subscribeButton.setStyleName("primary");
		secundaryLayout.addComponent(subscribeButton);
		
		// Main layout
		mainLayout.addComponent(secundaryLayout);
		mainLayout.addComponent(conditionsCheckAndLink);
		
		formLayout.addComponent(mainLayout);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (SubscriptionPresenter) presenter;
	}

	@Override
	public void bindEvents() {
		subscribeButton.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					
					emailTextField.validate();
					conditionsCheckBox.validate();
					
					presenter.onSubscribe(emailTextField.getValue());
					Notification.show("hooray!", 
							"You was successfully subscribed to our promotions and news",
							Notification.Type.HUMANIZED_MESSAGE);
					
				}catch(InvalidValueException e){
					emailTextField.setValidationVisible(true);
					emailTextField.markAsDirty();
					
					conditionsCheckBox.setValidationVisible(true);
					conditionsCheckBox.markAsDirty();
					
					Notification.show("Oops...", 
							"Please fix the fields marked in red",
							Notification.Type.ERROR_MESSAGE);
				}
			}
		});
	}

	@Override
	public void reset() {
		emailTextField.setValue("");
		conditionsCheckBox.setValue(false);
	}

	@Override
	public void setData(String key, String data) {
		if(key.equalsIgnoreCase("email")){
			emailTextField.setValue(data);
		}
	}

}
