package co.edu.icesi.driso.osr.ui.components;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.vaadin.teemu.ratingstars.RatingStars;

import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.ProductPresenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.views.ProductView;
import co.edu.icesi.driso.osr.util.OSRException;
import co.edu.icesi.driso.osr.util.OSRUtilities;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ProductSummary extends CustomComponent implements ViewComponent<Double> {

	private static final long serialVersionUID = 1L;
	private ProductPresenter presenter; 
	private final String[] product;
	
	private CssLayout mainLayout;
	private VerticalLayout informationLayout;
	private CssLayout bottomOptionsLayout;
	private CssLayout linksLayout;
	
	private final boolean completeSummary;
	private Button seeProductButton;
	private CssLayout buttonsGroup;
	private TextField quantityField;
	private Button addToCartButton;
	private RatingStars ratingStars;
	private TabSheet productInfoTabs;
	private final int productImageWidth = 320;
	private final int productImagePadding = 20;
	private final int containerBorderWidth = 2;
	
	private ValueChangeListener ratingListener;

	public ProductSummary(String[] product, boolean completeSummary) {
		this.product = product;
		this.completeSummary = completeSummary;

		initialize();
		buildMainLayout();
		bindEvents();
		setCompositionRoot(mainLayout);
	}
	
	private void initialize(){
		ratingListener = new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				try {
					presenter.onProductRating(product, 
							(Double) event.getProperty().getValue());
					
					Notification.show("You just rated this product with: " 
							+ event.getProperty().getValue());
					
				} catch (OSRException e) {
					Notification.show(
							"Rating error", 
							e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
			}
		};
	}

	@SuppressWarnings("unchecked")
	private void buildMainLayout() {

		int calculatedProductImageWidth = 
				productImageWidth - productImagePadding;
		int calculatedInformationWidth = 
				Application.pageWidth - (calculatedProductImageWidth + containerBorderWidth);

		// Layouts configuration
		mainLayout = new CssLayout();
		mainLayout.setStyleName("product-view-container");
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		
		if(completeSummary)
			mainLayout.addStyleName("product-summary-complete");

		informationLayout = new VerticalLayout();
		informationLayout.setId("product-view-data-container");
		informationLayout.setWidth(calculatedInformationWidth, Unit.PIXELS);
		
		bottomOptionsLayout = new CssLayout();
		bottomOptionsLayout.setId("product-view-actions");

		linksLayout = new CssLayout();
		linksLayout.setStyleName("v-component-group");

		// Product's data: name, description, image
		String[] productData = OSRUtilities.getProductInformation(Integer.parseInt(product[0]));

		// Product's image
		FileResource imageFile = 
				new FileResource(
						new File(OSRUtilities.getRealPathFor("/WEB-INF/images/products/" 
								+ productData[5])));
		Image productImage = new Image(null, imageFile);
		productImage.setAlternateText(productData[1]);
		productImage.setWidth(calculatedProductImageWidth, Unit.PIXELS);

		// Product's data
		Label productName = 
				new Label("<a href=\"#!" + ProductView.NAME + "/" + product[0] + 
						"\"><h2>" + productData[1] + "</h2></a>", ContentMode.HTML);
		informationLayout.addComponent(productName);

		Label productDescription = 
				new Label("<p>" + productData[2] + "</p>", ContentMode.HTML);
		informationLayout.addComponent(productDescription);
		
		String productPriceStr = "<p>Price: " + OSRUtilities.formatCurrency(productData[3]);
		
		if(!productData[3].equals(productData[4])){
			productPriceStr += "<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ "&nbsp;&nbsp;&nbsp;<strike>" 
							+ OSRUtilities.formatCurrency(productData[4])
							+ "</strike></p>";
		}else{
			productPriceStr += "</p>";
		}
		
		Label productPrice = new Label(productPriceStr, ContentMode.HTML);
		informationLayout.addComponent(productPrice);
		
		// Actions
		seeProductButton = new Button("View", FontAwesome.ARROW_RIGHT);
		seeProductButton.setStyleName("icon-align-right");

		linksLayout.addComponent(seeProductButton);

		addToCartButton = new Button("Add to cart", FontAwesome.SHOPPING_CART);
		addToCartButton.setStyleName("primary");
		linksLayout.addComponent(addToCartButton);
		
		// Rating
		ratingStars = new RatingStars();
		ratingStars.setValue(4.5);
//		ratingStars.setReadOnly(true);
		ratingStars.setImmediate(true);
		ratingStars.setId("product-rating");
		
		quantityField = new TextField();
		quantityField.setId("quantity-field");
		quantityField.setMaxLength(3);
		quantityField.setValue("1");
		quantityField.setWidth(40, Unit.PIXELS);
		quantityField.setConverter(Integer.class);
		quantityField.setConversionError(
				"Please specify a numeric value in the quantity field");

		if(!completeSummary){
			bottomOptionsLayout.addComponent(ratingStars);
			bottomOptionsLayout.addComponent(linksLayout);
			
			informationLayout.addComponent(bottomOptionsLayout);
			informationLayout.setComponentAlignment(bottomOptionsLayout, Alignment.BOTTOM_RIGHT);
		}else{	
			CssLayout helperLayout = new CssLayout();
			helperLayout.setId("product-view-actions");
			
			buttonsGroup = new CssLayout();
			buttonsGroup.setStyleName("v-component-group");
			buttonsGroup.addComponent(quantityField);
			buttonsGroup.addComponent(addToCartButton);
			
			helperLayout.addComponent(ratingStars);
			helperLayout.addComponent(buttonsGroup);

			informationLayout.addComponent(helperLayout);
			informationLayout.setComponentAlignment(helperLayout, Alignment.BOTTOM_RIGHT);
		}

		// Tabs container for the product info
		productInfoTabs = new TabSheet();
		productInfoTabs.setId("product-characteristics-table");
		productInfoTabs.setWidth(95, Unit.PERCENTAGE);

		Table table = new Table("The Brightest Stars");
		table.setWidth(100, Unit.PERCENTAGE);
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Mag",  Float.class, null);

		// Add a row the hard way
		Object newItemId = table.addItem();
		Item row1 = table.getItem(newItemId);
		row1.getItemProperty("Name").setValue("Sirius");
		row1.getItemProperty("Mag").setValue(-1.46f);

		// Add a few other rows using shorthand addItem()
		table.addItem(new Object[]{"Canopus",        -0.72f}, 2);
		table.addItem(new Object[]{"Arcturus",       -0.04f}, 3);
		table.addItem(new Object[]{"Alpha Centauri", -0.01f}, 4);

		// Show exactly the currently contained rows (items)
		table.setPageLength(table.size());
		productInfoTabs.addTab(table, "Dimensions");

		// main layout
		mainLayout.addComponent(productImage);
		mainLayout.addComponent(informationLayout);

		if(completeSummary){
			mainLayout.addComponent(productInfoTabs);
			
			// Comments
			String[][] data = {
					{"Mark Smith", "4.5", "This is an amazing product! I bought it three weeks ago and I have had the best times using it."},
					{"Layla Lawrence", "5", "Best thing you can buy out there!"},
					{"Martin Scala", "2", "Very poor quality on photos and videos"},
					{"Johan Gray", "3.5", ""}
			};
			for (int i = 0; i < 4; i++) {
				VerticalLayout commentLayout = 
						getComment(data[i][0], Double.valueOf(data[i][1]), data[i][2]);
				mainLayout.addComponent(new Label("<hr />", ContentMode.HTML));
				mainLayout.addComponent(commentLayout);
			}
		}
	}
	
	private VerticalLayout getComment(String name, double ratingVal, String description){
		HorizontalLayout heading = new HorizontalLayout();

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		String day = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR);
		String hour = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE);
		
		RatingStars rating = new RatingStars();
		rating.setValue(ratingVal);
		rating.setReadOnly(true);
		
		Label nameAndDate = new Label(
				"<h3>" + name + " <abbr title=\"" + date.toString() + "\">" + 
				day + " at " + hour + "</abbr></h3>", ContentMode.HTML);
		
		Label descriptionLabel = new Label("<p>" + description + "</p>", ContentMode.HTML);
		
		heading.addComponent(rating);
		heading.addComponent(nameAndDate);
		
		VerticalLayout commentLayout = new VerticalLayout();
		commentLayout.setStyleName("product-comment");
		commentLayout.addComponent(heading);
		
		if(!description.isEmpty())
			commentLayout.addComponent(descriptionLabel);
		
		return commentLayout;
	}
	
	public String[] getProduct(){
		return product;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (ProductPresenter) presenter;
	}

	@Override
	public void bindEvents() {
		
		seeProductButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Application.navigator.navigateTo(ProductView.NAME + "/" + product[0]);
			}
		});
		
		addToCartButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Integer intQuantity = null;
				
				try {
					intQuantity = (Integer) quantityField.getConvertedValue();
					
					if(intQuantity > 0){
						
						presenter.onAddingToCart(product, intQuantity);
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
		
		ratingStars.addValueChangeListener(ratingListener);
	}

	@Override
	public void reset() {
		ratingStars.removeValueChangeListener(ratingListener);
		ratingStars.setValue(0.0);
		ratingStars.addValueChangeListener(ratingListener);
	}

	@Override
	public void setData(String key, Double data) {
		if(key.equalsIgnoreCase("rating")){
			ratingStars.removeValueChangeListener(ratingListener);
			ratingStars.setValue(data);
			ratingStars.addValueChangeListener(ratingListener);
		}
	}

}
