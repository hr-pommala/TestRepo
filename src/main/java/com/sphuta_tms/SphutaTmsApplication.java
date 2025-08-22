package com.sphuta_tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for the Sphuta TMS Spring Boot Application.
 * <p>
 * This class bootstraps the application by initializing the Spring context
 * and starting the embedded web server.
 * </p>
 */
@SpringBootApplication
public class SphutaTmsApplication {

	// --------------------------------------------------------
	// Logger instance for the application startup lifecycle
	// --------------------------------------------------------
	private static final Logger log = LoggerFactory.getLogger(SphutaTmsApplication.class);

	/**
	 * Main method - Application entry point.
	 * <p>
	 * This method uses {@link SpringApplication#run(Class, String...)}
	 * to launch the Spring Boot application.
	 * </p>
	 *
	 * @param args Command-line arguments passed to the application
	 */
	public static void main(String[] args) {

		/* --------------------------------------------------------
		 * Log the start of the application initialization process
		 * -------------------------------------------------------- */
		log.info("Starting Sphuta TMS Application...");

		SpringApplication.run(SphutaTmsApplication.class, args);

		/* --------------------------------------------------------
		 * Log successful startup
		 * -------------------------------------------------------- */
		log.info("Sphuta TMS Application started successfully!");
	}
}
