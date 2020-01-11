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
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy hh:mm:ss");

	
	public AppointmentScheduler(Database db){
		this.db = db;
	}
	
	public void getDoctorSchedule(Scanner sc){
		
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
	
	public void fixAppointmentByPatient(Scanner sc){
		System.out.println("Which doctor would you like to fix an appointment with: ");
		//print doctor list
		List<Doctor> list = db.getDoctorList();
		printDoctorList(list);
		
		
		System.out.print("Select ID: ");
		String input = sc.nextLine();
		
		Doctor doc = db.getDoctorById(input);
		
		System.out.print("Select a date (e.g. 31012020) for appointment with " + doc.getName() +": ");
		String keyedDate = sc.nextLine();
		
		LocalDate date = LocalDate.parse(keyedDate, formatter);
		
		System.out.print("Please choose a following timeslot for " + date + " :");
		List<Integer> timeslot = db.getDoctorAvailableSlot(doc, date);
		this.printTimeSlot(timeslot);
		
		System.out.print("Select timeslot: ");
		String slot = sc.nextLine();
		
		System.out.println("Could you like to make the following appointment(Y/N)? ");
		String confirm = sc.nextLine();
		if (confirm.equalsIgnoreCase("Y")){
			
			System.out.print("Please input your patient ID: ");
			String patientId = sc.nextLine();
			
			Patient pat = db.getPatientById(patientId);
			if (pat != null){
				//request again or quit
			}
			
			Appointment app = new Appointment(db.getIdForNewAppointment(), date, timeslot.get(Integer.valueOf(slot) - 1), doc.getId(), pat.getId());
			db.addNewAppointment(app, doc.getId());
		} else {
			//reselect
		}
	}
	
	public void printDoctorList(List<Doctor> list){
		System.out.println("ID	Name");
		for (Doctor doc : list){
			System.out.println(doc.getId() + "\t" + doc.getName());
		}
		System.out.println();
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
	
	public void printTimeSlot(List<Integer> list){
		System.out.println("Slot	Time");
		int count = 1;
		for (Integer str: list){
			System.out.println(count + "\t" + str + ":00");
			count++;
		}
		
		System.out.println();
	}
}
