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

        // Read the entire file content
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(" ");
            }
        }

        String rawContent = content.toString().trim();

        // Split the content by spaces to get individual records
        String[] records = rawContent.split("\\s+");

        int startIndex = 0;
        if (records.length > 0 && records[0].toLowerCase().contains("id")) {
            startIndex = 1;
        }

        for (int i = startIndex; i < records.length; i++) {
            String record = records[i];
            System.out.println("Processing record: " + record);

            // Process the record
            if (record != null && !record.trim().isEmpty()) {
                // Handle the special case where the record ends with a comma
                // This indicates an empty manager ID which should be treated as null
                if (record.endsWith(",")) {
                    record = record + " "; // Add a space to ensure it splits correctly
                }

                String[] parts = record.split(",");

                // We need at least 4 parts (id, firstName, lastName, salary)
                if (parts.length >= 4) {
                    String id = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();

                    double salary;
                    try {
                        salary = Double.parseDouble(parts[3].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid salary format for employee " + id + ": " + parts[3]);
                        continue;
                    }

                    // Manager ID is optional and can be empty
                    String managerId = null;
                    if (parts.length > 4 && !parts[4].trim().isEmpty()) {
                        managerId = parts[4].trim();
                    }

                    System.out.println("Creating employee: " + id + ", " + firstName + " " + lastName + ", " + salary + ", " + managerId);
                    Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                    employees.add(employee);
                } else {
                    System.err.println("Invalid record format (less than 4 parts): " + record);
                }
            }
        }

        // Debug output
        System.out.println("Total employees loaded: " + employees.size());
        for (Employee emp : employees) {
            System.out.println("Employee: " + emp.getId() + ", " + emp.getFirstName() + " " + emp.getLastName() +
                    ", Manager ID: " + (emp.getManagerId() == null ? "null" : emp.getManagerId()));
        }

        return employees;
    }
}
