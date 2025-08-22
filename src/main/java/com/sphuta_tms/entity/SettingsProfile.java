package com.sphuta_tms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import com.sphuta_tms.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JPA Entity representing the {@code settings_profile} table.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Stores user-specific settings such as full name, phone, timezone, and locale.</li>
 *   <li>Enforces uniqueness on {@code userId} (1:1 mapping with users table).</li>
 *   <li>Applies default values (from {@link Constants}) if timezone/locale are missing.</li>
 *   <li>Automatically updates {@code updatedAt} timestamp on insert/update.</li>
 * </ul>
 */
@Entity
@Table(
        name = "settings_profile",
        uniqueConstraints = @UniqueConstraint(
                name = "uc_settings_profile_user_id",
                columnNames = {"user_id"}
        )
)
public class SettingsProfile {

    /** Logger instance for lifecycle events */
    private static final Logger log = LoggerFactory.getLogger(SettingsProfile.class);

    /** Foreign key to users table (primary key here). */
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    /** Full name of the user. */
    @Column(name = "full_name", length = 200)
    private String fullName;

    /** Phone number of the user (optional). */
    @Column(name = "phone", length = 50)
    private String phone;

    /** Preferred timezone (defaulted if blank). */
    @Column(name = "timezone", length = 100, nullable = false)
    private String timezone;

    /** Preferred locale (defaulted if blank). */
    @Column(name = "locale", length = 10, nullable = false)
    private String locale;

    /** Timestamp of last update (managed internally). */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /* ---------- Constructors ---------- */

    public SettingsProfile() {
        // Default constructor required by JPA
    }

    public SettingsProfile(Long userId, String fullName, String phone, String timezone, String locale) {
        this.userId = userId;
        this.fullName = fullName;
        this.phone = phone;
        this.timezone = timezone;
        this.locale = locale;
    }

    /* ---------- Lifecycle Callbacks ---------- */

    /**
     * Called before entity is inserted into the database.
     * Ensures defaults for timezone/locale and initializes {@code updatedAt}.
     */
    @PrePersist
    protected void prePersist() {
        if (this.timezone == null || this.timezone.isBlank()) {
            this.timezone = Constants.DEFAULT_TIMEZONE;
        }
        if (this.locale == null || this.locale.isBlank()) {
            this.locale = Constants.DEFAULT_LOCALE;
        }
        this.updatedAt = LocalDateTime.now();
        log.debug("PrePersist: Initialized SettingsProfile for userId={} with timezone={} and locale={}",
                userId, timezone, locale);
    }

    /**
     * Called before entity is updated in the database.
     * Refreshes {@code updatedAt} timestamp.
     */
    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        log.debug("PreUpdate: Updated SettingsProfile for userId={} at {}", userId, updatedAt);
    }

    /* ---------- Getters & Setters ---------- */

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /* ---------- equals / hashCode / toString ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SettingsProfile)) return false;
        SettingsProfile that = (SettingsProfile) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "SettingsProfile{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", timezone='" + timezone + '\'' +
                ", locale='" + locale + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
