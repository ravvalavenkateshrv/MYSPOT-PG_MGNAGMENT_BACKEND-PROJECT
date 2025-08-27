// src/main/java/com/myspot/backend/dto/request/PGRegistrationRequest.java
package com.myspot.backend.dto.request;

import com.myspot.backend.entities.PGManagement.PropertyType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * PG Management Registration Request DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PGRegistrationRequest {

    // Owner Details
    @NotBlank(message = "PG name is required")
    @Size(max = 200, message = "PG name must not exceed 200 characters")
    private String pgName;

    @NotBlank(message = "Owner name is required")
    @Size(max = 100, message = "Owner name must not exceed 100 characters")
    private String ownerName;

    @Size(max = 500, message = "Profile picture URL must not exceed 500 characters")
    private String pgProfilePicture;

    private List<@Size(max = 500, message = "Image URL must not exceed 500 characters") String> pgImages;

    private String pgDirection; // Google Maps URL

    private Map<String, Integer> pgShareMap; // Room sharing costs

    // Contact Information
    @NotBlank(message = "Contact person name is required")
    @Size(max = 100, message = "Contact person name must not exceed 100 characters")
    private String contactPersonName;

    @NotBlank(message = "Email address is required")
    @Email(message = "Provide a valid email address")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String emailAddress;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Provide a valid phone number")
    private String phoneNumber;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Provide a valid alternate phone number")
    private String alternatePhone;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be 8–50 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*$",
             message = "Password must contain uppercase, lowercase, digit, special character")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

    // Property Location
    @NotBlank(message = "Property address line1 is required")
    @Size(max = 300, message = "Address must not exceed 300 characters")
    private String propertyAddressLine1;

    @Size(max = 300, message = "Address line2 must not exceed 300 characters")
    private String propertyAddressLine2;

    private List<@Size(max = 100, message = "Location must not exceed 100 characters") String> pgNearbyLocations;

    @NotBlank(message = "City is required")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
    private String pincode;

    @DecimalMin(value = "-90.0", message = "Latitude must be ≥ -90")
    @DecimalMax(value = "90.0", message = "Latitude must be ≤ 90")
    private BigDecimal latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be ≥ -180")
    @DecimalMax(value = "180.0", message = "Longitude must be ≤ 180")
    private BigDecimal longitude;

    // Property Details
    @NotNull(message = "Property type is required")
    private PropertyType propertyType;

    @NotNull(message = "Total floors is required")
    @Min(value = 1, message = "At least 1 floor")
    @Max(value = 50, message = "Up to 50 floors")
    private Integer totalFloors;

    @NotNull(message = "Total rooms is required")
    @Min(value = 1, message = "At least 1 room")
    @Max(value = 1000, message = "Up to 1000 rooms")
    private Integer totalRooms;

    private String pgRulesText;
    private String description;

    private List<@Size(max = 50, message = "Feature must not exceed 50 characters") String> pgFeatures;

    private Map<String, Integer> pgSharing;

    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }

    public boolean hasRequiredFields() {
        return pgName != null && ownerName != null && contactPersonName != null &&
               emailAddress != null && phoneNumber != null &&
               password != null && propertyAddressLine1 != null &&
               city != null && state != null && country != null && pincode != null &&
               propertyType != null && totalFloors != null && totalRooms != null;
    }
}
