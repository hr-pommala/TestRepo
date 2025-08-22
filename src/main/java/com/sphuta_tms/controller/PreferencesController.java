package com.sphuta_tms.controller;

import com.sphuta_tms.dto.PreferencesRequest;
import com.sphuta_tms.dto.PreferencesResponse;
import com.sphuta_tms.entity.SettingsPreferences;
import com.sphuta_tms.service.PreferencesService;
import com.sphuta_tms.util.SphutaApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to handle Settings Preferences endpoints.
 * Exposes CRUD operations with validations and proper responses.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/settings/preferences")
@Tag(name = "Preferences API", description = "Manage user preferences (date format, week start, rounding)")
public class PreferencesController {

    /**
     * Service layer for managing user preferences.
     * Handles business logic between the controller
     * and the repository for {@link SettingsPreferences}.
     */
    @Autowired
    private PreferencesService preferencesService;


    // -------------------- GET ALL / GET BY PARAM --------------------
    @GetMapping
    @Operation(summary = "Get Preferences", description = "Fetch preferences using query parameter userId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferences fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameter"),
            @ApiResponse(responseCode = "404", description = "Preferences not found for the given user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public SphutaApiResponse<List<PreferencesResponse>> getAllPreferences() {
        log.info("Fetching all preferences records");
        return SphutaApiResponse.success(
                "All preferences fetched successfully",
                preferencesService.getAllPreferences()
        );
    }

    // -------------------- GET BY ID --------------------
    @GetMapping("/{userId}")
    @Operation(summary = "Get Preferences by ID", description = "Fetch preferences for a specific user by userId")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferences fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Preferences not found for the given user"),
            @ApiResponse(responseCode = "400", description = "Invalid userId"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public SphutaApiResponse<PreferencesResponse> getPreferencesById(@PathVariable String userId) {
        log.info("Fetching preferences by ID for user: {}", userId);
        return SphutaApiResponse.success(
                "Preferences fetched successfully",
                preferencesService.getPreferences(userId)
        );
    }

    // -------------------- POST --------------------
    @PostMapping
    @Operation(summary = "Create Preferences", description = "Create preferences for a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Preferences created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "409", description = "Preferences already exist for the user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public SphutaApiResponse<PreferencesResponse> createPreferences(
            @Valid @RequestBody PreferencesRequest request) {
        log.info("Creating preferences for user: {}", request.userId());
        return SphutaApiResponse.success(
                "Preferences created successfully",
                preferencesService.createPreferences(request)
        );
    }

    // -------------------- PUT --------------------
    @PutMapping("/{userId}")
    @Operation(summary = "Update Preferences", description = "Update all fields of user preferences")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferences updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Preferences not found for the given user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public SphutaApiResponse<PreferencesResponse> updatePreferences(
            @PathVariable String userId,
            @Valid @RequestBody PreferencesRequest request) {
        log.info("Updating all preferences for user: {}", userId);
        return SphutaApiResponse.success(
                "Preferences updated successfully",
                preferencesService.updatePreferences(userId, request)
        );
    }

    // -------------------- PATCH --------------------
    @PatchMapping("/{userId}")
    @Operation(summary = "Patch Preferences", description = "Update partial fields of user preferences")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferences patched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Preferences not found for the given user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public SphutaApiResponse<PreferencesResponse> patchPreferences(
            @PathVariable String userId,
            @RequestBody PreferencesRequest request) {
        log.info("Patching preferences for user: {}", userId);
        return SphutaApiResponse.success(
                "Preferences patched successfully",
                preferencesService.patchPreferences(userId, request)
        );
    }

    // -------------------- DELETE --------------------
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete Preferences", description = "Delete preferences for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferences deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Preferences not found for the given user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public SphutaApiResponse<Void> deletePreferences(@PathVariable String userId) {
        log.warn("Deleting preferences for user: {}", userId);
        preferencesService.deletePreferences(userId);
        return SphutaApiResponse.success("Preferences deleted successfully", null);
    }
}
