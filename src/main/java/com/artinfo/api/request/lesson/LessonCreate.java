package com.artinfo.api.request.lesson;

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

  private String imageUrl;
  private List<String> locations;
  private String name;
  private List<String> majors;
  private String phone;
  private Integer fee;
  private String intro;
  private Map<String, List<String>> degrees;
}
