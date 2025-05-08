package main.java.org.example.service;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;
import main.java.org.example.repository.EmployeeRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeAnalyzerServiceImpl implements EmployeeAnalyzerService {

    EmployeeRepository csvEmployeeRepository;
    private static final double MIN_SALARY_FACTOR = 1.2; // 20% more


    public EmployeeAnalyzerServiceImpl(EmployeeRepository csvEmployeeRepository){
        this.csvEmployeeRepository = csvEmployeeRepository;
    }

    @Override
    public List<Employee> findManagersEarningLessThanRequired() throws IOException {
        List<Employee> underpaidManagers = new ArrayList<>();
        Map<String, Employee> employeeMap = csvEmployeeRepository.buildOrganizationalStructure();

        for (Employee employee : employeeMap.values()) {
            if (employee.isManager() && calculateUnderpaymentAmount(employee) > 0) {
                underpaidManagers.add(employee);
            }
        }

        return underpaidManagers;
    }

    @Override
    public double calculateUnderpaymentAmount(Employee manager) {
        if (!manager.isManager()) {
            return 0;
        }

        double avgSubordinateSalary = manager.getSubordinatesAverageSalary();
        double minRequiredSalary = avgSubordinateSalary * MIN_SALARY_FACTOR;

        if (manager.getSalary() < minRequiredSalary) {
            return minRequiredSalary - manager.getSalary();
        }

        return 0;
    }

    @Override
    public List<Employee> findManagersEarningMoreThanAllowed() {
        return null;
    }
}
