package Tools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Class to write to an excel workbook
//Guide to usage:
//1. Instantiate
//2. Create new Sheet
//3. Write all data necessary
//4. Close workbook 
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public class ExcelWriter {
	
	private XSSFWorkbook workbook;
	private String outputFile;
	private ArrayList<XSSFSheet> sheets;
	private static int sheetCount;
	private static int maxCol;
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Constructor --> initializes a workbook
	public ExcelWriter(String outputFile) throws IOException {
		workbook = new XSSFWorkbook();
		this.outputFile=outputFile;
		sheets=new ArrayList<XSSFSheet>();
		sheetCount=0;
		maxCol=0;
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to create a new sheet in the workbook, add to sheet list
	public void newSheet(String sheetName) throws IOException {
		sheets.add(workbook.createSheet(sheetName));
		this.sheetCount++;
		completeWrite();
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to set a new file name
	public void setFileName(String outputFile) {
		this.outputFile=outputFile;
	}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to write a cell into a worksheet given sheet row and column
	public void write(int sheet,int row, int col, String data) throws IOException {
		
		//update number of columns if it has grown
        if(col>maxCol) {
        	maxCol=col;
        }
        
        //get sheet
        XSSFSheet tempSheet=sheets.get(sheet);
        
      	// Specific row number 
      	XSSFRow newRow = tempSheet.createRow(row-1); 
              
		//excel reader to get existing cells in row
		ExcelReader reader=new ExcelReader(outputFile);
		String[] oldCells=new String[maxCol];
		//fill old cells with blanks
		for(int i=0;i<maxCol;i++) {
			oldCells[i]=" ";
		}
		
		String check=reader.read(sheet, row, 1);
		//get old cells int array
		for(int i=1;i<maxCol;i++) {
			if(reader.read(sheet, row - 1, i - 1)!="DNE") {
				oldCells[i-1]=reader.read(sheet, row - 1, i - 1);
			}
		}
		
		//fill in new cell
		oldCells[col-1]=data;
				
        //fill row
        for(int i=0;i<maxCol;i++) {
        	 XSSFCell tempCell=newRow.createCell(i);
        	 tempCell.setCellValue(oldCells[i]);
        }
        completeWrite();
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to actually write data to the excel sheet
	public void completeWrite() throws IOException {
		FileOutputStream os = new FileOutputStream(outputFile);
        workbook.write(os);
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Method to close the workbook
	public void close() throws IOException {
		workbook.close();
	}
}
