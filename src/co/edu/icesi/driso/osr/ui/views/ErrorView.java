package co.edu.icesi.driso.osr.ui.views;

import co.edu.icesi.driso.osr.ui.views.client.ViewWrapper;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private ViewWrapper wrapper;

	public ErrorView(){
		buildUI();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Client Registration - Online Store Retailer");
	}
	
	public void buildUI(){
		
		setStyleName("content-layout");
		Label title = new Label("Ops... Something went wrong!", ContentMode.HTML);
		Label description = new Label("We are trying to resolve "
				+ "this error as soon as possible, please "
				+ "continue with your navigation in our "
				+ "site.", ContentMode.HTML);
		
		wrapper = new ViewWrapper(title, description);
		addComponent(wrapper);
	}

}
