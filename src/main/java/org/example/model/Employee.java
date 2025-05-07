package main.java.org.example.model;


import java.util.List;

/**
 * Represents an employee in the organization.
 */
public class Employee {
    private final String id;
    private final String firstName;
    private final double salary;
    private final String managerId;

    Employee(String id, String firstName, double salary, String managerId) {
        this.id = id;
        this.firstName = firstName;
        this.salary = salary;
        this.managerId = managerId;
    }

}
