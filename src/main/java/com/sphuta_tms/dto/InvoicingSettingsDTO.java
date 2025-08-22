package com.sphuta_tms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO representing invoicing settings for API request and response.
 * Using Java 17 record for immutability and boilerplate reduction.
 */
@Schema(description = "Data Transfer Object for Invoicing Settings")
public record InvoicingSettingsDTO(

        @NotBlank
        @Size(max = 36)
        @Schema(description = "Unique identifier for the user", example = "user-1234-uuid")
        String userId,

        @NotBlank
        @Size(max = 3)
        @Schema(description = "Currency code", example = "USD")
        String currency,

        @Size(max = 64)
        @Schema(description = "Tax identifier number (optional)", example = "12-3456789")
        String taxId,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = true, message = "Default tax rate cannot be negative")
        @DecimalMax(value = "1.0", inclusive = true, message = "Default tax rate must be less than or equal to 1.0")
        @Schema(description = "Default tax rate (e.g., 0.08 = 8%)", example = "0.08")
        BigDecimal defaultTaxRate,

        @NotBlank
        @Size(max = 64)
        @Schema(description = "Invoice number format", example = "INV-${yyyy}${seq:5}")
        String invoiceNumberFormat,

        @NotNull
        @Min(value = 0, message = "Payment terms must be zero or positive days")
        @Schema(description = "Payment terms in days", example = "14")
        Integer paymentTermsDays,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = true, message = "Late fee percentage cannot be negative")
        @Schema(description = "Late fee percentage", example = "0.0")
        BigDecimal lateFeePercent,

        @NotBlank
        @Size(max = 64)
        @Schema(description = "Template ID for invoice", example = "tmpl_default")
        String templateId,

        @Size(max = 36)
        @Schema(description = "Logo file identifier (optional)", example = "logo-uuid-1234")
        String logoFileId
) {}
