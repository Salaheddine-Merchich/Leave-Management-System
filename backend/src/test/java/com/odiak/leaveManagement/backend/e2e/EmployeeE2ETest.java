package com.odiak.leaveManagement.backend.e2e;

import com.odiak.leaveManagement.backend.e2e.pages.EmployeeListPage;
import com.odiak.leaveManagement.backend.e2e.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-End test suite for Employee Management.
 */
public class EmployeeE2ETest extends BaseTest {

    @Test
    @DisplayName("Test: Navigate to Employee List and Verify Default Admin Presence")
    public void testEmployeeListAndDetails() {
        HomePage homePage = new HomePage(driver, wait);
        EmployeeListPage employeeListPage = new EmployeeListPage(driver, wait);

        homePage.navigateTo(BASE_URL);

        homePage.clickEmployees();

        assertTrue(employeeListPage.isAtEmployeeList(), "URL should contain /employees");

        assertTrue(employeeListPage.isEmployeePresent("Admin User"),
                "Default admin 'Admin User' should be in the list");
        assertTrue(employeeListPage.isEmployeePresent("admin@example.com"), "Default admin email should be visible");
    }
}
