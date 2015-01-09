package co.edu.icesi.driso.osr.presenters;

import java.util.ArrayList;
import java.util.List;

import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.components.SearchBar;
import co.edu.icesi.driso.osr.ui.views.client.SearchResultsView;
import co.edu.icesi.driso.osr.util.OSRUtilities;


public class SearchPresenter implements Presenter {
	
	private final SearchBar viewComponent;
	private List<String> categories;
	
	public SearchPresenter(SearchBar view){
		this.viewComponent = view;
		categories = new ArrayList<String>();
	}
	
	/**
	 * It is called when user press the button "search"
	 * 
	 * @param category		The selected category to narrow results in the search
	 * @param searchString	The searched string
	 */
	public void onSearch(String category, String searchString){
		// TODO
		// Perform the search process
		// ...
		
		
		// And then, redirect to the results page
		Application.navigator.navigateTo(SearchResultsView.NAME);
	}
	
	@Override
	public void init() {
		categories = OSRUtilities.getAllProductCategories();
		
		this.viewComponent.setPresenter(this);
		this.viewComponent.reset();
	}

	public List<String> getSearchCategories(){
		return categories;
	}

}