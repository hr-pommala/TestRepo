package com.sphuta_tms.service;

import com.sphuta_tms.constants.Rounding;
import com.sphuta_tms.constants.WeekStart;
import com.sphuta_tms.dto.PreferencesRequest;
import com.sphuta_tms.dto.PreferencesResponse;
import com.sphuta_tms.entity.SettingsPreferences;
import com.sphuta_tms.exception.ResourceNotFoundException;
import com.sphuta_tms.repository.SettingsPreferencesRepository;
import com.sphuta_tms.util.PreferencesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link PreferencesServiceImpl}.
 * <p>
 * This class verifies CRUD operations on user preferences
 * using mocked dependencies for repository and mapper.
 * Logging has been added for better traceability during execution.
 */
class PreferencesServiceImplTest {

    /** Logger instance for debugging and monitoring test executions */
    private static final Logger log = LoggerFactory.getLogger(PreferencesServiceImplTest.class);

    /** Mocked repository for simulating database interactions */
    @Mock
    private SettingsPreferencesRepository repository;

    /** Mocked mapper for converting between entity and DTO */
    @Mock
    private PreferencesMapper mapper;

    /** Injected mock service instance under test */
    @InjectMocks
    private PreferencesServiceImpl service;

    /** Sample DTO request object used in test cases */
    private PreferencesRequest request;

    /** Sample entity object used in test cases */
    private SettingsPreferences entity;

    /** Sample response DTO object used in test cases */
    private PreferencesResponse response;

    /**
     * Setup method to initialize mocks and sample objects
     * before each test case is executed.
     */
    @BeforeEach
    void setUp() {
        log.debug("Initializing test setup...");
        MockitoAnnotations.openMocks(this);

        request = new PreferencesRequest(
                "user-123",
                "YYYY-MM-DD",
                WeekStart.MON,
                Rounding.NONE
        );

        entity = SettingsPreferences.builder()
                .userId("user-123")
                .dateFormat("YYYY-MM-DD")
                .weekStartsOn(WeekStart.MON)
                .rounding(Rounding.NONE)
                .updatedAt(LocalDateTime.now())
                .build();

        response = new PreferencesResponse(
                "user-123",
                "YYYY-MM-DD",
                WeekStart.MON,
                Rounding.NONE,
                LocalDateTime.now()
        );

        log.info("Test setup completed with sample data for userId: {}", request.userId());
    }

    /**
     * Test case for creating preferences successfully.
     */
    @Test
    void testCreatePreferences_Success() {
        log.info("Running testCreatePreferences_Success...");
        when(repository.existsByUserId("user-123")).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        PreferencesResponse result = service.createPreferences(request);

        assertNotNull(result, "PreferencesResponse should not be null");
        assertEquals("user-123", result.userId());
        verify(repository, times(1)).save(entity);
        log.debug("Preferences created successfully for userId: {}", result.userId());
    }

    /**
     * Test case for handling creation when preferences already exist.
     */
    @Test
    void testCreatePreferences_AlreadyExists() {
        log.info("Running testCreatePreferences_AlreadyExists...");
        when(repository.existsByUserId("user-123")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.createPreferences(request));
        verify(repository, never()).save(any());
        log.warn("Attempted to create preferences for an already existing userId: {}", request.userId());
    }

    /**
     * Test case for fetching preferences successfully by ID.
     */
    @Test
    void testGetPreferences_Success() {
        log.info("Running testGetPreferences_Success...");
        when(repository.findById("user-123")).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        PreferencesResponse result = service.getPreferences("user-123");

        assertEquals("user-123", result.userId());
        verify(repository, times(1)).findById("user-123");
        log.debug("Preferences fetched successfully for userId: {}", result.userId());
    }

    /**
     * Test case for handling scenario when preferences are not found.
     */
    @Test
    void testGetPreferences_NotFound() {
        log.info("Running testGetPreferences_NotFound...");
        when(repository.findById("user-123")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getPreferences("user-123"));
        log.error("Preferences not found for userId: {}", "user-123");
    }

    /**
     * Test case for fetching all preferences records.
     */
    @Test
    void testGetAllPreferences() {
        log.info("Running testGetAllPreferences...");
        when(repository.findAll()).thenReturn(Arrays.asList(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        List<PreferencesResponse> results = service.getAllPreferences();

        assertEquals(1, results.size());
        assertEquals("user-123", results.get(0).userId());
        log.debug("Total preferences records fetched: {}", results.size());
    }

    /**
     * Test case for updating preferences successfully.
     */
    @Test
    void testUpdatePreferences_Success() {
        log.info("Running testUpdatePreferences_Success...");
        when(repository.findById("user-123")).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        PreferencesResponse result = service.updatePreferences("user-123", request);

        assertEquals("user-123", result.userId());
        verify(repository, times(1)).save(entity);
        log.debug("Preferences updated successfully for userId: {}", result.userId());
    }

    /**
     * Test case for patching preferences successfully.
     */
    @Test
    void testPatchPreferences_Success() {
        log.info("Running testPatchPreferences_Success...");
        when(repository.findById("user-123")).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        PreferencesResponse result = service.patchPreferences("user-123", request);

        assertNotNull(result, "PreferencesResponse should not be null after patching");
        verify(repository, times(1)).save(entity);
        log.debug("Preferences patched successfully for userId: {}", result.userId());
    }

    /**
     * Test case for deleting preferences successfully.
     */
    @Test
    void testDeletePreferences_Success() {
        log.info("Running testDeletePreferences_Success...");
        when(repository.existsByUserId("user-123")).thenReturn(true);
        doNothing().when(repository).deleteById("user-123");

        service.deletePreferences("user-123");

        verify(repository, times(1)).deleteById("user-123");
        log.debug("Preferences deleted successfully for userId: {}", "user-123");
    }

    /**
     * Test case for handling delete operation when preferences not found.
     */
    @Test
    void testDeletePreferences_NotFound() {
        log.info("Running testDeletePreferences_NotFound...");
        when(repository.existsByUserId("user-123")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deletePreferences("user-123"));
        log.error("Failed to delete preferences. No record found for userId: {}", "user-123");
    }
}
