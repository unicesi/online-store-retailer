package co.edu.icesi.driso.osr.ui.components;


import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.views.client.HomeView;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

public class TopMenu extends CustomComponent {

	private static final long serialVersionUID = 1L;
	private VerticalLayout mainLayout;
	private MenuBar menu;

	public TopMenu() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	private VerticalLayout buildMainLayout() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(false);
		mainLayout.setSpacing(false);
		
		menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setWidth(100, Unit.PERCENTAGE);
		menu.setReadOnly(true);
		mainLayout.addComponent(menu);
		
		MenuItem homeMenuItem = menu.addItem("", FontAwesome.HOME, new MenuBar.Command() {

			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
		        Application.navigator.navigateTo(HomeView.NAME);
		    }  
		});
		
		homeMenuItem.setStyleName("v-menubar-menuitem-icon-only");
		
		return mainLayout;
	}
	
	public MenuBar getMenu(){
		return menu;
	}

}
