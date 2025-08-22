package com.sphuta_tms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object (DTO) representing a user's settings profile.
 * <p>
 * This record is used for both request and response operations involving
 * the {@code SettingsProfile} entity. It encapsulates all user preference
 * attributes with validation constraints and OpenAPI annotations.
 * </p>
 *
 * <p>Key points:</p>
 * <ul>
 *   <li>Immutable data structure (Java 17 record).</li>
 *   <li>Validation applied via Jakarta annotations.</li>
 *   <li>Swagger/OpenAPI {@code @Schema} annotations for API documentation.</li>
 * </ul>
 */
@Schema(description = "User settings profile information")
public record SettingsProfileDTO(

        /**
         * Unique identifier for the user.
         * Optional for requests (e.g., creation) but always included in responses.
         */
        @Schema(description = "User ID", example = "1001")
        Long userId,

        /**
         * Full name of the user.
         * Must not be blank.
         */
        @Schema(description = "Full name of the user", example = "John Doe")
        @NotBlank(message = "Full name is required")
        String fullName,

        /**
         * Phone number of the user.
         * Can include +, numbers, dashes, and spaces.
         */
        @Schema(description = "Phone number of the user", example = "+1-202-555-0173")
        @Pattern(regexp = "^[+0-9\\-\\s]*$", message = "Invalid phone number format")
        String phone,

        /**
         * Preferred timezone for the user.
         * Must not be blank.
         */
        @Schema(description = "Preferred timezone", example = "Asia/Kolkata")
        @NotBlank(message = "Timezone is required")
        String timezone,

        /**
         * Preferred locale of the user.
         * Must not be blank.
         */
        @Schema(description = "Preferred locale", example = "en_US")
        @NotBlank(message = "Locale is required")
        String locale
) { }
