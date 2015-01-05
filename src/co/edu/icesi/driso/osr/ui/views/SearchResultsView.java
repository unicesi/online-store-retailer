package co.edu.icesi.driso.osr.ui.views;

import java.util.ArrayList;
import java.util.List;

import co.edu.icesi.driso.osr.presenters.GenericPresenter;
import co.edu.icesi.driso.osr.presenters.Presenter;
import co.edu.icesi.driso.osr.presenters.ViewComponent;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.VerticalLayout;

// TODO: String[] ---> Product
public class SearchResultsView extends VerticalLayout implements View, ViewComponent<List<String[]>> {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "search-results";

	private GenericPresenter<String[]> presenter;
	private ViewWrapper wrapper;
	private List<String[]> results;
	
	public SearchResultsView(){
		results = new ArrayList<String[]>();
		buildUI();
	}
	
	private void buildUI(){
		
		
		wrapper = new ViewWrapper();
		addComponent(wrapper);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Page.getCurrent().setTitle("Search - Online Store Retailer");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = (GenericPresenter<String[]>) presenter;
	}

	@Override
	public void bindEvents() {
		// This view does not have special events to be handled
	}

	@Override
	public void reset() {
		results = new ArrayList<String[]>();
		
		// TODO: clean visual components...
	}

	@Override
	public void setData(String key, List<String[]> data) {
		if(key.equalsIgnoreCase("results")){
			this.results = data;
		}
	}

}
