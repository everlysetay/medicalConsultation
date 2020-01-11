package main.java.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class Database {

	HashMap<String, Doctor> doctors = new HashMap<String, Doctor>();
	HashMap<String, Patient> patients = new HashMap<String, Patient>();
	HashMap<String, List<Appointment>> appointments = new HashMap<String, List<Appointment>>();

	public Database(){
		
	}
	
	public void setDatabase(HashMap<String, Doctor> doctors, 
			HashMap<String, Patient> patients, 
			HashMap<String, List<Appointment>> appointments){
		this.doctors = doctors;
		this.patients = patients;
		this.appointments = appointments;
	}
	
	public Doctor getDoctorByName(String name){
		for (Entry<String, Doctor> doctor: doctors.entrySet()){
			Doctor doc = doctor.getValue();
			if (doc.getName().equals(name)){
				return doc;
			}
		}
		return null;
	}
	
	public Patient getPatientById(String id){
		return patients.get(id);
	}
	
	public Patient getPatientByName(String name){
		for (Entry<String, Patient> patient: patients.entrySet()){
			Patient pat = patient.getValue();
			if (pat.getName().equals(name)){
				return pat;
			}
		}
		return null;
	}
	
	public List<Appointment> getDoctorAppointmentByDay(Doctor doc, LocalDate date){
		List<Appointment> list = new ArrayList<Appointment>();
		
		HashMap<LocalDateTime, Appointment> all = doc.getAppointmentList();
		for (Entry<LocalDateTime, Appointment> app : all.entrySet()){
			if (app.getKey().toLocalDate().isEqual(date)){
				list.add(app.getValue());
			}
		}
		
		return list;
	}
}
