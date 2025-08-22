package com.sphuta_tms.service;

import com.sphuta_tms.dto.InvoicingSettingsDTO;
import com.sphuta_tms.entity.InvoicingSettings;
import com.sphuta_tms.exception.ResourceNotFoundException;
import com.sphuta_tms.repository.InvoicingSettingsRepository;
import com.sphuta_tms.util.ResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing invoicing settings.
 * Implements all CRUD and partial update operations.
 */
@Service
@Slf4j
@Transactional
public class InvoicingSettingsServiceImpl implements InvoicingSettingsService {

    @Autowired
    private InvoicingSettingsRepository repository;

    @Autowired
    private ResponseMapper mapper;

    /**
     * Fetch all invoicing settings.
     */
    @Override
    public List<InvoicingSettingsDTO> getAllSettings() {
        log.info("Fetching all invoicing settings");
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Fetch invoicing settings by user ID.
     */
    @Override
    public Optional<InvoicingSettingsDTO> getSettingsByUserId(String userId) {
        log.info("Fetching invoicing settings for userId={}", userId);
        return repository.findById(userId)
                .map(mapper::toDTO);
    }

    /**
     * Create new invoicing settings.
     */
    @Override
    public InvoicingSettingsDTO createSettings(InvoicingSettingsDTO dto) {
        log.info("Creating new invoicing settings for userId={}", dto.userId());
        InvoicingSettings entity = mapper.toEntity(dto);
        InvoicingSettings saved = repository.save(entity);
        log.debug("Invoicing settings created: {}", saved);
        return mapper.toDTO(saved);
    }

    /**
     * Update existing invoicing settings completely (PUT).
     */
    @Override
    public InvoicingSettingsDTO updateSettings(String userId, InvoicingSettingsDTO dto) {
        log.info("Updating invoicing settings for userId={}", userId);
        InvoicingSettings existing = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Settings not found for userId: " + userId));

        // Replace all fields
        existing.setCurrency(dto.currency());
        existing.setTaxId(dto.taxId());
        existing.setDefaultTaxRate(dto.defaultTaxRate());
        existing.setInvoiceNumberFormat(dto.invoiceNumberFormat());
        existing.setPaymentTermsDays(dto.paymentTermsDays());
        existing.setLateFeePercent(dto.lateFeePercent());
        existing.setTemplateId(dto.templateId());
        existing.setLogoFileId(dto.logoFileId());
        existing.setUpdatedAt(java.time.LocalDateTime.now());

        InvoicingSettings updated = repository.save(existing);
        log.debug("Invoicing settings updated: {}", updated);
        return mapper.toDTO(updated);
    }

    /**
     * Partially update invoicing settings (PATCH).
     */
    @Override
    public InvoicingSettingsDTO patchSettings(String userId, InvoicingSettingsDTO dto) {
        log.info("Patching invoicing settings for userId={}", userId);
        InvoicingSettings existing = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Settings not found for userId: " + userId));

        // Only update non-null fields
        if (dto.currency() != null) existing.setCurrency(dto.currency());
        if (dto.taxId() != null) existing.setTaxId(dto.taxId());
        if (dto.defaultTaxRate() != null) existing.setDefaultTaxRate(dto.defaultTaxRate());
        if (dto.invoiceNumberFormat() != null) existing.setInvoiceNumberFormat(dto.invoiceNumberFormat());
        if (dto.paymentTermsDays() != null) existing.setPaymentTermsDays(dto.paymentTermsDays());
        if (dto.lateFeePercent() != null) existing.setLateFeePercent(dto.lateFeePercent());
        if (dto.templateId() != null) existing.setTemplateId(dto.templateId());
        if (dto.logoFileId() != null) existing.setLogoFileId(dto.logoFileId());
        existing.setUpdatedAt(java.time.LocalDateTime.now());

        InvoicingSettings patched = repository.save(existing);
        log.debug("Invoicing settings patched: {}", patched);
        return mapper.toDTO(patched);
    }

    /**
     * Delete invoicing settings by user ID.
     */
    @Override
    public void deleteSettings(String userId) {
        log.info("Deleting invoicing settings for userId={}", userId);
        if (!repository.existsById(userId)) {
            log.warn("Settings not found for userId={}", userId);
            throw new ResourceNotFoundException("Settings not found for userId: " + userId);
        }
        repository.deleteById(userId);
        log.info("Invoicing settings deleted for userId={}", userId);
    }
}
