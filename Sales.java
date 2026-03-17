package jdbcExersice;

public class Sales {
	int orderNumber;
	String customerName;
	String customerCity;
	String salesmanName;
	double amount;
	double commissionAmount;
	
	// Constructor
	public Sales(int orderNumber, String customerName, String customerCity, String salesmanName, double amount, double commissionAmount) {
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.customerCity = customerCity;
		this.salesmanName = salesmanName;
		this.amount = amount;
		this.commissionAmount = commissionAmount;
	}
}
