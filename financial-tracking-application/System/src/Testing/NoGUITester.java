package Testing;

import java.io.IOException;
import java.util.ArrayList;
import Objects.Account;
import Objects.Buyer;
import TeamProject.SystemMain;
import Tools.ExcelReader;
import Tools.ExcelWriter;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Class to test system without javaFX
public class NoGUITester {	
	public static void main(String[] args) {
		
		//initializations
		//HARDCODED
		String inputFile="/home/olee/git/team5-ftta/SampleFiles/Configuration2020.xlsx";
		String outputFile="/home/olee/git/team5-ftta/SampleFiles/ExcelOutput.xlsx";
		ExcelReader reader=null;
		ExcelWriter writer=null;
		Account mainAccount = new Account();
		SystemMain mainSys = new SystemMain(mainAccount,inputFile);
		
		//reading the identifiers
		mainSys.readIdentifierPage(1);
		
		//reading the categories
		mainSys.readCategoryPage(2);
		
		try {
			reader = new ExcelReader(inputFile);
			writer = new ExcelWriter(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Create a sheet for each buyer
		int numBuyers = mainAccount.getBuyers().size();
		ArrayList<Buyer> buyers=mainAccount.getBuyers();
		try {
		for(int i=0;i<numBuyers;i++) {
			writer.newSheet(buyers.get(i).getName());
		}
		}catch (IOException e) {
			e.printStackTrace();
		}
				
		int sheets = reader.getSheets();
		
		//sheet where transactions start
		int currentSheet = 3;
		
		//Processing transactions
		while (currentSheet < sheets) {
			mainSys.readTransactionPage(currentSheet, writer);
			String outputFileName = reader.getSheetName(currentSheet) +"_Totals_Output "+".txt";
			mainSys.writeReport(outputFileName);
			for (int i = 0; i < numBuyers; i ++) {
				String individualOutputFileName = buyers.get(i).getName()+"_" + reader.getSheetName(currentSheet) +"_Output "+".txt";
				mainSys.writeReport(individualOutputFileName, mainAccount.getBuyer(i));
				buyers.get(i).resetTotals();
			}
			currentSheet++;
		}
	}
}

