package Objects;

import java.util.ArrayList;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Class to represent an account
public class Account {
	private ArrayList<Buyer> buyers;
	private ArrayList<Category> categories;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//constructor
	public Account() {
		buyers=new ArrayList<Buyer>();
		categories=new ArrayList<Category>();
		//categories(0) always = Miscellaneous
		addCategory(new Category("Miscellaneous",1.5));
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Getters and Setters
	
//Buyer
	public ArrayList<Buyer> getBuyers() {
		return buyers;
	}
	
	public void addBuyer(Buyer toAdd) {
		buyers.add(toAdd);
	}
	
	public Buyer getBuyer(int index) {
		return buyers.get(index);
	}
	
//Category
	public ArrayList<Category> getCategories(){
		return categories;
	}
	
	public Category getCategory(int index) {
		return categories.get(index);
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//add a category on the account
	public void addCategory(Category toAdd) {
		categories.add(toAdd);
		//add new total for all buyers
		for(Buyer temp:buyers) {
			temp.addTotal();
		}
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
