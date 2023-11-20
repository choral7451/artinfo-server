package com.artinfo.api.response.lesson;

import com.artinfo.api.domain.Location;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class LessonDetailResponse {

  private final Long id;
  private final UUID profileId;
  private final String imageUrl;
  private final Set<Location> locations;
  private final String name;
  private final String subjects;
  private final String phone;
  private final Long fee;
  private final String intro;
  private final String degree;
}
