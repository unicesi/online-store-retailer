package co.edu.icesi.driso.osr.ui.views;


import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.components.Footer;
import co.edu.icesi.driso.osr.ui.components.TopBar;
import co.edu.icesi.driso.osr.ui.components.TopMenu;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

public class ViewWrapper extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private final AbstractComponent[] components;
	
	public ViewWrapper(AbstractComponent ... components){
		this.components = components;
		buildUI();
	}
	
	public void buildUI(){
		TopBar topBar = new TopBar();
		this.addComponent(topBar);
		
		VerticalLayout container = new VerticalLayout();
		container.setMargin(false);
		container.setSpacing(false);
		container.setWidth(Application.pageWidth, Application.pageWidthUnit);
		
		TopMenu topMenu = new TopMenu();
		container.addComponent(topMenu);
		
		// Add all components to the page-elements container
		for (AbstractComponent component : components) {
			container.addComponent(component);
		}
		
		this.addComponent(container);
		this.setComponentAlignment(container, Alignment.TOP_CENTER);
		
		Footer footer = new Footer();
		this.addComponent(footer);
	}

}
