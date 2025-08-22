package com.sphuta_tms.service;


import com.sphuta_tms.dto.InvoicingSettingsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing invoicing settings.
 * Defines all business operations for CRUD and partial updates.
 */
public interface InvoicingSettingsService {

    /**
     * Fetch all invoicing settings.
     *
     * @return List of InvoicingSettingsDTO
     */
    List<InvoicingSettingsDTO> getAllSettings();

    /**
     * Fetch invoicing settings by user ID.
     *
     * @param userId unique identifier of the user
     * @return Optional containing InvoicingSettingsDTO if found
     */
    Optional<InvoicingSettingsDTO> getSettingsByUserId(String userId);

    /**
     * Create new invoicing settings.
     *
     * @param invoicingSettingsDTO DTO containing settings data
     * @return InvoicingSettingsDTO of the created record
     */
    InvoicingSettingsDTO createSettings(InvoicingSettingsDTO invoicingSettingsDTO);

    /**
     * Update existing invoicing settings completely (PUT).
     *
     * @param userId unique identifier of the user
     * @param invoicingSettingsDTO DTO containing updated settings
     * @return Updated InvoicingSettingsDTO
     */
    InvoicingSettingsDTO updateSettings(String userId, InvoicingSettingsDTO invoicingSettingsDTO);

    /**
     * Partially update invoicing settings (PATCH).
     *
     * @param userId unique identifier of the user
     * @param invoicingSettingsDTO DTO containing fields to update
     * @return Updated InvoicingSettingsDTO
     */
    InvoicingSettingsDTO patchSettings(String userId, InvoicingSettingsDTO invoicingSettingsDTO);

    /**
     * Delete invoicing settings by user ID.
     *
     * @param userId unique identifier of the user
     */
    void deleteSettings(String userId);
}
