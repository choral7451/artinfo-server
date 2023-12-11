package com.artinfo.api.domain.job;

import com.artinfo.api.domain.Major;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "recruit_jobs")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "profile_id")
  private UUID userId;

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

  @JsonManagedReference
  @ManyToMany
  @JoinTable(
    name = "recruit_jobs_majors",
    joinColumns = @JoinColumn(name = "recruit_job_id"),
    inverseJoinColumns = @JoinColumn(name = "major_id")
  )
  private List<Major> majors;

  @Column(name = "is_active")
  private boolean isActive = true;

  @CreatedDate
  @Column(name = "created_at", columnDefinition = "timestamp not null")
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public Job(UUID userId, String title, String companyName, String companyImageUrl, String linkUrl, String contents, List<Major> majors) {
    this.userId = userId;
    this.title = title;
    this.companyName = companyName;
    this.companyImageUrl = companyImageUrl;
    this.linkUrl = linkUrl;
    this.contents = contents;
    this.majors = majors;
  }

  public void edit(JobEditor editor) {
    this.title = editor.getTitle();
    this.companyName = editor.getCompanyName();
    this.companyImageUrl = editor.getCompanyImageUrl();
    this.linkUrl = editor.getLinkUrl();
    this.contents = editor.getContents();
    this.majors = editor.getMajors();
  }
}
