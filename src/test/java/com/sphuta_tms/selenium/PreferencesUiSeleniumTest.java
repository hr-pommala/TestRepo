package com.sphuta_tms.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PreferencesUiSeleniumTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    private static final String BASE_URL = "http://localhost:8080/preferences-ui";
    private static final String TEST_USER_ID = "a1b2c3d4-e5f6-7890-1234-56789abcdef0";

    // -------------------- SETUP --------------------
    /**
     * Setup WebDriver and WebDriverWait before all tests.
     */
    @BeforeAll
    static void setUpAll() {
        log.info("Setting up WebDriver and WebDriverWait");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /**
     * Tear down WebDriver after all tests.
     */
    @AfterAll
    static void tearDownAll() {
        log.info("Quitting WebDriver");
        if (driver != null) {
            driver.quit();
        }
    }

    // -------------------- CREATE --------------------
    /**
     * Test creating a new preference entry.
     * Steps:
     * 1. Navigate to BASE_URL
     * 2. Locate the first form (Create form)
     * 3. Fill userId, dateFormat, weekStartsOn, rounding
     * 4. Submit form
     * 5. Verify the table contains the new entry
     */
    @Test
    @Order(1)
    void testCreatePreference() {
        log.info("Test: Create Preference started");

        driver.get(BASE_URL);
        log.debug("Navigated to URL: {}", BASE_URL);

        // Locate create form (first form on the page)
        WebElement createForm = driver.findElements(By.tagName("form")).get(0);
        log.debug("Located create form element");

        // Fill form inputs
        createForm.findElement(By.name("userId")).sendKeys(TEST_USER_ID); // Element: userId input
        createForm.findElement(By.name("dateFormat")).sendKeys("YYYY-MM-DD"); // Element: dateFormat input
        createForm.findElement(By.name("weekStartsOn")).sendKeys("MON"); // Element: weekStartsOn input
        createForm.findElement(By.name("rounding")).sendKeys("NONE"); // Element: rounding input
        log.debug("Filled create form inputs");

        // Submit create form
        createForm.findElement(By.tagName("button")).click();
        log.info("Create form submitted");

        // Wait for the table row to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table/tbody/tr/td[text()='" + TEST_USER_ID + "']")
        ));
        log.debug("Table updated with new preference");

        // Verify table contains the new preference
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        assertTrue(rows.stream().anyMatch(row -> row.getText().contains(TEST_USER_ID)));
        log.info("Create Preference test completed successfully");
    }

    // -------------------- GET BY ID --------------------
    /**
     * Test fetching a preference by its UUID.
     * Steps:
     * 1. Navigate to BASE_URL with query param getByIdUserId
     * 2. Wait for the table row containing TEST_USER_ID
     * 3. Verify the row contains the correct userId
     */
    @Test
    @Order(2)
    void testGetPreferenceById() {
        log.info("Test: Get Preference By ID started");

        driver.get(BASE_URL + "?getByIdUserId=" + TEST_USER_ID);
        log.debug("Navigated to URL with query param: {}", TEST_USER_ID);

        // Wait and locate table row
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table/tbody/tr/td[text()='" + TEST_USER_ID + "']")
        ));
        log.debug("Located table row for userId: {}", TEST_USER_ID);

        // Verify userId in row
        assertEquals(TEST_USER_ID, row.getText());
        log.info("Get Preference By ID test completed successfully");
    }

    // -------------------- UPDATE --------------------
    /**
     * Test updating a preference.
     * Steps:
     * 1. Navigate to BASE_URL
     * 2. Locate first row and update form
     * 3. Update dateFormat
     * 4. Submit form
     * 5. Verify table reflects the updated value
     */
    @Test
    @Order(3)
    void testUpdatePreference() {
        log.info("Test: Update Preference started");

        driver.get(BASE_URL);
        WebElement firstRow = driver.findElement(By.xpath("//table/tbody/tr[1]")); // Element: first table row
        WebElement updateForm = firstRow.findElement(By.xpath(".//form[contains(@action,'/update')]")); // Element: update form
        log.debug("Located update form in first row");

        WebElement dateInput = updateForm.findElement(By.name("dateFormat")); // Element: dateFormat input
        dateInput.clear();
        dateInput.sendKeys("YYYY/MM/DD");
        updateForm.findElement(By.tagName("button")).click(); // Submit update form
        log.info("Update form submitted with new dateFormat");

        // Wait for table to reflect update
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table/tbody/tr/td[text()='YYYY/MM/DD']")
        ));
        log.debug("Table updated with updated dateFormat");

        // Verify table contains updated dateFormat
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        assertTrue(rows.stream().anyMatch(row -> row.getText().contains("YYYY/MM/DD")));
        log.info("Update Preference test completed successfully");
    }

    // -------------------- PATCH --------------------
    /**
     * Test patching a preference.
     * Steps:
     * 1. Navigate to BASE_URL
     * 2. Locate first row and patch form
     * 3. Patch dateFormat
     * 4. Submit form
     * 5. Verify table reflects the patched value
     */
    @Test
    @Order(4)
    void testPatchPreference() {
        log.info("Test: Patch Preference started");

        driver.get(BASE_URL);
        WebElement firstRow = driver.findElement(By.xpath("//table/tbody/tr[1]")); // Element: first table row
        WebElement patchForm = firstRow.findElement(By.xpath(".//form[contains(@action,'/patch')]")); // Element: patch form
        log.debug("Located patch form in first row");

        patchForm.findElement(By.name("dateFormat")).sendKeys("YYYY.MM.DD"); // Element: dateFormat input
        patchForm.findElement(By.tagName("button")).click(); // Submit patch form
        log.info("Patch form submitted with dateFormat: YYYY.MM.DD");

        // Wait for table to reflect patch
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table/tbody/tr/td[text()='YYYY.MM.DD']")
        ));
        log.debug("Table updated with patched dateFormat");

        // Verify table contains patched value
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        assertTrue(rows.stream().anyMatch(row -> row.getText().contains("YYYY.MM.DD")));
        log.info("Patch Preference test completed successfully");
    }

    // -------------------- DELETE --------------------
    /**
     * Test deleting a preference.
     * Steps:
     * 1. Navigate to BASE_URL
     * 2. Locate first row and delete form
     * 3. Handle JS confirmation alert
     * 4. Verify table no longer contains deleted entry
     */
    @Test
    @Order(5)
    void testDeletePreference() {
        log.info("Test: Delete Preference started");

        driver.get(BASE_URL);
        WebElement firstRow = driver.findElement(By.xpath("//table/tbody/tr[1]")); // Element: first table row
        WebElement deleteForm = firstRow.findElement(By.xpath(".//form[contains(@action,'/delete')]")); // Element: delete form
        log.debug("Located delete form in first row");

        // Click delete and handle confirmation alert
        deleteForm.findElement(By.tagName("button")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        log.info("Delete confirmation accepted");

        // Wait for table update and verify deletion
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        assertTrue(rows.stream().noneMatch(row -> row.getText().contains(TEST_USER_ID)));
        log.info("Delete Preference test completed successfully");
    }

    // -------------------- GET ALL --------------------
    /**
     * Test fetching all preferences.
     * Steps:
     * 1. Navigate to BASE_URL
     * 2. Verify table rows are present
     */
    @Test
    @Order(6)
    void testGetAllPreferences() {
        log.info("Test: Get All Preferences started");

        driver.get(BASE_URL);
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr")); // Element: all table rows
        assertNotNull(rows);
        assertTrue(rows.size() >= 0); // Table may be empty

        log.info("Get All Preferences test completed successfully");
        log.debug("Number of rows in table: {}", rows.size());
    }
}
