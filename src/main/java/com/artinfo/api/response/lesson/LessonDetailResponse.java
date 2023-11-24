package com.artinfo.api.response.lesson;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class LessonDetailResponse {

  private final Long id;
  private final UUID userId;
  private final String imageUrl;
  private final List<String> locations;
  private final String name;
  private final List<String> majors;
  private final String phone;
  private final Integer fee;
  private final String intro;
  private final List<Map<String, String>> degrees;
}
