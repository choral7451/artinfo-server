package com.artinfo.api.domain;

import com.artinfo.api.domain.enums.CompanyCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "company_certifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyCertification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private CompanyCategory category;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "is_verified", nullable = false)
  private Boolean isVerified = false;

  @OneToMany(mappedBy = "companyCertification",cascade = CascadeType.REMOVE)
  private List<Image> images;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false)
  private User user;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public CompanyCertification(String name, List<Image> images, User user) {
    this.name = name;
    this.images = images;
    this.user = user;
  }
}
