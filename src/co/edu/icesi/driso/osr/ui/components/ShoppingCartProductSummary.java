package co.edu.icesi.driso.osr.ui.components;

import java.io.File;

import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.ShoppingCartProductPresenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.util.OSRUtilities;

import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class ShoppingCartProductSummary extends CustomComponent implements ViewComponent<Integer> {
	
	private static final long serialVersionUID = 1L;
	private ShoppingCartProductPresenter presenter;
	
	private HorizontalLayout mainLayout;
	private final String[] product;
	private CssLayout quantityLayout;
	private TextField quantityField;
	private Button updateQuantityButton;
	private Button deleteButton;
	
	
	public ShoppingCartProductSummary(String[] product){
		this.product = product;
		
		buildMainLayout();
		bindEvents();
		setCompositionRoot(mainLayout);
	}
	
	public void buildMainLayout(){
		// Layout configuration
		mainLayout = new HorizontalLayout();
		mainLayout.setWidth(90, Unit.PERCENTAGE);
		mainLayout.setStyleName("margin-centered");
		
		quantityLayout = new CssLayout();
		quantityLayout.setStyleName("v-component-group");
		
		// Product image
		FileResource imageResource = new FileResource(new File(
				OSRUtilities.getRealPathFor("/WEB-INF/images/thumbnails/"
						+ product[2])));
		Image productImage = new Image(null, imageResource);
		
		// Quantity field and refresh button
		quantityField = new TextField("Quantity");
		quantityField.setStyleName("small");
		quantityField.setMaxLength(3);
		quantityField.setConverter(Integer.class);
		quantityField.setConversionError(
				"Please specify a numeric value in the quantity field");
		
		updateQuantityButton = new Button(FontAwesome.REFRESH);
		updateQuantityButton.setStyleName("small");
		
		quantityLayout.addComponent(quantityField);
		quantityLayout.addComponent(updateQuantityButton);
		
		// Delete button
		deleteButton = new Button("Remove", FontAwesome.TRASH_O);
		deleteButton.setStyleName("small");
		
		// Main layout components
		mainLayout.addComponent(productImage);
		mainLayout.addComponent(quantityLayout);
		mainLayout.addComponent(deleteButton);
	}
	
	public String[] getProduct(){
		return product;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (ShoppingCartProductPresenter) presenter;
	}

	@Override
	public void bindEvents() {
		updateQuantityButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Integer intQuantity = null;
				try {
					intQuantity = (Integer) quantityField.getConvertedValue();
					if (intQuantity > 0) {
						
						presenter.onQuantityChange(Integer.parseInt(product[0]),
								intQuantity);
						Notification
								.show("Yay!",
										"This product was succesfully updated",
										Notification.Type.HUMANIZED_MESSAGE);
					} else {
						Notification.show("Oops...",
								"You must buy at least one product",
								Notification.Type.ERROR_MESSAGE);
					}
				} catch (ConversionException e) {
					Notification
							.show("Oops...",
									quantityField.getConversionError(),
									Notification.Type.ERROR_MESSAGE);
				}
			}
		});
		
		deleteButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
			}
		});
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
