package main.java.org.example.repository;

import main.java.org.example.model.Employee;

import java.io.IOException;
import java.util.List;

public class CsvEmployeeRepository implements EmployeeRepository{

    String filePath;

    public CsvEmployeeRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Employee> getAllEmployees() throws IOException {
        return null;
    }
}
