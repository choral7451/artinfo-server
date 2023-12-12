package com.artinfo.api.response.job;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class JobDetailResponse {
  private Long id;
  private UUID userId;
  private String title;
  private String companyName;
  private String companyImageUrl;
  private String linkUrl;
  private String contents;
  private List<String> majors;
}
