package com.odiak.leaveManagement.backend.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By employeeName = By.cssSelector(".user-name");
    private By employeeEmail = By.cssSelector(".user-email");

    public EmployeeDetailsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isAtEmployeeDetails() {
        return wait.until(ExpectedConditions.urlContains("/info-employee"));
    }

    public String getName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(employeeName)).getText();
    }
}
