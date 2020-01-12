package test.java.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.java.controller.PrintFunctions;
import main.java.db.PatientDatabase;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class TestPrintFunctions {

	PrintFunctions pf;
	
	@Before
	public void init(){
		pf = new PrintFunctions();
	}
	
	@Test
	public void TestPrintDoctorList_Correct(){
		List<Doctor> list = new ArrayList<Doctor>();
		
		Doctor d1 = new Doctor(1, "D1Name");
		list.add(d1);
		Doctor d2 = new Doctor(2, "D2Name");
		list.add(d2);
		
		String result = "ID\tName\n"
				+ "D1\tD1Name\n"
				+ "D2\tD2Name\n";
		
		assertEquals(result, pf.printDoctorList(list));
	}
	
	//similar id for doctor will get overwritten
	@Test
	public void TestPrintDoctorList_DuplicateDoctor(){
		HashMap<String, Doctor> map = new HashMap<String, Doctor>();
		
		Doctor d1 = new Doctor(1, "D1Name");
		map.put("D1", d1);
		Doctor d2 = new Doctor(1, "D2Name");
		map.put("D1", d2);
		
		String result = "ID\tName\n"
				+ "D1\tD2Name\n";
		
		List<Doctor> list = new ArrayList<Doctor>(map.values());
		
		assertEquals(result, pf.printDoctorList(list));
	}
	
	//Print appointment List -- correct
	@Test
	public void TestPrintAppointmentList_Correct1Input(){
		PatientDatabase patDb = new PatientDatabase();
		List<Appointment> appList = new ArrayList<Appointment>();
		
		LocalDateTime now = LocalDateTime.now();
		Appointment app = new Appointment(1, now, "D1", "P1");
		appList.add(app);
		
		HashMap<String, Patient> patientList = new HashMap<String, Patient>();
		Patient patient = new Patient("P1Name", 12, 'F');
		patientList.put("P1", patient);
		patDb.setDatabase(patientList);
		
		String result = "S/N		Time		Name		Age\n"
				+ "1\t\t"+now.toLocalTime()+"\t\tP1Name\t\t12\n";
		
		assertEquals(result, pf.printAppointmentList(appList, patDb));
	}
	
	//Print appointment List -- Patient not in PatientDb
	@Test
	public void TestPrintAppointmentList_PatientNotInDatabase() {
		PatientDatabase patDb = new PatientDatabase();
		List<Appointment> appList = new ArrayList<Appointment>();
		
		LocalDateTime now = LocalDateTime.now();
		Appointment app = new Appointment(1, now, "D1", "P2");
		appList.add(app);

		HashMap<String, Patient> patientList = new HashMap<String, Patient>();
		Patient patient = new Patient("P1Name", 12, 'F');
		patientList.put("P1", patient);
		patDb.setDatabase(patientList);
		
		String result = "S/N		Time		Name		Age\n"
				+ "1\t\t"+now.toLocalTime()+"\n";
		
		assertEquals(result, pf.printAppointmentList(appList, patDb));
	}
	
	//Print appointment List -- no appointment
	@Test
	public void TestPrintAppointmentList_CorrectNoAppointment(){
		PatientDatabase patDb = new PatientDatabase();
		List<Appointment> appList = new ArrayList<Appointment>();
		
		HashMap<String, Patient> patientList = new HashMap<String, Patient>();
		Patient patient = new Patient("P1Name", 12, 'F');
		patientList.put("P1", patient);
		patDb.setDatabase(patientList);
		
		String result = "No appointment have been made";
		
		assertEquals(result, pf.printAppointmentList(appList, patDb));
	}
	
	//Print timeslot -- correct, have timeslot
	@Test
	public void TestPrintTimeSlot_haveTimeslot(){
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(8);
		list.add(9);
		
		String result = "Slot	Time\n"
				+ "1\t8:00\n"
				+ "2\t9:00\n";
		
		assertEquals(result, pf.printTimeSlot(list));
	}
	
	//Print timeslot -- correct, no timeslot
	@Test
	public void TestPrintTimeSlot_noTimeslot(){
		List<Integer> list = new ArrayList<Integer>();
		
		String result = "No timeslot available, please select another day";
		
		assertEquals(result, pf.printTimeSlot(list));
	}
	
}
