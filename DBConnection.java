package jdbcExersice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;	// Imported to store our Sales objects

public class DBConnection {
	public static void main (String[] args) {
		
		// Instead of asking user to type, pass values in right when the program starts
		if (args.length <3) {
			System.out.println("Usage: java DBConnection <username> <password> <database>");
			System.exit(1);
		}
		
		// Storing dynamic arguments instead of hard coding the information
		String user = args[0];
		String password = args[1];
		String dbName = args[2];
		
		// Database name in URL is dynamic instead of hard coded to the name 
		String url = "jdbc:mariadb://localhost:3306/" + dbName;
		
		// Create ArrayList to hold Sales objects
		ArrayList<Sales> salesData = new ArrayList<>();
		
		try {
			 Connection conn = DriverManager.getConnection(url, user, password);
			 System.out.println("Connection to MariaDB established successfully!\n");
			 
			 // Retrieve all data and calculate the commission amount
			 String query = "SELECT o.order_no, c.customer_name, c.city, s.name AS salesman_name, " +
		               "o.purchase_amt, (o.purchase_amt * s.commission) AS comm_amount " +
		               "FROM orders o" +
		               " JOIN customer c ON o.customer_id = c.customer_id" +
		               " JOIN salesman s ON o.salesman_id = s.salesman_id";
			 
			 PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery();
	         
	         // Loop through the result and create Sales objects
	         while (rs.next()) {
	        	 Sales currentSale = new Sales(
	        			 rs.getInt("order_no"),
	        			 rs.getString("customer_name"),
	        			 rs.getString("city"),
	        			 rs.getString("salesman_name"),
	        			 rs.getDouble("purchase_amt"),
	        			 rs.getDouble("comm_amount")
	        			 );
	        	 
	        	 salesData.add(currentSale);
	         }
	         
	         // Print out the ArrayList to verify data was stored properly
	         System.out.println("- Data stored in ArrayList<Sales> -");
	         for (Sales s : salesData) {
	        	 System.out.println("Order: " + s.orderNumber + " | Customer: " + s.customerName + " | Salesman: " + s.salesmanName + " | Commission: $" + s.commissionAmount);
	         }
	         
	         // Close connections
	         rs.close();
	         pstmt.close();
	         conn.close();
	         
		} catch (SQLException e) {
			System.err.println("Database connection or query failed:");
			e.printStackTrace();
		}
	}
}
