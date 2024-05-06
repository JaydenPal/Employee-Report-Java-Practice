package misc;

/* 
 * Employee report is a Java Class that stores/represents employee data and generates 
 * an employee report as a String from its employee data
 * 
 * @author UTSA-CS-4773-FALL-23-BYRON-LONG (modified:Jayden Palacios)
*/

public class EmployeeReport {
	private static String [] firstNames;
	private static String [] lastNames;
	private static int [] ages;
	private static String [] employeeTypes;
	private static double [] employeePays;
	private static int [] yearsHired;

	/*
	 * generateReportFromFile(String inputFileName)
	 * creates an employee report from data in provided input file
	 * @param inputFileName - a String containing the input file name to be used
	 * @return - a String containing the employee report generated from the input data
	 */
	public static String generateReportFromFile(String inputFileName) {
		StringBuffer employeeReport = new StringBuffer();
		
		int employeeCount = EmployeeReportReader.createEmployeeRecords(inputFileName);
		employeeReport.append(EmployeeReportWriter.writeEmployeeReport());

		if(employeeCount == 0) {
			System.err.println("No records found in data file");
			return null;
		}
		return employeeReport.toString();
	}

	public static String [] getFirstNames() {
		return firstNames;
	}

	public static void setFirstNames(String [] newFirstNames) {
		firstNames = newFirstNames;
	}

	public static String [] getLastNames() {
		return lastNames;
	}

	public static void setLastNames(String [] newLastNames) {
		lastNames = newLastNames;
	}
	
	public static int [] getAges() {
		return ages;
	}

	public static void setAges(int [] newAges) {
		ages = newAges;
	}

	public static String [] getEmployeeTypes() {
		return employeeTypes;
	}

	public static void setEmployeeTypes(String [] newEmployeeTypes) {
		employeeTypes = newEmployeeTypes;
	}
	
	public static double [] getEmployeePays() {
		return employeePays;
	}

	public static void setEmployeePays(double [] newEmployeePays) {
		employeePays = newEmployeePays;
	}
	
	public static int [] getYearsHired(){
		return yearsHired;
	}

	public static void setYearsHired(int [] newYearsHired) {
		yearsHired = newYearsHired;
	}
}