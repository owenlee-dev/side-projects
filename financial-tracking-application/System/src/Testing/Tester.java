package Testing;

import java.io.IOException;
import java.text.ParseException;

import Objects.Account;
import Objects.Transaction;
import TeamProject.SystemMain;
import Tools.ExcelReader;
import Tools.ExcelWriter;

public class Tester {

	public static void main(String[] args) {
		
		String inputFile="/C:/Code/Project/team5-ftta/ExcelTest/AccountInfo.xlsx";
		String outputFile="/C:/Code/Project/team5-ftta/ExcelTest/OutputWorkbook.xlsx";
		ExcelReader reader=null;
		Account mainAccount = new Account();
		//SystemMain mainSys = new SystemMain(mainAccount,inputFile);
		ExcelWriter writer=null;
		Transaction trans=null;
		Transaction trans2 = null;
		//For row number purposes
		int transCounter = 0;
		try {
			//reader = new ExcelReader(outputFile);
			//new excel writer
			writer=new ExcelWriter(outputFile);
			//new writer
			writer.newSheet("yuh");
			trans=new Transaction("05/05/2020","sobeys","SL",45.0);
			trans2 = new Transaction("06/22/2020", "please", "TL", 52.0);
			transCounter ++;
			writeTransaction(trans,writer, transCounter);
			transCounter ++;
			writeTransaction(trans2, writer, transCounter);
			//writeTransaction(trans2, writer);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	
	public static void writeTransaction(Transaction transaction,ExcelWriter writer, int transNum) throws IOException{
		//Set transaction elements for writing
				String buyer = transaction.getBuyer();
				String description = transaction.getDescription();
				String amount = Double.toString(transaction.getValue());
				String transDate = transaction.getStringDate();
				int sheetNum=0;
				
					writer.write(sheetNum, transNum, 1, transDate);
				
					writer.write(sheetNum, transNum, 2, description);
				
					writer.write(sheetNum, transNum, 3, buyer);
				
					writer.write(sheetNum, transNum, 4, amount);
				
			
	}
}
