package co.edu.icesi.driso.osr.ui.components;

import java.io.File;

import co.edu.icesi.driso.osr.presenters.ProductPresenter;
import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.views.ProductView;
import co.edu.icesi.driso.osr.util.OSRUtilities;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

public class FeaturedProducts extends CustomComponent {

	private static final long serialVersionUID = 1L;
	private VerticalLayout mainLayout;
	private ProductSummary topProduct;
	private CustomLayout productsLayout;
	private final String layout = "superminigrid-SADIE";
	public final boolean showTopProduct;
	public int relatedProductId = -1;
	private Label relatedProductsLabel;

	public FeaturedProducts(){
		this(true);
	}
	
	/**
	 * Featured products related to a specific product
	 * 
	 * @param showTopProduct	Specifies if the top product should be shown
	 * @param relatedProductId			
	 */
	public FeaturedProducts(boolean showTopProduct, int relatedProductId){
		this.relatedProductId = relatedProductId;
		this.showTopProduct = showTopProduct;
		buildMainLayout();
		assignPresenters();
		setCompositionRoot(mainLayout);
	}
	
	public FeaturedProducts(boolean showTopProduct) {
		this.showTopProduct = showTopProduct;
		buildMainLayout();
		assignPresenters();
		setCompositionRoot(mainLayout);
	}

	private void buildMainLayout() {
		// Layout configuration
		mainLayout = new VerticalLayout();
		mainLayout.setStyleName("featured-products-container");
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		
		productsLayout = new CustomLayout(layout);
		
		// Show top product
		if(showTopProduct)
			topProduct = new ProductSummary(OSRUtilities.getProductInformation(1), false);
		
		// Add a title in case the featured products correspond to related products
		if(relatedProductId > -1){
			relatedProductsLabel = new Label("<h1>Related Products</h1>", ContentMode.HTML);
			mainLayout.addComponent(relatedProductsLabel);
		}
		
		// Get products
		String[][] productsData = relatedProductId > -1 ? 
				getRelatedProducts() : getProducts();
		
		// Create items
		int items = 3;
		for (int i = 1; i <= items; i++) {
			addProductToPanel(
				i, 
				productsLayout, 
				productsData[i-1][0], 
				productsData[i-1][1], 
				productsData[i-1][2]
			);
		}

		// Main layout
		if(showTopProduct)
			mainLayout.addComponent(topProduct);
		
		mainLayout.addComponent(productsLayout);
	}
	
	private void addProductToPanel(int n, CustomLayout categoriesLayout, 
			String imagePath, String heading, String link){
		
		FileResource imageFile = new FileResource(new File(imagePath));
		Image productImage = new Image(null, imageFile); 
		categoriesLayout.addComponent(productImage, "image" + n);

		Label title = new Label("<h2>" + heading + "</h2>", ContentMode.HTML);
		categoriesLayout.addComponent(title, "heading" + n);

		Link viewMoreLink = new Link("View more", new ExternalResource(link));
		categoriesLayout.addComponent(viewMoreLink, "readmore" + n);
	}
	
	private String[][] getProducts(){
		String[][] data = {
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/products/win8tablet4.png"),
					"Surface <span>Tablet</span>",
					"#!" + ProductView.NAME + "/1"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/products/win8tablet2.png"),
					"Lenovo <span>Tablet</span>",
					"#!" + ProductView.NAME + "/2"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/products/win8tablet3.png"),
					"Toshiba <span>Tablet</span>",
					"#!" + ProductView.NAME + "/3"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/thumbnails/beats.png"),
					"Leisure <span>Time</span>!",
					"#!" + ProductView.NAME + "/4"
				}
		};
		
		return data;
	}
	
	private String[][] getRelatedProducts(){
		// Uses relatedProductId
		String[][] data = {
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/thumbnails/beats.png"),
					"Leisure <span>Time</span>!",
					"#!" + ProductView.NAME + "/4"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/products/win8tablet2.png"),
					"Lenovo <span>Tablet</span>",
					"#!" + ProductView.NAME + "/2"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/products/win8tablet3.png"),
					"Toshiba <span>Tablet</span>",
					"#!" + ProductView.NAME + "/3"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/products/win8tablet4.png"),
					"Surface <span>Tablet</span>",
					"#!" + ProductView.NAME + "/1"
				}
		};
		
		return data;
	}
	
	public void assignPresenters(){
		if(showTopProduct){
			ProductPresenter productPresenter = 
					new ProductPresenter(topProduct, 
							Application.shoppingCartPresenter);
			
			productPresenter.init();
		}
	}

}
