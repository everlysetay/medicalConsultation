package main.java.model;

public class Patient extends Human{
	
	int id;
	
	public Patient(String name, int age, char gender) {
		super(name, age, gender);
	}
	
	public void setPatientId(int id){
		this.id = id;
	}
	
	public String getId(){
		return "P"+this.id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getAge(){
		return age;
	}
	
	public char getGender(){
		return gender;
	}
}
