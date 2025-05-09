package org.example;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;
import main.java.org.example.repository.EmployeeRepository;
import main.java.org.example.service.EmployeeAnalyzerService;
import main.java.org.example.service.EmployeeAnalyzerServiceImpl;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: java -jar BigCompany-1.0-SNAPSHOT.jar <csv_file_path>");
            return;
        }

        String csvFilePath = args[0];

        try {
            EmployeeRepository repository = new CsvEmployeeRepository(csvFilePath);

            EmployeeAnalyzerService analyzerService = new EmployeeAnalyzerServiceImpl(repository);
            System.out.println("======ManagersEarningLessThanRequired======");
            printLogs(analyzerService.findManagersEarningLessThanRequired());
            System.out.println("======ManagersEarningMoreThanAllowed======");
            printLogs(analyzerService.findManagersEarningMoreThanAllowed());
            System.out.println("======EmployeesWithLongReportingLines======");
            printLogs(analyzerService.findEmployeesWithLongReportingLines());

        } catch (IOException e) {
            System.err.println("Error reading employee data: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static void printLogs(List<Employee> employees){
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
    }
}