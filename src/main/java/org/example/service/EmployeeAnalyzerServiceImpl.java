package main.java.org.example.service;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.EmployeeRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeAnalyzerServiceImpl implements EmployeeAnalyzerService {

    EmployeeRepository csvEmployeeRepository;
    private static final double MIN_SALARY_FACTOR = 1.2; // 20% more
    private static final double MAX_SALARY_FACTOR = 1.5; // 50% more
    private static final int MAX_REPORTING_LINE = 4; // Maximum levels from CEO



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

    private double calculateUnderpaymentAmount(Employee manager) {
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
    public List<Employee> findManagersEarningMoreThanAllowed() throws IOException {
        List<Employee> overpaidManagers = new ArrayList<>();
        Map<String, Employee> employeeMap = csvEmployeeRepository.buildOrganizationalStructure();

        for (Employee employee : employeeMap.values()) {
            if (employee.isManager() && calculateOverpaymentAmount(employee) > 0) {
                overpaidManagers.add(employee);
            }
        }

        return overpaidManagers;
    }

    private double calculateOverpaymentAmount(Employee manager) {
        if (!manager.isManager()) {
            return 0;
        }

        double avgSubordinateSalary = manager.getSubordinatesAverageSalary();
        double maxAllowedSalary = avgSubordinateSalary * MAX_SALARY_FACTOR;

        if (manager.getSalary() > maxAllowedSalary) {
            return manager.getSalary() - maxAllowedSalary;
        }

        return 0;
    }

    @Override
    public List<Employee> findEmployeesWithLongReportingLines() throws IOException {
        List<Employee> employeesWithLongLines = new ArrayList<>();
        Map<String, Employee> employeeMap = csvEmployeeRepository.buildOrganizationalStructure();

        for (Employee employee : employeeMap.values()) {
            if (calculateExcessReportingLevels(employee) > 0) {
                employeesWithLongLines.add(employee);
            }
        }

        return employeesWithLongLines;
    }

    int calculateExcessReportingLevels(Employee employee) {
        if (employee.getDistanceFromCEO() > MAX_REPORTING_LINE) {
            return employee.getDistanceFromCEO() - MAX_REPORTING_LINE;
        }
        return 0;
    }
}
