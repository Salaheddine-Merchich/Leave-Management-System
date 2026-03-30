package com.odiak.leaveManagement.backend.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By homeNavLink = By.linkText("Accueil");
    private By leavesNavLink = By.linkText("Congés");
    private By addLeaveNavLink = By.linkText("Ajouter");
    private By employeesNavLink = By.linkText("Employés");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void navigateTo(String baseUrl) {
        driver.get(baseUrl);
    }

    public void clickHome() {
        wait.until(ExpectedConditions.elementToBeClickable(homeNavLink)).click();
    }

    public void clickLeaves() {
        wait.until(ExpectedConditions.elementToBeClickable(leavesNavLink)).click();
    }

    public void clickAddLeave() {
        wait.until(ExpectedConditions.elementToBeClickable(addLeaveNavLink)).click();
    }

    public void clickEmployees() {
        wait.until(ExpectedConditions.elementToBeClickable(employeesNavLink)).click();
    }
}
