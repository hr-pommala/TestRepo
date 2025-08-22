package com.sphuta_tms.util;

/**
 * Application-wide constants for Sphuta TMS.
 *
 * <p>Centralized storage of default values and reusable messages.
 * Helps avoid duplication of "magic strings" across the codebase.</p>
 *
 * <p>Categories include:
 * <ul>
 *   <li>Default values (timezone, locale)</li>
 *   <li>Success messages</li>
 *   <li>Error messages</li>
 * </ul>
 * </p>
 */
public final class Constants {

    // Prevent instantiation
    private Constants() {}

    // -------------------- DEFAULTS --------------------
    /** Default timezone if user does not supply one. */
    public static final String DEFAULT_TIMEZONE = "America/Chicago";

    /** Default locale if user does not supply one. */
    public static final String DEFAULT_LOCALE = "en_US";

    // -------------------- SUCCESS MESSAGES --------------------
    public static final String PROFILE_FETCH_SUCCESS  = "Profile fetched successfully";
    public static final String PROFILE_CREATE_SUCCESS = "Profile created successfully";
    public static final String PROFILE_UPDATE_SUCCESS = "Profile updated successfully";
    public static final String PROFILE_DELETE_SUCCESS = "Profile deleted successfully";

    // -------------------- ERROR MESSAGES --------------------
    public static final String PROFILE_NOT_FOUND_ERROR = "Profile not found for user id: ";
    public static final String BAD_REQUEST_ERROR       = "Bad request: ";
}
