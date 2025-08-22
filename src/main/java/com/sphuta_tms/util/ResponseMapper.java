package com.sphuta_tms.util;

import com.sphuta_tms.dto.InvoicingSettingsDTO;
import com.sphuta_tms.entity.InvoicingSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class to convert between Entity and DTO.
 */
@Component
@Slf4j
public class ResponseMapper {

    /**
     * Convert InvoicingSettings entity to DTO.
     *
     * @param entity the InvoicingSettings entity
     * @return InvoicingSettingsDTO
     */
    public InvoicingSettingsDTO toDTO(InvoicingSettings entity) {
        if (entity == null) {
            log.warn("Attempted to convert null entity to DTO");
            return null;
        }

        InvoicingSettingsDTO dto = new InvoicingSettingsDTO(
                entity.getUserId(),
                entity.getCurrency(),
                entity.getTaxId(),
                entity.getDefaultTaxRate(),
                entity.getInvoiceNumberFormat(),
                entity.getPaymentTermsDays(),
                entity.getLateFeePercent(),
                entity.getTemplateId(),
                entity.getLogoFileId()
        );

        log.debug("Converted entity to DTO: {}", dto);
        return dto;
    }

    /**
     * Convert DTO to InvoicingSettings entity.
     *
     * @param dto the InvoicingSettingsDTO
     * @return InvoicingSettings entity
     */
    public InvoicingSettings toEntity(InvoicingSettingsDTO dto) {
        if (dto == null) {
            log.warn("Attempted to convert null DTO to entity");
            return null;
        }

        InvoicingSettings entity = InvoicingSettings.builder()
                .userId(dto.userId())
                .currency(dto.currency())
                .taxId(dto.taxId())
                .defaultTaxRate(dto.defaultTaxRate())
                .invoiceNumberFormat(dto.invoiceNumberFormat())
                .paymentTermsDays(dto.paymentTermsDays())
                .lateFeePercent(dto.lateFeePercent())
                .templateId(dto.templateId())
                .logoFileId(dto.logoFileId())
                .build();

        log.debug("Converted DTO to entity: {}", entity);
        return entity;
    }
}
