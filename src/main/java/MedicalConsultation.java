package main.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import main.java.controller.CancelAppointment;
import main.java.controller.CreateAppointment;
import main.java.controller.CsvImporter;
import main.java.controller.GetAppointmentForDoctor;
import main.java.controller.ScheduleAppointment;
import main.java.db.AppointmentDatabase;
import main.java.db.DoctorDatabase;
import main.java.db.PatientDatabase;

public class MedicalConsultation {

	public static void main(String args[]){
		boolean end = false;
		
		System.out.println("=================== Welcome to Medical Consultation Booking System =====================");
		
		//import appointment settings -- allow other sets of data to be allowed into the system
		AppointmentDatabase appDb = new AppointmentDatabase();
		DoctorDatabase docDb = new DoctorDatabase();
		PatientDatabase patDb = new PatientDatabase();
		Path path = null;
		if (args.length > 0){
			path = Paths.get(System.getProperty("user.dir") + "/" + args[0]);
		} else {
			//read database from system path
			path = Paths.get(System.getProperty("user.dir") +"/resource/data.csv");
		}
		
		CsvImporter ci = new CsvImporter(path);
		appDb = ci.getAppointmentDb();
		docDb = ci.getDoctorsDb();
		patDb = ci.getPatientDb();
		
		ScheduleAppointment sa = new ScheduleAppointment(appDb, docDb, patDb);
		GetAppointmentForDoctor ad = new GetAppointmentForDoctor();
		CreateAppointment ca = new CreateAppointment();
		CancelAppointment cancel = new CancelAppointment();
		
		//take show main menu
		while (!end){	
			Scanner sc = new Scanner(System.in);
			//write main menu
			System.out.println("Select one of the following options:");
			System.out.println("1. Check all appointments for a given doctor & date");
			System.out.println("2. Fix an appointment");
			System.out.println("3. Cancel appointment");
			System.out.println("Type 'esc' anytime to stop the system");
			
			System.out.println();
			System.out.print("Select Action:  ");
			String input = sc.nextLine();
			
			switch(input.toLowerCase().replace(" ", "")){
				case "1":
					ad.getDoctorSchedule(sc, sa);
					System.out.println();
					break;
				case "2":
					ca.fixAppointmentByPatient(sc, sa);
					System.out.println();
					break;
				case "3":
					cancel.cancelAppointment(sc, sa);
					System.out.println();
					break;
				case "esc":
					end = true;
					break;
				default:
					System.out.println();
			}
			
			if (end){
				//save current db into csv
				sc.close();
			}
		}
		
		System.out.println(" ================ Thank you for using Medical Consultation Booking System ===============");
	}

}
