package TeamProject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import Objects.Category;
import Objects.Transaction;
import Objects.Account;
import Objects.Buyer;
import Tools.ExcelReader;
import Tools.ExcelWriter;

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//SystemMain will be the place that our application does all its background work
public class SystemMain {
	
	private Account account;
	private ExcelReader reader;
	private String inputFile;
	
	public SystemMain (Account accountIn, String inputFileIn) {
		
		account = accountIn;
		inputFile = inputFileIn;
		try {
			reader = new ExcelReader(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to get identifiers from input file and save them in associated buyer object 
//This method also needs to set the buyers, so every time a new buyer is read a buyer object is created and 
//added to account buyer list
	public void readIdentifierPage(int sheet) {
		String tempInfo;
		int rowCount = 1;
		int colCount = 0;
		boolean reading = true;
		Buyer tempBuyer;
		try {
			reader = new ExcelReader(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Reading the buyer & identifiers to the account
		while(reading){
			tempInfo = reader.read(sheet, 0, colCount);
			if(tempInfo.equals(" ")) {
				reading = false;
			}
			else{
				tempBuyer = new Buyer(tempInfo);
				tempInfo = reader.read(sheet, rowCount, colCount);
				while(!(tempInfo.equals("DNE"))) {
					tempBuyer.addIdentifier(tempInfo);
					rowCount++;
					tempInfo = reader.read(sheet, rowCount, colCount);
				}
				colCount++;
				rowCount = 1;
				account.addBuyer(tempBuyer);
			}
		}
	}	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to read categories from category sheet -> build category objects -> save in category list of account
	public void readCategoryPage(int sheet) {
		Category tempCate;
		String tempInfo;
		double tempPoints;
		int rowCount = 2;
		int colCount = 0;
		boolean reading = true;
		try {
			reader = new ExcelReader(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(reading){
			//Getting the Category name and the points from excel sheet
			rowCount = 2;
			tempInfo = reader.read(sheet, 0, colCount);
			tempPoints = reader.readNum(sheet, 1, colCount);
			if(tempInfo.equals(" ")){
				reading = false;
			}
			else{
				tempCate = new Category(tempInfo, tempPoints);
				int numBuyers = account.getBuyers().size();
				ArrayList<Buyer> buyers = account.getBuyers();
				for (int i = 0; i < numBuyers; i++) {
					buyers.get(i).addTotal();
				}
				//Getting tags and adding them to category
				tempInfo = reader.read(sheet, rowCount, colCount);
				while(!tempInfo.equals("DNE")){
					tempCate.addTag(tempInfo);
					rowCount++;
					tempInfo = reader.read(sheet, rowCount, colCount);
				}
				account.addCategory(tempCate);
				colCount++;
			}
		}
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
//Method to read transaction sheet and utilize methods as seen in class description at top of class
	public void readTransactionPage(int sheet, ExcelWriter writer) {
		//instantiate
		int rowCount = 1;
		String tempDate, tempDes,tempBuyer;
		double tempValue;
		Transaction tempTransaction;
		
		//Excel reader and writer setup
		try {
			reader = new ExcelReader(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//parse through raw transactions categorize and then write to output
		
			//tempInfo is to check if the next row is empty
			String tempInfo = reader.read(sheet, rowCount, 0);  
			while(!tempInfo.equals("DNE")) {
				//transaction info
				tempDate = reader.read(sheet, rowCount, 0);
				tempDes = reader.read(sheet, rowCount, 1);
				tempBuyer = reader.read(sheet, rowCount, 4);
				tempValue = reader.readNum(sheet, rowCount, 2);		
				
				//Categorizing transaction and increase counter
				Buyer tempBuy = findBuyer(tempDes);
				
				//adding month associated to buyers totals
				tempBuy.setMonth(reader.getSheetName(sheet));
				tempTransaction = new Transaction(tempDate, tempDes, tempBuy.getName(), tempValue);
				categorize(tempBuy, tempTransaction);
				tempBuy.addTrans();
				
				//write to output excel list
				writeProcessedTransaction(writer, tempTransaction);
				rowCount++;
				//tempInfo is to check if the next row is empty
				tempInfo = reader.read(sheet, rowCount, 0);		
			}
			
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to take account buyer and transaction, update the total that corresponds to
//the category the transaction in, found by scanning tags
	public void categorize(Buyer buyerIn, Transaction transaction) {
		
		String buyer=buyerIn.getName();
		
		//category that this description applies to
		Category found=null;
		
		//buyer that this account applies to
		Buyer tempBuyer=null;
		for(Buyer temp: account.getBuyers()) {
			if(temp.getName()==buyer) {
				tempBuyer=temp;
				break;
			}
		}
		//description that needs to be matches
		String description=transaction.getDescription();
		
		//list of categories in account --> categories(0)=miscellaneous
		ArrayList<Category> categories = account.getCategories();
		
		//list of tags from category
		ArrayList<String> tags;
		
		int categoryIndex=0;
		int rightCatIndex = 0;
		
		//search through category tags in order to  see if tags are within description
		for(Category tempCategory: categories) {
			categoryIndex++;
			//get tag list for current category that is being checked
			tags=tempCategory.getTags();
			for(String tempTag:tags) {
				//check if description contains current tag
				if(description.equals(tempTag)) {
					//if tag is found in description then it belongs to tempCategory
					found=tempCategory;
					rightCatIndex = categoryIndex - 1;
					break;
				}
			}
		}
		//if found=null then add to miscellaneous
		if(found==null) {
			found=categories.get(0);
		}
		
		//Increment total of found category by amount in transaction
		//also increment buyers points total
		
		tempBuyer.updateTotal(rightCatIndex, transaction.getValue());
		tempBuyer.updatePoints(found.getPoints()*transaction.getValue());
	
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to write transaction to output transaction list that is separated by buyer
	public void writeProcessedTransaction(ExcelWriter writer, Transaction transaction) {

		String buyer = transaction.getBuyer();
		String description = transaction.getDescription();
		String amount = Double.toString(transaction.getValue());
		String transDate = transaction.getDate();
		int buyerTrans = 0;
		int sheetNum=0;
		
		//find the correct buyer to write to
		for(int i=0;i<account.getBuyers().size();i++) {
			if(account.getBuyer(i).getName()==buyer) {
				sheetNum=i;
				buyerTrans = account.getBuyer(i).getTrans();
			}
		}
		try {
			writer.write(sheetNum, buyerTrans, 1, transDate);
			writer.write(sheetNum,  buyerTrans, 2, description);
			writer.write(sheetNum, buyerTrans, 3, buyer);
			writer.write(sheetNum, buyerTrans, 4, amount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//method to find the buyer of a transaction using identifiers associated with all buyers on the account
	public Buyer findBuyer(String description) {
		Buyer toReturn=null;
		ArrayList<Buyer> buyers= account.getBuyers();
		//cycle through all buyers
		for(Buyer buyerTemp:buyers) {
			//each buyer cycle through all identifiers
			for(String IDtemp:buyerTemp.getIdentifiers()) {
				//if an identifier appears in transaction description it must be that buyer
				if(description.equals(IDtemp)) {
					toReturn=buyerTemp;
				}
			}
		}
		return toReturn;
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//method to write output given a buyer and output file
	public void writeReport(String outputFileName, Buyer buyerIn) {
			
		String buyerName = buyerIn.getName();
		String info="";
		info += toString(buyerName);
			
		try {
			FileWriter output = new FileWriter(outputFileName);
			output.write(info);
			output.close();
		}
		catch (IOException e) {
			System.out.println("Output error");
		}
	}
		
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//method to writeReport for overall account
	public void writeReport(String outputFileName) {
			
			
		String buyerName = null;
		String info = toString(buyerName);
			
		try {
				
			FileWriter output = new FileWriter(outputFileName);
			output.write(info);
			output.close();
				
		}
		catch (IOException e) {
				
			System.out.println("Output error");
				
		}
			
	}	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//To string method
	public String toString(String buyerNameIn) {
		
		NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();
		NumberFormat pointFormat=NumberFormat.getInstance();
		pointFormat.setMaximumFractionDigits(2);
		pointFormat.setMinimumFractionDigits(2);
		String result;
		//add Month name
		if(buyerNameIn !=null) {
			result = "Monthly Spending Trends\nBuyer: "+buyerNameIn+" \nMonth: " + account.getBuyer(1).getMonth() + "\nCategory\t\tMoney Spent\t\tPoints\n\n";
		}
		else {
			result = "Monthly Spending Trends\nAccount Totals \nMonth: " + account.getBuyer(1).getMonth() + "\nCategory\tMoney Spent\tPoints\n\n";
		}
		double grandTotal = 0;
		double pointsTotal = 0;
		ArrayList<Buyer> buyers=account.getBuyers();
		
		
		for (int i = 0; i < account.getCategories().size(); i++) {
			
			double categoryTotal = 0;
			double categoryPoints = 0;
			
			//if there is no buyer name
			if (buyerNameIn == null) {
				for (int j = 0; j < buyers.size(); j++) { 
					Buyer current=buyers.get(j);
					categoryTotal += current.getTotals().get(i);

				}
				
				categoryPoints += categoryTotal * account.getCategories().get(i).getPoints();
			}
			
			//if there is a buyer name
			else { 
				
				for (int j = 0; j < account.getBuyers().size(); j++) {
					Buyer current=buyers.get(j);
					if(buyerNameIn.equals(current.getName())) {
						categoryTotal += current.getTotals().get(i);
						break;
					}
				}
				categoryPoints += categoryTotal * account.getCategories().get(i).getPoints();
			}
			
			if (categoryPoints <= 0) {
				categoryPoints = 0;
			}
			
			grandTotal += categoryTotal;
			pointsTotal += categoryPoints;
			
			String printCategoryInfo = String.format("%-23s %-23s %-23s", account.getCategories().get(i).getName(), 
					dollarFormat.format(categoryTotal), pointFormat.format(categoryPoints)); 
			result += printCategoryInfo + "\n"; 
		}
		result += "------------------------------------------------------------------\nTotals:\t\t\t";
		result += String.format("%-23s %-23s", pointFormat.format(grandTotal),(int)pointsTotal);
		
		return result;
		
	}
}
