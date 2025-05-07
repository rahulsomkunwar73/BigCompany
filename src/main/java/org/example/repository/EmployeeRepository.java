package main.java.org.example.repository;

import main.java.org.example.model.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeRepository {

    List<Employee> getAllEmployees() throws IOException;
}
