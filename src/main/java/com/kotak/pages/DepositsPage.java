package com.kotak.pages;

import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.kotak.base.TestBase;
import com.kotak.util.TestUtil;

public class DepositsPage extends TestBase {
	HomePage homePage;
	// Object repository

	@FindBy(xpath = "//div[@class='ieco-wrapper-main-layout']//span[@class='ng-star-inserted']/b")
	WebElement amountField;
	@FindBys(@FindBy(tagName = "img"))
	List<WebElement> links;
	@FindBy(xpath = "//div[contains(text(),'Minimum:')]")
	WebElement minAmountField;
	@FindBy(name = "investedAmt")
	WebElement amountInput;
	@FindBy(xpath = "//div[contains(text(),'Maximum:')]")
	WebElement maxAmountField;

	// Initializing the Page Objects:

	public DepositsPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions:

	public void verifyAllBrokenImages() {
		homePage = new HomePage();
		homePage.deposits.click();
		for (WebElement link : links) {
			String imgUrl = link.getAttribute("src");
			TestUtil.verifyBrokenImg(imgUrl);
		}
		TestUtil.generateAlert(driver, "Verified Broken Images");
		TestUtil.nap(3);
	}

	public void amountFieldValidation(String amount) {
		String amt = amount.substring(0, amount.indexOf("."));
		try {
			TestUtil.clickByJS(amountField);
			TestUtil.nap(1);
		} catch (Exception e) {
			TestUtil.clickByJS(amountInput);
		}
		try {
			TestUtil.clearInputByRobotClass();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		TestUtil.nap(1);
		amountInput.sendKeys(amt);
		TestUtil.nap(1);
		amountInput.sendKeys(Keys.TAB);
		TestUtil.nap(1);
		String minAmount = "";
		String maxAmount = "";
		int count = 0;
		try {
			boolean statusMin = minAmountField.isDisplayed();
			if (statusMin == true) {
				System.out.println("Amount " + amt + " is less than Min value");
				minAmount = minAmountField.getText();
				Assert.assertNotEquals(minAmount, "Minimum: 10000");
			}
		} catch (Exception e) {
			System.out.println("Amount " + amt + " is valid");
			count++;
		}
		try {
			boolean statusMax = maxAmountField.isDisplayed();
			if (statusMax == true) {
				System.out.println("Amount " + amt + " is greater than Max value");
				maxAmount = maxAmountField.getText();
				Assert.assertNotEquals(maxAmount, "Maximum: 100000");
			}
		} catch (Exception e) {
			if (count == 0) {
				System.out.println("Amount " + amt + " is valid");
			}
		}
	}

}
