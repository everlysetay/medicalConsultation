package main.java.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class Database {

	HashMap<String, Doctor> doctors = new HashMap<String, Doctor>();
	HashMap<String, Patient> patients = new HashMap<String, Patient>();
	List<Appointment> appointments = new ArrayList<Appointment>();

	public Database(){
		
	}
	
	public void setDatabase(HashMap<String, Doctor> doctors, 
			HashMap<String, Patient> patients, 
			List<Appointment> appointments){
		this.doctors = doctors;
		this.patients = patients;
		this.appointments = appointments;
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
	
	public List<Doctor> getDoctorList(){
		return new ArrayList<Doctor>(doctors.values());
	}
	
	public Doctor getDoctorById(String id){
		return doctors.get(id);
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
	
	public List<Integer> getDoctorAvailableSlot(Doctor doc, LocalDate date){
		List<Appointment> scheduled = this.getDoctorAppointmentByDay(doc, date);
		List<Integer> list = new LinkedList<Integer>();
		
		//user work from 8 to 16 hrs
		for (int i = 8; i < 16; i++){
			list.add(i);
		}
		
		for (Appointment app: scheduled){
			list.remove(Integer.valueOf(app.getDateTime().getHour()));
		}
		
		return list;
	}
	
	public int getIdForNewAppointment(){
		return appointments.size();
	}
	
	public boolean addNewAppointment(Appointment app, String doctorId){
		appointments.add(app);
		Doctor doc = doctors.get(doctorId);
		doc.setAppointment(app.getDateTime(), app);
		return true;
	}
}
