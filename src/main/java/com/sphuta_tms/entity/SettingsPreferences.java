package com.sphuta_tms.entity;

import com.sphuta_tms.constants.Rounding;
import com.sphuta_tms.constants.WeekStart;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity class representing user preferences.
 * Each user has exactly one preferences record stored in this table.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "settings_preferences")
public class SettingsPreferences {

    /**
     * Unique identifier for the user (UUID).
     */
    @Id
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    /**
     * Preferred date format (default: YYYY-MM-DD).
     */
    @Column(name = "date_format", nullable = false, length = 20)
    private String dateFormat;

    /**
     * Week start day (enum: MON, SUN).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "week_starts_on", nullable = false, length = 3)
    private WeekStart weekStartsOn;

    /**
     * Rounding preference for timesheet entries.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "rounding", nullable = false, length = 16)
    private Rounding rounding;

    /**
     * Timestamp of the last update.
     * Automatically set when record is updated.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
