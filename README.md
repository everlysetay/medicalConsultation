# Medical Consultation Appointment Booking

## Table of Contents
- [Introduction](#introduction)
- [Assumptions](#assumptions)
- [Setup](#setup)
- [Usage](#usage)
- [Folder Structure Conventions](#folder-structure-conventions)
- [Coding Convention](#coding-convention)

### Introduction
`medicalConsultation` contains source code written in Java™ Programming Language program coupled with Junit test cases for valdation to build an appointment system.

This project requires [Gradle to be installed](https://gradle.org/), followed by some usage steps listed below.

### Assumptions
1. Appointments are available to be made daily without any breaks, 8am to 4pm.
2. Appointments can only be booked by per hour basis (e.g. 8:00, 9:00 but not 8:30, 9:30)
3. Doctors cannot initiate appointments.
4. Patients are able to book 2 doctors on the same day, same time.
5. CSV will be populated only when program is ran.
6. System does not store all lastest information after system terminates.
7. Patients are able to book backdated appointment (e.g. 2018, when now in year 2020)
8. Date provided by user will always be in the accurate format
9. System doesn't require login to make appointments
10. System to be built without SQL database
11. System should allow other sets of csv input other than the default provided data.

### Setup

First, install [Homebrew](https://brew.sh/) unto the machine. Then run the following commands to install [Gradle](https://gradle.org/) under the `medicalConsultation` dir.
```
medicalConsultation $ brew install gradle
==> Installing gradle 
...
...
🍺  /usr/local/Cellar/gradle/5.4.1: 13,767 files, 235.4MB, built in 1 minute 23 seconds

```

### Usage

You can compile and view the test cases result of the program `medicalConsultation` by running the following command. Upon completion of the command execution, you can see the build dir being generated under the `medicalConsultation`. 
```
medicalConsultation $ ./scripts/setup
```

You can execute the program in command line by running the following commands in the `medicalConsultation` dir. 

```
medicalConsultation $ ./scripts/medical_consultation
```

### Folder Structure Conventions

#### Top-level directory layout

> The current directory structure and naming conventions for `medicalConsultation`

    .
    ├── bin                     
    ├── build                   # Compiled files 
    ├── build.gradle            # Program build structure
    ├── gradle  				        # Gradle library file to do compilation      
    ├── gradlew
    ├── lib                     # Contain additional junit.jar for ecplise
    ├── README.md   
    ├── resource                # CSV file to load into system, as well as test input
    ├── scripts		              # Provided functional_specs to enable more testing scenarios
    ├── settings.gradle     
    └── src                     # Source files and Junit test files
    

#### Source files and Automated test files

The actual source files and Junit test source files of the software project `parking_lot/src` are stored in the folder structure.

	.
    ├── main   									          # Actual Source files for MedicalConsultation
    │   └── java 
    │       ├── controller
    │       │   ├── CancelAppointment.java
    │       │   ├── CreateAppointment.java
    │       │   ├── CsvImporter.java
    │       │   ├── GetAppointmentForDoctor.java
    │       │   ├── PrintFunctions.java
    │       │   └── ScheduleAppointment.java
    │       ├── db
    │       │   ├── AppointmentDatabase.java
    │       │   ├── DoctorDatabase.java
    │       │   └── PatientDatabase.java
    │       ├── model
    │       │   ├── Appointment.java
    │       │   ├── Doctor.java
    │       │   ├── Human.java
    │			  │	  └── Patient.java
    │       └── MedicalConsultation.java
    ├── test                    				# Test files of Medical Consultation reflecting main
    │   └── java 
    │       ├── controller
    │       │   ├── TestCsvImporter.java
    │       │   ├── TestPrintFunction.java
    │       │   └── TestSCheduleAppointment.java
    └── ...

### Coding Convention

The actual source files of `medicalConsultation` are written in CamelCase Notation for source code in the Java™ Programming Language. 
