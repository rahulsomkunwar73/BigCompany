package main.java.org.example.repository;

import main.java.org.example.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvEmployeeRepository implements EmployeeRepository{

    String filePath;

    public CsvEmployeeRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Employee> getAllEmployees() throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = br.readLine();
            if (line != null && line.toLowerCase().contains("id") && line.toLowerCase().contains("firstname")) {
                // This is a header, skip it
            } else {
                processLine(line, employees);
            }

            while ((line = br.readLine()) != null) {
                processLine(line, employees);
            }
        }

        return employees;
    }

    /**
     * Processes a single line from the CSV file.
     *
     * @param line The line to process
     * @param employees The list to add the employee to
     */
    private void processLine(String line, List<Employee> employees) {
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        String[] parts = line.split(",");
        if (parts.length < 5) {
            System.err.println("Invalid line format: " + line);
            return;
        }

        String id = parts[0].trim();
        String firstName = parts[1].trim();
        String lastName = parts[2].trim();
        double salary;
        try {
            salary = Double.parseDouble(parts[3].trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid salary format for employee " + id + ": " + parts[3]);
            return;
        }

        String managerId = parts[4].trim();
        if (managerId.isEmpty()) {
            managerId = null;
        }

        Employee employee = new Employee(id, firstName, lastName, salary, managerId);
        employees.add(employee);
    }
}
