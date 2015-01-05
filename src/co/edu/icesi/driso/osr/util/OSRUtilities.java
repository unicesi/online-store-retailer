package co.edu.icesi.driso.osr.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.VaadinService;

public class OSRUtilities {
	
	private static final NumberFormat formatter = NumberFormat.getCurrencyInstance();

	public static String getRealPathFor(String str){
		String path = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + 
				(str.startsWith("/") ? str : "/" + str);
		return path;
	}
	
	public static String formatCurrency(String currency){
		return formatter.format(Double.valueOf(currency));
	}
	
	public static List<String> readFile(String path) throws IOException {
		List<String> fileContent = new ArrayList<String>();
		
		BufferedReader br = 
				new BufferedReader(
						new FileReader(
								OSRUtilities.getRealPathFor(path)));
		String line = "";

		while((line = br.readLine()) != null){
			fileContent.add(line);
		}
		
		br.close();
		return fileContent;
	}
	
	public static List<String> getAllProductCategories(){
		List<String> categories = new ArrayList<String>();
		try {
			BufferedReader br = 
					new BufferedReader(
							new FileReader(
									OSRUtilities.getRealPathFor(
											MenuUtilities.taxonomyFilePath)));
			
			// Skip line No. 1: # Google_Product_Taxonomy_Version: 2013-12-12
			String line = br.readLine();
			
			while((line = br.readLine()) != null){
				String[] lineItems = line.split(">");

				if(lineItems.length == 1){
					String category = OSRUtilities.removeInitialEndingSpaces(lineItems[0]);
					categories.add(category);
				}
			}
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return categories;
	}
	
	public static String removeInitialEndingSpaces(String item){
		if(item.charAt(0) == ' ')
			item = item.substring(1);
		if(item.charAt(item.length() - 1) == ' ')
			item = item.substring(0, item.length() - 1);

		return item;
	}

}
