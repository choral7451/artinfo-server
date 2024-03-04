package com.artinfo.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserEdit {
  private UUID userId;

  @NotBlank(message = "사용자 이름를 입력해 주세요.")
  private String name;

  private String iconImageUrl;

  @NotBlank(message = "사용자 공개 아이디를 입력해 주세요.")
  private String publicNickname;

  private String secretNickname;
}
