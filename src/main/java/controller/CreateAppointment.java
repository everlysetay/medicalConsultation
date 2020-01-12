package main.java.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import main.java.model.Doctor;
import main.java.model.Patient;

public class CreateAppointment {
	
	PrintFunctions pf = new PrintFunctions();
	
	public void fixAppointmentByPatient(Scanner sc, ScheduleAppointment sa){
		System.out.println("Which doctor would you like to fix an appointment with: ");
		//print doctor list
		List<Doctor> list = sa.getDoctorList();
		System.out.println(pf.printDoctorList(list));
		
		Doctor doc;
		try {
			doc = sa.getDoctor(sc);
		} catch (Exception e){
			return;
		}
		
		LocalDate date;
		try {
			date = sa.getDate(sc, doc);
		} catch(Exception e){
			return;
		}

		
		int time;
		try {
			time = sa.getTime(sc, doc, date);
		} catch (Exception e){
			return;
		}
		
		Patient pat;
		try {
			pat = sa.getPatient(sc); 
		} catch (Exception e){
			return;
		}
		
		
		boolean result = sa.scheduleAppointment(sc, date, time, doc, pat);
		if (result){
			System.out.println("Appointment made with " + doc.getName() + " on " + date + " " + time);
		}	else {
			return;
		}
	}
}
