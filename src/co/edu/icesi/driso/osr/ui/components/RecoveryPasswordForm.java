package co.edu.icesi.driso.osr.ui.components;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RecoveryPasswordForm extends CustomComponent {

	private static final long serialVersionUID = 1L;
	private VerticalLayout mainLayout;
	private FormLayout recoveryForm;
	private TextField emailTextField;
	private Button recoveryButton;

	public RecoveryPasswordForm() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	private void buildMainLayout(){
		// Layout configuration
		mainLayout = new VerticalLayout();
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		
		recoveryForm = new FormLayout();
		recoveryForm.setStyleName("light");
		recoveryForm.setWidth(80, Unit.PERCENTAGE);

		// Form elements
		emailTextField = new TextField("Email");
		// TODO: Verify max length
		emailTextField.setInputPrompt("example@abc.com");
		emailTextField.setMaxLength(50);
		emailTextField.setRequired(true);
		emailTextField.setRequiredError("You need to provide your email");
		emailTextField.setWidth(80, Unit.PERCENTAGE);
		emailTextField.focus();

		recoveryButton = new Button("Recover", FontAwesome.LOCK);
		recoveryButton.setStyleName("primary right");
		recoveryButton.setWidth(20, Unit.PERCENTAGE);
		recoveryButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("Password Recovery", 
						"An email has been sent to you with a new password", 
						Notification.Type.ASSISTIVE_NOTIFICATION);
			}
		});

		// Form elements group
		recoveryForm.addComponent(emailTextField);
		
		mainLayout.addComponent(recoveryForm);
		mainLayout.addComponent(recoveryButton);
	}

}
