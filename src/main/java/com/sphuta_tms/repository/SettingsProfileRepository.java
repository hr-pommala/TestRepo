package com.sphuta_tms.repository;

import com.sphuta_tms.entity.SettingsProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link SettingsProfile}.
 *
 * <p>
 * Extends {@link JpaRepository} to provide CRUD operations,
 * pagination, and query derivation.
 * </p>
 */
@Repository
public interface SettingsProfileRepository extends JpaRepository<SettingsProfile, Long> {

    /**
     * Find a settings profile by user ID.
     *
     * @param userId the ID of the user
     * @return an {@link Optional} containing the settings profile if found
     */
    Optional<SettingsProfile> findByUserId(Long userId);

    /**
     * Check if a settings profile exists for a given user ID.
     *
     * @param userId the ID of the user
     * @return true if the profile exists, false otherwise
     */
    boolean existsByUserId(Long userId);
}
