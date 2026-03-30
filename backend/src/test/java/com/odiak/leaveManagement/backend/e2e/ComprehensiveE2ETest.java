package com.odiak.leaveManagement.backend.e2e;

import com.odiak.leaveManagement.backend.e2e.pages.HomePage;
import com.odiak.leaveManagement.backend.e2e.pages.LeaveFormPage;
import com.odiak.leaveManagement.backend.e2e.pages.LeaveListPage;
import com.odiak.leaveManagement.backend.e2e.pages.EmployeeListPage;
import com.odiak.leaveManagement.backend.e2e.pages.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Comprehensive E2E Test Suite covering all possible flows:
 * - Navigation across all pages
 * - Complete Leave Flow (Add -> View -> Edit -> Delete)
 * - Employee View
 */
public class ComprehensiveE2ETest extends BaseTest {

    @Test
    @DisplayName("Test: Comprehensive Full Workflow (Navigation, Leave Management, Employee List)")
    public void testFullSiteWorkflow() {
        HomePage homePage = new HomePage(driver, wait);
        LeaveFormPage leaveFormPage = new LeaveFormPage(driver, wait);
        LeaveListPage leaveListPage = new LeaveListPage(driver, wait);
        EmployeeListPage employeeListPage = new EmployeeListPage(driver, wait);

        homePage.navigateTo(BASE_URL);

        homePage.clickLeaves();
        assertTrue(leaveListPage.isRedirectedToLeavesList(), "Should navigate to /Leaves");

        homePage.clickEmployees();
        assertTrue(employeeListPage.isAtEmployeeList(), "Should navigate to /employees");

        homePage.clickAddLeave();
        wait.until(ExpectedConditions.urlContains("/add-leave"));
        assertTrue(driver.getCurrentUrl().contains("/add-leave"), "Should navigate to /add-leave");

        String initialReason = "Full Test Reason " + System.currentTimeMillis();
        String updatedReason = "Updated Test Reason " + System.currentTimeMillis();

        leaveFormPage.fillForm("2026-07-01", "2026-07-05", initialReason, "PENDING");
        leaveFormPage.submitForm();

        assertTrue(leaveListPage.isRedirectedToLeavesList(), "After submission, should go to /Leaves");
        assertTrue(leaveListPage.doesListContainReason(initialReason), "Initial reason should be visible");

        leaveListPage.clickEditForReason(initialReason);
        assertTrue(driver.getCurrentUrl().contains("/edit-leave"), "Should be on edit page");

        leaveFormPage.fillForm("2026-07-01", "2026-07-10", updatedReason, "APPROVED");
        leaveFormPage.submitForm();

        assertTrue(leaveListPage.isRedirectedToLeavesList(), "After edit, should go to /Leaves");
        assertTrue(leaveListPage.doesListContainReason(updatedReason), "Updated reason should be visible");

        leaveListPage.clickDeleteForReason(updatedReason);
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        System.out.println("Delete clicked");
    }
}
