package com.sphuta_tms.controller;

import com.sphuta_tms.dto.SettingsProfileDTO;
import com.sphuta_tms.service.SettingsProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * UI Controller for SettingsProfile CRUD operations.
 *
 * <p>This controller provides Thymeleaf-based forms to interact
 * with the service layer for:
 * - Creating a profile
 * - Fetching a profile
 * - Updating a profile
 * - Partially updating (patch)
 * - Deleting a profile
 *
 * Each method is mapped to a form submission or navigation action.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/ui/settings/profile")
public class SettingsProfileUIController {

    /** Logger for tracking controller actions */
    private static final Logger log = LoggerFactory.getLogger(SettingsProfileUIController.class);

    /** Service layer dependency for profile CRUD operations */
    private final SettingsProfileService profileService;

    /**
     * Display the form page with an empty profile object.
     *
     * @param model Thymeleaf model object to pass data to the view
     * @return profile-form.html
     */
    @GetMapping
    public String showProfileForm(Model model) {
        log.info("Loading Settings Profile UI page...");

        // Initialize the form with an empty DTO
        model.addAttribute("profile", new SettingsProfileDTO(null, "", "", "", ""));
        return "profile-form";
    }

    /**
     * Handle profile creation.
     *
     * @param dto   Data transfer object containing form values
     * @param model Thymeleaf model for passing messages
     * @return profile-form.html
     */
    @PostMapping("/create")
    public String createProfile(@ModelAttribute SettingsProfileDTO dto, Model model) {
        log.info("Request received to create profile for userId={}", dto.userId());

        profileService.createProfile(dto.userId(), dto);

        log.debug("Profile creation completed for userId={}", dto.userId());

        model.addAttribute("message", "Profile created successfully!");
        model.addAttribute("profile", dto);
        return "profile-form";
    }

    /**
     * Fetch a profile by userId.
     *
     * @param userId The ID of the user
     * @param model  Thymeleaf model for passing profile and messages
     * @return profile-form.html
     */
    @GetMapping("/get")
    public String getProfile(@RequestParam Long userId, Model model) {
        log.info("Request received to fetch profile for userId={}", userId);

        SettingsProfileDTO profile = profileService.getProfile(userId);

        log.debug("Profile retrieved: {}", profile);

        model.addAttribute("profile", profile);
        model.addAttribute("message", "Profile fetched successfully!");
        return "profile-form";
    }

    /**
     * Update an existing profile fully.
     *
     * @param dto   Updated DTO
     * @param model Thymeleaf model for response message
     * @return profile-form.html
     */
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute SettingsProfileDTO dto, Model model) {
        log.info("Request received to update profile for userId={}", dto.userId());

        profileService.updateProfile(dto.userId(), dto);

        log.debug("Profile update completed for userId={}", dto.userId());

        model.addAttribute("message", "Profile updated successfully!");
        model.addAttribute("profile", dto);
        return "profile-form";
    }

    /**
     * Partially update a profile (patch).
     *
     * @param dto   DTO containing only the fields to update
     * @param model Thymeleaf model
     * @return profile-form.html
     */
    @PostMapping("/patch")
    public String patchProfile(@ModelAttribute SettingsProfileDTO dto, Model model) {
        log.info("Request received to patch profile for userId={}", dto.userId());

        profileService.patchProfile(dto.userId(), dto);

        log.debug("Profile patch completed for userId={}", dto.userId());

        model.addAttribute("message", "Profile patched successfully!");
        model.addAttribute("profile", dto);
        return "profile-form";
    }

    /**
     * Delete a profile by userId.
     *
     * @param userId ID of the user profile to delete
     * @param model  Thymeleaf model
     * @return profile-form.html
     */
    @PostMapping("/delete")
    public String deleteProfile(@RequestParam Long userId, Model model) {
        log.info("Request received to delete profile for userId={}", userId);

        profileService.deleteProfile(userId);

        log.debug("Profile deletion completed for userId={}", userId);

        model.addAttribute("message", "Profile deleted successfully!");
        model.addAttribute("profile", new SettingsProfileDTO(null, "", "", "", ""));
        return "profile-form";
    }
}
