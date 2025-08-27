package com.myspot.backend.entities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * PG Management Entity
 * 
 * Represents a PG (Paying Guest) management/owner in the MySpot platform
 * Contains all PG-related information including authentication,
 * property details, verification status, and business information.
 * 
 * @author MySpot Development Team
 * @version 1.0.0
 */
@Entity
@Table(name = "pg_management", 
       indexes = {
           @Index(name = "idx_pg_email", columnList = "email_address"),
           @Index(name = "idx_pg_phone", columnList = "phone_number"),
           @Index(name = "idx_pg_active", columnList = "is_active"),
           @Index(name = "idx_pg_verified", columnList = "email_verified"),
           @Index(name = "idx_pg_city", columnList = "city"),
           @Index(name = "idx_pg_property_type", columnList = "property_type")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"passwordHash", "emailOtp", "phoneOtp"})
@EqualsAndHashCode(of = {"pgId", "emailAddress"})
public class PGManagement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pg_id")
    private Long pgId;
    
    // Owner Details
    @Column(name = "pg_name", nullable = false, length = 200)
    private String pgName;
    
    @Column(name = "owner_name", nullable = false, length = 100)
    private String ownerName;
    
    @Column(name = "pg_profile_picture", length = 500)
    private String pgProfilePicture;
    
    @ElementCollection
    @CollectionTable(name = "pg_images", joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "image_url", length = 500)
    private List<String> pgImages;
    
    @Column(name = "pg_direction", columnDefinition = "TEXT")
    private String pgDirection; // Google Maps URL
    
    @ElementCollection
    @CollectionTable(name = "pg_share_costs", joinColumns = @JoinColumn(name = "pg_id"))
    @MapKeyColumn(name = "room_share")
    @Column(name = "cost")
    private Map<String, Integer> pgShareMap; // e.g., {"1": 6000, "2": 4000, "3": 3000}
    
    // Contact Information
    @Column(name = "contact_person_name", nullable = false, length = 100)
    private String contactPersonName;
    
    @Column(name = "email_address", nullable = false, unique = true, length = 150)
    private String emailAddress;
    
    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    private String phoneNumber;
    
    @Column(name = "alternate_phone", length = 20)
    private String alternatePhone;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    // Property Location
    @Column(name = "property_address_line1", nullable = false, length = 300)
    private String propertyAddressLine1;
    
    @Column(name = "property_address_line2", length = 300)
    private String propertyAddressLine2;
    
    @ElementCollection
    @CollectionTable(name = "pg_nearby_locations", joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "location", length = 100)
    private List<String> pgNearbyLocations;
    
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    
    @Column(name = "state", nullable = false, length = 50)
    private String state;
    
    @Column(name = "country", nullable = false, length = 50)
    private String country;
    
    @Column(name = "pincode", nullable = false, length = 10)
    private String pincode;
    
    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;
    
    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;
    
    // Property Details
    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;
    
    @Column(name = "total_floors", nullable = false)
    private Integer totalFloors;
    
    @Column(name = "total_rooms", nullable = false)
    private Integer totalRooms;
    
    @Column(name = "pg_rules_text", columnDefinition = "TEXT")
    private String pgRulesText;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @ElementCollection
    @CollectionTable(name = "pg_features", joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "feature", length = 50)
    private List<String> pgFeatures; // e.g., ["food", "gym", "tv", "wifi", "laundry"]
    
    @ElementCollection
    @CollectionTable(name = "pg_sharing_options", joinColumns = @JoinColumn(name = "pg_id"))
    @MapKeyColumn(name = "sharing_number")
    @Column(name = "amount")
    private Map<String, Integer> pgSharing; // e.g., {1: 6000, 2: 4000, 3: 3000}
    
    // Authentication & Status Fields (following customer pattern)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Builder.Default
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;
    
    @Builder.Default
    @Column(name = "phone_verified", nullable = false)
    private Boolean phoneVerified = false;
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;
    
    // OTP Management Fields (following customer pattern)
    @Column(name = "email_otp", length = 6)
    private String emailOtp;
    
    @Column(name = "phone_otp", length = 6)
    private String phoneOtp;
    
    @Column(name = "otp_expiry_time")
    private LocalDateTime otpExpiryTime;
    
    @Builder.Default
    @Column(name = "otp_attempts", nullable = false)
    private Integer otpAttempts = 0;
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "password_reset_token", length = 255)
    private String passwordResetToken;
    
    @Column(name = "password_reset_expiry")
    private LocalDateTime passwordResetExpiry;
    
    @Column(name = "last_otp_request")
    private LocalDateTime lastOtpRequest;
    
    @Builder.Default
    @Column(name = "login_attempts", nullable = false)
    private Integer loginAttempts = 0;
    
    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;
    
    // Version for optimistic locking
    @Version
    private Long version;
    
    /**
     * Property Type enumeration
     */
    public enum PropertyType {
        BOYS_PG("Boys PG"),
        GIRLS_PG("Girls PG"), 
        COLIVE_PG("Colive PG");
        
        private final String displayName;
        
        PropertyType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Verification Status enumeration
     */
    public enum VerificationStatus {
        PENDING("Pending"),
        VERIFIED("Verified"),
        REJECTED("Rejected");
        
        private final String displayName;
        
        VerificationStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Check if account is locked
     */
    public boolean isAccountLocked() {
        return accountLockedUntil != null && LocalDateTime.now().isBefore(accountLockedUntil);
    }
    
    /**
     * Check if OTP is expired
     */
    public boolean isOtpExpired() {
        return otpExpiryTime == null || LocalDateTime.now().isAfter(otpExpiryTime);
    }
    
    /**
     * Check if password reset token is expired
     */
    public boolean isPasswordResetTokenExpired() {
        return passwordResetExpiry == null || LocalDateTime.now().isAfter(passwordResetExpiry);
    }
    
    /**
     * Reset OTP attempts
     */
    public void resetOtpAttempts() {
        this.otpAttempts = 0;
    }
    
    /**
     * Increment OTP attempts
     */
    public void incrementOtpAttempts() {
        this.otpAttempts = (this.otpAttempts == null) ? 1 : this.otpAttempts + 1;
    }
    
    /**
     * Reset login attempts
     */
    public void resetLoginAttempts() {
        this.loginAttempts = 0;
        this.accountLockedUntil = null;
    }
    
    /**
     * Increment login attempts
     */
    public void incrementLoginAttempts() {
        this.loginAttempts = (this.loginAttempts == null) ? 1 : this.loginAttempts + 1;
        
        // Lock account after 5 failed attempts for 30 minutes
        if (this.loginAttempts >= 5) {
            this.accountLockedUntil = LocalDateTime.now().plusMinutes(30);
        }
    }
}
 