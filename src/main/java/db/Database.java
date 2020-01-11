package main.java.db;

import java.util.HashMap;

import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class Database {

	HashMap<String, Doctor> doctors = new HashMap<String, Doctor>();
	HashMap<String, Patient> patients = new HashMap<String, Patient>();
	HashMap<String, Appointment> appointments = new HashMap<String, Appointment>();

	public Database(){
		
	}
	
	public void setDatabase(HashMap<String, Doctor> doctors, 
			HashMap<String, Patient> patients, 
			HashMap<String, Appointment> appointments){
		this.doctors = doctors;
		this.patients = patients;
		this.appointments = appointments;
	}
}
