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

        // Step 1: Navigate to Home
        homePage.navigateTo(BASE_URL);

        // Step 2: Click on "Employees" in Navbar
        homePage.clickEmployees();

        // Step 3: Verify we are on /employees
        assertTrue(employeeListPage.isAtEmployeeList(), "URL should contain /employees");

        // Step 4: Verify default admin is present
        assertTrue(employeeListPage.isEmployeePresent("Admin User"),
                "Default admin 'Admin User' should be in the list");
        assertTrue(employeeListPage.isEmployeePresent("admin@example.com"), "Default admin email should be visible");
    }
}
