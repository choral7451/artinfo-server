package com.artinfo.api.response;

import com.artinfo.api.domain.Profile;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ProfileResponse {
  private UUID id;
  private String name;
  private String email;
  private String iconImageUrl;
  private short articleCnt;
  private short commentCnt;
  private boolean isTeacher;
}
