package com.sphuta_tms.exception;

/**
 * Exception thrown when a requested resource (e.g., preferences) is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
