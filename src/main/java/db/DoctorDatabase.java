package main.java.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import main.java.model.Doctor;

public class DoctorDatabase {
	
	HashMap<String, Doctor> doctors = new HashMap<String, Doctor>();
	
	public void setDatabase(HashMap<String, Doctor> doctors){
		this.doctors = doctors;
	}
	
	public List<Doctor> getDoctorList(){
		return new ArrayList<Doctor>(doctors.values());
	}
	
	public Doctor getDoctorById(String id){
		return doctors.get(id);
	}
	
	public Doctor getDoctorByName(String name){
		for (Entry<String, Doctor> doctor: doctors.entrySet()){
			Doctor doc = doctor.getValue();
			if (doc.getName().equals(name)){
				return doc;
			}
		}
		return null;
	}
}
