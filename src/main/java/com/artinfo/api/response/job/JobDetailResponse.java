package com.artinfo.api.response.job;

import com.artinfo.api.domain.enums.RecruitJobsCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class JobDetailResponse {
  private Long id;
  private UUID profileId;
  private String title;
  private String companyName;
  private String companyImageUrl;
  private String linkUrl;
  private String contents;
  private RecruitJobsCategory category;
  private boolean isActive;
}
