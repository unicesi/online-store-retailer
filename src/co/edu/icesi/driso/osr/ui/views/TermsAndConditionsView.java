package co.edu.icesi.driso.osr.ui.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import co.edu.icesi.driso.osr.util.OSRUtilities;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class TermsAndConditionsView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	private final String termsAndConditionsFile = "WEB-INF/files/terms-and-conditions.txt";
	public static final String NAME = "conditions";
	private VerticalLayout contentLayout;
	private Label titleLabel;
	private Label subtitleLabel;
	private ViewWrapper wrapper;
	
	public TermsAndConditionsView(){
		buildUI();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}
	
	public void buildUI(){
		
		// Layout configuration
		contentLayout = new VerticalLayout();
		contentLayout.setStyleName("content-layout");
		
		titleLabel = new Label("<h1>Terms and Conditions</h1>", ContentMode.HTML);
		subtitleLabel = new Label("<h3>Each item in your order is sold by Amazon "
				+ "Export Sales, Inc. (\"Amazon Export\") or the merchant that the "
				+ "item is specified as sold by (\"Merchant\").</h3>", ContentMode.HTML);
		
		List<String> pageContent = new ArrayList<String>();
		
		try {
			pageContent = OSRUtilities.readFile(termsAndConditionsFile);
		} catch (IOException e) {
			Notification.show("Unknown error", "An unknown error has occurred, "
					+ "please reload the page. If this error continues "
					+ "contact the system administrator", 
					Notification.Type.ERROR_MESSAGE);
		}
		
		contentLayout.addComponent(titleLabel);
		contentLayout.addComponent(subtitleLabel);
		
		for (int i = 0; i < pageContent.size(); i++) {
			String elem = pageContent.get(i);
			Label elemLabel = null;
			
			if(elem.startsWith("**")){
				elemLabel = new Label("<p><b>" + elem.substring(2) + "</b></p>", ContentMode.HTML);
			}else{				
				elemLabel = new Label("<p>" + elem + "</p>", ContentMode.HTML);
			}
			
			contentLayout.addComponent(elemLabel);
		}
		
		wrapper = new ViewWrapper(contentLayout);
		addComponent(wrapper);
		
	}

}
