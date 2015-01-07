package co.edu.icesi.driso.osr.ui.components;

import java.util.Collection;
import java.util.List;

import org.vaadin.pagingcomponent.ComponentsManager;
import org.vaadin.pagingcomponent.PagingComponent;
import org.vaadin.pagingcomponent.builder.ElementsBuilder;
import org.vaadin.pagingcomponent.button.ButtonPageNavigator;
import org.vaadin.pagingcomponent.customizer.style.StyleCustomizer;
import org.vaadin.pagingcomponent.listener.impl.LazyPagingComponentListener;

import co.edu.icesi.driso.osr.presenters.SquaredProductPresenter;
import co.edu.icesi.driso.osr.ui.Application;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;

public class ProductGrid extends CustomComponent {

	private static final long serialVersionUID = 1L;
	private VerticalLayout mainLayout;
	private GridLayout productsLayout;
	private final List<String[]> products;
	private final int productsPerPage;
	private final int productsPerRow;
	private final int buttonsPerPage;
	private PagingComponent<String[]> paginationBar;
	
	public ProductGrid(List<String[]> products,	int productsPerPage, int productsByRow){
		this.products = products;
		this.productsPerPage = productsPerPage;
		this.productsPerRow = productsByRow;
		buttonsPerPage = 8;
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}
	
	public void buildMainLayout(){
		
		setStyleName("content-layout");
		
		// Layout configuration
		mainLayout = new VerticalLayout();
		mainLayout.setWidth(100, Unit.PERCENTAGE);
		
		productsLayout = new GridLayout(productsPerRow, (int) (productsPerPage / Double.valueOf(productsPerRow)));
		productsLayout.setWidth(100, Unit.PERCENTAGE);
		
		StyleCustomizer paginationStyle = new StyleCustomizer() {

			private static final long serialVersionUID = 1L;

			@Override
			public void styleTheOthersElements(ComponentsManager manager,
					ElementsBuilder builder) {
				
				if(manager.getNumberTotalOfPages() < 2){
					return;
				}
				
				builder.getButtonFirst().setCaption("");
				builder.getButtonFirst().removeStyleName("link");
				builder.getButtonFirst().setIcon(FontAwesome.FAST_BACKWARD);
				builder.getButtonFirst().addStyleName("primary v-menubar-menuitem-icon-only");
				
				builder.getButtonLast().setCaption("");
				builder.getButtonLast().removeStyleName("link");
				builder.getButtonLast().setIcon(FontAwesome.FAST_FORWARD);
				builder.getButtonLast().addStyleName("primary v-menubar-menuitem-icon-only");
				
				builder.getButtonPrevious().setCaption("");
				builder.getButtonPrevious().removeStyleName("link");
				builder.getButtonPrevious().setIcon(FontAwesome.STEP_BACKWARD);
				builder.getButtonPrevious().addStyleName("v-menubar-menuitem-icon-only");
			
				builder.getButtonNext().setCaption("");
				builder.getButtonNext().removeStyleName("link");
				builder.getButtonNext().setIcon(FontAwesome.STEP_FORWARD);
				builder.getButtonNext().addStyleName("v-menubar-menuitem-icon-only");
				
				for (ButtonPageNavigator b : builder.getListButtons()) {
					b.removeStyleName("link");
					b.addStyleName("page-button");
				}
				
				boolean enabled = !manager.isFirstPage();
				builder.getButtonFirst().setEnabled(enabled);
				builder.getButtonPrevious().setEnabled(enabled);
				builder.getFirstSeparator().setVisible(false);
				
				enabled = !manager.isLastPage();
				builder.getButtonLast().setEnabled(enabled);
				builder.getButtonNext().setEnabled(enabled);
				builder.getLastSeparator().setVisible(false);
			}
			
			@Override
			public void styleButtonPageNormal(ButtonPageNavigator button, int pageNumber) {
				button.setPage(pageNumber);
				button.removeStyleName("primary");
			}
			
			@Override
			public void styleButtonPageCurrentPage(ButtonPageNavigator button,
					int pageNumber) {
				
				button.setPage(pageNumber);
				button.addStyleName("primary");
			}
		};
		
		paginationBar = 
				new PagingComponent<String[]>(productsPerPage, buttonsPerPage, products, 
						paginationStyle, 
						new LazyPagingComponentListener<String[]>(productsLayout) {

			private static final long serialVersionUID = 1L;

			@Override
			protected Collection<String[]> getItemsList(int startIndex, int endIndex) {
				return products.subList(startIndex, endIndex);
			}

			@Override
			protected Component displayItem(int index, String[] item) {
				SquaredProductSummary productSummary = 
						new SquaredProductSummary(item);
				
				SquaredProductPresenter productPresenter = 
						new SquaredProductPresenter(productSummary, 
								Application.shoppingCartPresenter);
				productPresenter.init();
				
				return productSummary;
			}
		});
		
		paginationBar.setStyleName("pagination");
		
		mainLayout.addComponent(productsLayout);
		mainLayout.addComponent(paginationBar);
	}	
}
