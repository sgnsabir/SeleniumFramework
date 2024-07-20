package sleniumframework.tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import sleniumframework.testcomponents.BaseTest;
//This is sample databaseTesting implemented in Selenium, I have different project for Database Testing.
public class DatabaseTesting extends BaseTest {
	String host = "localhost";
	String port = "3306";

	@Test(priority=1)
	void schemaValidation_tableName() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/classicmodels", "root",
				"root");

		Statement s = con.createStatement();
		// ResultSet rs = s.executeQuery("select * from credentials where
		// scenario='zerobalancecard'");
		ResultSet rs = s.executeQuery("show tables");
		String tableName[] = { "credentials", "customers", "departments", "employees", "offices", "orderdetails",
				"orders", "payments", "productlines", "products", "regions" };
		int i = 0;
		while (rs.next()) {
			System.out.println(rs.getString("Tables_in_classicmodels"));
			String actualTableName = rs.getString("Tables_in_classicmodels");
			Assert.assertEquals(actualTableName, tableName[i]);
			i++;
		}
	}
	@Test(priority=2)
	void sampleTest() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/classicmodels", "root",
				"root");
		Statement s = con.createStatement();

	ResultSet rs = s.executeQuery("select * from credentials where scenario='zerobalancecard'");while(rs.next())
	{
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.id("username")).sendKeys(rs.getString("username"));
		driver.findElement(By.id("password")).sendKeys(rs.getString("password"));
		// driver.findElement(By.id("Login")).click();
		System.out.println(rs.getString("username"));
		System.out.println(rs.getString("password"));
	}
}
}
