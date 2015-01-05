package co.edu.icesi.driso.osr.ui.components;


import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.SearchPresenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;
import co.edu.icesi.driso.osr.util.OnEnterKeyHandler;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;

public class SearchBar extends CustomComponent implements ViewComponent<String> {

	private static final long serialVersionUID = 1L;
	private SearchPresenter presenter;
	
	private CssLayout mainLayout;
	private ComboBox categoryComboBox;
	private TextField searchBox;
	private Button searchButton;
	
	public SearchBar() {
		buildMainLayout();
		bindEvents();
		setCompositionRoot(mainLayout);
	}

	private void buildMainLayout() {
		mainLayout = new CssLayout();
		mainLayout.setWidthUndefined();
		mainLayout.setStyleName("v-component-group");

		categoryComboBox = new ComboBox();
		categoryComboBox.setWidth(180, Unit.PIXELS);
		categoryComboBox.setInputPrompt("Select a category");
		categoryComboBox.setInvalidAllowed(false);
		categoryComboBox.setNullSelectionAllowed(false);
		
		searchBox = new TextField();
		searchBox.setInputPrompt("Enter product's name");
		searchBox.setImmediate(true);
		searchBox.setInvalidAllowed(false);
		searchBox.setColumns(20);
		
		searchButton = new Button(FontAwesome.SEARCH);
		searchButton.setStyleName("v-menubar-menuitem-icon-only");
		
		mainLayout.addComponent(categoryComboBox);
		mainLayout.addComponent(searchBox);
		mainLayout.addComponent(searchButton);
	}
	
	private void performSearch(){
		String selectedCategory = (String) categoryComboBox.getValue();
		String searchString = searchBox.getValue();
		
		presenter.onSearch(selectedCategory, searchString);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (SearchPresenter) presenter;
	}

	@Override
	public void reset() {
		categoryComboBox.addItems(presenter.getSearchCategories());
	}
	
	@Override
	public void bindEvents(){
		searchButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				performSearch();
			}
		});
		
		OnEnterKeyHandler onEnterHandler=new OnEnterKeyHandler(){
            @Override
            public void onEnterKeyPressed() {
                performSearch();
            }
        };
        onEnterHandler.installOn(searchBox);
	}

	@Override
	public void setData(String key, String data) {
		// This view component does not have any data to update
	}

}
