package com.sphuta_tms.util;

import com.sphuta_tms.dto.SettingsProfileDTO;
import com.sphuta_tms.entity.SettingsProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Mapper utility class for converting between
 * {@link SettingsProfile} entity and {@link SettingsProfileDTO}.
 *
 * <p>Provides:
 * <ul>
 *   <li>Entity ⇆ DTO conversion</li>
 *   <li>DTO → Entity with explicit userId override</li>
 *   <li>Patch-style updates (ignores null/blank values)</li>
 * </ul>
 * </p>
 */
@Slf4j
public final class SettingsProfileMapper {

    // Prevent instantiation
    private SettingsProfileMapper() {}

    /** Convert Entity -> DTO. */
    public static SettingsProfileDTO toDto(SettingsProfile entity) {
        if (entity == null) {
            log.warn("Attempted to map null SettingsProfile to DTO");
            return null;
        }
        return new SettingsProfileDTO(
                entity.getUserId(),
                entity.getFullName(),
                entity.getPhone(),
                entity.getTimezone(),
                entity.getLocale()
        );
    }

    /** Convert DTO -> Entity. */
    public static SettingsProfile toEntity(SettingsProfileDTO dto) {
        if (dto == null) {
            log.warn("Attempted to map null DTO to SettingsProfile");
            return null;
        }
        SettingsProfile entity = new SettingsProfile();
        entity.setUserId(dto.userId());  // ⚠️ Consider removing if ID should only come from service
        entity.setFullName(dto.fullName());
        entity.setPhone(dto.phone());
        entity.setTimezone(dto.timezone());
        entity.setLocale(dto.locale());
        return entity;
    }

    /** Convert DTO -> Entity with explicit userId override. */
    public static SettingsProfile toEntity(Long userId, SettingsProfileDTO dto) {
        SettingsProfile entity = toEntity(dto);
        if (entity != null) {
            entity.setUserId(userId);
            log.debug("Mapped DTO to Entity with userId override: {}", userId);
        }
        return entity;
    }

    /**
     * Patch update: apply only non-null and non-blank DTO fields
     * onto an existing entity.
     */
    public static void updateEntity(SettingsProfile entity, SettingsProfileDTO dto) {
        if (Objects.isNull(entity) || Objects.isNull(dto)) {
            log.warn("Skipping update: entity or DTO is null");
            return;
        }

        if (Objects.nonNull(dto.fullName()) && !dto.fullName().isBlank()) {
            log.debug("Updating fullName from {} to {}", entity.getFullName(), dto.fullName());
            entity.setFullName(dto.fullName());
        }
        if (Objects.nonNull(dto.phone()) && !dto.phone().isBlank()) {
            log.debug("Updating phone from {} to {}", entity.getPhone(), dto.phone());
            entity.setPhone(dto.phone());
        }
        if (Objects.nonNull(dto.timezone()) && !dto.timezone().isBlank()) {
            log.debug("Updating timezone from {} to {}", entity.getTimezone(), dto.timezone());
            entity.setTimezone(dto.timezone());
        }
        if (Objects.nonNull(dto.locale()) && !dto.locale().isBlank()) {
            log.debug("Updating locale from {} to {}", entity.getLocale(), dto.locale());
            entity.setLocale(dto.locale());
        }
    }
}
