package Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Class to read excel files
//Guide to usage:
//1. Instantiate
//2. Read method
//3. Close workbook with close method
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public class ExcelReader {

	private String inputFile;
	private XSSFWorkbook workbook;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
//Constructor
	public ExcelReader(String inputFile) throws IOException {
		this.inputFile=inputFile;
		FileInputStream file=new FileInputStream(new File(inputFile));
		workbook = new XSSFWorkbook(file);
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to return the amount of sheets
	public int getSheets() {
		int sheets;
		sheets = workbook.getNumberOfSheets();
		return sheets;
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
//Read method given sheet number row and column returns data in cell
	public String read(int sheetIndex, int row, int col) {
		XSSFSheet worksheet=workbook.getSheetAt(sheetIndex);
		XSSFRow rowIndex=worksheet.getRow(row);

		//if row does not exist yet
		if(rowIndex==null) {
			return "DNE";
		}
		XSSFCell cell=rowIndex.getCell(col);
		if(cell!=null) {
			return cell.getStringCellValue();
		}
		else {
			return " ";
		}
		
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Reading a numeric value from an excel sheet	
	public double readNum(int sheetIndex, int row, int col) {
		XSSFSheet worksheet=workbook.getSheetAt(sheetIndex);
		XSSFRow rowIndex=worksheet.getRow(row);
		//if row does not exist yet
		if(rowIndex==null) {
			return 0.0;
		}
		XSSFCell cell=rowIndex.getCell(col);
		if(cell!=null) {
			return cell.getNumericCellValue();
		}
		else {
			return 0.0;
		}
		
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Gets name of sheet from excel file
	public String getSheetName(int sheet) {
		String name = workbook.getSheetName(sheet);
		return name;
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//close method to close workbook
	public void close() throws IOException {
		this.workbook.close();
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
}
