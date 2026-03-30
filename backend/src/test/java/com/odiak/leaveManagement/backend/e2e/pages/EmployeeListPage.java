package com.odiak.leaveManagement.backend.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class EmployeeListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By employeeCard = By.cssSelector(".user-card");
    private By employeeName = By.cssSelector(".user-info strong");
    private By infoLink = By.cssSelector(".info-link");
    private By deleteButton = By.cssSelector(".delete-button");

    public EmployeeListPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isAtEmployeeList() {
        return wait.until(ExpectedConditions.urlContains("/employees"));
    }

    public boolean isEmployeePresent(String nameOrEmail) {
        By employeeInfo = By.xpath("//div[@class='user-info' and contains(., '" + nameOrEmail + "')]");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(employeeInfo));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void clickFirstEmployeeInfo() {
        wait.until(ExpectedConditions.elementToBeClickable(infoLink)).click();
    }

    public void clickDeleteFirstEmployee() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
    }
}
