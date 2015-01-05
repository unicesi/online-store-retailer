package co.edu.icesi.driso.osr.ui.components;

import java.io.File;


import co.edu.icesi.driso.osr.ui.views.CategoryView;
import co.edu.icesi.driso.osr.util.OSRUtilities;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

public class CategoriesSummary extends CustomComponent {

	public enum CategoriesPanelType {
		HALF_SIZE_ITEM,
		THIRD_SIZE_ITEM
	}
	
	public enum CategoriesPanelStyle {
		MARLEY,
		SADIE,
		LAYLA,
		OSCAR,
		RUBY,
		ROXY,
		BUBBA,
		GOLIATH
	}
	
	private static final long serialVersionUID = 1L;
	private HorizontalLayout mainLayout;
	private final CategoriesPanelType type;
	private final CategoriesPanelStyle style;
	private CustomLayout categoriesLayout;

	public CategoriesSummary(CategoriesPanelType type, CategoriesPanelStyle style) {
		
		this.type = type;
		this.style = style;

		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	public CategoriesSummary(){
		this(CategoriesPanelType.HALF_SIZE_ITEM, CategoriesPanelStyle.LAYLA);
	}

	private void buildMainLayout() {
		mainLayout = new HorizontalLayout();
		boolean bigPanel = type == CategoriesPanelType.HALF_SIZE_ITEM;
		String 	layout = (bigPanel ? "grid" : "minigrid");
				layout += "-" + style.toString();
				
		categoriesLayout = new CustomLayout(layout);
		
		// Get products
		String[][] productsData = bigPanel ? getCategories() : getMiniCategories();
		
		// Create items
		int items = bigPanel ? 2 : 3;
		for (int i = 1; i <= items; i++) {
			addCategoryToGrid(
				i, 
				categoriesLayout, 
				productsData[i-1][0], 
				productsData[i-1][1], 
				productsData[i-1][2],
				productsData[i-1][3]
			);
		}

		// Main layout
		mainLayout.addComponent(categoriesLayout);
	}

	private void addCategoryToGrid(int n, CustomLayout categoriesLayout, 
			String imagePath, String heading, String caption, String link){
		
		FileResource imageFile = new FileResource(new File(imagePath));
		Image productImage = new Image(null, imageFile); 
		categoriesLayout.addComponent(productImage, "image" + n);

		Label title = new Label("<h2>" + heading + "</h2>", ContentMode.HTML);
		categoriesLayout.addComponent(title, "heading" + n);

		Label description = new Label("<p>" + caption + "</p>", ContentMode.HTML);
		categoriesLayout.addComponent(description, "paragraph" + n);

		Link viewMoreLink = new Link("View more", new ExternalResource(link));
		categoriesLayout.addComponent(viewMoreLink, "readmore" + n);
	}
	
	private String[][] getCategories(){
		String[][] data = {
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/categories/18.jpg"),
					"Men's <span>Fashion</span>",
					"Rain never fails to dampen the mood, buy nice cloths for those rainy days!",
					"#!" + CategoryView.NAME + "/1"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/categories/21.jpg"),
					"Leisure <span>Time</span>",
					"Whenever, Wherever, Whatever!",
					"#!" + CategoryView.NAME + "/2"
				}
		};
		
		return data;
	}
	
	private String[][] getMiniCategories(){
		String[][] data = {
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/categories/33.jpg"),
					"All <span>Food</span>",
					"You can find everything from organic food to the juiciest steak!",
					"#!" + CategoryView.NAME + "/3"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/categories/31.jpg"),
					"On <span>Vacations</span>",
					"Are you going somewhere? find clothing and accessories to go on vacations",
					"#!" + CategoryView.NAME + "/4"
				},
				{
					OSRUtilities.getRealPathFor("/WEB-INF/images/categories/29.jpg"),
					"Work <span>Tools</span>",
					"You can find everything from a bolt to a crane!",
					"#!" + CategoryView.NAME + "/5"
				}
		};
		
		return data;
	}

}
