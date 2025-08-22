package com.sphuta_tms.constants;

/**
 * Application-specific constants.
 * Stores default values, API messages, and other fixed strings.
 */
public final class AppConstants {

    private AppConstants() {
        // Private constructor to prevent instantiation
    }

    // Default values
    public static final String DEFAULT_CURRENCY = "USD";
    public static final String DEFAULT_INVOICE_FORMAT = "INV-${yyyy}${seq:5}";
    public static final int DEFAULT_PAYMENT_TERMS = 14;
    public static final String DEFAULT_TEMPLATE_ID = "tmpl_default";

    // API messages
    public static final String MSG_SETTINGS_NOT_FOUND = "Invoicing settings not found";
    public static final String MSG_SETTINGS_CREATED = "Settings created successfully";
    public static final String MSG_SETTINGS_UPDATED = "Settings updated successfully";
    public static final String MSG_SETTINGS_PATCHED = "Settings patched successfully";
    public static final String MSG_SETTINGS_DELETED = "Settings deleted successfully";
    public static final String MSG_FETCH_ALL_SETTINGS = "Fetched all settings";
    public static final String MSG_FETCH_SINGLE_SETTING = "Fetched settings successfully";

}
