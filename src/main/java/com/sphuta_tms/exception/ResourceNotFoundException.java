package com.sphuta_tms.exception;

/**
 * Exception thrown when a requested resource cannot be found.
 *
 * <p>
 * Typically mapped to HTTP 404 (Not Found).
 * </p>
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause of the exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
