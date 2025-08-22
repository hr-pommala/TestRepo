package com.sphuta_tms.util;

import com.sphuta_tms.dto.PreferencesRequest;
import com.sphuta_tms.dto.PreferencesResponse;
import com.sphuta_tms.entity.SettingsPreferences;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between DTOs and entity.
 */
@Slf4j
@Component
public class PreferencesMapper {

    /**
     * Convert PreferencesRequest DTO to SettingsPreferences entity.
     *
     * @param request PreferencesRequest
     * @return SettingsPreferences entity
     */
    public SettingsPreferences toEntity(PreferencesRequest request) {
        log.debug("Mapping PreferencesRequest to SettingsPreferences entity: {}", request);

        SettingsPreferences entity = SettingsPreferences.builder()
                .userId(request.userId())
                .dateFormat(request.dateFormat())
                .weekStartsOn(request.weekStartsOn())  // now matches WeekStart enum
                .rounding(request.rounding())          // now matches Rounding enum
                .build();

        log.info("Mapped PreferencesRequest with userId={} to entity", entity.getUserId());
        return entity;
    }

    /**
     * Convert SettingsPreferences entity to PreferencesResponse DTO.
     *
     * @param entity SettingsPreferences
     * @return PreferencesResponse DTO
     */
    public PreferencesResponse toResponse(SettingsPreferences entity) {
        log.debug("Mapping SettingsPreferences entity to PreferencesResponse: {}", entity);

        PreferencesResponse response = new PreferencesResponse(
                entity.getUserId(),
                entity.getDateFormat(),
                entity.getWeekStartsOn(),
                entity.getRounding(),
                entity.getUpdatedAt()
        );

        log.info("Mapped SettingsPreferences entity with userId={} to PreferencesResponse", response.userId());
        return response;
    }
}
