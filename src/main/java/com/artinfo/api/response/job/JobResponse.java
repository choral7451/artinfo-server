package com.artinfo.api.response.job;

import com.artinfo.api.domain.Job;
import com.artinfo.api.domain.Major;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class JobResponse {
  private Long id;
  private String title;
  private String companyName;
  private String companyImageUrl;
  private List<String> majors;
  private LocalDateTime createdAt;

  @Builder
  public JobResponse(Job job) {
    this.id = job.getId();
    this.title = job.getTitle();
    this.companyName = job.getCompanyName();
    this.companyImageUrl = job.getCompanyImageUrl();
    this.majors = job.getMajors().stream().map(Major::getName).toList();
    this.createdAt = job.getCreatedAt();
  }
}
