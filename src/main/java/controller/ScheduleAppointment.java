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

	public List<Doctor> getDoctorList(){
		return docDb.getDoctorList();
	}
	
	public Doctor getDoctor(Scanner sc) throws Exception{
		boolean validDoc = false;
		Doctor doc = null;
		while (!validDoc){
			System.out.print("Please input the doctor's id: " );
			String doctorId = sc.nextLine(); 
			
			if (doctorId.equals("esc"))
				throw new Exception();
			
			doc = docDb.getDoctorById(doctorId);
			if (doc == null){
				System.out.println("Invalid doctor inputted");
			} else {
				validDoc = true;
			}
		}
		return doc;
	}
	
	
	public LocalDate getDate(Scanner sc, Doctor doc) throws Exception{
		boolean validDate = false;
		LocalDate date = null;
		while (!validDate) {
			System.out.print("Please type in appointment date(e.g.31012020) for " + doc.getName() + ": ");
			String input = sc.nextLine();
			
			if (input.equals("esc"))
				throw new Exception();
			
			try {
				date = LocalDate.parse(input, formatter);
				// search for all doctor's appointment on that day				
				validDate = true;				
			} catch (Exception e){
				System.out.println("Unrecognised date format, please try again");
			}
		}
		return date;
	}
	
	public int getTime(Scanner sc, Doctor doc, LocalDate date) throws Exception{
		boolean validTimeSlot = false;
		
		int time = 0;
		while (!validTimeSlot){
			System.out.print("Please choose a following timeslot for " + date + " :");
			List<Integer> timeslot = appDb.getDoctorAvailableSlot(doc, date);
			System.out.println(pf.printTimeSlot(timeslot));
			
			System.out.print("Select timeslot: ");
			String slot = sc.nextLine();
			
			if (slot.equals("esc"))
				throw new Exception();
			
			int selected = Integer.valueOf(slot) - 1;
			if (-1 < selected  && selected < timeslot.size()){
				validTimeSlot = true;
				time = timeslot.get(selected);
			} else {
				System.out.println("Please choose a slot within the above mentioned timing");
			}
		}
		return time;
	}

	public Patient getPatient(Scanner sc) throws Exception{
		boolean validPatient = false;
		
		Patient pat = null;
		while (!validPatient){
			
			System.out.print("Please input your patient ID: ");
			String input = sc.nextLine();
			
			if (input.equals("esc"))
				throw new Exception();
			
			pat = patDb.getPatientById(input);
			if (pat != null){
				//request again or quit
				validPatient = true;
			} else {
				System.out.println("Database does not have records of patient. Please enter a valid userid");
			}
		}
		return pat;
	}

	public boolean scheduleAppointment(Scanner sc, LocalDate date, int time, Doctor doc, Patient pat){
		System.out.print("Would you like to make the following appointment(Y/N)? ");
		String confirm = sc.nextLine();
		
		if (confirm.equalsIgnoreCase("Y")){
			int appId = appDb.getIdForNewAppointment();
			Appointment app = new Appointment(appId, date, time, doc.getId(), pat.getId());
			appDb.addNewAppointment(app, doc.getId());
			return true;
		} else {
			return false;
		}
	}
	
	public Appointment getAppointmentWithDoctorForPatient(Scanner sc, Doctor doc, LocalDate date, Patient pat) throws Exception{
		Appointment app = null;
		boolean validSlot = false;
		List<Appointment> appList = appDb.getDoctorAppointmentByDay(doc, date, pat);
		if (appList.size() < 1) {
			System.out.println("You do not have any appointment with " + doc.getName() + " on this day");
			return null;
		} else {
			while (!validSlot) {
				System.out.println(pf.printAppointmentList(appList, patDb));
				
				System.out.print("Please select the appointment S/N that you would like to edit: ");
				String input = sc.nextLine();
				
				if (input.equals("esc"))
					throw new Exception();
				
				int selected = Integer.valueOf(input) - 1;
				if ( -1 < selected && selected < appList.size()) {
					//valid
					validSlot = true;
					app = appList.get(selected);
				} else {
					System.out.println("Invalid appointment selected. Please try again.");
				}
			}
		}
		
		return app;
	}
	
	public boolean cancelAppointment(Scanner sc, Doctor doc, Appointment app){
		System.out.print("Confirm cancellation of the following booking(Y/N): ");
		String confirm = sc.nextLine();
		if (confirm.equalsIgnoreCase("Y")){
			appDb.removeAppointment(app, doc.getId());
			return true;
		} else {
			System.out.println("Return back to main menu");
			return false;
		}
	}
	
	public boolean getDoctorAppointmentList(Doctor doc,LocalDate date){
		List<Appointment> app = appDb.getDoctorAppointmentByDay(doc, date, null);
		if (app.size() > 0) {
			System.out.println();
			System.out.println("Schedule for " + doc.getName() + " on " + date.toString() + ":");
			System.out.println(pf.printAppointmentList(app, patDb));
			return true;
		} else {
			return false;
		}
	}
}
