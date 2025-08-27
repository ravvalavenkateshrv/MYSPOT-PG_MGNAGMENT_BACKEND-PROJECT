package com.myspot.backend.repository;

import com.myspot.backend.entities.PGManagement;
import com.myspot.backend.entities.PGManagement.VerificationStatus;
import com.myspot.backend.entities.PGManagement.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * PG Management Repository
 * 
 * Data access layer for PG Management operations
 * following the same patterns as customer repository
 */
@Repository
public interface PGManagementRepository extends JpaRepository<PGManagement, Long> {
    
    /**
     * Find PG Management by email address
     */
    Optional<PGManagement> findByEmailAddress(String emailAddress);
    
    /**
     * Find PG Management by password reset token
     */
    Optional<PGManagement> findByPasswordResetToken(String token);
    
    /**
     * Check if email address already exists
     */
    boolean existsByEmailAddress(String emailAddress);
    
    /**
     * Check if phone number already exists
     */
    boolean existsByPhoneNumber(String phoneNumber);
    
    /**
     * Find active PG Management by email
     */
    Optional<PGManagement> findByEmailAddressAndIsActiveTrue(String emailAddress);
    
    /**
     * Find PG Management by verification status
     */
    List<PGManagement> findByVerificationStatus(VerificationStatus verificationStatus);
    
    /**
     * Find PG Management by city
     */
    List<PGManagement> findByCityAndIsActiveTrue(String city);
    
    /**
     * Find PG Management by property type
     */
    List<PGManagement> findByPropertyTypeAndIsActiveTrue(PropertyType propertyType);
    
    /**
     * Find PG Management by city and property type
     */
    List<PGManagement> findByCityAndPropertyTypeAndIsActiveTrue(String city, PropertyType propertyType);
    
    /**
     * Find all active PG Management
     */
    List<PGManagement> findAllByIsActiveTrueOrderByCreatedAtDesc();
    
    /**
     * Find PG Management by email verified status
     */
    List<PGManagement> findByEmailVerified(Boolean emailVerified);
    
    /**
     * Custom query to find PG Management by location proximity
     */
    @Query("SELECT p FROM PGManagement p WHERE p.isActive = true AND " +
           "(:city IS NULL OR LOWER(p.city) = LOWER(:city)) AND " +
           "(:state IS NULL OR LOWER(p.state) = LOWER(:state)) " +
           "ORDER BY p.createdAt DESC")
    List<PGManagement> findByLocationProximity(@Param("city") String city, @Param("state") String state);
    
    /**
     * Find PG Management with pending email verification
     */
    @Query("SELECT p FROM PGManagement p WHERE p.emailVerified = false AND p.isActive = true")
    List<PGManagement> findPendingEmailVerification();
    
    /**
     * Count total active PG Management
     */
    long countByIsActiveTrue();
    
    /**
     * Count by verification status
     */
    long countByVerificationStatus(VerificationStatus verificationStatus);
    
    /**
     * Find PG Management by owner name
     */
    List<PGManagement> findByOwnerNameContainingIgnoreCaseAndIsActiveTrue(String ownerName);
    
    /**
     * Find PG Management by PG name
     */
    List<PGManagement> findByPgNameContainingIgnoreCaseAndIsActiveTrue(String pgName);
    
    /**
     * Find PGs by sharing option availability
     */
    @Query("SELECT p FROM PGManagement p WHERE p.isActive = true AND " +
           "p.pgSharing IS NOT NULL AND KEY(p.pgSharing) = :sharingType")
    List<PGManagement> findBySharingTypeAvailable(@Param("sharingType") Integer sharingType);
    
    /**
     * Find PGs within price range for specific sharing
     */
    @Query("SELECT p FROM PGManagement p WHERE p.isActive = true AND " +
           "p.pgSharing IS NOT NULL AND " +
           "VALUE(p.pgSharing) BETWEEN :minPrice AND :maxPrice")
    List<PGManagement> findByPriceRange(@Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice);
}
