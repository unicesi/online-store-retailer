package co.edu.icesi.driso.osr.ui.components;

import co.edu.icesi.driso.osr.presenters.SignInPresenter;
import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.ui.views.RecoveryPasswordView;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SignInForm implements PopupView.Content, ViewComponent<String> {
	
	private static final long serialVersionUID = 1L;
	private SignInPresenter presenter;
	
	private VerticalLayout mainLayout;
	private FormLayout formLayout;
	private TextField emailTextField;
	private PasswordField passwordField;
	private CssLayout actionsLayout;
	private Link forgotPasswordLink;
	private Button loginButton;
	
	public SignInForm() {
		buildMainLayout();
		bindEvents();
	}
	
	private void buildMainLayout(){	
		mainLayout = new VerticalLayout();
		mainLayout.setSizeUndefined();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		
		formLayout = new FormLayout();
		formLayout.setSizeUndefined();
		
		actionsLayout = new CssLayout();
		actionsLayout.setWidth(100, Unit.PERCENTAGE);
				
		emailTextField = new TextField("Email");
		// TODO: verify max length
		emailTextField.setMaxLength(50);
		emailTextField.addValidator(new EmailValidator("You need to provide a valid email"));
		emailTextField.setRequired(true);
		emailTextField.setRequiredError("You need to provide your email");
		emailTextField.setValidationVisible(false);
		emailTextField.focus();
		
		passwordField = new PasswordField("Password");
		// TODO: verify max length
		passwordField.setMaxLength(20);
		passwordField.setRequired(true);
		passwordField.setRequiredError("Please specify your password");
		passwordField.setValidationVisible(false);
		passwordField.setIcon(FontAwesome.LOCK);
		
		forgotPasswordLink = 
				new Link("Forgot your password?", 
						new ExternalResource("#!" + RecoveryPasswordView.NAME));
		forgotPasswordLink.setStyleName("small-font");
		forgotPasswordLink.setWidth(50, Unit.PERCENTAGE);
		actionsLayout.addComponent(forgotPasswordLink);
		
		loginButton = new Button("Sign in");
		loginButton.setWidth(50, Unit.PERCENTAGE);
		actionsLayout.addComponent(loginButton);
		
		formLayout.addComponent(emailTextField);
		formLayout.addComponent(passwordField);
		
		mainLayout.addComponent(formLayout);
		mainLayout.addComponent(actionsLayout);
	}

	@Override
	public String getMinimizedValueAsHTML() {
		return "<div class=\"v-button v-widget\" tabindex=\"1\" role=\"button\" "
				+ "style=\"border-top-right-radius: 3px; border-bottom-right-radius: 3px; \">"
				+ "<span class=\"v-button-wrap\">"
				+ "<span class=\"v-button-caption\">Sign in</span>"
				+ "</span>"
				+ "</div>";
	}

	@Override
	public Component getPopupComponent() {
		return mainLayout;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (SignInPresenter) presenter;
	}

	@Override
	public void bindEvents() {
		
		loginButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try{
					emailTextField.validate();
					passwordField.validate();
					
					boolean r = 
							presenter.onLogin(
									emailTextField.getValue(), 
									passwordField.getValue());
					if(r){
						Page.getCurrent().reload();
					}else{
						Notification.show(
							"Login error", 
							"The email-password combination is not correct", 
							Notification.Type.ERROR_MESSAGE);
					}
					
				}catch(InvalidValueException e){
					emailTextField.setValidationVisible(true);
					emailTextField.markAsDirty();
					passwordField.setValidationVisible(true);;
					passwordField.markAsDirty();
					Notification.show(
							"Oops...", 
							"Please fix the fileds marked in red", 
							Notification.Type.ERROR_MESSAGE);
				}
			}
		});
		
	}

	@Override
	public void reset() {
		emailTextField.setValue("");
		passwordField.setValue("");
	}

	@Override
	public void setData(String key, String data) {
		if(key.equalsIgnoreCase("email")){
			emailTextField.setValue(data);
		}else if(key.equalsIgnoreCase("password")){
			passwordField.setValue(data);
		}
	}

}
