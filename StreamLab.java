package jdbcExersice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StreamLab {

	public static void main(String[] args) {
		
		// Checks if the command arguments are provided
		if (args.length < 3) {
			System.out.println("Command arguments are not provided.");
			System.exit(1);
		}
		
		String user = args[0];
		String password = args[1];
		String dbName = args[2]; 
		String url = "jdbc:mariadb://localhost:3306/" + dbName;
		
		ArrayList<SalesPerson> salesPersonList = new ArrayList<>();
		
		try {
			 Connection conn = DriverManager.getConnection(url, user, password);
			 System.out.println("Connection to MariaDB established successfully!\n");
			 
			 String query = "SELECT s.name, s.city, s.commission, COALESCE(SUM(o.purchase_amt), 0) AS total_sales " +
		               		"FROM salesman s " +
		               		"LEFT JOIN orders o ON s.salesman_id = o.salesman_id " +
		               		"GROUP BY s.salesman_id, s.name, s.city, s.commission";
			 
			 PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery();
	         
	         // Loop to fill the ArrayList with sales people
	         while (rs.next()) {
	                SalesPerson sp = new SalesPerson(
	                    rs.getString("name"),
	                    rs.getString("city"),
	                    rs.getDouble("commission"),
	                    rs.getDouble("total_sales")
	                );
	                salesPersonList.add(sp);
	            }

	            rs.close();
	            pstmt.close();
	            conn.close();
		} catch (SQLException e) {
			
			// Exit on failure
			System.err.println("Database connection or query failed:");
			e.printStackTrace();
		}
		// -------------------------------
		// Stream-Based operations
		// -------------------------------
		
		// Prints all salesPerson's names and total earnings (earnings being total money made for the company, not money brought home).
		System.out.println("\n--- 1. Salesperson Total Earnings (Revenue) ---");
        System.out.printf("%-20s | %-15s%n", "Salesperson Name", "Total Earnings");
        System.out.println("----------------------------------------");
        
        salesPersonList.stream()
        .forEach(sp -> System.out.printf("%-20s | $%.2f%n", sp.getName(), sp.getTotalSales()));
        
        // Prints all salesPerson's names and total commissions (money that is being taken home).
        System.out.println("\n--- 2. Salesperson Total Commissions ---");
        System.out.printf("%-20s | %-15s%n", "Salesperson Name", "Total Commission");
        System.out.println("----------------------------------------");
        
        salesPersonList.stream()
        .forEach(sp -> System.out.printf("%-20s | $%.2f%n", sp.getName(), sp.getTotalCommissionAmount()));

	}

}
