package com.odiak.leaveManagement.backend.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LeaveListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By leaveCard = By.cssSelector(".leave-card");
    private By leaveReasonText = By.cssSelector(".leave-info h2");

    public LeaveListPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isRedirectedToLeavesList() {
        return wait.until(ExpectedConditions.urlContains("/Leaves"));
    }

    public boolean doesListContainReason(String reason) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(leaveCard));
        List<WebElement> leaveCards = driver.findElements(leaveReasonText);
        for (WebElement element : leaveCards) {
            if (element.getText().contains(reason)) {
                return true;
            }
        }
        return false;
    }

    public void clickEditForReason(String reason) {
        By editBtn = By.xpath("//li[contains(@class, 'leave-card')]//h2[contains(text(), '" + reason
                + "')]/../following-sibling::div[@class='leave-actions']//a[contains(text(), 'Edit')]");
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
    }

    public void clickDetailsForReason(String reason) {
        By detailsBtn = By.xpath("//li[contains(@class, 'leave-card')]//h2[contains(text(), '" + reason
                + "')]/../following-sibling::div[@class='leave-actions']//a[contains(text(), 'Détails')]");
        wait.until(ExpectedConditions.elementToBeClickable(detailsBtn)).click();
    }

    public void clickDeleteForReason(String reason) {
        By deleteBtn = By.xpath("//li[contains(@class, 'leave-card')]//h2[contains(text(), '" + reason
                + "')]/../following-sibling::div[@class='leave-actions']//button[contains(@class, 'delete-btn')]");
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
    }
}
