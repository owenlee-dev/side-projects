package Testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import Objects.Account;
import Objects.Buyer;
import Objects.Category;

import org.junit.Test;

public class TestAccount {

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		Account acc = new Account();
		Buyer buyerOne = new Buyer("SL");
		Buyer buyerTwo = new Buyer("TL");
		Category gas = new Category("Gas", 4.5);
		Category food = new Category("Food", 3.5);
		ArrayList<Buyer> buyerList= new ArrayList<Buyer>();
		ArrayList<Category> cateList = new ArrayList<Category>();
		buyerList.add(buyerOne);
		buyerList.add(buyerTwo);
		cateList.add(gas);
		cateList.add(food);
		
		acc.addBuyer(buyerOne);
		acc.addBuyer(buyerTwo);
		
		acc.addCategory(gas);
		acc.addCategory(food);
		
		assertEquals(buyerOne, acc.getBuyer(0));
		assertEquals("TL", acc.getBuyer(1).getName());
		
		assertEquals(gas, acc.getCategory(1));
		assertEquals("Food", acc.getCategory(2).getName());
		
		assertEquals(buyerList, acc.getBuyers());
		assertEquals(food, acc.getCategories().get(2));
		
		
	}
}
