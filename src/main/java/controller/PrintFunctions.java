package main.java.controller;

import java.time.LocalDateTime;
import java.util.List;

import main.java.db.PatientDatabase;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class PrintFunctions {

	public void printDoctorList(List<Doctor> list){
		System.out.println("ID	Name");
		for (Doctor doc : list){
			System.out.println(doc.getId() + "\t" + doc.getName());
		}
		System.out.println();
	}
	
	public void printAppointmentList(List<Appointment> app, PatientDatabase patDb){
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
