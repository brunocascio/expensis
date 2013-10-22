package com.brunocascio.expensis;

public class Expensi {
	private long id;
	private String description;
	private float price;
	private String date;
	
	
	public Expensi(String description, float price, String date){
		this.description = description;
		this.price = price;
		this.date = date;
	}
	
	public long getId() {
	  return id;
	}
	
	public void setId(long id) {
	  this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
	  return description+" $"+price+" "+" "+date;
	}
}
