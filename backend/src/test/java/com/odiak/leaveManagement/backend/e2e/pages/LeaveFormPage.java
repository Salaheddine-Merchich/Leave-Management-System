package com.odiak.leaveManagement.backend.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeaveFormPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
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
        org.openqa.selenium.WebElement startEl = wait
                .until(ExpectedConditions.visibilityOfElementLocated(startDateInput));
        org.openqa.selenium.WebElement endEl = driver.findElement(endDateInput);

        startEl.clear();
        startEl.sendKeys(startDate);

        endEl.clear();
        endEl.sendKeys(endDate);

        driver.findElement(reasonInput).clear();
        driver.findElement(reasonInput).sendKeys(reason);

        Select statusDropdown = new Select(driver.findElement(statusSelect));
        statusDropdown.selectByValue(status);
    }

    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }
}
