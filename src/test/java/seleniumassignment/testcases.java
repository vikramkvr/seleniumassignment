package seleniumassignment;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testcases extends genericclass{
	
	Properties prop = getpropertydetails();
	WebDriver driver;
	
	@BeforeMethod
	public void startTest()
	{
		this.driver = driverInititation(driver);
		driver.get("http://jupiter.cloud.planittesting.com");
		driver.manage().window().maximize();
		
	}
	

	
	@Test
	public void testcase1() throws InterruptedException
	{
		
		explicitwait_visibilityOfElement(driver, prop.getProperty("contact_menu"));
		driver.findElement(By.xpath(prop.getProperty("contact_menu"))).click();
		explicitwait_visibilityOfElement(driver, prop.getProperty("submit_button"));
		driver.findElement(By.xpath(prop.getProperty("submit_button"))).click();
		explicitwait_visibilityOfElement(driver, prop.getProperty("forename_error"));
		
		//validating error messages
		
		checkForElementExistence(driver, prop.getProperty("forename_error"), "Forename is required");
		
		checkForElementExistence(driver, prop.getProperty("email_error"), "Email is required");
		
		checkForElementExistence(driver, prop.getProperty("message_error"), "Message is required");
		
		completeContactinfo(driver, prop);
		
	}


	@Test
	public void testcase2()
	{
		
		fillAndSubmitContactInfo(driver,prop,5);
	
	}
	

	@Test
	public void testcase3() throws InterruptedException
	{
		
		explicitwait_visibilityOfElement(driver, prop.getProperty("shop_menu"));
		driver.findElement(By.xpath(prop.getProperty("shop_menu"))).click();
		
		//Buy 2 Stuffed Frog, 5 Fluffy Bunny, 3 Valentine Bear
		
		explicitwait_visibilityOfElement(driver, prop.getProperty("stuffed_frog_buy"));
		clickItem(driver, prop.getProperty("stuffed_frog_buy"),2);
		clickItem(driver, prop.getProperty("fluffy_bunny_buy"),5);
		clickItem(driver, prop.getProperty("valentine_Bear_buy"),3);
		explicitwait_visibilityOfElement(driver, prop.getProperty("cart_menu"));
		
		//Go to the cart page
		clickItem(driver, prop.getProperty("cart_menu"),1);
		validateCartPage(driver,prop);
		
		
	}

	@AfterMethod
	public void endTest()
	{
		driver.close();
				
	}
}
