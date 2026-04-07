package jdbcExersice;

public class SalesPerson {
	// Fields
	private String name;
	private String city;
	private double commission;
	private double totalSales;
	
	// Constructor
	public SalesPerson(String name, String city, double commission, double totalSales) {
		this.name = name;
		this.city = city;
		this.commission = commission;
		this.totalSales = totalSales;
	}
	
	// Getters
	public String getName() { return name; }
	public String getCity() { return city; }
	public double getCommission() { return commission; }
	public double getTotalSales() { return totalSales; }
	
	// Method to get the total amount earned after commission rate.
	public double getTotalCommissionAmount() {
		return totalSales * commission;
	}
	
	// toString
	@Override
	public String toString() {
		return "SalesPerson [Name= " +name + ", City= " +city + ", Commission rate=" +commission + "Total Sales=$" +totalSales + "]";
	}
}
