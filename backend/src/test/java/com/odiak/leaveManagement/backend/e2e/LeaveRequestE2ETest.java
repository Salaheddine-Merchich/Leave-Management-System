package com.odiak.leaveManagement.backend.e2e;

import com.odiak.leaveManagement.backend.e2e.pages.HomePage;
import com.odiak.leaveManagement.backend.e2e.pages.LeaveFormPage;
import com.odiak.leaveManagement.backend.e2e.pages.LeaveListPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-End test suite for the Complete Leave Request Flow.
 */
public class LeaveRequestE2ETest extends BaseTest {

    @Test
    @DisplayName("Test: Complete Leave Request Workflow from Home to Submission")
    public void testCompleteLeaveRequestFlow() {
        HomePage homePage = new HomePage(driver, wait);
        LeaveFormPage leaveFormPage = new LeaveFormPage(driver, wait);
        LeaveListPage leaveListPage = new LeaveListPage(driver, wait);

        // Define test data
        String testReason = "Vacations E2E Test " + System.currentTimeMillis();
        String testStartDate = "2026-06-15";
        String testEndDate = "2026-06-20";
        String testStatus = "PENDING";

        // Step 1: Navigate to Home
        homePage.navigateTo(BASE_URL);

        // Step 2: Click on "Add Leave"
        homePage.clickAddLeave();

        // Step 3: Fill Leave Form
        leaveFormPage.fillForm(testStartDate, testEndDate, testReason, testStatus);

        // Step 4: Submit Form
        leaveFormPage.submitForm();

        // Step 5: Verify Redirection to List and Existence of the Request
        assertTrue(leaveListPage.isRedirectedToLeavesList(),
                "Final URL should contain /Leaves.");
        assertTrue(leaveListPage.doesListContainReason(testReason),
                "The leave request with reason '" + testReason + "' should be in the list.");
    }
}
