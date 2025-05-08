package main.java.org.example.service;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;

import java.util.List;

public class EmployeeAnalyzerServiceImpl implements EmployeeAnalyzerService {

    CsvEmployeeRepository csvEmployeeRepository;

    public EmployeeAnalyzerServiceImpl(CsvEmployeeRepository csvEmployeeRepository){
        this.csvEmployeeRepository = csvEmployeeRepository;
    }

    @Override
    public List<Employee> findManagersEarningLessThanRequired() {
        return null;
    }
}
