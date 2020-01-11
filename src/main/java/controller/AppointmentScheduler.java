package main.java.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import main.java.db.AppointmentDatabase;
import main.java.db.DoctorDatabase;
import main.java.db.PatientDatabase;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class AppointmentScheduler {

	AppointmentDatabase appDb;
	DoctorDatabase docDb;
	PatientDatabase patDb;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy hh:mm:ss");

	
	public AppointmentScheduler(AppointmentDatabase ad, DoctorDatabase dd, PatientDatabase pd){
		this.appDb = ad;
		this.docDb = dd;
		this.patDb = pd;
	}
	
	public void getDoctorSchedule(Scanner sc){
		
		boolean validDoc = false;
		Doctor doc = null;
		while (!validDoc){
			System.out.print("Please input the doctor's name: " );
			String doctorName = sc.nextLine(); 
			
			if (doctorName.equals("esc"))
				return;
			
			doc = docDb.getDoctorByName(doctorName);
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
		List<Doctor> list = docDb.getDoctorList();
		printDoctorList(list);
		
		
		System.out.print("Select ID: ");
		String input = sc.nextLine();
		
		Doctor doc = docDb.getDoctorById(input);
		
		System.out.print("Select a date (e.g. 31012020) for appointment with " + doc.getName() +": ");
		String keyedDate = sc.nextLine();
		
		try {
			LocalDate requiredDate = LocalDate.parse(keyedDate, formatter);
			System.out.print("Please choose a following timeslot for " + requiredDate + " :");
			List<Integer> timeslot = appDb.getDoctorAvailableSlot(doc, requiredDate);
			this.printTimeSlot(timeslot);
			
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
	
	public void cancelAppointment(Scanner sc){
		System.out.println("Which doctor would you like to cancel an appointment with: ");
		//print doctor list
		List<Doctor> list = docDb.getDoctorList();
		printDoctorList(list);
		
		
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
			this.printAppointmentList(appList);
			
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
	
	public void printDoctorList(List<Doctor> list){
		System.out.println("ID	Name");
		for (Doctor doc : list){
			System.out.println(doc.getId() + "\t" + doc.getName());
		}
		System.out.println();
	}
	
	public void printAppointmentList(List<Appointment> app){
		int count = 1;
		if (app.size() > 0){
			System.out.println("S/N		Time		Name		Age");
			for (Appointment appointment: app){
				Patient patient = patDb.getPatientById(appointment.getPatientId());
				LocalDateTime datetime = appointment.getDateTime();
				System.out.println(count + "\t\t" +datetime.toLocalTime() + "\t\t" + patient.getName() + "\t\t" + patient.getAge());
				count++;
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
