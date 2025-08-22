package com.sphuta_tms.exception;

/**
 * Exception thrown when a client sends an invalid or malformed request.
 *
 * <p>Intended to be translated into an HTTP <b>400 Bad Request</b> response
 * by a global exception handler (e.g., {@code @ControllerAdvice}).</p>
 *
 * <p>Typical usage:</p>
 * <pre>
 *   if (input == null) {
 *       throw new BadRequestException("Input cannot be null");
 *   }
 * </pre>
 */
public class BadRequestException extends RuntimeException {

    /**
     * Creates a new {@link BadRequestException} with a descriptive message.
     *
     * @param message detail about the cause of the bad request
     */
    public BadRequestException(String message) {
        super(message);
    }
}
