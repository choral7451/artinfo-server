package com.artinfo.api.request.lesson;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageCreate {
  private String title;

  @NotBlank(message = "이미지 주소를 입력해 주세요.")
  private String url;
}
