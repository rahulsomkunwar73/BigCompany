package main.java.org.example.service;

import main.java.org.example.model.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeAnalyzerService {

   List<Employee> findManagersEarningLessThanRequired() throws IOException;

   List<Employee> findManagersEarningMoreThanAllowed() throws IOException;

   List<Employee> findEmployeesWithLongReportingLines() throws IOException;


}
