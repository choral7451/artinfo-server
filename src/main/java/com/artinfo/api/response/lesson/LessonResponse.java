package com.artinfo.api.response.lesson;

import com.artinfo.api.domain.Lesson;
import com.artinfo.api.domain.Location;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
public class LessonResponse {

  private final Long id;
  private final UUID profileId;
  private final String imageUrl;
  private final Set<Location> locations;
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
