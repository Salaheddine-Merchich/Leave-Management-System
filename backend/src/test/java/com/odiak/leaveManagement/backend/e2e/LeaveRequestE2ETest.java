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

        String testReason = "Vacations E2E Test " + System.currentTimeMillis();
        String testStartDate = "2026-06-15";
        String testEndDate = "2026-06-20";
        String testStatus = "PENDING";

        homePage.navigateTo(BASE_URL);

        homePage.clickAddLeave();

        leaveFormPage.fillForm(testStartDate, testEndDate, testReason, testStatus);

        leaveFormPage.submitForm();

        assertTrue(leaveListPage.isRedirectedToLeavesList(),
                "Final URL should contain /Leaves.");
        assertTrue(leaveListPage.doesListContainReason(testReason),
                "The leave request with reason '" + testReason + "' should be in the list.");
    }
}
