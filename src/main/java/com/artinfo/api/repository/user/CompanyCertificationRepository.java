package com.artinfo.api.repository.user;

import com.artinfo.api.domain.CompanyCertification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyCertificationRepository extends JpaRepository<CompanyCertification, Long> {
  Optional<CompanyCertification> findByUserIdAndIsVerified(UUID userId, Boolean isVerified);
}
