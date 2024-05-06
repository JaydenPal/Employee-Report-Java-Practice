package misc;

import java.util.HashMap;
import java.util.Set;

/*
 * EmployeeReportWriter is a Java class that writes the contents of the employee report
 * using the data from the EmployeeReport class to a String that is returned
 * to EmployeeReport
 * 
 * @author Jayden Palacios (ubc007)
 */
public class EmployeeReportWriter {

    private static final int AGE = 0;
    private static final int COMMISSION = 1;
    private static final int HOURLY = 2;
    private static final int SALARY = 3;
    private static final int TOTAL = 0;
    private static final int COUNT = 1;

	//grab static variables from EmployeeReport class for easy and more readable use 
    private static StringBuffer employeeReport;
    private static String [] firstNames = EmployeeReport.getFirstNames();
    private static String [] lastNames = EmployeeReport.getLastNames();
    private static int [] ages = EmployeeReport.getAges();
    private static String [] employeeTypes = EmployeeReport.getEmployeeTypes();
    private static double [] employeePays = EmployeeReport.getEmployeePays();
    private static int [] yearsHired = EmployeeReport.getYearsHired();

    /*
     * public static String writeEmployeeReport()
     * writes contents of a string representing the Employee report and returns it
     */
    public static String writeEmployeeReport() {
        employeeReport = new StringBuffer();
        writeColumns();
        writePayStatisitics();
        //get the same name info for first and last names. add to report
		employeeReport.append(String.format("%s", generateSameNameInfo(firstNames, "First")));
		employeeReport.append(String.format("%s", generateSameNameInfo(lastNames, "Last")));
        return employeeReport.toString();
    }

    private static void writePayStatisitics() {
        double [][] payData = calculatePayStatisitcs();
		float averageAge = (float) payData[AGE][TOTAL] / firstNames.length;
		float averageCommission = (float) payData[COMMISSION][TOTAL] / (float) payData[COMMISSION][COUNT];
		float averageHourly = (float) payData[HOURLY][TOTAL] / (float) payData[HOURLY][COUNT];
        //add pay stats to report
		employeeReport.append(String.format("\nAverage age:         %12.1f\n", averageAge));
		employeeReport.append(String.format("Average commission:  $%12.2f\n", averageCommission));
		employeeReport.append(String.format("Average hourly wage: $%12.2f\n",averageHourly));
		//avergageSalary calculated here to avoid unwated rounding side-effects
		employeeReport.append(String.format("Average salary:      $%12.2f\n",
		 payData[SALARY][TOTAL] / payData[SALARY][COUNT]));
    }

    private static double [][] calculatePayStatisitcs() {
        double [][] payData = new double[4][4];
        fillPayArray(payData);
		for(int i = 0; i < firstNames.length; i++) {
			payData[AGE][0] += ages[i];
			if(employeeTypes[i].equals("Commission")) {
				payData[COMMISSION][TOTAL] += employeePays[i];
				payData[COMMISSION][COUNT]++;
			} else if(employeeTypes[i].equals("Hourly")) {
				payData[HOURLY][TOTAL] += employeePays[i];
				payData[HOURLY][COUNT]++;
			} else if(employeeTypes[i].equals("Salary")) {
				payData[SALARY][TOTAL] += employeePays[i];
				payData[SALARY][COUNT]++;
			}
		}
        return payData;
    }

	private static String generateSameNameInfo(String [] names, String nameType){
		StringBuffer nameInfo = new StringBuffer();
        HashMap<String, Integer> nameHashMap = new HashMap<String, Integer>();

        int hashMapSize = fillSameNameHashMap(nameHashMap, names);

		nameInfo.append(String.format("\n%s names with more than one person sharing it:\n", nameType));
		if(hashMapSize > 0) { //if hasmap is not empty
			Set<String> set = nameHashMap.keySet();
			for(String curName : set) {
				if(nameHashMap.get(curName) > 1)
					nameInfo.append(String.format("%s, # people with this name: %d\n", curName, nameHashMap.get(curName)));
			}
		} else {
			nameInfo.append(String.format("All %s names are unique", nameType));
		}
		return nameInfo.toString();
	}

    private static void fillPayArray(double [][] payArr) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                payArr[i][j] = 0;
            }
        }
    }

    private static int fillSameNameHashMap(HashMap<String, Integer> nameHashMap, String [] names) {
		int hashMapSize = 0;
		for(int i = 0; i < names.length; i++) {
			if(nameHashMap.containsKey(names[i])) {
				nameHashMap.put(names[i], nameHashMap.get(names[i]) + 1);
				hashMapSize++;
			} else {
				nameHashMap.put(names[i], 1);
			}
		}
        return hashMapSize;
    }

    private static void writeColumns() {
        //add the report columns
		employeeReport.append(String.format("# of people imported: %d\n", firstNames.length));
		employeeReport.append(String.format("\n%-30s %s %s %-12s %13s\n", "Person Name", "Age", "Hired", "Emp. Type", "Pay"));
		for(int i = 0; i < 30; i++)
			employeeReport.append(String.format("-"));
		employeeReport.append(String.format(" --- ----- "));
		for(int i = 0; i < 12; i++)
			employeeReport.append(String.format("-"));
		employeeReport.append(String.format(" "));
		for(int i = 0; i < 13; i++)
			employeeReport.append(String.format("-"));
		employeeReport.append(String.format("\n"));
		for(int i = 0; i < firstNames.length; i++) {
			employeeReport.append(String.format("%-30s %-3d %-5d %-12s $%12.2f\n", firstNames[i] + " " + lastNames[i], ages[i]
						, yearsHired[i], employeeTypes[i], employeePays[i]));
		}
    }
}