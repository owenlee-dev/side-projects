package Testing;

import java.io.IOException;

import Tools.ExcelReader;

public class ReadTest {
	public static void main(String[] args) {
		String inputFile="/C:/Code/Project/team5-ftta/ExcelTest/TestWorkbook.xlsx";
		ExcelReader reader=null;
		try {
			reader = new ExcelReader(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(reader.read(0, 2, 3));
	
	
	}
}
