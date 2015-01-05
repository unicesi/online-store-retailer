package co.edu.icesi.driso.osr.util;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;


import co.edu.icesi.driso.osr.ui.Application;
import co.edu.icesi.driso.osr.ui.views.CategoryView;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

public class MenuUtilities {

	public static final String taxonomyFilePath = "/WEB-INF/GoodRelations/taxonomy.en-US.txt";

	public static void addAllMenuItems(MenuBar menu){

		Hashtable<String, MenuItem> categories = new Hashtable<String, MenuItem>();
		List<String> taxonomy;
		try {
			taxonomy = OSRUtilities.readFile(MenuUtilities.taxonomyFilePath);
			
			// skip first line: # Google_Product_Taxonomy_Version: 2013-12-12
			for (int i = 1; i < taxonomy.size(); i++) {
				String[] lineItems = taxonomy.get(i).split(">");

				if(lineItems.length == 1){
					String newName = OSRUtilities.removeInitialEndingSpaces(lineItems[0]);
					MenuItem newCategory = menu.addItem(newName, null, getMenuItemCommand(1));

					categories.put(newName, newCategory);
				}else{
					String lastItemName = OSRUtilities.removeInitialEndingSpaces(lineItems[lineItems.length - 1]);
					String penultimateName = OSRUtilities.removeInitialEndingSpaces(lineItems[lineItems.length - 2]);

					MenuItem parent = categories.get(penultimateName);
					MenuItem newCategory = parent.addItem(lastItemName, null, getMenuItemCommand(1));

					categories.put(lastItemName, newCategory);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static MenuBar.Command getMenuItemCommand(final int categoryId){
		MenuBar.Command command = new MenuBar.Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Application.navigator.navigateTo(CategoryView.NAME + "/" + categoryId);
			}  
		};

		return command;
	}

}