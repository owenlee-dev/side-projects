package Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Class to represent a single transaction made on a card
public class Transaction {
	
	private String date;
	private String description;
	private String buyer;
	private double value;
	private String outputFile;
	private String inputFile;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//constructor
	public Transaction(String date, String description, String buyer, double value) {
		this.date = date;
		this.description=description;
		this.buyer =buyer;
		this.value=value;
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Getters and Setters

//Date
	public String getDate() {
		return date;
	}
	
	public void setDate(String input) {	
		this.date = input;
	}

//Description
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}

//Buyer
	public String getBuyer() {
		return buyer;
	}
	
	public void setBuyer(String buyer) {
		this.buyer=buyer;
	}

//Value
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value=value;
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//toString
	public String toString() {
		return date+ " " + description + " " + buyer + " " + value;
	}
}
