package com.sphuta_tms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity class representing the invoicing settings for a user.
 * Maps to the table 'settings_invoicing'.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "settings_invoicing")
public class InvoicingSettings {

    /** Unique identifier for the user */
    @Id
    @Column(name = "user_id", length = 36)
    private String userId;

    /** Currency code, default USD */
    @Column(name = "currency", length = 3, nullable = false)
    private String currency = "USD";

    /** Tax identifier number */
    @Column(name = "tax_id", length = 64)
    private String taxId;

    /** Default tax rate (e.g., 0.0800 = 8%) */
    @Column(name = "default_tax_rate", precision = 5, scale = 4, nullable = false)
    private BigDecimal defaultTaxRate = BigDecimal.valueOf(0.0000);

    /** Invoice number format */
    @Column(name = "invoice_number_format", length = 64, nullable = false)
    private String invoiceNumberFormat = "INV-${yyyy}${seq:5}";

    /** Payment terms in days */
    @Column(name = "payment_terms_days", nullable = false)
    private Integer paymentTermsDays = 14;

    /** Late fee percentage */
    @Column(name = "late_fee_percent", precision = 6, scale = 3, nullable = false)
    private BigDecimal lateFeePercent = BigDecimal.valueOf(0.000);

    /** Template ID for invoice */
    @Column(name = "template_id", length = 64, nullable = false)
    private String templateId = "tmpl_default";

    /** Logo file identifier */
    @Column(name = "logo_file_id", length = 36)
    private String logoFileId;

    /** Last update timestamp */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // This method sets updatedAt automatically before insert or update
    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        updatedAt = LocalDateTime.now();
    }
}
