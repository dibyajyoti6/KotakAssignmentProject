package com.kotak.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.kotak.base.TestBase;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 10;
	public static long IMPLICIT_WAIT = 10;

	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "\\Resources\\data-amount.xlsx";
	static Workbook book;
	static Sheet sheet;

	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}

	public static void takeScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/Screenshots/" + System.currentTimeMillis() + ".png"));
	}

	public static void verifyBrokenImg(String urlLink) {

		try {
			URL link = new URL(urlLink);
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
			httpConn.setConnectTimeout(2000);
			httpConn.connect();
			if (httpConn.getResponseCode() == 200) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage() + " valid image link");
			}
			if (httpConn.getResponseCode() == 404) {
				System.out.println(urlLink + " - " + httpConn.getResponseMessage() + " broken image");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void nap(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean retryingFindClick(WebElement ele) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				ele.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	public static void clickByJS(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", ele);
	}

	public static void clearInputByRobotClass() throws InterruptedException, AWTException {
		Robot robot = new Robot();
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_A);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_A);
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
	}

	public static void generateAlert(WebDriver driver, String message) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("alert('" + message + "')");
	}

}
