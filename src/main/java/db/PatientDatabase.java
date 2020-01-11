package main.java.db;

import java.util.HashMap;
import java.util.Map.Entry;

import main.java.model.Patient;

public class PatientDatabase {
	HashMap<String, Patient> patients = new HashMap<String, Patient>();

	public void setDatabase(HashMap<String, Patient> patients){
		this.patients = patients;
	}
	
	public Patient getPatientById(String id){
		return patients.get(id);
	}
	
	public Patient getPatientByName(String name){
		for (Entry<String, Patient> patient: patients.entrySet()){
			Patient pat = patient.getValue();
			if (pat.getName().equals(name)){
				return pat;
			}
		}
		return null;
	}
}
