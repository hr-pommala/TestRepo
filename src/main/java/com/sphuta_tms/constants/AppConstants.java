package com.sphuta_tms.constants;

/**
 * Application-wide constants used across layers.
 *
 * Best practice: Use a final utility class for messages & fixed values.
 * Enums (WeekStart, Rounding) are used for strict domains.
 */
public final class AppConstants {

    // Prevent instantiation
    private AppConstants() {}

    /** ======= Default Values ======= */
    public static final String DEFAULT_DATE_FORMAT = "YYYY-MM-DD";
    public static final String DEFAULT_WEEK_START = "MON";
    public static final String DEFAULT_ROUNDING = "NONE";

    /** ======= API Messages ======= */
    public static final String MSG_PREF_CREATED = "Preferences created successfully";
    public static final String MSG_PREF_FETCHED = "Preferences retrieved successfully";
    public static final String MSG_PREF_UPDATED = "Preferences updated successfully";
    public static final String MSG_PREF_PATCHED = "Preferences patched successfully";
    public static final String MSG_PREF_DELETED = "Preferences deleted successfully";
    public static final String MSG_PREF_NOT_FOUND = "Preferences not found for the given user";

    /** ======= Validation Messages ======= */
    public static final String MSG_INVALID_DATE_FORMAT = "Invalid date format";
    public static final String MSG_INVALID_WEEK_START = "Invalid week start value (must be MON or SUN)";
    public static final String MSG_INVALID_ROUNDING = "Invalid rounding value";
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
