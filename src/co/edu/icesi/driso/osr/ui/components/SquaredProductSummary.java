package co.edu.icesi.driso.osr.ui.components;

import java.io.File;

import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.SquaredProductPresenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.ui.views.ProductView;
import co.edu.icesi.driso.osr.util.OSRUtilities;

import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SquaredProductSummary extends CustomComponent implements ViewComponent<Integer> {

	private static final long serialVersionUID = 1L;
	private SquaredProductPresenter presenter;
	
	private VerticalLayout mainLayout;
	private CssLayout buttonsGroup;
	private final String[] product;
	private TextField quantityField;
	private Button addToCartButton;
	
	public SquaredProductSummary(String[] product){
		this.product = product;
		buildMainLayout();
		bindEvents();
		setCompositionRoot(mainLayout);
	}
	
	private void buildMainLayout(){
		mainLayout = new VerticalLayout();
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		mainLayout.setStyleName("squared-product-summary product-summary");
		
		buttonsGroup = new CssLayout();
		buttonsGroup.setWidth(100, Unit.PERCENTAGE);
		
		quantityField = new TextField();
		quantityField.setStyleName("small");
		quantityField.setMaxLength(3);
		quantityField.setValue("1");
		quantityField.setWidth(29, Unit.PERCENTAGE);
		quantityField.setConverter(Integer.class);
		quantityField.setConversionError(
				"Please specify a numeric value in the quantity field");
		
		addToCartButton = new Button("Add", FontAwesome.SHOPPING_CART);
		addToCartButton.setStyleName("small");
		addToCartButton.setWidth(66, Unit.PERCENTAGE);
		
		buttonsGroup.addComponent(quantityField);
		buttonsGroup.addComponent(addToCartButton);
		
		Label productName = 
				new Label("<h1><a href=\"#!" + ProductView.NAME + "/" + 
						product[0] + "\">" 
						+ product[1] + "</a></h1>", ContentMode.HTML);
		
		Label productPrice = 
				new Label("<h3>" + OSRUtilities.formatCurrency(product[4]) + "</h3>", 
						ContentMode.HTML);
		
		Label lastProductPrice = 
				new Label("<span class=\"prev-price\"><strike>" + 
						OSRUtilities.formatCurrency(product[3]) + "</strike></span>", 
						ContentMode.HTML);
		
		FileResource imageResource = new FileResource(
				new File(OSRUtilities.getRealPathFor("/WEB-INF/images/thumbnails/" + 
						product[2])));
		Image productImage = new Image(null, imageResource);
		
		mainLayout.addComponent(productImage);
		mainLayout.addComponent(productName);
		mainLayout.addComponent(productPrice);
		
		if(!product[3].equals(product[4])){
			productPrice.setStyleName("highlighted");
		}else{
			lastProductPrice = new Label("<span class=\"prev-price\">&nbsp;</span>",
					ContentMode.HTML);
		}
		
		mainLayout.addComponent(lastProductPrice);
		mainLayout.addComponent(buttonsGroup);
	}
	
	@Override
	public void bindEvents(){
		
		addToCartButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Integer intQuantity = null;
				try {
					intQuantity = (Integer) quantityField.getConvertedValue();
					if(intQuantity > 0){
						
						presenter.onAddingToCart(Integer.parseInt(product[0]), intQuantity);
						Notification.show("Yay!", 
								"This product was succesfully added to your shopping cart",
								Notification.Type.HUMANIZED_MESSAGE);
					}else{
						Notification.show("Oops...", 
								"You must add at least one product", 
								Notification.Type.ERROR_MESSAGE);
					}
				}catch(ConversionException e){
					Notification.show("Oops...", 
							"Please specify a numeric value in the quantity field", 
							Notification.Type.ERROR_MESSAGE);
				}
			}
		});
		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (SquaredProductPresenter) presenter;
	}

	@Override
	public void reset() {
		quantityField.setValue("");
	}

	@Override
	public void setData(String key, Integer data) {
		if(key.equalsIgnoreCase("quantity")){
			quantityField.setValue(String.valueOf(data));
		}
	}
}
