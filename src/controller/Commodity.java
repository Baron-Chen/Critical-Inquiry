package controller;

import org.bson.Document;

public class Commodity {
	
	private String name = "";
	private String brand = "";
	private String capacity = "";
	private String manufacture = "";
	private String country = "";
	private String upc = "";
	private String remarks = "";
	private String image = "";
	private Document document;
	
	public String getName() {
		return name;
	};
	
	public String getBrand() {
		return brand;
	};
	
	public String getCapacity() {
		return capacity;
	};
	
	public String getManufacture() {
		return manufacture;
	};
	
	public String getCountry() {
		return country;
	};
	
	public String getUpc() {
		return upc;
	};
	
	public String getImage() {
		return image;
	};
	
	public String getRemarks() {
		return remarks;
	};
	
	public void setName(String in_name) {
		this.name = in_name;
	};
	
	public void setBrand(String in_brand) {
		this.brand = in_brand;
	};
	
	public void setCapacity(String in_capacity) {
		this.capacity = in_capacity;
	};
	
	public void setManufacture(String in_manufacture) {
		this.manufacture = in_manufacture;
	};
	
	public void setCountry(String in_country) {
		this.country = in_country;
	};
	
	public void setUpc(String in_upc) {
		this.upc = in_upc;
	};
	
	public void setRemarks(String in_remarks) {
		this.remarks = in_remarks;
	};
	
	public void setImage(String in_image) {
		this.image = in_image;
	};
	
	public Document getDoc() {
		this.document = new Document("name", name).append("brand", brand).append("capacity", capacity)
		.append("manufacturer", manufacture).append("country", country).append("upc", upc).append("remarks", remarks).append("image", image);
		return this.document;
	};
	
}
