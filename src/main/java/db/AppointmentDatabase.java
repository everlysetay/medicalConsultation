package main.java.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class AppointmentDatabase {

	List<Appointment> appointments = new ArrayList<Appointment>();

	
	public void setDatabase(
			List<Appointment> appointments){
		this.appointments = appointments;
	}

	public int getDbSize(){
		return appointments.size();
	}
	
	public List<Appointment> getDoctorAppointmentByDay(Doctor doc, LocalDate date, Patient pat){
		LinkedHashMap<LocalDateTime, Appointment> list = new LinkedHashMap<LocalDateTime, Appointment>();
		
		for (Appointment app: appointments) {
			if (app.getDoctorId().equals(doc.getId())){
				if (app.getDateTime().toLocalDate().isEqual(date)){
					if (pat == null){
						list.put(app.getDateTime(), app);
					} else {
						if (pat.getId().equals(app.getPatientId())){
							list.put(app.getDateTime(), app);
						}
					}
				}
			}
		}
		
		return new ArrayList<Appointment>(list.values());
	}
	
	public List<Integer> getDoctorAvailableSlot(Doctor doc, LocalDate date){
		List<Appointment> scheduled = this.getDoctorAppointmentByDay(doc, date, null);
		List<Integer> list = new LinkedList<Integer>();
		
		//user work from 8 to 16 hrs
		for (int i = 8; i < 16; i++){
			list.add(i);
		}
		
		for (Appointment app: scheduled){
			list.remove(Integer.valueOf(app.getDateTime().getHour()));
		}
		
		return list;
	}
	
	public int getIdForNewAppointment(){
		return appointments.size();
	}
	
	public boolean addNewAppointment(Appointment app, String doctorId){
		appointments.add(app);
//		Doctor doc = doctors.get(doctorId);
//		doc.setAppointment(app.getDateTime(), app);
		return true;
	}
	
	public boolean removeAppointment(Appointment app, String doctorId){
		appointments.remove(app);
//		Doctor doc = doctors.get(doctorId);
		
		return true;
	}
}
