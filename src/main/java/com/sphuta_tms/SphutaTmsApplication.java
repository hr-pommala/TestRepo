package com.sphuta_tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.ZoneId;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

/**
 * Sphuta TMS - Settings Profile service.
 *
 * <p>Bootstraps the Spring Boot application and sets global defaults
 * (like the JVM timezone) early during startup.</p>
 *
 * <p>Notes:
 * <ul>
 *   <li>Java 17+ compatible.</li>
 *   <li>Spring Boot 3.x.</li>
 *   <li>Other components (entities, DTO records, services, controllers, exceptions)
 *       are added in subsequent files.</li>
 * </ul>
 * </p>
 */
@Slf4j
@SpringBootApplication
public class SphutaTmsApplication {

    /**
     * Application entry point.
     * <p>
     * Sets default JVM timezone to application default before the context loads.
     * The constant value is duplicated here for early start; later we’ll centralize
     * the same value in {@code com.sphuta.tms.util.Constants}.
     * </p>
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Define the default application timezone
        String defaultTimezone = "America/Chicago";

        // Set a safe default timezone before Spring context initialization
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of(defaultTimezone)));
        log.info("Setting default timezone to: {}", defaultTimezone);

        // Start Spring Boot application
        log.info("Starting Sphuta TMS Application...");
        SpringApplication.run(SphutaTmsApplication.class, args);
        log.info("Sphuta TMS Application started successfully.");
    }
}
