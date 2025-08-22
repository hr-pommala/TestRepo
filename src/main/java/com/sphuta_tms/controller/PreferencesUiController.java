package com.sphuta_tms.controller;

import com.sphuta_tms.constants.Rounding;
import com.sphuta_tms.constants.WeekStart;
import com.sphuta_tms.dto.PreferencesRequest;
import com.sphuta_tms.dto.PreferencesResponse;
import com.sphuta_tms.service.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller
@RequestMapping("/preferences-ui")
@Slf4j
public class PreferencesUiController {

    @Autowired
    private PreferencesService preferencesService;

    // -------------------- DASHBOARD --------------------
    /**
     * Load dashboard with all preferences and optional getByIdUserId query.
     * Element/Block Comments:
     * - preferences: List of all preferences displayed in table
     * - message: Status message for UI
     * - getByIdUserId: pre-filled input for Get By ID form
     */
    @GetMapping
    public String dashboard(@RequestParam(required = false) String getByIdUserId, Model model) {
        log.info("Loading dashboard");
        try {
            List<PreferencesResponse> preferences = preferencesService.getAllPreferences();
            log.debug("Fetched {} preferences", preferences.size());
            model.addAttribute("preferences", preferences);
            model.addAttribute("message", "");
            model.addAttribute("getByIdUserId", getByIdUserId != null ? getByIdUserId : "");
        } catch (Exception e) {
            log.error("Error loading dashboard", e);
            model.addAttribute("message", "Error loading dashboard: " + e.getMessage());
            model.addAttribute("preferences", List.of());
            model.addAttribute("getByIdUserId", "");
        }
        return "preferences";
    }

    // -------------------- CREATE --------------------
    /**
     * Handle create preference form submission.
     * Element/Block Comments:
     * - userId, dateFormat, weekStartsOn, rounding: Input form fields
     * - preferences: Updated list displayed after creation
     * - message: Success/Error message shown to user
     */
    @PostMapping
    public String createPreference(@RequestParam String userId,
                                   @RequestParam String dateFormat,
                                   @RequestParam String weekStartsOn,
                                   @RequestParam String rounding,
                                   Model model) {
        log.info("Creating preference for userId: {}", userId);
        try {
            PreferencesRequest request = new PreferencesRequest(
                    userId,
                    dateFormat,
                    WeekStart.valueOf(weekStartsOn),
                    Rounding.valueOf(rounding)
            );
            log.debug("PreferencesRequest created: {}", request);
            preferencesService.createPreferences(request);
            model.addAttribute("message", "Preference created successfully!");
            log.info("Preference created successfully for userId: {}", userId);
        } catch (Exception e) {
            log.error("Error creating preference for userId: {}", userId, e);
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        model.addAttribute("preferences", preferencesService.getAllPreferences());
        model.addAttribute("getByIdUserId", "");
        return "preferences";
    }

    // -------------------- UPDATE --------------------
    /**
     * Handle update preference form submission.
     * Element/Block Comments:
     * - userId, dateFormat, weekStartsOn, rounding: Input fields from update form
     * - preferences: Updated list displayed after update
     * - message: Success/Error message shown to user
     */
    @PostMapping("/update")
    public String updatePreference(@RequestParam String userId,
                                   @RequestParam String dateFormat,
                                   @RequestParam String weekStartsOn,
                                   @RequestParam String rounding,
                                   Model model) {
        log.info("Updating preference for userId: {}", userId);
        try {
            PreferencesRequest request = new PreferencesRequest(
                    userId,
                    dateFormat,
                    WeekStart.valueOf(weekStartsOn),
                    Rounding.valueOf(rounding)
            );
            log.debug("PreferencesRequest for update: {}", request);
            preferencesService.updatePreferences(userId, request);
            model.addAttribute("message", "Preference updated successfully!");
            log.info("Preference updated successfully for userId: {}", userId);
        } catch (Exception e) {
            log.error("Error updating preference for userId: {}", userId, e);
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        model.addAttribute("preferences", preferencesService.getAllPreferences());
        model.addAttribute("getByIdUserId", "");
        return "preferences";
    }

    // -------------------- PATCH --------------------
    /**
     * Handle patch preference form submission (partial update).
     * Element/Block Comments:
     * - dateFormat, weekStartsOn, rounding: Optional fields
     * - preferences: Updated list displayed after patch
     * - message: Success/Error message shown to user
     */
    @PostMapping("/patch")
    public String patchPreference(@RequestParam String userId,
                                  @RequestParam(required = false) String dateFormat,
                                  @RequestParam(required = false) String weekStartsOn,
                                  @RequestParam(required = false) String rounding,
                                  Model model) {
        log.info("Patching preference for userId: {}", userId);
        try {
            PreferencesRequest request = new PreferencesRequest(
                    userId,
                    dateFormat,
                    (weekStartsOn != null && !weekStartsOn.isEmpty()) ? WeekStart.valueOf(weekStartsOn) : null,
                    (rounding != null && !rounding.isEmpty()) ? Rounding.valueOf(rounding) : null
            );
            log.debug("PreferencesRequest for patch: {}", request);
            preferencesService.patchPreferences(userId, request);
            model.addAttribute("message", "Preference patched successfully!");
            log.info("Preference patched successfully for userId: {}", userId);
        } catch (Exception e) {
            log.error("Error patching preference for userId: {}", userId, e);
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        model.addAttribute("preferences", preferencesService.getAllPreferences());
        model.addAttribute("getByIdUserId", "");
        return "preferences";
    }

    // -------------------- DELETE --------------------
    /**
     * Handle delete preference form submission.
     * Element/Block Comments:
     * - userId: ID of the preference to delete
     * - preferences: Updated list displayed after deletion
     * - message: Success/Error message shown to user
     */
    @PostMapping("/delete")
    public String deletePreference(@RequestParam String userId, Model model) {
        log.info("Deleting preference for userId: {}", userId);
        try {
            preferencesService.deletePreferences(userId);
            model.addAttribute("message", "Preference deleted successfully!");
            log.info("Preference deleted successfully for userId: {}", userId);
        } catch (Exception e) {
            log.error("Error deleting preference for userId: {}", userId, e);
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        model.addAttribute("preferences", preferencesService.getAllPreferences());
        model.addAttribute("getByIdUserId", "");
        return "preferences";
    }
}
