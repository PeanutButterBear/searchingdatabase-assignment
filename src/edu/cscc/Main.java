package edu.cscc;
import java.sql.*;
import java.util.Scanner;

//Calvin Gates, 10/3/2022, formatting data obtained from a database AND using prepared statements to filter results with java

public class Main {

	//Database credentials
	static final String USER = "";
	static final String PASS = "";
	static final String PORT = "";
	static final String HOST = "";
	static final String DATABASE = "";

	// Build connection URL
	static final String connectionURL = "jdbc:sqlserver://" + HOST + ":" + PORT + ";databaseName=" + DATABASE + ";user=" + USER + ";password=" + PASS + ";encrypt=true;TrustServerCertificate=true";
	
	public static void main(String[] args) {
		PreparedStatement stmt = null;
		
		// Open a connection using Connection URL - auto close connection and statement
		//I removed the "Statement stmt = conn.createStatement();" portion of the try statement so not sure if that affects auto close
		try ( Connection conn = DriverManager.getConnection(connectionURL); ) {

			//User inputs country string, saved to variable "searchCountry" because I was afraid it'd conflict with my other country variable
			Scanner input = new Scanner(System.in);
			System.out.println("Customer Search by Country");
			System.out.print("Enter country: ");
			String searchCountry = input.next();
			
			//Added "WHERE" keyword to filter based on country user specified, used prepared statement from example
			String sql = "SELECT CompanyName, Address, City, Region, PostalCode, Country FROM Customers WHERE Country = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, searchCountry);
			ResultSet rs = stmt.executeQuery();

			System.out.println("\nCompany Name                             Address                                            City                 Region          Postal     Country");
			System.out.println("========================================================================================================================================================");

			while (rs.next()) {
				String companyName = rs.getNString(1);
				String address = rs.getNString(2);
				String city = rs.getNString(3);
				String region = rs.getNString(4);
				String postal = rs.getNString(5);
				String country = rs.getNString(6);
				
				if (address == null) {
					region = "n/a";
				}
				if (city == null) {
					region = "n/a";
				}
				if (region == null) {
					region = "n/a";
				}
				if (postal == null) {
					region = "n/a";
				}
				if (country == null) {
					region = "n/a";
				}
				//formatting: "-" means left justification, "s" specifies string, the # sets the amount of characters, "\n" makes sure console rows match db rows
				System.out.printf("%-40s %-50s %-20s %-15s %-10s %-15s\n", companyName, address, city, region, postal, country);
			}
			// Clean-up result set
			rs.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} 
		System.out.println("========================================================================================================================================================");
	}
}

//Version command:
//String sql = "SELECT @@VERSION";
