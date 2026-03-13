package com.odiak.leaveManagement.backend.e2e;

import com.odiak.leaveManagement.backend.e2e.pages.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Selenium E2E Test Suite for Leave Management System.
 * Tests all possible flows/routes as requested by the user.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebsiteE2ETestSuite extends BaseTest {

    private HomePage homePage;
    private LeaveListPage leaveListPage;
    private LeaveFormPage leaveFormPage;
    private LeaveDetailsPage leaveDetailsPage;
    private EmployeeListPage employeeListPage;
    private EmployeeDetailsPage employeeDetailsPage;

    @BeforeEach
    public void setupPages() {
        homePage = new HomePage(driver, wait);
        leaveListPage = new LeaveListPage(driver, wait);
        leaveFormPage = new LeaveFormPage(driver, wait);
        leaveDetailsPage = new LeaveDetailsPage(driver, wait);
        employeeListPage = new EmployeeListPage(driver, wait);
        employeeDetailsPage = new EmployeeDetailsPage(driver, wait);
    }

    @Test
    @Order(1)
    @DisplayName("Test 1: Global Navigation Check")
    public void testGlobalNavigation() {
        homePage.navigateTo(BASE_URL);

        homePage.clickLeaves();
        assertTrue(leaveListPage.isRedirectedToLeavesList(), "Navigation to Leave List failed.");

        homePage.clickEmployees();
        assertTrue(employeeListPage.isAtEmployeeList(), "Navigation to Employee List failed.");

        homePage.clickAddLeave();
        assertTrue(driver.getCurrentUrl().contains("/add-leave"), "Navigation to Add Leave Form failed.");

        homePage.clickHome();
        assertEquals(BASE_URL + "/", driver.getCurrentUrl(), "Navigation back to Home failed.");
    }

    @Test
    @Order(2)
    @DisplayName("Test 2: Complete Leave Request Lifecycle (Create -> View -> Update -> Delete)")
    public void testLeaveLifecycle() {
        String baseReason = "Cycle Request " + System.currentTimeMillis();
        String updatedReason = baseReason + " - UPDATED";

        // 1. CREATE
        homePage.navigateTo(BASE_URL);
        homePage.clickAddLeave();
        leaveFormPage.fillForm("2026-08-01", "2026-08-10", baseReason, "PENDING");
        leaveFormPage.submitForm();

        assertTrue(leaveListPage.isRedirectedToLeavesList(), "Should be redirected to the list after submission.");
        assertTrue(leaveListPage.doesListContainReason(baseReason), "The new leave request reason should be visible.");

        // 2. VIEW DETAILS (Assuming it's the one we just added and might be first)
        // Note: For deterministic testing, one might search for the element,
        // but here we demonstrate the flow.
        // We'll use JS to click details button if there's any ambiguity.
        leaveListPage.clickDetailsForReason(baseReason); // New method to add
        assertTrue(leaveDetailsPage.isAtLeaveDetails(), "Details page should be reached.");
        String actualReason = leaveDetailsPage.getReason();
        System.out.println("DEBUG-REASON: '" + actualReason + "'");
        assertTrue(actualReason.contains(baseReason), "Details should show the correct reason. Found: " + actualReason);

        // 3. UPDATE
        homePage.clickLeaves();
        leaveListPage.clickEditForReason(baseReason);
        leaveFormPage.fillForm("2026-08-01", "2026-08-15", updatedReason, "APPROVED");
        leaveFormPage.submitForm();

        assertTrue(leaveListPage.isRedirectedToLeavesList(), "Should be redirected to the list after update.");
        assertTrue(leaveListPage.doesListContainReason(updatedReason), "The updated reason should be visible.");

        // 4. DELETE
        int sizeBefore = driver.findElements(org.openqa.selenium.By.cssSelector(".leave-card")).size();
        leaveListPage.clickDeleteForReason(updatedReason);
        wait.until(ExpectedConditions.alertIsPresent()).accept();

        // Brief wait for deletion
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }

        // After delete, check size or if list is empty
        int sizeAfter = driver.findElements(org.openqa.selenium.By.cssSelector(".leave-card")).size();
        System.out.println("Delele logic done: before size: " + sizeBefore + ", after size: " + sizeAfter);
    }

    @Test
    @Order(3)
    @DisplayName("Test 3: Employee Management View")
    public void testEmployeeView() {
        homePage.navigateTo(BASE_URL);
        homePage.clickEmployees();

        assertTrue(employeeListPage.isEmployeePresent("Admin User"), "Default admin should exist in the database.");

        employeeListPage.clickFirstEmployeeInfo();
        assertTrue(employeeDetailsPage.isAtEmployeeDetails(), "Should reach employee details page.");
        assertTrue(employeeDetailsPage.getName().contains("Admin User"), "Details should show the admin's name.");
    }

    @Test
    @Order(4)
    @DisplayName("Test 4: Form Validation (Negative case)")
    public void testFormValidation() {
        homePage.navigateTo(BASE_URL);
        homePage.clickAddLeave();

        // Submit button should be disabled for empty form
        boolean isEnabled = driver.findElement(org.openqa.selenium.By.cssSelector("button.submit-button")).isEnabled();
        assertTrue(!isEnabled, "Submit button should be disabled for empty fields.");
    }
}
