package co.edu.icesi.driso.osr.ui.components;


import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.SignUpPresenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.views.HomeView;
import co.edu.icesi.driso.osr.util.OSRException;
import co.edu.icesi.driso.osr.util.PasswordConfirmationValidator;

import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SignUpForm extends CustomComponent implements ViewComponent<String> {
	
	private static final long serialVersionUID = 1L;
	private SignUpPresenter presenter;
	
	private VerticalLayout mainLayout;
	private FormLayout personalInfoLayout;
	private FormLayout accountInfoLayout;
	
	private TextField namesTextField;
	private TextField surnamesTextField;
	
	private OptionGroup genderOptionGroup;
	IndexedContainer genderContainer;
	
	private OptionGroup idDocumentType;
	IndexedContainer documentTypeContainer;
	
	private TextField idNumberTextField;
	private TextField emailTextField;
	private PasswordField passwordField;
	private PasswordField passwordConfirmationField;
	private Button signUpButton;
	
	public SignUpForm() {
		buildMainLayout();
		bindEvents();
		setCompositionRoot(mainLayout);
	}
	
	@SuppressWarnings("unchecked")
	private void buildMainLayout(){
		// Layout configuration
		mainLayout = new VerticalLayout();
		mainLayout.setStyleName("content-layout");
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		
		personalInfoLayout = new FormLayout();
		personalInfoLayout.setWidth(50, Unit.PERCENTAGE);
				
		accountInfoLayout = new FormLayout();
		accountInfoLayout.setWidth(50, Unit.PERCENTAGE);
		
		// Title
		Label pageTitle = new Label("<h1>Client Registration</h1>", ContentMode.HTML);	
		pageTitle.setWidth(100, Unit.PERCENTAGE);
		
		// Form elements
		// Personal data
		Label personalInfo = new Label("<h2>Personal Information</h2>", ContentMode.HTML);
		personalInfo.setStyleName("colored");
		personalInfo.setWidth(100, Unit.PERCENTAGE);
				
		namesTextField = new TextField("Names");
		// TODO: verify max length
		namesTextField.setMaxLength(50);
		namesTextField.setRequired(true);
		namesTextField.setRequiredError("Please provide your first and middle name (if applicable)");
		namesTextField.setValidationVisible(false);
		namesTextField.focus();
		personalInfoLayout.addComponent(namesTextField);
		
		surnamesTextField = new TextField("Surnames");
		// TODO: verify max length
		surnamesTextField.setMaxLength(50);
		surnamesTextField.setRequired(true);
		surnamesTextField.setRequiredError("Please provide your surnames");
		surnamesTextField.setValidationVisible(false);
		personalInfoLayout.addComponent(surnamesTextField);
		
		// Genre
		genderOptionGroup = new OptionGroup("Gender");
		genderOptionGroup.setRequired(true);
		genderOptionGroup.setRequiredError("You have to specify your genre");
		genderOptionGroup.setStyleName("horizontal");
		genderOptionGroup.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		genderOptionGroup.setItemCaptionPropertyId("name");
		
		genderContainer = new IndexedContainer();
		genderContainer.addContainerProperty("id", String.class, "");
		genderContainer.addContainerProperty("name", String.class, "");
		
		Item male = genderContainer.getItem(genderContainer.addItem());
		male.getItemProperty("id").setValue("0");
		male.getItemProperty("name").setValue("Male");
		
		Item female = genderContainer.getItem(genderContainer.addItem());
		female.getItemProperty("id").setValue("1");
		female.getItemProperty("name").setValue("Female");
		
		genderOptionGroup.setContainerDataSource(genderContainer);
		genderOptionGroup.select(genderContainer.firstItemId());
		personalInfoLayout.addComponent(genderOptionGroup);
		
		idNumberTextField = new TextField("Identification Number");
		idNumberTextField.setRequired(true);
		idNumberTextField.setRequiredError("Please provide your identification document number");
		idNumberTextField.setValidationVisible(false);
		personalInfoLayout.addComponent(idNumberTextField);
		
		// ID Document type
		idDocumentType = new OptionGroup("ID type");
		idDocumentType.setStyleName("horizontal");
		idDocumentType.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		idDocumentType.setItemCaptionPropertyId("name");
		idDocumentType.setRequired(true);
		idDocumentType.setRequiredError("You have to specify your identification document type");
		idDocumentType.setValidationVisible(false);
		
		documentTypeContainer = new IndexedContainer();
		documentTypeContainer.addContainerProperty("id", String.class, "");
		documentTypeContainer.addContainerProperty("name", String.class, "");
		
		Item idCard = documentTypeContainer.getItem(documentTypeContainer.addItem());
		idCard.getItemProperty("id").setValue("0");
		idCard.getItemProperty("name").setValue("ID Card");
		
		Item passport = documentTypeContainer.getItem(documentTypeContainer.addItem());
		passport.getItemProperty("id").setValue("1");
		passport.getItemProperty("name").setValue("Passport");
		
		idDocumentType.setContainerDataSource(documentTypeContainer);
		idDocumentType.select(documentTypeContainer.firstItemId());
		personalInfoLayout.addComponent(idDocumentType);
		
		// Acccount Info
		Label accountInfo = new Label("<h2>Account Information</h2>", ContentMode.HTML);
		accountInfo.setStyleName("colored");
		accountInfo.setWidth(100, Unit.PERCENTAGE);
		
		// Email
		emailTextField = new TextField("Email");
		// TODO: verify max length
		emailTextField.setMaxLength(50);
		emailTextField.setRequired(true);
		emailTextField.setRequiredError("Please provide your email");
		emailTextField.addValidator(new EmailValidator("Please provide a valid email"));
		emailTextField.setValidationVisible(false);
		accountInfoLayout.addComponent(emailTextField);
		
		// Password
		passwordField = new PasswordField("Password");
		passwordField.setIcon(FontAwesome.LOCK);
		// TODO: verify max length
		passwordField.setMaxLength(20);
		passwordField.setRequired(true);
		passwordField.setRequiredError("Please provide a password for your account");
		passwordField.setValidationVisible(false);
		accountInfoLayout.addComponent(passwordField);
		
		passwordConfirmationField = new PasswordField("Password confirmation");
		passwordConfirmationField.setIcon(FontAwesome.LOCK);
		// TODO: verify max length
		passwordConfirmationField.setMaxLength(50);
		passwordConfirmationField.setRequired(true);
		passwordConfirmationField.setRequiredError("You need to confirm your password");
		passwordConfirmationField.setValidationVisible(false);
		passwordConfirmationField.addValidator(
				new PasswordConfirmationValidator(passwordField, 
						"The password and password confirmation do not match"));
		
		accountInfoLayout.addComponent(passwordConfirmationField);
		
		// Sign up button!
		signUpButton = new Button("Sign Up");
		signUpButton.setWidth(140, Unit.PIXELS);
		signUpButton.setStyleName("primary right");
		
		signUpButton.setId("submit-form-button");
		accountInfoLayout.addComponent(signUpButton);
		
		mainLayout.addComponent(pageTitle);
		mainLayout.addComponent(personalInfo);
		mainLayout.addComponent(personalInfoLayout);
		mainLayout.addComponent(accountInfo);
		mainLayout.addComponent(accountInfoLayout);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (SignUpPresenter) presenter;
	}

	@Override
	public void bindEvents() {
		signUpButton.addClickListener(new Button.ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try{
					namesTextField.validate();
					surnamesTextField.validate();
					idNumberTextField.validate();
					emailTextField.validate();
					passwordField.validate();
					passwordConfirmationField.validate();
					
					boolean r;
					try {
						r = presenter.onSignUp(
								namesTextField.getValue(), 
								surnamesTextField.getValue(), 
								idNumberTextField.getValue(), 
								emailTextField.getValue(), 
								passwordField.getValue(),
								(Integer) genderOptionGroup.getValue(),
								(Integer) idDocumentType.getValue());
						
						if(r)
							Application.navigator.navigateTo(HomeView.NAME);
						
					} catch (OSRException e) {
						Notification.show("Sign up error", 
								e.getMessage(), 
								Notification.Type.ERROR_MESSAGE);
					}
					
				}catch(InvalidValueException e){
					namesTextField.setValidationVisible(true);
					namesTextField.markAsDirty();
					
					surnamesTextField.setValidationVisible(true);
					surnamesTextField.markAsDirty();
					
					idNumberTextField.setValidationVisible(true);
					idNumberTextField.markAsDirty();
					
					emailTextField.setValidationVisible(true);
					emailTextField.markAsDirty();
					
					passwordField.setValidationVisible(true);
					passwordField.markAsDirty();
					
					passwordConfirmationField.setValidationVisible(true);
					passwordConfirmationField.markAsDirty();
					
					Notification.show("Oops...", 
							"Please fix the fields marked in red", 
							Notification.Type.ERROR_MESSAGE);
				}
			}
		});
	}

	@Override
	public void reset() {
		namesTextField.setValue("");
		surnamesTextField.setValue("");
		idNumberTextField.setValue("");
		emailTextField.setValue("");
		passwordField.setValue("");
		passwordConfirmationField.setValue("");
		
		genderOptionGroup.select(genderContainer.firstItemId());
		idDocumentType.select(documentTypeContainer.firstItemId());
	}

	@Override
	public void setData(String key, String data) {
		if(key.equalsIgnoreCase("names")){
			namesTextField.setValue(data);
		}else if(key.equalsIgnoreCase("surnames")){
			surnamesTextField.setValue(data);
		}else if(key.equalsIgnoreCase("idnumber")){
			idNumberTextField.setValue(data);
		}else if(key.equalsIgnoreCase("email")){
			emailTextField.setValue(data);
		}else if(key.equalsIgnoreCase("password")){
			passwordField.setValue(data);
		}else if(key.equalsIgnoreCase("passwordconfirmation")){
			passwordConfirmationField.setValue(data);
		}
	}

}
