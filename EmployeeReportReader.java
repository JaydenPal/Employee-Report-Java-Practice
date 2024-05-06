package misc;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * EmployeeReportReader is a Java class that contains a method, and its helpers, for reading in employee data
 * from a provided input file and storing that data in the EmployeeReport Java class
 * 
 * @author Jayden Palacios (ubc007)
 */

public class EmployeeReportReader {
    // will be set to memmers of EmployeeReport class for easy and more readable use
    private static String [] firstNames;
	private static String [] lastNames;
	private static int [] ages;
	private static String [] employeeTypes;
	private static double [] employeePays;
	private static int [] yearsHired;
    
    /*
     * @param - inputFileName - a string that holds the name of the file to read form
     * @return - an int representing the num of employees in input file
     * this method initializes and writes data from input file to members of Employee
     * Report class
     */
    public static int createEmployeeRecords(String inputFileName) {
        //count num of employees and initialize static arrays
        int employeeCount = countEmployees(inputFileName);
        initializeMembers(employeeCount);

        //read and store input data
        if (firstNames == null) 
            return 0;
        employeeCount = readEmployeeData(inputFileName);
        return employeeCount;
    }

    private static int readEmployeeData(String inputFileName) {
        Scanner inputScanner = openScanner(inputFileName);
        int employeeCount = 0;
        //read input data and add to report, organzing data by employee last name (lexographically)
		while(inputScanner.hasNextLine()) {
			String inpuString = inputScanner.nextLine();
			if(inpuString.length() > 0) {
				String [] curEmployeeData = inpuString.split(",");
				if(addEmployeeRecord(curEmployeeData, employeeCount) == 1) {
                    employeeCount = 0;
                    return employeeCount;
                }

				employeeCount++;
			}
		}
        inputScanner.close();
        return employeeCount;
    }

    private static int addEmployeeRecord(String [] curEmployeeData, int employeeCount) {
        int index;
        for(index = 0; index < lastNames.length; index++) {
            if(lastNames[index] == null)
                break;
            if(lastNames[index].compareTo(curEmployeeData[1]) > 0) {
                for(int i = employeeCount; i > index; i--) {
                    setEmployeeData(i);
                }
                break;
            }
        }
        return parseEmployeeData(curEmployeeData, index);
    }

	private static int parseEmployeeData(String curEmployeeData[], int index) {
		try {
			EmployeeReport.getAges()[index] = Integer.parseInt(curEmployeeData[2]);
			employeePays[index] = Double.parseDouble(curEmployeeData[4]);
			yearsHired[index] = Integer.parseInt(curEmployeeData[5]);
            firstNames[index] = curEmployeeData[0];
			lastNames[index] = curEmployeeData[1];
			employeeTypes[index] = curEmployeeData[3];
		} catch(Exception e) {
			System.err.println(e.getMessage());
            return 1;
		}
        return 0;
	}

    private static int countEmployees(String inputFileName) {
        Scanner inputScanner = openScanner(inputFileName);

		int employeeCount = 0;
		while(inputScanner.hasNextLine()) {
			String curLine = inputScanner.nextLine();
			if(curLine.length() > 0)
				employeeCount++;
		}
        inputScanner.close();
        return employeeCount;
    }

    private static void initializeMembers(int employeeCount) {
        //initilize static arrays with size of emmployeeCount
		EmployeeReport.setFirstNames(new String[employeeCount]);
		EmployeeReport.setLastNames(new String[employeeCount]);
		EmployeeReport.setAges(new int[employeeCount]);
		EmployeeReport.setEmployeeTypes(new String[employeeCount]);
		EmployeeReport.setEmployeePays(new double[employeeCount]);
		EmployeeReport.setYearsHired(new int[employeeCount]);
        //set class members to the static arrays in EmployeeReport.java for readabillity and easy use
        firstNames = EmployeeReport.getFirstNames();
        lastNames = EmployeeReport.getLastNames();
        ages = EmployeeReport.getAges();
        employeeTypes = EmployeeReport.getEmployeeTypes();
        employeePays = EmployeeReport.getEmployeePays();
        yearsHired = EmployeeReport.getYearsHired();
    }

    private static void setEmployeeData(int i) {
        firstNames[i] = firstNames[i - 1];
        lastNames[i] = lastNames[i - 1];
        ages[i] = ages[i - 1];
        employeeTypes[i] = employeeTypes[i - 1];
        employeePays[i] = employeePays[i - 1];
        yearsHired[i] = yearsHired[i - 1];
    }

    private static Scanner openScanner(String inputFileName){
		Scanner newScanner = null;
		try {
			newScanner = new Scanner(new File(inputFileName));
			return newScanner;
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}