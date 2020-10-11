package TeamProject;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import Objects.*;
import Tools.*;
import TeamProject.SystemMain;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public class SystemGUI extends Application {
	
	private SystemMain mainSys;
	private Account mainAccount = new Account();
	private String infoFileName;
	private TextField infoFileField;
	private Text result;
	private Stage stage;
	private ExcelWriter transWriter;
	private ExcelReader reader;
	//HARDCODED
	private String outputFileName = "/home/olee/git/team5-ftta/SampleFiles/ExcelOutput.xlsx"; 
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Initializing GUI
	@Override
	public void start(Stage stageOne) {
		stage = stageOne;
		stageOne.setTitle("Financial Tracker");
		
		result = new Text("Initialization");
		
		Scene scene1 = firstDecision();
		stageOne.setScene(scene1);
		stageOne.show();
		
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Setting up first GUI Scene
	public Scene firstDecision() {
		Label infoFileLabel = new Label("Enter the File Address Containing the Account Information");
		infoFileField = new TextField();
		infoFileField.setPrefWidth(150);
		
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				
				//getting the file address
				infoFileName = infoFileField.getText();
				
				//initializing excel tools
				try {
					reader = new ExcelReader(infoFileName);
					transWriter = new ExcelWriter(outputFileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				

				
				//creating the main system to manipulate the file
				mainSys = new SystemMain(mainAccount, infoFileName);
				
				//reading the identifiers
				mainSys.readIdentifierPage(1);
				
				//reading the categories
				mainSys.readCategoryPage(2);
				
				int sheets = reader.getSheets();
				
				int numBuyers = mainAccount.getBuyers().size();
				ArrayList<Buyer> buyers=mainAccount.getBuyers();
				
				//Creating a new sheet for each buyer
				try {
				for(int i=0;i<numBuyers;i++) {
					transWriter.newSheet(buyers.get(i).getName());
				}
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				int currentSheet = 3;
				
				//Processing transactions
				while (currentSheet < sheets) {
					mainSys.readTransactionPage(currentSheet, transWriter);
					String outputFileName = reader.getSheetName(currentSheet) +"_Totals_Output "+".txt";
					mainSys.writeReport(outputFileName);
					for (int i = 0; i < numBuyers; i ++) {
						String individualOutputFileName = buyers.get(i).getName()+"_" + reader.getSheetName(currentSheet) +"_Output "+".txt";
						mainSys.writeReport(individualOutputFileName, mainAccount.getBuyer(i));
						buyers.get(i).resetTotals();
					}
					
					currentSheet++;
					
				}
				stage.setScene(endScene());
			}
		});
		//set flowpane
		FlowPane initial = new FlowPane(result,infoFileLabel, infoFileField, submitButton);
		initial.setOrientation(Orientation.VERTICAL);
		initial.setAlignment(Pos.CENTER);
		initial.setHgap(10);
		initial.setVgap(10);
		
		return new Scene(initial, 325, 250); 
		
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//set scene
	public Scene endScene() {
			
			result.setText("Submissions Complete");
			
			FlowPane end = new FlowPane(result);
			end.setAlignment(Pos.CENTER);
			
			return new Scene(end, 300, 300);
	}
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
//Main method
	public static void main(String[] args) {
		Application.launch(args);
		
	}	

}
