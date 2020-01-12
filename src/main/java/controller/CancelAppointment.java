package main.java.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class CancelAppointment {
	
	PrintFunctions pf = new PrintFunctions();

	public void cancelAppointment(Scanner sc, ScheduleAppointment sa){
		System.out.println("Which doctor would you like to cancel an appointment with: ");
		//print doctor list
		List<Doctor> list = sa.getDoctorList();
		System.out.println(pf.printDoctorList(list));
		
		Doctor doc;
		try {
			doc = sa.getDoctor(sc);
		} catch (Exception e){
			//exit current menu
			return;
		}
		
		LocalDate date;
		try {
			date = sa.getDate(sc, doc);
		}catch (Exception e){
			//exit current menu
			return;
		}
			
		Patient pat;
		try {
			pat = sa.getPatient(sc);
		}catch (Exception e){
			//exit current menu
			return;
		}
		
		Appointment app;
		try {
			app = sa.getAppointmentWithDoctorForPatient(sc, doc, date, pat);
		} catch (Exception e){
			return;
		}
		
		if (app != null) {
			boolean result = sa.cancelAppointment(sc, doc, app);
			if (result){
				System.out.println("Appointment with " + doc.getName() + " on " + app.getDateTime() + " cancelled");
			} else {
				System.out.println("Return back to main menu");
				return;
			}
		}
		
	}
}
