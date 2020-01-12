package main.java.controller;

import java.time.LocalDate;
import java.util.Scanner;

import main.java.model.Doctor;

public class GetAppointmentForDoctor {
		
	PrintFunctions pf = new PrintFunctions();
	
	public void getDoctorSchedule(Scanner sc, ScheduleAppointment sa){
		
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
		
		sa.getDoctorAppointmentList(doc, date);
	}
}
