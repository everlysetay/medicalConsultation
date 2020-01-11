package main.java.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
	
	int id;
	Date date;
	String doctorId;
	String patientId;
	
    DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHH:mm:ss");

    public Appointment(int id, Date date, String doctorId){
    	this.id = id;
    	this.date = date;
    	this.doctorId = doctorId;
    }
    
    public void setPatientId(String patientId){
    	this.patientId = patientId;
    }
}
