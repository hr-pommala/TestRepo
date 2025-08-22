package com.sphuta_tms.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/**
 * Generic API response wrapper for all controllers.
 *
 * Provides structured JSON response with status, message, data, and timestamp.
 *
 * @param <T> the type of data being returned in response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SphutaApiResponse<T> {

    private final int statusCode;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;

    public SphutaApiResponse(int statusCode, String message, T data, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // Getters (no setters → immutability)
    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // -------------------- SUCCESS FACTORY METHODS --------------------

    /** Full control with explicit status code */
    public static <T> SphutaApiResponse<T> success(int statusCode, String message, T data) {
        return new SphutaApiResponse<>(statusCode, message, data, LocalDateTime.now());
    }

    /** Default success (200) with message + data */
    public static <T> SphutaApiResponse<T> success(String message, T data) {
        return success(200, message, data);
    }

    /** Default success (200) with only message (no data) */
    public static SphutaApiResponse<Void> success(String message) {
        return success(200, message, null);
    }

    // -------------------- ERROR FACTORY METHODS --------------------

    /** Error with explicit status code + message */
    public static <T> SphutaApiResponse<T> error(int statusCode, String message) {
        return new SphutaApiResponse<>(statusCode, message, null, LocalDateTime.now());
    }

    /** Error from Exception */
    public static <T> SphutaApiResponse<T> error(int statusCode, Exception ex) {
        return new SphutaApiResponse<>(statusCode, ex.getMessage(), null, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "SphutaTmsApiResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
