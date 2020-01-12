package main.java.controller;

import java.time.LocalDateTime;
import java.util.List;

import main.java.db.PatientDatabase;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class PrintFunctions {

	public String printDoctorList(List<Doctor> list){
		String result = "ID\tName\n";
		for (Doctor doc : list){
			result += doc.getId() + "\t" + doc.getName() + "\n";
		}
		return result;
	}
	
	public String printAppointmentList(List<Appointment> app, PatientDatabase patDb){
		int count = 1;
		String result = "";
		if (app.size() > 0){
			result += "S/N		Time		Name		Age\n";
			for (Appointment appointment: app){
				Patient patient = patDb.getPatientById(appointment.getPatientId());
				if (patient != null){
					//patient should not be null
					LocalDateTime datetime = appointment.getDateTime();
					result += count + "\t\t" +datetime.toLocalTime() + "\t\t" + patient.getName() + "\t\t" + patient.getAge()+"\n";
					count++;
				}	else {
					LocalDateTime datetime = appointment.getDateTime();
					result += count + "\t\t" +datetime.toLocalTime() + "\n";
				}
			}
		} else {
			return "No appointment have been made";
		}
		return result;
	}
	
	public String printTimeSlot(List<Integer> list){
		String result = "";
		if (list.size() > 0){
			result += "Slot	Time\n";
			int count = 1;
			for (Integer str: list){
				result += count + "\t" + str + ":00\n";
				count++;
			}
		} else {
			return "No timeslot available, please select another day";
		}
		return result;
	}
}
