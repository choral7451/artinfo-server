package com.artinfo.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
public class UserResponse {
  private UUID id;
  private String name;
  private String email;
  private String iconImageUrl;
  private Long lessonId;
  private short articleCnt;
  private short commentCnt;
  private boolean isTeacher;
}
