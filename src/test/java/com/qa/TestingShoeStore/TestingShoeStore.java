package com.qa.TestingShoeStore;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
public class TestingShoeStore {

	public static WebDriver driver=null;

	@BeforeTest
	public static void setUP(){
		driver=new FirefoxDriver();
		driver.get("http://shoestore-manheim.rhcloud.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(priority=1)
	public static void monthlyRelease() throws InterruptedException{
		String month1="//*[@id='header_nav']/nav/ul/li[";
		String month2="]/a";
		String brand1="//ul/li[";
		String brand2="]/div/table/tbody/tr[1]/td[2]/a";
		String price1="//ul/li[";
		String price2="]/div/table/tbody/tr[3]/td[2]";
		String image1="//ul/li[";
		String image2="]/div/table/tbody/tr[6]/td/img";
		int j=1;
		for(int i=1;i<=12;i++){
			System.out.println("For the month: "+i);
			driver.findElement(By.xpath(month1+i+month2)).click();
			while(isElementPresent(brand1+j+brand2)){	
				System.out.println("Brand");
				System.out.println(driver.findElement(By.xpath(brand1+j+brand2)).getText());
				System.out.println("Price");
				while(isElementPresent(price1+j+price2)){
					System.out.println(driver.findElement(By.xpath(price1+j+price2)).getText());
					System.out.println("Image");
					while(isElementPresent(image1+j+image2)){
						System.out.println(driver.findElement(By.xpath(image1+j+image2)).getAttribute("src"));
						break;
					}
					break;
				}
				j++;
			}
			j=1;
		}
		System.out.println();
	}

	public static boolean isElementPresent(String ele_xpath) {
		int count=driver.findElements(By.xpath(ele_xpath)).size();
		if(count==0)
			return false;
		else
			return true;
	}

	@Test(priority=2)
	public static void submitEmailAddress(){
		System.out.println("Sending Email");
		String emailAddress="keerthij849@gmail.com";
		driver.findElement(By.xpath("//*[@id='remind_email_input']")).sendKeys(emailAddress);
		driver.findElement(By.xpath("//*[@id='remind_email_form']/div/input[2]")).click();
		String emailNotification=driver.findElement(By.xpath("//*[@id='flash']/div")).getText();
		String emailResultText="Thanks! We will notify you of our new shoes at this email: "+emailAddress;
		System.out.println(emailResultText);
		Assert.assertEquals(emailResultText,emailNotification);
	}

	@AfterTest
	public static void tearDown(){
		driver.close();
	}
}
