package main.java;

import java.nio.file.Path;
import java.nio.file.Paths;

import main.java.controller.CsvImporter;
import main.java.db.Database;

public class MedicalConsultation {

	public static void main(String args[]){
		boolean end = false;
		
		System.out.println("Medical Consultation Booking System");
		
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
		
		while (!end){
			
		}
	}

}
