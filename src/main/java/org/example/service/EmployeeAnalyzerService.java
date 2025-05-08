package main.java.org.example.service;

import main.java.org.example.model.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeAnalyzerService {

   List<Employee> findManagersEarningLessThanRequired() throws IOException;

   double calculateUnderpaymentAmount(Employee manager);


   List<Employee> findManagersEarningMoreThanAllowed() throws IOException;

   double calculateOverpaymentAmount(Employee manager);
}
