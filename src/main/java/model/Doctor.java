package main.java.model;

public class Doctor {
	
	int id;
	String name;

	public Doctor(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getId(){
		return "D"+ id;
	}
	
	public String getName(){
		return name;
	}
}
