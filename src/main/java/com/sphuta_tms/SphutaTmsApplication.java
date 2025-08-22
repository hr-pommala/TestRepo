package com.sphuta_tms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Sphuta TMS Application.
 * <p>
 * This class bootstraps the Spring Boot application.
 * It will scan for components, configurations, and beans within the base package
 * and its subpackages.
 * </p>
 */
@SpringBootApplication
public class SphutaTmsApplication {

	/** SLF4J Logger for this class */
	private static final Logger logger = LoggerFactory.getLogger(SphutaTmsApplication.class);

	/**
	 * Main method which starts the Spring Boot application.
	 *
	 * @param args command-line arguments passed during startup
	 */
	public static void main(String[] args) {
		logger.info("Starting Sphuta TMS Application...");

		try {
			SpringApplication.run(SphutaTmsApplication.class, args);
			logger.info("✅ Sphuta TMS Application started successfully.");
		} catch (Exception e) {
			logger.error("❌ Application failed to start due to an error: {}", e.getMessage(), e);
		}
	}
}
