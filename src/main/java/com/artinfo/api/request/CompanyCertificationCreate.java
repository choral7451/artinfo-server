package com.artinfo.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CompanyCertificationCreate {
  private UUID userId;

  @NotBlank(message = "사용자 이름를 입력해 주세요.")
  private String name;

  @NotBlank(message = "소속 단체명을 입력해 주세요.")
  private String companyName;

  @NotBlank(message = "사용자 익명 아이디를 입력해 주세요.")
  private String secretNickname;

  private List<String> imageUrls;
//  private List<ImageCreate> images;
}


