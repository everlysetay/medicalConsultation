package main.java.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import main.java.db.Database;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class AppointmentScheduler {

	Database db;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	
	public AppointmentScheduler(Database db){
		this.db = db;
	}
	
	public void getDoctorSchedule(){
		Scanner sc = new Scanner(System.in);
		
		boolean validDoc = false;
		Doctor doc = null;
		while (!validDoc){
			System.out.print("Please input the doctor's name: " );
			String doctorName = sc.nextLine(); 
			
			if (doctorName.equals("esc"))
				return;
			
			doc = db.getDoctorByName(doctorName);
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
				List<Appointment> app = db.getDoctorAppointmentByDay(doc, date);
				System.out.println();
				System.out.println("Schedule for " + doc.getName() + " on " + date.toString() + ":");
				this.printAppointmentList(app);
				
				validDate = true;
				
			} catch (Exception e){
				System.out.println(e);
				System.out.println("Unrecognised date format");
			}
		}

	}
	
	public void printAppointmentList(List<Appointment> app){
		if (app.size() > 0){
			System.out.println("Time		Name		Age");
			for (Appointment appointment: app){
				Patient patient = db.getPatientById(appointment.getPatient());
				LocalDateTime datetime = appointment.getDateTime();
				System.out.println(datetime.toLocalTime() + "\t\t" + patient.getName() + "\t\t" + patient.getAge());
			}
		} else {
			System.out.println("No appointment have been made");
		}
		System.out.println();
	}
}
