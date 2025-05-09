package main.java.org.example.repository;

import main.java.org.example.model.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EmployeeRepository {

    /**
     * Gets all employees from the data source.
     *
     * @return List of all employees
     * @throws IOException If there is an error reading the data
     */
    List<Employee> getAllEmployees() throws IOException;

    /**
     * Builds the organizational structure by establishing manager-subordinate relationships.
     *
     * @return Map of employee IDs to employees with organizational structure populated
     * @throws IOException If there is an error reading the data
     */
    Map<String, Employee> buildOrganizationalStructure() throws IOException;

    /**
     * Gets the CEO (employee with no manager).
     *
     * @return The CEO employee
     * @throws IOException If there is an error reading the data
     */
    Employee getCEO() throws IOException;
}
