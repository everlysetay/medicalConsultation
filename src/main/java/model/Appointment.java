package main.java.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {
	
	int id;
	LocalDateTime datetime;
	String doctorId;
	String patientId;

    public Appointment(int id, LocalDate date, int hour, String doctorId, String patientId){
		LocalDateTime dt = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), hour, 0);
		this.id = id;
		this.datetime = dt;
		this.doctorId = doctorId;
		this.patientId = patientId;
    }
    
    public Appointment(int id, LocalDateTime datetime, String doctorId, String patientId){
    	this.id = id;
    	this.datetime = datetime;
    	this.doctorId = doctorId;
    	this.patientId = patientId;
    }
   
    
    public LocalDateTime getDateTime(){
    	return datetime;
    }
    
    public String getPatientId(){
    	return patientId;
    }
    
    public String getDoctorId(){
    	return doctorId;
    }
}
