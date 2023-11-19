package com.artinfo.api.domain;

import com.artinfo.api.domain.enums.RecruitJobsCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "recruit_jobs")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class RecruitJob {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "profile_id")
  private UUID profileId;

  @Column(name = "title")
  private String title;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "company_image_url", columnDefinition = "text")
  private String companyImageUrl;

  @Column(name = "link_url", columnDefinition = "text")
  private String linkUrl;

  @Column(name = "contents", columnDefinition = "text")
  private String contents;

  @Enumerated(EnumType.STRING)
  private RecruitJobsCategory category;

  @Column(name = "is_active")
  private boolean isActive = true;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public RecruitJob(UUID profileId, String title, String companyName, String companyImageUrl, String linkUrl, String contents, RecruitJobsCategory category) {
    this.profileId = profileId;
    this.title = title;
    this.companyName = companyName;
    this.companyImageUrl = companyImageUrl;
    this.linkUrl = linkUrl;
    this.contents = contents;
    this.category = category;
  }
}
