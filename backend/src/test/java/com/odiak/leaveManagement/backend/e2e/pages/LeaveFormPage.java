package com.odiak.leaveManagement.backend.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeaveFormPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By startDateInput = By.id("startDate");
    private By endDateInput = By.id("endDate");
    private By reasonInput = By.id("reason");
    private By statusSelect = By.id("status");
    private By submitButton = By.cssSelector("button.submit-button");

    public LeaveFormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void fillForm(String startDate, String endDate, String reason, String status) {
        WebElement startEl = wait.until(ExpectedConditions.visibilityOfElementLocated(startDateInput));
        WebElement endEl = driver.findElement(endDateInput);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true })); arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", startEl, startDate);
        js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true })); arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", endEl, endDate);

        WebElement reasonEl = driver.findElement(reasonInput);
        reasonEl.clear();
        reasonEl.sendKeys(reason);
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", reasonEl);

        Select statusDropdown = new Select(driver.findElement(statusSelect));
        statusDropdown.selectByValue(status);
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", driver.findElement(statusSelect));
    }

    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }
}
