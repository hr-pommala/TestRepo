package com.sphuta_tms.controller;

import com.sphuta_tms.dto.SettingsProfileDTO;
import com.sphuta_tms.service.SettingsProfileService;
import com.sphuta_tms.util.SphutaApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to handle Settings Profile endpoints.
 * Exposes CRUD operations with validations and proper responses.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/settings/profile")
@RequiredArgsConstructor
@Tag(name = "Settings Profile API", description = "Manage user settings profile (name, phone, timezone, locale)")
public class SettingsProfileController {


    private final SettingsProfileService profileService;

    // -------------------- GET ALL --------------------
    @GetMapping
    @Operation(summary = "Get All Profiles", description = "Fetch all user profiles")
    public SphutaApiResponse<List<SettingsProfileDTO>> getAllProfiles() {
        log.info("Fetching all profiles");
        return SphutaApiResponse.success(
                "Profiles fetched successfully",
                profileService.getAllProfiles()
        );
    }

    // -------------------- GET BY PATH --------------------
    @GetMapping("/{userId}")
    @Operation(summary = "Get Profile by ID", description = "Fetch profile for a specific user by userId")
    public SphutaApiResponse<SettingsProfileDTO> getProfileById(@PathVariable Long userId) {
        log.info("Fetching profile by ID for user: {}", userId);
        return SphutaApiResponse.success(
                "Profile fetched successfully",
                profileService.getProfile(userId)
        );
    }

    // -------------------- POST --------------------
    @PostMapping
    @Operation(summary = "Create Profile", description = "Create settings profile for a new user")
    @ApiResponse(responseCode = "201", description = "Profile created successfully")
    public SphutaApiResponse<SettingsProfileDTO> createProfile(
            @Valid @RequestBody SettingsProfileDTO dto) {
        log.info("Creating profile for user: {}", dto.userId());
        return SphutaApiResponse.success(
                "Profile created successfully",
                profileService.createProfile(dto.userId(), dto)
        );
    }

    // -------------------- PUT --------------------
    @PutMapping("/{userId}")
    @Operation(summary = "Update Profile", description = "Update all fields of a user's profile")
    public SphutaApiResponse<SettingsProfileDTO> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody SettingsProfileDTO dto) {
        log.info("Updating all profile fields for user: {}", userId);
        return SphutaApiResponse.success(
                "Profile updated successfully",
                profileService.updateProfile(userId, dto)
        );
    }

    // -------------------- PATCH --------------------
    @PatchMapping("/{userId}")
    @Operation(summary = "Patch Profile", description = "Update partial fields of user profile")
    public SphutaApiResponse<SettingsProfileDTO> patchProfile(
            @PathVariable Long userId,
            @RequestBody SettingsProfileDTO dto) {
        log.info("Patching profile for user: {}", userId);
        return SphutaApiResponse.success(
                "Profile patched successfully",
                profileService.patchProfile(userId, dto)
        );
    }

    // -------------------- DELETE --------------------
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete Profile", description = "Delete profile for a user")
    public SphutaApiResponse<Void> deleteProfile(@PathVariable Long userId) {
        log.warn("Deleting profile for user: {}", userId);
        profileService.deleteProfile(userId);
        return SphutaApiResponse.success("Profile deleted successfully", null);
    }
}
