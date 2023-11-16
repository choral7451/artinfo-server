package com.artinfo.api.response;

import com.artinfo.api.domain.Lesson;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class LessonResponse {

  private final Long id;
  private final UUID profileId;
  private final String imageUrl;
  private final String locations;
  private final String name;
  private final String subjects;

  public LessonResponse(Lesson lesson) {
    this.id = lesson.getId();
    this.profileId = lesson.getProfileId();
    this.imageUrl = lesson.getImageUrl();
    this.locations = lesson.getLocations();
    this.name = lesson.getName();
    this.subjects = lesson.getSubjects();
  }
}
