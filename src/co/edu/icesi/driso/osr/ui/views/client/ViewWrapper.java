package co.edu.icesi.driso.osr.ui.views.client;


import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.components.Footer;
import co.edu.icesi.driso.osr.ui.components.TopBar;
import co.edu.icesi.driso.osr.ui.components.TopMenu;
import co.edu.icesi.driso.osr.util.MenuUtilities;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

public class ViewWrapper extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private VerticalLayout container;
	private TopBar topBar;
	private TopMenu topMenu;
	private Footer footer;
	private final AbstractComponent[] components;
	
	public ViewWrapper(AbstractComponent ... components){
		this.components = components;
		buildUI();
	}
	
	public void buildUI(){
		topBar = new TopBar();
		addComponent(topBar);
		
		container = new VerticalLayout();
		container.setMargin(false);
		container.setSpacing(false);
		container.setWidth(Application.pageWidth, Application.pageWidthUnit);
		
		topMenu = new TopMenu();
		addMenuItems();
		
		container.addComponent(topMenu);
		
		// Add all components to the page-elements container
		for (AbstractComponent component : components) {
			container.addComponent(component);
		}
		
		addComponent(container);
		setComponentAlignment(container, Alignment.TOP_CENTER);
		
		footer = new Footer();
		addComponent(footer);
	}
	
	public void replaceComponent(AbstractComponent oldComponent, 
			AbstractComponent newComponent){
		
		container.replaceComponent(oldComponent, newComponent);
	}
	
	private void addMenuItems(){
		MenuUtilities.addCategoriesToMenu(topMenu.getMenu());
	}

}
