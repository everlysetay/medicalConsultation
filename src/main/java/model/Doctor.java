package main.java.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Doctor {
	
	int id;
	String name;
	LinkedHashMap<LocalDateTime, Appointment> list = new LinkedHashMap<LocalDateTime, Appointment>();

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
	
	public void setAppointment(LocalDateTime datetime, Appointment app){
		this.list.put(datetime, app);
	}
	
	public HashMap<LocalDateTime, Appointment> getAppointmentList(){
		return list;
	}
}
