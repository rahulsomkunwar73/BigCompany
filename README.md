# BigCompany

## Project Brief


This project analyzes a company's organizational structure to identify potential improvements based on the following criteria:

1. Managers should earn at least 20% more than the average salary of their direct subordinates
2. Managers should not earn more than 50% more than the average salary of their direct subordinates
3. Reporting lines should not be longer than 4 levels between an employee and the CEO
## Project Structure

The project follows a clean architecture with proper separation of concerns:

- **Model**: Employee class
- **Repository**: For data access (CSV file parsing)
- **Service**: Business logic for analyzing the organizational structure

## Data Format

The application expects a CSV file with employee data in the following format:

```
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
```

## Assumptions
   1. The file is assumed to have head `Id,firstName,lastName,salary,managerId` else application will throw IllegalArgumentException
   2. The file is assumed to have 4 comma separated values `124,Martin,Chekov,45000,123`
   3. Employee data will consist only one CEO

## Instructions to Run the Project

1. **Clone the repository:**
   ```sh
   git clone https://github.com/rahulsomkunwar73/BigCompany
   cd BigCompany

2. **Build the project using Maven:**
   ```sh
   mvn clean install

3. **Run the program with a CSV file:**
   ```sh
   java -jar target/BigCompany-1.0-SNAPSHOT.jar path/to/your/csv/file.csv

