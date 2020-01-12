package main.java.controller;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import main.java.db.AppointmentDatabase;
import main.java.db.DoctorDatabase;
import main.java.db.PatientDatabase;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class CsvImporter {

	HashMap<String, Doctor> doctors = new HashMap<String, Doctor>();
	HashMap<String, Patient> patients = new HashMap<String, Patient>();
	ArrayList<Appointment> appointments = new ArrayList<Appointment>();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHH:mm:ss");
	AppointmentDatabase ad = new AppointmentDatabase();
	DoctorDatabase dd = new DoctorDatabase();
	PatientDatabase pd = new PatientDatabase();
	
	//return initial database of appointments
	public CsvImporter(Path path){
		this.extractInformation(path);
	}
	
	public AppointmentDatabase getAppointmentDb(){
		return ad;
	}
	
	public DoctorDatabase getDoctorsDb(){
		return dd;
	}
	
	public PatientDatabase getPatientDb(){
		return pd;
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
						if (!doctors.containsKey(fields[0]) && doctorId.contains("D")) {
							int id = Integer.valueOf(doctorId.replace("D", ""));
							String name = fields[1];
							Doctor doc = new Doctor(id, name);
							
							doctors.put(doctorId, doc);
						} 
						
						String patientId = fields[2];
						if (!patients.containsKey(fields[2]) && patientId.contains("P")){
							int id = Integer.valueOf(patientId.replace("P", ""));
							String name = fields[3];
							int age = Integer.valueOf(fields[4]);
							char gender = fields[5].charAt(0);
							
							Patient patient = new Patient(name, age, gender);
							patient.setPatientId(id);
							
							patients.put(patientId, patient);
						}
						
						String appointmentId = fields[6];
						int id = Integer.valueOf(appointmentId.replace("A", ""));
						LocalDateTime date = LocalDateTime.parse(fields[7], formatter);
						
						Appointment appointment = new Appointment(id, date, doctorId, patientId);						
						appointments.add(appointment);
					} 
					//if header, do not save -- skip line
				}				
				line = br.readLine();
			}
			
			
		} catch (Exception e){
			System.out.println("Unable to load CSV data file");
		}
		
		ad.setDatabase(appointments);
		dd.setDatabase(doctors);
		pd.setDatabase(patients);
	}
}
