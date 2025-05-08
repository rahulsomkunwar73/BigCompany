package main.java.org.example.service;

import main.java.org.example.model.Employee;

import java.util.List;

public interface EmployeeAnalyzerService {

   List<Employee> findManagersEarningLessThanRequired();


}
