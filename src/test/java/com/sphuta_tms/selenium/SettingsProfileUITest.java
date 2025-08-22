package com.sphuta_tms.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Selenium UI Test for SettingsProfile CRUD via Thymeleaf forms.
 *
 * This test class automates the browser-based testing of the
 * Settings Profile module using Selenium WebDriver.
 *
 * Features tested:
 *  - Create Profile
 *  - Get Profile
 *  - Update Profile
 *  - Patch Profile
 *  - Delete Profile
 *
 * Execution order is controlled with @Order annotations.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SettingsProfileUITest {

    private static WebDriver driver;

    /** Logger instance for structured logs */
    private static final Logger log = LoggerFactory.getLogger(SettingsProfileUITest.class);

    /**
     * Setup ChromeDriver before running the tests.
     * Navigates to the Settings Profile UI page.
     */
    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Open the UI page
        driver.get("http://localhost:8080/ui/settings/profile");
        driver.manage().window().maximize();

        log.info("Opened Settings Profile UI page successfully");
    }

    /**
     * Test Case 1: Create a new profile.
     * Steps:
     *  - Locate Create Profile form
     *  - Fill all input fields
     *  - Submit form
     *  - Verify success message
     */
    @Test
    @Order(1)
    public void testCreateProfile() {
        WebElement form = driver.findElement(By.xpath("//form[h3[text()='Create Profile']]"));

        // Fill input fields
        form.findElement(By.name("userId")).sendKeys("101");
        form.findElement(By.name("name")).sendKeys("John Doe");
        form.findElement(By.name("phone")).sendKeys("1234567890");
        form.findElement(By.name("timezone")).sendKeys("IST");
        form.findElement(By.name("locale")).sendKeys("en_IN");

        // Click submit
        form.findElement(By.tagName("button")).click();

        Assertions.assertTrue(driver.getPageSource().contains("Profile created successfully"));
        log.info("✅ testCreateProfile passed");
    }

    /**
     * Test Case 2: Fetch the created profile.
     * Steps:
     *  - Locate Get Profile form
     *  - Enter userId
     *  - Submit form
     *  - Verify success message
     */
    @Test
    @Order(2)
    public void testGetProfile() {
        WebElement form = driver.findElement(By.xpath("//form[h3[text()='Get Profile']]"));

        form.findElement(By.name("userId")).sendKeys("101");
        form.findElement(By.tagName("button")).click();

        Assertions.assertTrue(driver.getPageSource().contains("Profile fetched successfully"));
        log.info("✅ testGetProfile passed");
    }

    /**
     * Test Case 3: Update an existing profile.
     * Steps:
     *  - Locate Update Profile form
     *  - Provide updated name
     *  - Submit form
     *  - Verify success message
     */
    @Test
    @Order(3)
    public void testUpdateProfile() {
        WebElement form = driver.findElement(By.xpath("//form[h3[text()='Update Profile']]"));

        form.findElement(By.name("userId")).sendKeys("101");
        form.findElement(By.name("name")).sendKeys("Updated Name");
        form.findElement(By.tagName("button")).click();

        Assertions.assertTrue(driver.getPageSource().contains("Profile updated successfully"));
        log.info("✅ testUpdateProfile passed");
    }

    /**
     * Test Case 4: Patch (partial update) the profile.
     * Steps:
     *  - Locate Patch Profile form
     *  - Update phone number
     *  - Submit form
     *  - Verify success message
     */
    @Test
    @Order(4)
    public void testPatchProfile() {
        WebElement form = driver.findElement(By.xpath("//form[h3[text()='Patch Profile (Partial Update)']]"));

        form.findElement(By.name("userId")).sendKeys("101");
        form.findElement(By.name("phone")).sendKeys("9999999999");
        form.findElement(By.tagName("button")).click();

        Assertions.assertTrue(driver.getPageSource().contains("Profile patched successfully"));
        log.info("✅ testPatchProfile passed");
    }

    /**
     * Test Case 5: Delete the profile.
     * Steps:
     *  - Locate Delete Profile form
     *  - Provide userId
     *  - Submit form
     *  - Verify success message
     */
    @Test
    @Order(5)
    public void testDeleteProfile() {
        WebElement form = driver.findElement(By.xpath("//form[h3[text()='Delete Profile']]"));

        form.findElement(By.name("userId")).sendKeys("101");
        form.findElement(By.tagName("button")).click();

        Assertions.assertTrue(driver.getPageSource().contains("Profile deleted successfully"));
        log.info("✅ testDeleteProfile passed");
    }

    /**
     * Close browser after all test cases are executed.
     */
    @AfterAll
    public static void teardown() {
        if (driver != null) {
            driver.quit();
            log.info("Browser closed successfully");
        }
    }
}
