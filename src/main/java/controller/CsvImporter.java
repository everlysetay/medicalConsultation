package main.java.controller;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.db.Database;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class CsvImporter {

	HashMap<String, Doctor> doctors = new HashMap<String, Doctor>();
	HashMap<String, Patient> patients = new HashMap<String, Patient>();
	HashMap<String, List<Appointment>> appointments = new HashMap<String, List<Appointment>>();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHH:mm:ss");
	Database db = new Database();
	
	//return initial database of appointments
	public CsvImporter(Path path){
		this.extractInformation(path);
	}
	
	public Database getDb(){
		return db;
	}
	
	public void extractInformation(Path path){
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.US_ASCII)){
			String line = br.readLine();
			
			while (line != null) {
				if (!line.contains("doctor_id")) {
					
					String[] fields = line.replace(" ", "").split(",");
					if (fields.length == 8) {
						//correct input
						String doctorId = fields[0];
						if (!doctors.containsKey(fields[0])) {
							int id = Integer.valueOf(doctorId.replace("D", ""));
							String name = fields[1];
							Doctor doc = new Doctor(id, name);
							
							doctors.put(doctorId, doc);
						} 
						
						String patientId = fields[2];
						if (!patients.containsKey(fields[2])){
							int id = Integer.valueOf(patientId.replace("P", ""));
							String name = fields[3];
							int age = Integer.valueOf(fields[4]);
							char gender = fields[5].charAt(0);
							
							Patient patient = new Patient(name, age, gender);
							patient.setPatientId(id);
							
							patients.put(patientId, patient);
						}
						
						String appointmentId = fields[6];
						int id = Integer.valueOf(fields[6].replace("A", ""));
						LocalDateTime date = LocalDateTime.parse(fields[7], formatter);
						
						Appointment appointment = new Appointment(id, date, doctorId);
						appointment.setPatientId(patientId);
						if (!appointments.containsKey(fields[6])){
							List<Appointment> appList = new ArrayList<Appointment>();
							appList.add(appointment);
							appointments.put(appointmentId, appList);
							
							Doctor doc = doctors.get(doctorId);
							doc.setAppointment(date, appointment);
						} else {
							List<Appointment> list = appointments.get(fields[6]);
							list.add(appointment);
						}
						
					} 
					//if header, do not save
				}				
				line = br.readLine();
			}
			
			
		} catch (Exception e){
			System.out.println(e);
			System.out.println("Unable to load CSV data file");
		}
		
		db.setDatabase(doctors, patients, appointments);
	}
}