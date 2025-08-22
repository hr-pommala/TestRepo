package com.sphuta_tms.service.impl;

import com.sphuta_tms.dto.SettingsProfileDTO;
import com.sphuta_tms.entity.SettingsProfile;
import com.sphuta_tms.exception.ResourceNotFoundException;
import com.sphuta_tms.repository.SettingsProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SettingsProfileServiceImpl} using Mockito.
 *
 * Purpose:
 *  - To validate the business logic of CRUD and PATCH methods
 *    in the service layer by mocking repository interactions.
 *
 * Approach:
 *  - Repository calls are mocked using Mockito
 *  - Only service logic is verified in isolation
 */
class SettingsProfileServiceImplTest {

    @Mock
    private SettingsProfileRepository repository;

    @InjectMocks
    private SettingsProfileServiceImpl service;

    private SettingsProfile entity;
    private SettingsProfileDTO dto;

    /** Logger for structured test logs */
    private static final Logger log = LoggerFactory.getLogger(SettingsProfileServiceImplTest.class);

    /**
     * Initialize mock repository and sample data before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample entity for testing
        entity = new SettingsProfile();
        entity.setUserId(1L);
        entity.setFullName("John Doe");
        entity.setPhone("+1-202-555-0173");
        entity.setTimezone("Asia/Kolkata");
        entity.setLocale("en_US");

        // Matching DTO for input
        dto = new SettingsProfileDTO(
                1L,
                "John Doe",
                "+1-202-555-0173",
                "Asia/Kolkata",
                "en_US"
        );
    }

    // ============================================================
    // CREATE
    // ============================================================

    @Test
    @DisplayName("✅ Create profile successfully")
    void testCreateProfile_Success() {
        // Mock repository response
        when(repository.existsByUserId(1L)).thenReturn(false);
        when(repository.save(any(SettingsProfile.class))).thenReturn(entity);

        // Call service
        SettingsProfileDTO result = service.createProfile(1L, dto);

        // Validate result
        assertNotNull(result);
        assertEquals("John Doe", result.fullName());

        // Verify repository interactions
        verify(repository, times(1)).existsByUserId(1L);
        verify(repository, times(1)).save(any(SettingsProfile.class));

        log.info("✅ testCreateProfile_Success passed");
    }

    @Test
    @DisplayName("❌ Create profile fails when already exists")
    void testCreateProfile_AlreadyExists() {
        when(repository.existsByUserId(1L)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> service.createProfile(1L, dto));

        verify(repository, times(1)).existsByUserId(1L);
        verify(repository, never()).save(any(SettingsProfile.class));

        log.info("✅ testCreateProfile_AlreadyExists passed");
    }

    // ============================================================
    // READ
    // ============================================================

    @Test
    @DisplayName("✅ Get profile successfully")
    void testGetProfile_Success() {
        when(repository.findByUserId(1L)).thenReturn(Optional.of(entity));

        SettingsProfileDTO result = service.getProfile(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.fullName());

        verify(repository, times(1)).findByUserId(1L);

        log.info("✅ testGetProfile_Success passed");
    }

    @Test
    @DisplayName("❌ Get profile fails when not found")
    void testGetProfile_NotFound() {
        when(repository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getProfile(1L));

        verify(repository, times(1)).findByUserId(1L);

        log.info("✅ testGetProfile_NotFound passed");
    }

    // ============================================================
    // UPDATE
    // ============================================================

    @Test
    @DisplayName("✅ Update profile successfully")
    void testUpdateProfile_Success() {
        when(repository.findByUserId(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(SettingsProfile.class))).thenReturn(entity);

        // New DTO for update
        SettingsProfileDTO updatedDto = new SettingsProfileDTO(
                1L, "Jane Doe", "+44-1234-567890", "America/New_York", "fr_FR"
        );

        SettingsProfileDTO result = service.updateProfile(1L, updatedDto);

        assertNotNull(result);
        assertEquals("Jane Doe", result.fullName());
        assertEquals("fr_FR", result.locale());

        verify(repository, times(1)).findByUserId(1L);
        verify(repository, times(1)).save(any(SettingsProfile.class));

        log.info("✅ testUpdateProfile_Success passed");
    }

    @Test
    @DisplayName("❌ Update profile fails when not found")
    void testUpdateProfile_NotFound() {
        when(repository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateProfile(1L, dto));

        verify(repository, times(1)).findByUserId(1L);
        verify(repository, never()).save(any(SettingsProfile.class));

        log.info("✅ testUpdateProfile_NotFound passed");
    }

    // ============================================================
    // PATCH
    // ============================================================

    @Test
    @DisplayName("✅ Patch profile successfully")
    void testPatchProfile_Success() {
        when(repository.findByUserId(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(SettingsProfile.class))).thenReturn(entity);

        // Only updating name (partial DTO)
        SettingsProfileDTO partialDto = new SettingsProfileDTO(
                null, "Updated Name", null, null, null
        );

        SettingsProfileDTO result = service.patchProfile(1L, partialDto);

        assertNotNull(result);
        assertEquals("Updated Name", result.fullName());

        verify(repository, times(1)).findByUserId(1L);
        verify(repository, times(1)).save(any(SettingsProfile.class));

        log.info("✅ testPatchProfile_Success passed");
    }

    @Test
    @DisplayName("❌ Patch profile fails when not found")
    void testPatchProfile_NotFound() {
        when(repository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.patchProfile(1L, dto));

        verify(repository, times(1)).findByUserId(1L);
        verify(repository, never()).save(any(SettingsProfile.class));

        log.info("✅ testPatchProfile_NotFound passed");
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Test
    @DisplayName("✅ Delete profile successfully")
    void testDeleteProfile_Success() {
        when(repository.findByUserId(1L)).thenReturn(Optional.of(entity));
        doNothing().when(repository).delete(entity);

        service.deleteProfile(1L);

        verify(repository, times(1)).findByUserId(1L);
        verify(repository, times(1)).delete(entity);

        log.info("✅ testDeleteProfile_Success passed");
    }

    @Test
    @DisplayName("❌ Delete profile fails when not found")
    void testDeleteProfile_NotFound() {
        when(repository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteProfile(1L));

        verify(repository, times(1)).findByUserId(1L);
        verify(repository, never()).delete(any(SettingsProfile.class));

        log.info("✅ testDeleteProfile_NotFound passed");
    }
}
