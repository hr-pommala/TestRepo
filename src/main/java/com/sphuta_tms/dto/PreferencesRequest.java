package com.sphuta_tms.dto;


import com.sphuta_tms.constants.Rounding;
import com.sphuta_tms.constants.WeekStart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO for Preferences API request using Java 17 Record.
 * Immutable and concise representation of the request payload.
 */
public record PreferencesRequest(

        @NotBlank(message = "UserId is required")
        String userId,

        @Pattern(regexp = "YYYY-MM-DD", message = "Date format must be YYYY-MM-DD")
        String dateFormat,

        WeekStart weekStartsOn,

        Rounding rounding

) {}
