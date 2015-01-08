package co.edu.icesi.osr.dtos;

public class ProductoDTO extends org.driso.osr.domain.dtos.ProductoDTO {
	
	private static final long serialVersionUID = 1L;
	private String imageName;
	private boolean hasDiscount;
	private double priceBeforeDiscount;

	public boolean hasDiscount(){
		return hasDiscount;
	}
	
	public double getPriceBeforeDiscount(){
		return priceBeforeDiscount;
	}
	
	public void setDiscount(double newPrice){
		priceBeforeDiscount = getPrecio();
		setPrecio((int) newPrice);
		hasDiscount = true;
	}
	
	public String getImageName(){
		return imageName;
	}
	
	public void setImageName(String imageName){
		this.imageName = imageName;
	}
	
}
