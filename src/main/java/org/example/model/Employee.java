package main.java.org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final double salary;
    private final String managerId;
    private Employee manager;
    private List<Employee> subordinates = new ArrayList<>();
    private int distanceFromCEO = -1;

    public Employee(String id, String firstName, String lastName, double salary, String managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public double getSalary() {
        return salary;
    }

    public String getManagerId() {
        return managerId;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    public int getDistanceFromCEO() {
        return distanceFromCEO;
    }

    public void setDistanceFromCEO(int distanceFromCEO) {
        this.distanceFromCEO = distanceFromCEO;
    }

    public boolean isManager() {
        return !subordinates.isEmpty();
    }

    public boolean isCEO() {
        return managerId == null;
    }

    public double getSubordinatesAverageSalary() {
        if (subordinates.isEmpty()) {
            return 0;
        }

        double totalSalary = 0;
        for (Employee subordinate : subordinates) {
            totalSalary += subordinate.getSalary();
        }

        return totalSalary / subordinates.size();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + getFullName() + '\'' +
                ", salary=" + salary +
                ", managerId='" + managerId + '\'' +
                ", subordinates=" + subordinates.size() +
                ", distanceFromCEO=" + distanceFromCEO +
                '}';
    }
}