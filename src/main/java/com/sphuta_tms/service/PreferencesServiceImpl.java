package com.sphuta_tms.service;


import com.sphuta_tms.constants.AppConstants;
import com.sphuta_tms.dto.PreferencesRequest;
import com.sphuta_tms.dto.PreferencesResponse;
import com.sphuta_tms.entity.SettingsPreferences;
import com.sphuta_tms.exception.ResourceNotFoundException;
import com.sphuta_tms.repository.SettingsPreferencesRepository;
import com.sphuta_tms.util.PreferencesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing user preferences.
 * Handles all CRUD operations and business logic.
 */
@Slf4j
@Service
public class PreferencesServiceImpl implements PreferencesService {

    /**
     * Repository for performing CRUD operations on
     * {@link SettingsPreferences} entities.
     */
    @Autowired
    private SettingsPreferencesRepository repository;

    /**
     * Mapper utility for converting between
     * {@link PreferencesRequest},
     * {@link PreferencesResponse}, and
     * {@link SettingsPreferences}.
     */
    @Autowired
    private PreferencesMapper mapper;


    @Override
    public PreferencesResponse createPreferences(PreferencesRequest request) {
        log.info("Creating preferences for userId={}", request.userId());

        if (repository.existsByUserId(request.userId())) {
            throw new IllegalArgumentException("Preferences already exist for userId=" + request.userId());
        }

        SettingsPreferences entity = mapper.toEntity(request);
        SettingsPreferences saved = repository.save(entity);

        log.debug("Preferences created successfully for userId={}", saved.getUserId());
        return mapper.toResponse(saved);
    }

    @Override
    public PreferencesResponse getPreferences(String userId) {
        log.info("Fetching preferences for userId={}", userId);

        SettingsPreferences entity = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.MSG_PREF_NOT_FOUND));

        return mapper.toResponse(entity);
    }

    @Override
    public List<PreferencesResponse> getAllPreferences() {
        log.info("Fetching all preferences records");
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PreferencesResponse updatePreferences(String userId, PreferencesRequest request) {
        log.info("Updating preferences for userId={}", userId);

        SettingsPreferences entity = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.MSG_PREF_NOT_FOUND));

        entity.setDateFormat(request.dateFormat());
        entity.setWeekStartsOn(request.weekStartsOn());
        entity.setRounding(request.rounding());

        SettingsPreferences updated = repository.save(entity);
        log.debug("Preferences updated for userId={}", updated.getUserId());

        return mapper.toResponse(updated);
    }

    @Override
    public PreferencesResponse patchPreferences(String userId, PreferencesRequest request) {
        log.info("Patching preferences for userId={}", userId);

        SettingsPreferences entity = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.MSG_PREF_NOT_FOUND));

        // Only update non-null values
        Optional.ofNullable(request.dateFormat()).ifPresent(entity::setDateFormat);
        Optional.ofNullable(request.weekStartsOn()).ifPresent(entity::setWeekStartsOn);
        Optional.ofNullable(request.rounding()).ifPresent(entity::setRounding);

        SettingsPreferences patched = repository.save(entity);
        log.debug("Preferences patched for userId={}", patched.getUserId());

        return mapper.toResponse(patched);
    }

    @Override
    public void deletePreferences(String userId) {
        log.warn("Deleting preferences for userId={}", userId);

        if (!repository.existsByUserId(userId)) {
            throw new ResourceNotFoundException(AppConstants.MSG_PREF_NOT_FOUND);
        }
        repository.deleteById(userId);
    }
}
