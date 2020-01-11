package main.java.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Appointment {
	
	int id;
	LocalDateTime datetime;
	String doctorId;
	String patientId;
	
    DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHH:mm:ss");

    public Appointment(int id, LocalDateTime datetime, String doctorId){
    	this.id = id;
    	this.datetime = datetime;
    	this.doctorId = doctorId;
    }
    
    public void setPatientId(String patientId){
    	this.patientId = patientId;
    }
    
    public LocalDateTime getDateTime(){
    	return datetime;
    }
    
    public String getPatient(){
    	return patientId;
    }
}
