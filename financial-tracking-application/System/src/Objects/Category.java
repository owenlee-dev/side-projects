package Objects;

import java.util.ArrayList;
import java.io.*;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Class to represent a category in an account
public class Category {
	private String name;
	private ArrayList<String> tags;
	private double points;
	private String inputFile;	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Constructor
	public Category(String name, double points) {
		this.name=name;
		this.points=points;
		tags=new ArrayList<String>();
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Getters and Setters
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//Name
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	//Points
	public double getPoints() {
		return points;
	}
	
	public void setPoints() {
		this.points=points;
	}
	//Tags
	public ArrayList<String> getTags(){
		return tags;
	}
	//Input File
	public void setInputFile(String inputFile) {
		this.inputFile=inputFile;
	}
	//Tags
	public void addTag(String toAdd){
		tags.add(toAdd);
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
