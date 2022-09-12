package seleniumassignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;



public class genericclass {
	
	
	public WebDriver driverInititation(WebDriver driver)
	{
		
		System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
		return  driver = new ChromeDriver();
	}
	public Properties getpropertydetails()
	{
		FileInputStream fis = null;
	      Properties prop = null;
	      try {
	         fis = new FileInputStream(".\\object.properties");
	         prop = new Properties();
	         prop.load(fis);
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	         try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
	      return prop;
	}

	public void explicitwait_visibilityOfElement(WebDriver driver,String path)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
				
	}
	
	public void checkForElementExistence(WebDriver driver, String path,String expected_message)
	{
		if(driver.findElement(By.xpath(path)).isEnabled())
		{
			if(driver.findElement(By.xpath(path)).getText().equals(expected_message))
			{//System.out.println(driver.findElement(By.xpath(path)).getText()+ " is the expected message");
			Reporter.log(driver.findElement(By.xpath(path)).getText()+ " is the expected message");
			
			}
			else
			{
				Reporter.log(driver.findElement(By.xpath(path)).getText()+ " is not the expected message");
				Assert.fail(driver.findElement(By.xpath(path)).getText()+ " is not the expected message");
			}
			
		}
	}
	
	public void checkForElementNonExistence(WebDriver driver, String path,String expected_message)
	{
		if(driver.findElements(By.xpath(path)).isEmpty())
		{
			//System.out.println(expected_message + " is gone");
			Reporter.log(expected_message + " is gone");
			
			
		}
		else
		{
			
			Reporter.log(expected_message + " is exists");
			Assert.fail(expected_message + " is exists");
		}
	}
	
	public void fillAndSubmitContactInfo(WebDriver driver, Properties prop, int loopcount)
	{
		for(int i=1;i<=loopcount;i++)
		{
		explicitwait_visibilityOfElement(driver, prop.getProperty("contact_menu"));
		driver.findElement(By.xpath(prop.getProperty("contact_menu"))).click();
		
		explicitwait_visibilityOfElement(driver, prop.getProperty("forename_input"));
		driver.findElement(By.xpath(prop.getProperty("forename_input"))).sendKeys("Vikram");
		driver.findElement(By.xpath(prop.getProperty("email_input"))).sendKeys("vikram.kandi@gmail.com");
		driver.findElement(By.xpath(prop.getProperty("message_input"))).sendKeys("this is new contact");
		driver.findElement(By.xpath(prop.getProperty("submit_button"))).click();
		explicitwait_visibilityOfElement(driver, prop.getProperty("submit_success_text"));
		checkForElementExistence(driver, prop.getProperty("submit_success_text"), "Thanks "+"Vikram"+", we appreciate your feedback.");
		Reporter.log("verified contact submission for "+i+" times \n");
		driver.findElement(By.xpath(prop.getProperty("back_button_contactpage"))).click();
		}
	}
	public void clickItem(WebDriver driver, String path,int iteration)
	{
		for(int i=0;i<iteration;i++)
		{
		driver.findElement(By.xpath(path)).click();
		}
	}
	public void compareValues(Double amount1, Double amount2, String stringval)
	{
		if(Double.compare(amount1, amount2)==0)
			//System.out.println(stringval +" values are matching");
		Reporter.log(stringval +" values are matching");
		
		else
		{
			Reporter.log(stringval +" values are not matching");
			Assert.fail(stringval +" values are not matching");
		}
	}
	
	public void completeContactinfo(WebDriver driver, Properties prop)
	{
		driver.findElement(By.xpath(prop.getProperty("forename_input"))).sendKeys("Vikram");
		checkForElementNonExistence(driver, prop.getProperty("forename_error"), "forename error");
		
		driver.findElement(By.xpath(prop.getProperty("email_input"))).sendKeys("vikram.kandi@gmail.com");
		checkForElementNonExistence(driver, prop.getProperty("email_error"), "email error");
		
		driver.findElement(By.xpath(prop.getProperty("message_input"))).sendKeys("this is message to add user");
		checkForElementNonExistence(driver, prop.getProperty("message_error"), "message error");
	}
	public int findNumberOfDistinctItemsInCart(WebDriver driver,Properties prop)
	{
		return driver.findElements(By.xpath(prop.getProperty("rows_in_cart"))).size();
	}
	
	public void validateCartPage(WebDriver driver,Properties prop)
	{
		explicitwait_visibilityOfElement(driver, prop.getProperty("total_value"));
		
		int number_of_items = findNumberOfDistinctItemsInCart(driver,prop);
		double sum_of_subitems=0;
		String unparsedtotalvalue="";
		for(int i=1;i<=number_of_items;i++)
		{
			String unparsedprice = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[2]")).getText();
			String value = unparsedprice.substring(1);
			double price = Double.parseDouble(value);
			
			
			String unparsed_numberofitems = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[3]/input")).getAttribute("value");
			int numberofitems = Integer.parseInt(unparsed_numberofitems);
			
			
			String unparsed_subtotal = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[4]")).getText();
			String temp_subtotal = unparsed_subtotal.substring(1);
			double subtotal = Double.parseDouble(temp_subtotal);
			
			
			compareValues(subtotal, price*numberofitems, "subtotal");
			
			sum_of_subitems = sum_of_subitems + subtotal;
			unparsedtotalvalue = driver.findElement(By.xpath(prop.getProperty("total_value"))).getText();
			
			//System.out.println(driver.findElement(By.xpath("//tbody/tr["+i+"]/td[1]")).getText()+ " price is "+price);
			Reporter.log(driver.findElement(By.xpath("//tbody/tr["+i+"]/td[1]")).getText()+ " price is "+price);
			
		}
		
		String[] total = unparsedtotalvalue.split(": ");
		
		double truevalue = Double.parseDouble(total[1]);
		
		compareValues(truevalue, sum_of_subitems, "total");
		
	}
	
	
}
