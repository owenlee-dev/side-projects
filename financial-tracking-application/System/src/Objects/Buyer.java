package Objects;

import java.util.ArrayList;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Class to represent a buyer on an account
public class Buyer {
	
	private ArrayList<String> identifiers = new ArrayList<String>();
	private ArrayList<Double> totals = new ArrayList<Double>();
	private double pointsTotal;
	private String name;
	private String month;
	private int transCounter = 0;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public Buyer(String name) {
		this.name=name;
		//add first total for miscellaneous
		totals.add(0.0);
		pointsTotal=0;
		identifiers=new ArrayList<String>();
		month = null;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//getters and setters
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//get name
	public String getName() {
		return name;
	}
//when new category is added a new total will also be added
	public void addTotal() {
		totals.add(0.0);
	}
//update total of given category index (0 is miscellaneous)
	public void updateTotal(int index, double value) {
		double current=totals.get(index);
		totals.set(index, current+value);
	}
	
//update total points by value
	public void updatePoints(double value) {
		pointsTotal+=value;
	}
	
//get list of totals
	public ArrayList<Double> getTotals(){
		return totals;
	}
	
//add an identifier to a buyer
	public void addIdentifier(String identIn) {
		identifiers.add(identIn);
	}
//get list of identifiers
	public ArrayList<String> getIdentifiers(){
		return identifiers;
	}

	public void setMonth(String monthIn) {
		month = monthIn;
	}
//get month	
	public String getMonth() {
		return month;
	}
//get number of transactions	
	public int getTrans() {
		return transCounter;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//method to reset totals of a buyer
	public void resetTotals() {
		pointsTotal=0;
		int size=totals.size();
		for(int i=0;i<size;i++) {
			totals.set(i,0.0);
		}
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//method to add a transaction 
	public void addTrans() {
		transCounter++;
	}
}
