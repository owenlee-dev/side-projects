package Testing;

import java.text.ParseException;

import Objects.Account;
import Objects.Buyer;
import Objects.Category;
import TeamProject.SystemMain;

public class OutputTester {

	public static void main(String[] args) {
		
		Account acc = new Account();
		Buyer buyer1 = new Buyer("CD");
		Buyer buyer2 = new Buyer ("LM");
		
		buyer1.setMonth("January");
		buyer2.setMonth("January");
		
		acc.addBuyer(buyer1);
		acc.addBuyer(buyer2);
		
		Category cat1 = new Category("Groceries",1.5);
		
		cat1.addTag("Sobeys");
		
		acc.addCategory(cat1);
		
		buyer1.updateTotal(1, 20);
		
		System.out.println(buyer1.getTotals().get(1));
		System.out.println(buyer2.getTotals().get(1));
		
		Category cat2 = new Category("Pharmacy", 1);
		
		acc.addCategory(cat2);
		
		buyer1.updateTotal(2, 35);
		buyer2.updateTotal(2, 200.50);
		
		Category cat3 = new Category("Gas",1.5);
		
		acc.addCategory(cat3);
		
		buyer1.updateTotal(3,40);
		buyer2.updateTotal(3,20);
		
		Category cat4 = new Category("Dining", 1);
		
		acc.addCategory(cat4);
		
		System.out.print(acc.getCategories().get(1).getPoints());
		
		buyer2.updateTotal(4,75);
		
		buyer1.updateTotal(0,-25);
		
		SystemMain system = new SystemMain(acc);
		
		try {
			system.writeReport("outputBuyer1test.txt",buyer1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			system.writeReport("outputBuyer2test.txt",buyer2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			system.writeReport("outputAccountTest.txt");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
