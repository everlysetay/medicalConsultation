package main.java.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import main.java.db.AppointmentDatabase;
import main.java.db.DoctorDatabase;
import main.java.db.PatientDatabase;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class CreateAppointment {
	
	AppointmentDatabase appDb;
	DoctorDatabase docDb;
	PatientDatabase patDb;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	
	PrintFunctions pf = new PrintFunctions();
	
	public CreateAppointment(AppointmentDatabase ad, DoctorDatabase dd, PatientDatabase pd){
		this.appDb = ad;
		this.docDb = dd;
		this.patDb = pd;
	}
	
	public void fixAppointmentByPatient(Scanner sc){
		System.out.println("Which doctor would you like to fix an appointment with: ");
		//print doctor list
		List<Doctor> list = docDb.getDoctorList();
		System.out.println(pf.printDoctorList(list));
		
		
		System.out.print("Select ID: ");
		String input = sc.nextLine();
		
		Doctor doc = docDb.getDoctorById(input);
		
		System.out.print("Select a date (e.g. 31012020) for appointment with " + doc.getName() +": ");
		String keyedDate = sc.nextLine();
		
		try {
			LocalDate requiredDate = LocalDate.parse(keyedDate, formatter);
			System.out.print("Please choose a following timeslot for " + requiredDate + " :");
			List<Integer> timeslot = appDb.getDoctorAvailableSlot(doc, requiredDate);
			System.out.println(pf.printTimeSlot(timeslot));
			
			System.out.print("Select timeslot: ");
			String slot = sc.nextLine();
			
			System.out.print("Could you like to make the following appointment(Y/N)? ");
			String confirm = sc.nextLine();
			if (confirm.equalsIgnoreCase("Y")){
				
				System.out.print("Please input your patient ID: ");
				String patientId = sc.nextLine();
				
				Patient pat = patDb.getPatientById(patientId);
				if (pat != null){
					//request again or quit
				}
				
				Appointment app = new Appointment(appDb.getIdForNewAppointment(), requiredDate, timeslot.get(Integer.valueOf(slot) - 1), doc.getId(), pat.getId());
				appDb.addNewAppointment(app, doc.getId());
			} else {
				//reselect
			}
		} catch (Exception e) {
			
		}
	}
}
