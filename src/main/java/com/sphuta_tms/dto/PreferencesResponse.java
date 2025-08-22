package com.sphuta_tms.dto;

import com.sphuta_tms.constants.Rounding;
import com.sphuta_tms.constants.WeekStart;

import java.time.LocalDateTime;

/**
 * DTO for sending preferences back to the client.
 * Record ensures immutability and auto-generates constructor, getters, equals, hashCode, toString.
 */
public record PreferencesResponse(

        String userId,
        String dateFormat,
        WeekStart weekStartsOn,
        Rounding rounding,
        LocalDateTime updatedAt

) { }
