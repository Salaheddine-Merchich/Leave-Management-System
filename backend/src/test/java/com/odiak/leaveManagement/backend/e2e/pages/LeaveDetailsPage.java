package com.odiak.leaveManagement.backend.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeaveDetailsPage {
    private WebDriverWait wait;

    private By reasonHeader = By.cssSelector(".leave-info-content h3 span");

    public LeaveDetailsPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
    }

    public boolean isAtLeaveDetails() {
        return wait.until(ExpectedConditions.urlContains("/info-leave"));
    }

    public String getReason() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(reasonHeader)).getText();
    }
}
