package com.artinfo.api.request.job;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class JobCreate {
  private UUID userId;
  private String title;
  private String companyName;
  private String companyImageUrl;
  private String linkUrl;
  private String contents;
  private List<String> majors;
}
