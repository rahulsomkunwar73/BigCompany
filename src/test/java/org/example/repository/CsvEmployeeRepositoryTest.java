package test.java.org.example.repository;

import main.java.org.example.model.Employee;
import main.java.org.example.repository.CsvEmployeeRepository;
import main.java.org.example.repository.EmployeeRepository;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;
public class CsvEmployeeRepositoryTest {

    @Test
    public void testGetAllEmployees() throws IOException {
        // Arrange
        String filePath = "src/test/java/org/example/resources/csvfile.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);

        // Act
        List<Employee> employees = repository.getAllEmployees();

        // Assert
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testCEOData() throws IOException {
        // Arrange
        String filePath = "src/test/java/org/example/resources/csvfile.csv";
        EmployeeRepository repository = new CsvEmployeeRepository(filePath);
        // Act
        List<Employee> employees = repository.getAllEmployees();

        // Assert

        Employee ceo = employees.stream()
                .filter(e -> e.getId().equals("123"))
                .findFirst()
                .orElse(null);

        assertNotNull(ceo);
        assertEquals("Joe", ceo.getFirstName());
        assertEquals("Doe", ceo.getLastName());
        assertEquals(60000, ceo.getSalary(), 0.01);
        assertNull(ceo.getManagerId());

    }

    @Test
    public void testEmployeeDataWithoutHeader() throws IOException {
        Path tempDir = Files.createTempDirectory("test-temp-dir");

        File csvFile = tempDir.resolve("test-data-invalid-line.csv").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("123,Joe,Doe,60000,\n");
            writer.write("invalidLine\n"); // This should be skipped
            writer.write("124,Martin,Chekov,45000,123\n");
        }

        EmployeeRepository repository = new CsvEmployeeRepository(csvFile.getAbsolutePath());
        // Assert

        assertThrows(IllegalArgumentException.class, () -> {
             repository.getAllEmployees();
        });


    }

}
