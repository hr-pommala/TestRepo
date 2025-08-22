package com.sphuta_tms.service;


import com.sphuta_tms.dto.PreferencesRequest;
import com.sphuta_tms.dto.PreferencesResponse;

import java.util.List;

/**
 * Service interface for managing user preferences.
 * Defines contract for all CRUD operations.
 */
public interface PreferencesService {

    /**
     * Create new preferences for a user.
     *
     * @param request PreferencesRequest DTO
     * @return created PreferencesResponse
     */
    PreferencesResponse createPreferences(PreferencesRequest request);

    /**
     * Get preferences by user ID.
     *
     * @param userId user identifier
     * @return PreferencesResponse
     */
    PreferencesResponse getPreferences(String userId);

    /**
     * Get all preferences (for admin usage).
     *
     * @return list of PreferencesResponse
     */
    List<PreferencesResponse> getAllPreferences();

    /**
     * Update (replace) preferences for a user.
     *
     * @param userId  user identifier
     * @param request PreferencesRequest DTO
     * @return updated PreferencesResponse
     */
    PreferencesResponse updatePreferences(String userId, PreferencesRequest request);

    /**
     * Partially update preferences (PATCH).
     *
     * @param userId  user identifier
     * @param request PreferencesRequest DTO (only non-null fields are updated)
     * @return updated PreferencesResponse
     */
    PreferencesResponse patchPreferences(String userId, PreferencesRequest request);

    /**
     * Delete preferences by user ID.
     *
     * @param userId user identifier
     */
    void deletePreferences(String userId);
}
