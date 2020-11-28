package com.kotak.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.kotak.base.TestBase;
import com.kotak.pages.HomePage;
import com.kotak.util.TestUtil;

public class HomePage extends TestBase {

	// Object repository
	@FindBy(xpath = "//div[@class='swiper-wrapper']/div[4]")
	WebElement deposits;

	@FindBy(xpath = "//h2[text()='Fixed deposit']")
	WebElement fixedDeposits;

	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	// Actions:

	public DepositsPage clickOnDeposits() {
		deposits.click();
		TestUtil.nap(2);
		fixedDeposits.click();
		TestUtil.nap(2);
		return new DepositsPage();
	}

}
