package com.sphuta_tms.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Generic API response wrapper used across all endpoints.
 * Ensures consistent structure for success & error responses.
 *
 * @param <T> the type of response data
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record SphutaApiResponse<T>(
        boolean success,
        String message,
        T data,
        String traceId,
        LocalDateTime timestamp
) {
    /**
     * Static factory method for success responses.
     */
    public static <T> SphutaApiResponse<T> success(String message, T data) {
        return SphutaApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Static factory method for error responses.
     */
    public static <T> SphutaApiResponse<T> error(String message) {
        return SphutaApiResponse.<T>builder()
                .success(false)
                .message(message)
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
