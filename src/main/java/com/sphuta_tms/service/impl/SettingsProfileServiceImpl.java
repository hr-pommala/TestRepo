package com.sphuta_tms.service.impl;

import com.sphuta_tms.dto.SettingsProfileDTO;
import com.sphuta_tms.entity.SettingsProfile;
import com.sphuta_tms.exception.ResourceNotFoundException;
import com.sphuta_tms.repository.SettingsProfileRepository;
import com.sphuta_tms.service.SettingsProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link SettingsProfileService}.
 *
 * <p>
 * Handles business logic for managing user settings profiles,
 * including create, retrieve, update, partial update (patch), and delete operations.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingsProfileServiceImpl implements SettingsProfileService {

    private final SettingsProfileRepository repository;

    // -------------------- CREATE --------------------
    @Override
    @Transactional
    public SettingsProfileDTO createProfile(Long userId, SettingsProfileDTO dto) {
        log.info("Creating settings profile for userId={}", userId);

        if (repository.existsByUserId(userId)) {
            log.error("Profile already exists for userId={}", userId);
            throw new IllegalStateException("Profile already exists for this user");
        }

        SettingsProfile entity = mapToEntity(dto);
        entity.setUserId(userId);

        SettingsProfile saved = repository.save(entity);
        log.debug("Profile created: {}", saved);

        return mapToDto(saved);
    }

    // -------------------- READ --------------------
    @Override
    public SettingsProfileDTO getProfile(Long userId) {
        log.info("Fetching settings profile for userId={}", userId);

        SettingsProfile entity = repository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Profile not found for userId={}", userId);
                    return new ResourceNotFoundException("Profile not found for userId=" + userId);
                });

        return mapToDto(entity);
    }

    @Override
    public List<SettingsProfileDTO> getAllProfiles() {
        log.info("Fetching all settings profiles");

        List<SettingsProfile> entities = repository.findAll();
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // -------------------- UPDATE --------------------
    @Override
    @Transactional
    public SettingsProfileDTO updateProfile(Long userId, SettingsProfileDTO dto) {
        log.info("Updating full settings profile for userId={}", userId);

        SettingsProfile entity = repository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Profile not found for userId={}", userId);
                    return new ResourceNotFoundException("Profile not found for userId=" + userId);
                });

        entity.setFullName(dto.fullName());
        entity.setPhone(dto.phone());
        entity.setTimezone(dto.timezone());
        entity.setLocale(dto.locale());

        SettingsProfile updated = repository.save(entity);
        log.debug("Profile updated: {}", updated);

        return mapToDto(updated);
    }

    // -------------------- PATCH --------------------
    @Override
    @Transactional
    public SettingsProfileDTO patchProfile(Long userId, SettingsProfileDTO dto) {
        log.info("Patching settings profile for userId={}", userId);

        SettingsProfile entity = repository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Profile not found for userId={}", userId);
                    return new ResourceNotFoundException("Profile not found for userId=" + userId);
                });

        if (dto.fullName() != null) entity.setFullName(dto.fullName());
        if (dto.phone() != null) entity.setPhone(dto.phone());
        if (dto.timezone() != null) entity.setTimezone(dto.timezone());
        if (dto.locale() != null) entity.setLocale(dto.locale());

        SettingsProfile patched = repository.save(entity);
        log.debug("Profile patched: {}", patched);

        return mapToDto(patched);
    }

    // -------------------- DELETE --------------------
    @Override
    @Transactional
    public void deleteProfile(Long userId) {
        log.warn("Deleting settings profile for userId={}", userId);

        SettingsProfile entity = repository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Profile not found for userId={}", userId);
                    return new ResourceNotFoundException("Profile not found for userId=" + userId);
                });

        repository.delete(entity);
        log.info("Profile deleted successfully for userId={}", userId);
    }

    // -------------------- MAPPERS --------------------
    private SettingsProfileDTO mapToDto(SettingsProfile entity) {
        return new SettingsProfileDTO(
                entity.getUserId(),
                entity.getFullName(),
                entity.getPhone(),
                entity.getTimezone(),
                entity.getLocale()
        );
    }

    private SettingsProfile mapToEntity(SettingsProfileDTO dto) {
        SettingsProfile entity = new SettingsProfile();
        entity.setUserId(dto.userId());
        entity.setFullName(dto.fullName());
        entity.setPhone(dto.phone());
        entity.setTimezone(dto.timezone());
        entity.setLocale(dto.locale());
        return entity;
    }
}
