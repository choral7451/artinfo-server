package com.artinfo.api.request.lesson;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class LessonEdit {
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

  @Builder
  public LessonEdit(UUID userId, String name, String imageUrl, List<String> locations, List<String> majors, String phone, Integer fee, String intro, Map<String, List<String>> degrees) {
    this.userId = userId;
    this.name = name;
    this.imageUrl = imageUrl;
    this.locations = locations;
    this.majors = majors;
    this.phone = phone;
    this.fee = fee;
    this.intro = intro;
    this.degrees = degrees;
  }
}
