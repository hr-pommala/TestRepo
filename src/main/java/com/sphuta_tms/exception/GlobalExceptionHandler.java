package com.sphuta_tms.exception;

import com.sphuta_tms.util.SphutaApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
 * Global exception handler for the application.
 * Catches exceptions and returns proper ApiResponse with HTTP status codes.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle resource not found exception.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<SphutaApiResponse<?>> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(SphutaApiResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle custom validation exception.
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<SphutaApiResponse<?>> handleValidation(ValidationException ex) {
        log.error("Validation error: {}", ex.getMessage());
        return new ResponseEntity<>(SphutaApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle bean validation errors (@Valid DTOs).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SphutaApiResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.error("Validation failed: {}", errors);
        return new ResponseEntity<>(SphutaApiResponse.error(errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle JSR-380 (Bean Validation) constraint violations.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<SphutaApiResponse<?>> handleConstraintViolation(ConstraintViolationException ex) {
        String errors = ex.getConstraintViolations()
                .stream()
                .map(err -> err.getPropertyPath() + ": " + err.getMessage())
                .collect(Collectors.joining(", "));

        log.error("Constraint violation: {}", errors);
        return new ResponseEntity<>(SphutaApiResponse.error(errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle generic exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<SphutaApiResponse<?>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(SphutaApiResponse.error("An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle missing static resources (e.g., favicon.ico).
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<SphutaApiResponse<?>> handleNoResourceFound(NoResourceFoundException ex) {
        if ("favicon.ico".equalsIgnoreCase(ex.getResourcePath())) {
            log.warn("Ignored missing favicon.ico request");
            return new ResponseEntity<>(SphutaApiResponse.error("Favicon not found"), HttpStatus.NOT_FOUND);
        }
        log.warn("Resource not found: {}", ex.getResourcePath());
        return new ResponseEntity<>(SphutaApiResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
