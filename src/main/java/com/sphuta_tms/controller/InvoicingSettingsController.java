package com.sphuta_tms.controller;

import com.sphuta_tms.dto.InvoicingSettingsDTO;
import com.sphuta_tms.exception.ResourceNotFoundException;
import com.sphuta_tms.service.InvoicingSettingsService;
import com.sphuta_tms.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing invoicing settings.
 * Provides endpoints for CRUD and partial updates.
 */
@RestController
@RequestMapping("/api/v1/settings/invoicing")
@Slf4j
@Tag(name = "Invoicing Settings", description = "API for managing invoicing settings")
public class InvoicingSettingsController {

    @Autowired
    private InvoicingSettingsService service;

    /**
     * GET /api/v1/settings/invoicing
     * Fetch all invoicing settings.
     */

    @GetMapping
    @Operation(summary = "Get all invoicing settings",
            description = "Returns a list of all invoicing settings configured in the system")
    public ResponseEntity<ApiResponse<List<InvoicingSettingsDTO>>> getAllSettings() {
        log.info("GET request: Fetch all invoicing settings");
        List<InvoicingSettingsDTO> settingsList = service.getAllSettings();
        return ResponseEntity.ok(ApiResponse.success("Fetched all settings", settingsList));
    }

    /**
     * GET /api/v1/settings/invoicing/{userId}
     * Fetch invoicing settings by user ID.
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get invoicing settings by user ID",
            description = "Returns the invoicing settings for the specified user ID")
    public ResponseEntity<ApiResponse<InvoicingSettingsDTO>> getSettingsByUserId(
            @Parameter(description = "Unique identifier of the user") @PathVariable String userId) {
        log.info("GET request: Fetch settings for userId={}", userId);
        return service.getSettingsByUserId(userId)
                .map(dto -> ResponseEntity.ok(ApiResponse.success("Fetched settings", dto)))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Settings not found for userId: " + userId));
    }

    /**
     * POST /api/v1/settings/invoicing
     * Create new invoicing settings.
     */
    @PostMapping
    @Operation(summary = "Create new invoicing settings",
            description = "Creates invoicing settings for a specific user")
    public ResponseEntity<ApiResponse<InvoicingSettingsDTO>> createSettings(
            @Parameter(description = "Invoicing settings data to create") @Valid @RequestBody InvoicingSettingsDTO dto) {
        log.info("POST request: Create invoicing settings for userId={}", dto.userId());
        InvoicingSettingsDTO created = service.createSettings(dto);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Settings created successfully", created));
    }

    /**
     * PUT /api/v1/settings/invoicing/{userId}
     * Fully update invoicing settings.
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update invoicing settings completely",
            description = "Replaces existing invoicing settings for a user with new values")
    public ResponseEntity<ApiResponse<InvoicingSettingsDTO>> updateSettings(
            @Parameter(description = "Unique identifier of the user") @PathVariable String userId,
            @Parameter(description = "Updated invoicing settings data") @Valid @RequestBody InvoicingSettingsDTO dto) {
        log.info("PUT request: Update settings for userId={}", userId);
        InvoicingSettingsDTO updated = service.updateSettings(userId, dto);
        return ResponseEntity.ok(ApiResponse.success("Settings updated successfully", updated));
    }

    /**
     * PATCH /api/v1/settings/invoicing/{userId}
     * Partially update invoicing settings.
     */
    @PatchMapping("/{userId}")
    @Operation(summary = "Partially update invoicing settings",
            description = "Updates only the provided fields of invoicing settings for a user")
    public ResponseEntity<ApiResponse<InvoicingSettingsDTO>> patchSettings(
            @Parameter(description = "Unique identifier of the user") @PathVariable String userId,
            @Parameter(description = "Partial invoicing settings data to update") @RequestBody InvoicingSettingsDTO dto) {
        log.info("PATCH request: Patch settings for userId={}", userId);
        InvoicingSettingsDTO patched = service.patchSettings(userId, dto);
        return ResponseEntity.ok(ApiResponse.success("Settings patched successfully", patched));
    }

    /**
     * DELETE /api/v1/settings/invoicing/{userId}
     * Delete invoicing settings.
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete invoicing settings by user ID",
            description = "Deletes invoicing settings for the specified user")
    public ResponseEntity<ApiResponse<Void>> deleteSettings(
            @Parameter(description = "Unique identifier of the user") @PathVariable String userId) {
        log.info("DELETE request: Delete settings for userId={}", userId);
        service.deleteSettings(userId);
        return ResponseEntity.ok(ApiResponse.success("Settings deleted successfully", null));
    }
}
