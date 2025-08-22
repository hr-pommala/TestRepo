package com.sphuta_tms.service;

import com.sphuta_tms.dto.SettingsProfileDTO;
import com.sphuta_tms.entity.SettingsProfile;

import java.util.List;

/**
 * Service interface for managing {@link SettingsProfile}.
 *
 * <p>Provides methods for creating, retrieving, updating,
 * partially updating, and deleting user settings profiles.</p>
 */
public interface SettingsProfileService {

    /**
     * Create a new settings profile for the given user.
     *
     * @param userId the ID of the user
     * @param dto    the profile data transfer object
     * @return the created {@link SettingsProfileDTO}
     */
    SettingsProfileDTO createProfile(Long userId, SettingsProfileDTO dto);

    /**
     * Retrieve a settings profile for the given user.
     *
     * @param userId the ID of the user
     * @return the {@link SettingsProfileDTO}
     */
    SettingsProfileDTO getProfile(Long userId);

    /**
     * Retrieve all settings profiles.
     *
     * @return list of {@link SettingsProfileDTO}
     */
    List<SettingsProfileDTO> getAllProfiles();

    /**
     * Update an existing settings profile (full replacement).
     *
     * @param userId the ID of the user
     * @param dto    the profile data transfer object
     * @return the updated {@link SettingsProfileDTO}
     */
    SettingsProfileDTO updateProfile(Long userId, SettingsProfileDTO dto);

    /**
     * Partially update an existing settings profile (only provided fields).
     *
     * @param userId the ID of the user
     * @param dto    the profile data transfer object with optional fields
     * @return the patched {@link SettingsProfileDTO}
     */
    SettingsProfileDTO patchProfile(Long userId, SettingsProfileDTO dto);

    /**
     * Delete a settings profile by user ID.
     *
     * @param userId the ID of the user
     */
    void deleteProfile(Long userId);
}
