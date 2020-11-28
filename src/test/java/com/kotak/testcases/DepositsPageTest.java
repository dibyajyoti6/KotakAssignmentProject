package com.kotak.testcases;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.kotak.base.TestBase;
import com.kotak.pages.DepositsPage;
import com.kotak.pages.HomePage;
import com.kotak.util.TestUtil;
import com.kotak.util.VideoRecorderHelper;

public class DepositsPageTest extends TestBase {
	HomePage homePage;
	DepositsPage depositPage;
	String sheetName = "Amount";

	@BeforeMethod
	public void setUp() {
		initialization();
		homePage = new HomePage();
		depositPage = new DepositsPage();

	}

	@Test(priority = 1, description = "Testscript will identify all broken images in Deposit Page")
	public void validateBrokenImagesTest() throws Exception {
		VideoRecorderHelper.startRecord("BrokenImageRecording");
		depositPage.verifyAllBrokenImages();
		VideoRecorderHelper.stopRecord();
	}

	@Test(priority = 2, dataProvider = "amount", description = "Testscript will validate amount field")
	public void validateAmountFieldTest(String amount) throws Exception {
		VideoRecorderHelper.startRecord("AmountFieldVerificationRecording");
		homePage.clickOnDeposits();
		depositPage.amountFieldValidation(amount);
		VideoRecorderHelper.stopRecord();
	}

	@DataProvider(name = "amount")
	public Object[][] getAmountTestData() {
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
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
