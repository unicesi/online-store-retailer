package co.edu.icesi.driso.osr.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	public static String[] getProductInformation(int productId){
		Random gen = new Random();
		String v = gen.nextBoolean() ? "545000" : "599000";
		return new String[]{
/* 0 */			productId + "",
/* 1 */			"Sony Xperia Z3", 
/* 2 */			"It’s the details that make the difference " +
				"between a good smartphone and a great one. " +
				"Our premium Xperia™ smartphones and tablets " +
				"bring together the best of Sony technologies, " +
				"crafted using the finest quality materials in " +
				"a waterproof body, for a design that can " +
				"withstand the test of time. So don’t settle " +
				"for good when you can have great.", 
/* 3 */			"545000",
/* 4 */			v,
/* 5 */			"htc_one.png"
		};
	}

}
