package com.sphuta_tms.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard API response wrapper.
 *
 * @param <T> the type of the response data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /** Status of the API response (success / error) */
    private String status;

    /** Message describing the result */
    private String message;

    /** Data returned by the API */
    private T data;

    /**
     * Factory method for successful response.
     *
     * @param message response message
     * @param data    response data
     * @param <T>     data type
     * @return ApiResponse with status 'success'
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data);
    }

    /**
     * Factory method for error response.
     *
     * @param message error message
     * @param <T>     data type
     * @return ApiResponse with status 'error'
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("error", message, null);
    }
}
