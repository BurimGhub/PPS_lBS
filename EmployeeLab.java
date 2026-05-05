package weekThirteen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeLab {
    public static void main(String[] args) {
        // Step 1: Initialize list and use JDBC connection
        List<Employee> employeesList = new ArrayList<>();
        
        // MariaDB JDBC configuration
        String url = "jdbc:mariadb://localhost:3306/employee"; 
        String user = "user";
        String password = "easwaren123";

        String query = "SELECT id, name, salary FROM employees";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                employeesList.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed. Make sure your MariaDB server is running.");
            e.printStackTrace();
        }


        // Step 2: Print a list of all Employees using a functional interface
        System.out.println("--- All Employees ---");
        employeesList.forEach(System.out::println);

        // Step 3: Define a Predicate to filter high earners
        Predicate<Employee> isHighEarner = e -> e.getSalary() > 50000;
        
        List<Employee> highEarners = employeesList.stream()
                .filter(isHighEarner)
                .collect(Collectors.toList());

        // Step 4: Print list of high earners
        System.out.println("\n--- High Earners (>$50,000) ---");
        highEarners.forEach(System.out::println);

        // Step 5: Function to apply a 15% tax reduction
        Function<Employee, Employee> applyTax = e -> {
            // Returns a new Employee object to prevent mutating the original list's data
            return new Employee(e.getId(), e.getName(), e.getSalary() * 0.85); 
        };

        // Step 6: Function to format salary
        Function<Employee, String> formatSalary = e -> String.format("$%.2f", e.getSalary());

        // Step 7: Stream, filter, apply tax, format, and create a List<Employee>
        // Note: We use .peek() to handle the "format" string requirement while still 
        // allowing the stream to collect back into a List<Employee>.
        System.out.println("\n--- Taxed High Earners (Formatted) ---");
        List<Employee> taxedHighEarners = employeesList.stream()
                .filter(isHighEarner)
                .map(applyTax)
                .peek(e -> System.out.println(e.getName() + " new salary: " + formatSalary.apply(e)))
                .collect(Collectors.toList());

        // Step 8: Extra - Single sequence using partitionBy
        System.out.println("\n--- Extra: Partitioned & Taxed (All Employees) ---");
        
        // partitioningBy splits the stream into a Map with two keys: true (>$50k) and false (<=$50k)
        Map<Boolean, List<String>> partitionedAndFormatted = employeesList.stream()
                .collect(Collectors.partitioningBy(
                        isHighEarner, 
                        Collectors.mapping(e -> {
                            // Apply correct tax rate based on the salary bracket
                            double taxedSalary = e.getSalary() > 50000 ? e.getSalary() * 0.85 : e.getSalary() * 0.90;
                            Employee tempEmp = new Employee(e.getId(), e.getName(), taxedSalary);
                            
                            // Neatly format the output string
                            return String.format("%s: %s", tempEmp.getName(), formatSalary.apply(tempEmp));
                        }, Collectors.toList())
                ));

        System.out.println("High Earners (15% Tax applied):");
        partitionedAndFormatted.get(true).forEach(System.out::println);

        System.out.println("\nStandard Earners (10% Tax applied):");
        partitionedAndFormatted.get(false).forEach(System.out::println);
    }
}