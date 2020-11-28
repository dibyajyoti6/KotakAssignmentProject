package com.kotak.testcases;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.kotak.base.TestBase;
import com.kotak.pages.DepositsPage;
import com.kotak.pages.HomePage;
import com.kotak.util.RetryAnalyzer;
import com.kotak.util.TestUtil;
import com.kotak.util.VideoRecorderHelper;

public class HomePageTest extends TestBase {
	HomePage homePage;
	DepositsPage depositPage;

	@BeforeMethod
	public void setUp() {
		initialization();
		homePage = new HomePage();
	}

	@Test(priority = 1, retryAnalyzer = RetryAnalyzer.class)
	public void clickOnDepositsTest() throws Exception {
		VideoRecorderHelper.startRecord("Clicking on fix deposit");
		depositPage = homePage.clickOnDeposits();
		VideoRecorderHelper.stopRecord();
	}

	@AfterMethod
	public void tearDown(ITestResult result) {

		if (ITestResult.FAILURE == result.getStatus()) {
			try {
				TestUtil.takeScreenshot();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		driver.quit();
	}
}
