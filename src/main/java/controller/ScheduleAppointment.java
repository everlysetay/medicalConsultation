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

public class ScheduleAppointment {

	AppointmentDatabase appDb;
	DoctorDatabase docDb;
	PatientDatabase patDb;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

	PrintFunctions pf = new PrintFunctions();
	
	public ScheduleAppointment(AppointmentDatabase ad, DoctorDatabase dd, PatientDatabase pd){
		this.appDb = ad;
		this.docDb = dd;
		this.patDb = pd;
	}
	
	public void getDoctorSchedule(Scanner sc){
		
		boolean validDoc = false;
		Doctor doc = null;
		while (!validDoc){
			System.out.print("Please input the doctor's id: " );
			String doctorId = sc.nextLine(); 
			
			if (doctorId.equals("esc"))
				return;
			
			doc = docDb.getDoctorById(doctorId);
			if (doc == null){
				System.out.println("Invalid doctor inputted");
			} else {
				validDoc = true;
			}
		}
		
		boolean validDate = false;
		while (!validDate) {
			System.out.print("Please type in appointment date(e.g.31012020) for " + doc.getName() + ": ");
			String input = sc.nextLine();
			
			if (input.equals("esc"))
				return;
			
			try {
				LocalDate date = LocalDate.parse(input, formatter);
				// search for all doctor's appointment on that day
				List<Appointment> app = appDb.getDoctorAppointmentByDay(doc, date, null);
				System.out.println();
				System.out.println("Schedule for " + doc.getName() + " on " + date.toString() + ":");
				System.out.println(pf.printAppointmentList(app, patDb));
				
				validDate = true;
				
			} catch (Exception e){
				System.out.println(e);
				System.out.println("Unrecognised date format");
			}
		}

	}
	
	
	
	
	
	
}
