package test.java.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.controller.CsvImporter;
import main.java.controller.ScheduleAppointment;
import main.java.model.Appointment;
import main.java.model.Doctor;
import main.java.model.Patient;

public class TestScheduleAppointment {

	ScheduleAppointment sa;
	Scanner sc;
	
	@Before
	public void init(){
		Path path = Paths.get(System.getProperty("user.dir") +"/resource/data.csv");
		CsvImporter ci = new CsvImporter(path);
		
		sa = new ScheduleAppointment(ci.getAppointmentDb(), 
										ci.getDoctorsDb(), 
										ci.getPatientDb());
		sc = new Scanner(System.in);
	}
	
	@After
	public void cleanup(){
		sc = new Scanner(System.in);
	}
	
	@Test
	public void TestGetDoctorList(){
		ArrayList<Doctor> test = new ArrayList<Doctor>();
		Doctor d1 = new Doctor(1, "D1Name");
		Doctor d2 = new Doctor(2, "D2Name");
		test.add(d1);
		test.add(d2);
		List<Doctor> list = sa.getDoctorList();
		
		assertEquals(test.size(), list.size());
		assertEquals(test.get(0).getName(), list.get(0).getName());
		assertEquals(test.get(1).getName(), list.get(1).getName());
	}
	
	@Test
	public void TestGetDoctor_validDoctor(){
		String data = "D1";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(data.getBytes()));
		  sc = new Scanner(System.in);
		} finally {
		  System.setIn(stdin);
		}
		
		try {
			Doctor doc = sa.getDoctor(sc);
			
			assertEquals(doc.getId(), "D1");
		} catch (Exception e) {
			assertTrue(false);
		} 
	}
	
	@Test
	public void TestGetDoctor_esc(){
		String data = "D1";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(data.getBytes()));
		  sc = new Scanner(System.in);
		} finally {
		  System.setIn(stdin);
		}
		
		try {
			Doctor doc = sa.getDoctor(sc);
			
			assertEquals(doc.getId(), "D1");
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void TestGetDate_validInput(){
		String data = "08032018";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(data.getBytes()));
		  sc = new Scanner(System.in);
		  System.out.println(sc.nextLine());
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		try {
			LocalDate date = sa.getDate(sc, doc);
			
			assertEquals(date.getDayOfMonth(), 8);
			assertEquals(date.getMonthValue(), 3);
			assertEquals(date.getYear(), 2018);
		} catch (Exception e) {
//			assertTrue(false);
		}
	}
	

	@Test
	public void TestGetDate_esc(){
		String data = "esc";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(data.getBytes()));
		  sc = new Scanner(System.in);
//		  System.out.println(sc.nextLine());
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		try {
			LocalDate date = sa.getDate(sc, doc);
			
			assertEquals(date.getDayOfMonth(), 8);
			assertEquals(date.getMonthValue(), 3);
			assertEquals(date.getYear(), 2018);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void TestGetTime_validInput(){
		String data = "4";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(data.getBytes()));
		  sc = new Scanner(System.in);
//		  System.out.println(sc.nextLine());
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		LocalDate date = LocalDate.now();
		try {
			int time = sa.getTime(sc, doc, date);
			
			assertEquals(time, 11);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void TestGetTime_esc(){
		String data = "esc";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(data.getBytes()));
		  sc = new Scanner(System.in);
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		LocalDate date = LocalDate.now();
		try {
			int time = sa.getTime(sc, doc, date);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void TestGetPatient_valid(){
		String data = "P1";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(data.getBytes()));
		  sc = new Scanner(System.in);
		} finally {
		  System.setIn(stdin);
		}
		
		try {
			Patient pat = sa.getPatient(sc);
			assertEquals(pat.getId(), "P1");
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	@Test
	public void TestGetPatient_esc(){
		String data = "esc";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(data.getBytes()));
		  sc = new Scanner(System.in);
		} finally {
		  System.setIn(stdin);
		}
		
		try {
			Patient pat = sa.getPatient(sc);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void TestScheduleAppointment_Yes(){
		String input = "Y";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(input.getBytes()));
		  sc = new Scanner(System.in);
 
		} finally {
		  System.setIn(stdin);
		}
		
		LocalDate date = LocalDate.now();
		int time = 10;
		Doctor doc = new Doctor(1, "D1Name");
		Patient pat = new Patient("P1Name", 12, 'M');
		pat.setPatientId(1);
		try {
			boolean result = sa.scheduleAppointment(sc, date, time, doc, pat);
			assertTrue(result);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void TestScheduleAppointment_NotYes(){
		String input = "n";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(input.getBytes()));
		  sc = new Scanner(System.in);
 
		} finally {
		  System.setIn(stdin);
		}
		
		LocalDate date = LocalDate.now();
		int time = 10;
		Doctor doc = new Doctor(1, "D1Name");
		Patient pat = new Patient("P1Name", 12, 'M');
		pat.setPatientId(1);
		try {
			boolean result = sa.scheduleAppointment(sc, date, time, doc, pat);
			assertFalse(result);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void TestGetAppointmentWithDoctorForPatient_NoAppointment(){
		String input = "n";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(input.getBytes()));
		  sc = new Scanner(System.in);
 
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		Patient pat = new Patient("P1Name", 12, 'M');
		pat.setPatientId(1);
		LocalDate date = LocalDate.now();
		
		try {
			Appointment app = sa.getAppointmentWithDoctorForPatient(sc, doc, date, pat);
		
			assertEquals(null, app);
		} catch (Exception e){
			assertTrue(false);
		}
	}
	
	@Test
	public void TestGetAppointmentWithDoctorForPatient_GetAppointment(){
		String input = "1";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(input.getBytes()));
		  sc = new Scanner(System.in);
 
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		Patient pat = new Patient("P1Name", 12, 'M');
		pat.setPatientId(1);
		LocalDate date = LocalDate.of(2018, 3, 8);
		
		try {
			Appointment app = sa.getAppointmentWithDoctorForPatient(sc, doc, date, pat);
		
			assertEquals(8, app.getDateTime().getDayOfMonth());
			assertEquals(3, app.getDateTime().getMonthValue());
			assertEquals(2018, app.getDateTime().getYear());
			
		} catch (Exception e){
			assertTrue(false);
		}
	}
	
	@Test
	public void TestGetAppointmentWithDoctorForPatient_esc(){
		String input = "esc";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(input.getBytes()));
		  sc = new Scanner(System.in);
 
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		Patient pat = new Patient("P1Name", 12, 'M');
		pat.setPatientId(1);
		LocalDate date = LocalDate.of(2018, 3, 8);
		
		try {
			Appointment app = sa.getAppointmentWithDoctorForPatient(sc, doc, date, pat);
			
			assertTrue(false);
		} catch (Exception e){
			assertTrue(true);
		}
	}
	
	@Test
	public void TestCancelAppointment_Yes(){
		String input = "Y";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(input.getBytes()));
		  sc = new Scanner(System.in);
 
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		LocalDate date = LocalDate.of(2018, 3, 8);
		int hour = 9;
		String patId = "P1";
		Appointment app = new Appointment(1, date, hour, doc.getId(), patId);
		
		try {
			boolean result = sa.cancelAppointment(sc, doc, app);
			
			assertTrue(result);
		} catch (Exception e){
			assertTrue(false);
		}
	}
	
	@Test
	public void TestCancelAppointment_No(){
		String input = "n";
		InputStream stdin = System.in;
		try {
		  System.setIn(new ByteArrayInputStream(input.getBytes()));
		  sc = new Scanner(System.in);
 
		} finally {
		  System.setIn(stdin);
		}
		
		Doctor doc = new Doctor(1, "D1Name");
		LocalDate date = LocalDate.of(2018, 3, 8);
		int hour = 9;
		String patId = "P1";
		Appointment app = new Appointment(1, date, hour, doc.getId(), patId);
		
		try {
			boolean result = sa.cancelAppointment(sc, doc, app);
			
			assertFalse(result);
		} catch (Exception e){
			assertTrue(false);
		}
	}
	
	@Test
	public void TestGetDoctorAppointmentList_HaveSchedule(){
		Doctor doc = new Doctor(1, "D1Name");
		LocalDate date = LocalDate.of(2018, 3, 8);

		try {
			boolean result = sa.getDoctorAppointmentList(doc, date);
			
			assertTrue(result);
		} catch (Exception e){
			assertTrue(false);
		}
	}
	
	@Test
	public void TestGetDoctorAppointmentList_NoSchedule(){
		Doctor doc = new Doctor(1, "D1Name");
		LocalDate date = LocalDate.of(2020, 3, 8);

		try {
			boolean result = sa.getDoctorAppointmentList(doc, date);
			
			assertFalse(result);
		} catch (Exception e){
			assertTrue(false);
		}
	}
}
