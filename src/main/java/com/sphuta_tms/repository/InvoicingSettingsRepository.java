package com.sphuta_tms.repository;


import com.sphuta_tms.entity.InvoicingSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for InvoicingSettings entity.
 * Provides CRUD operations and custom database queries if needed.
 */
@Repository
public interface InvoicingSettingsRepository extends JpaRepository<InvoicingSettings, String> {

    /**
     * Find invoicing settings by currency code.
     *
     * @param currency the currency code to search for
     * @return Optional containing InvoicingSettings if found
     */
    Optional<InvoicingSettings> findByCurrency(String currency);

    /**
     * Check if settings exist for a given template ID.
     *
     * @param templateId the template ID to search for
     * @return true if settings exist
     */
    boolean existsByTemplateId(String templateId);
}
