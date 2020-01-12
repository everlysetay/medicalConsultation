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

public class CancelAppointment {
	
	AppointmentDatabase appDb;
	DoctorDatabase docDb;
	PatientDatabase patDb;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	
	PrintFunctions pf = new PrintFunctions();

	public CancelAppointment(AppointmentDatabase ad, DoctorDatabase dd, PatientDatabase pd){
		this.appDb = ad;
		this.docDb = dd;
		this.patDb = pd;
	}
	
	public void cancelAppointment(Scanner sc){
		System.out.println("Which doctor would you like to cancel an appointment with: ");
		//print doctor list
		List<Doctor> list = docDb.getDoctorList();
		pf.printDoctorList(list);
		
		
		System.out.print("Select ID: ");
		String input = sc.nextLine();
		
		Doctor doc = docDb.getDoctorById(input);
		
		System.out.print("Select a date (e.g. 31012020) for appointment with " + doc.getName() +": ");
		String keyedDate = sc.nextLine();
		
		LocalDate date = LocalDate.parse(keyedDate, formatter);
		
		System.out.print("Please input your patient ID: ");
		String patientId = sc.nextLine();
		
		Patient pat = patDb.getPatientById(patientId);
		if (pat != null){
			//request again or quit
		}
		
		//retrieve doctor's timeslot with patient
		List<Appointment> appList = appDb.getDoctorAppointmentByDay(doc, date, pat);
		if (appList.size() < 1) {
			System.out.println("You do not have any appointment on this day");
		} else {
			pf.printAppointmentList(appList, patDb);
			
			System.out.print("Please select the S/N you would like to cancel: ");
			String slot = sc.nextLine();
			
			//cancel this input
			System.out.print("Confirm cancellation of the following booking(Y/N): ");
			String confirm = sc.nextLine();
			if (confirm.equalsIgnoreCase("Y")){
				Appointment app = appList.get(Integer.valueOf(slot) - 1);
				appDb.removeAppointment(app, doc.getId());
			} else {
				//recheck
			}
		}	
	}
}
