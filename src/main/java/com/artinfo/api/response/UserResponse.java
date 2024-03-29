package com.artinfo.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserResponse {
  private UUID id;
  private String name;
  private String email;
  private String iconImageUrl;
  private Long lessonId;
  private Integer articleCnt;
  private Integer commentCnt;
  private Boolean isTeacher;
}
