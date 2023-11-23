package com.artinfo.api.request.lesson;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class LessonCreate {
  //todo 추후 삭제 필요
  private UUID userId;

  @NotBlank
  private String name;

  @NotBlank
  private String imageUrl;

  private List<String> locations;

  private List<String> majors;

  @NotBlank
  private String phone;

  private Integer fee;

  @NotBlank
  private String intro;

  private Map<String, List<String>> degrees;
}
