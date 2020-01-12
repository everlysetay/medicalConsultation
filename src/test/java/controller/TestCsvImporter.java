package test.java.controller;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import main.java.controller.CsvImporter;

public class TestCsvImporter {

	CsvImporter ci;
	
	//Correct CSV input
	@Test
	public void TestCsvImporterCorrectInput_AppointmentDatabaseSize(){
		Path path = Paths.get(System.getProperty("user.dir") +"/resource/data.csv");
		ci = new CsvImporter(path);
		
		//test number of records in database
		assertEquals(ci.getAppointmentDb().getDbSize(), 8);
	}
	
	@Test
	public void TestCsvImporterCorrectInput_DoctorDatabaseSize(){
		Path path = Paths.get(System.getProperty("user.dir") +"/resource/data.csv");
		ci = new CsvImporter(path);
		
		//test number of records in database
		assertEquals(ci.getDoctorsDb().getDbSize(), 2);
	}
	
	@Test
	public void TestCsvImporterCorrectInput_PatientDatabaseSize(){
		Path path = Paths.get(System.getProperty("user.dir") +"/resource/data.csv");
		ci = new CsvImporter(path);
		
		//test number of records in database
		assertEquals(ci.getPatientDb().getDbSize(), 3);
	}
	
	@Test
	public void TestCsvImporter_WrongPath(){
		Path path = Paths.get(System.getProperty("user.dir") +"/data.csv");
		ci = new CsvImporter(path);
		
		//test number of records in database
		assertEquals(ci.getPatientDb().getDbSize(), 0);
		assertEquals(ci.getDoctorsDb().getDbSize(), 0);
		assertEquals(ci.getPatientDb().getDbSize(), 0);
	}
	
	//CSV fields only have 7 fields instead of 8
	@Test
	public void TestCsvImporter_MissingField(){
		Path path = Paths.get(System.getProperty("user.dir") +"/resource/test/data_missingfield.csv");
		ci = new CsvImporter(path);
		
		//test number of records in database
		assertEquals(ci.getPatientDb().getDbSize(), 0);
		assertEquals(ci.getDoctorsDb().getDbSize(), 0);
		assertEquals(ci.getPatientDb().getDbSize(), 0);
	}
	
	//CSV fields have patient id & doctor id swapped
	@Test
	public void TestCsvImporter_SwapId(){
		Path path = Paths.get(System.getProperty("user.dir") +"/resource/test/data_idswap.csv");
		ci = new CsvImporter(path);
		
		//test number of records in database
		assertEquals(ci.getPatientDb().getDbSize(), 0);
		assertEquals(ci.getDoctorsDb().getDbSize(), 0);
		assertEquals(ci.getPatientDb().getDbSize(), 0);
	}
	
	//CSV fields contain null

}
