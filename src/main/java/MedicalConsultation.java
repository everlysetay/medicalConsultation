package main.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import main.java.controller.AppointmentScheduler;
import main.java.controller.CsvImporter;
import main.java.db.Database;

public class MedicalConsultation {

	public static void main(String args[]){
		boolean end = false;
		
		System.out.println("=================== Welcome to Medical Consultation Booking System =====================");
		
		//import appointment settings -- allow other sets of data to be allowed into the system
		Database db = new Database();
		if (args.length <= 0){
			//read database from system path
			Path path = Paths.get(System.getProperty("user.dir") +"/resource/data.csv");

			CsvImporter ci = new CsvImporter(path);
			db = ci.getDb();
		} else {
			System.out.println("read from csv file");
		}
		
		AppointmentScheduler as = new AppointmentScheduler(db);
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
					as.getDoctorSchedule();
					break;
				case "2":
					break;
				case "3":
					break;
				case "admin-hidden":
					break;
				case "esc":
					end = true;
					break;
				default:
					System.out.println();
			}
			
			if (end){
				sc.close();
			}
		}
		
		System.out.println(" ================ Thank you for using Medical Consultation Booking System ===============");
	}

}
