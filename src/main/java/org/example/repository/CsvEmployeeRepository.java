package main.java.org.example.repository;

import main.java.org.example.model.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvEmployeeRepository implements EmployeeRepository {

    private final String filePath;
    private Map<String, Employee> employeeMap;
    private Employee ceo;
    private boolean dataLoaded = false;

    public CsvEmployeeRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Employee> getAllEmployees() throws IOException {
        loadDataIfNeeded();
        return new ArrayList<>(employeeMap.values());
    }

    @Override
    public Map<String, Employee> buildOrganizationalStructure() throws IOException {
        loadDataIfNeeded();
        return employeeMap;
    }

    @Override
    public Employee getCEO() throws IOException {
        loadDataIfNeeded();
        return ceo;
    }

    private void loadDataIfNeeded() throws IOException {
        if (dataLoaded) {
            return;
        }

        employeeMap = new HashMap<>();

        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

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

        if (records.length == 0 || !records[0].toLowerCase().contains("id") || !records[0].toLowerCase().contains("firstname")) {
            throw new IllegalArgumentException("CSV file must have a header with 'Id' and 'firstName' columns");
        }

        // First pass: Create all employees
        for (int i = 1; i < records.length; i++) {
            String record = records[i];

            try {
                Employee employee = parseEmployee(record);
                if (employee != null) {
                    employeeMap.put(employee.getId(), employee);

                    if (employee.isCEO()) {
                        ceo = employee;
                    }
                }
            } catch (Exception e) {
                // Skip invalid lines
                System.err.println("Skipping invalid record: " + record + " - " + e.getMessage());
            }
        }

        // Second pass: Establish manager-subordinate relationships
        for (Employee employee : employeeMap.values()) {
            String managerId = employee.getManagerId();
            if (managerId != null) {
                Employee manager = employeeMap.get(managerId);
                if (manager != null) {
                    employee.setManager(manager);
                    manager.addSubordinate(employee);
                }
            }
        }

        // Third pass: Calculate distances from CEO
        if (ceo != null) {
            calculateDistanceFromCEO(ceo, 0);
        }
        dataLoaded = true;
    }

    private void calculateDistanceFromCEO(Employee employee, int distance) {
        employee.setDistanceFromCEO(distance);
        for (Employee subordinate : employee.getSubordinates()) {
            calculateDistanceFromCEO(subordinate, distance + 1);
        }
    }

    private Employee parseEmployee(String record) {
        if (record == null || record.trim().isEmpty()) {
            return null;
        }

        // Handle the special case where the record ends with a comma
        if (record.endsWith(",")) {
            record = record + " "; // Add a space to ensure it splits correctly
        }

        String[] parts = record.split(",");

        // We need at least 4 parts (id, firstName, lastName, salary)
        if (parts.length < 4) {
            System.err.println("Invalid record format (less than 4 parts): " + record);
            return null;
        }

        String id = parts[0].trim();
        String firstName = parts[1].trim();
        String lastName = parts[2].trim();

        double salary;
        try {
            salary = Double.parseDouble(parts[3].trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid salary format for employee " + id + ": " + parts[3]);
            return null;
        }

        // Manager ID is optional and can be empty
        String managerId = null;
        if (parts.length > 4 && !parts[4].trim().isEmpty()) {
            managerId = parts[4].trim();
        }

        return new Employee(id, firstName, lastName, salary, managerId);
    }
}