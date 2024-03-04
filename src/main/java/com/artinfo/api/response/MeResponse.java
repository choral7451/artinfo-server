package com.artinfo.api.response;

import com.artinfo.api.domain.enums.CompanyCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class MeResponse {
  private UUID id;
  private String name;
  private String publicNickname;
  private String secretNickname;
  private String email;
  private String iconImageUrl;
  private CompanyCategory companyCategory;
  private String companyName;
  private Long lessonId;
  private Boolean isTeacher;
}
