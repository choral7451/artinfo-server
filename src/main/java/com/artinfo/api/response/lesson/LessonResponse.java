package com.artinfo.api.response.lesson;

import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.domain.Location;
import com.artinfo.api.domain.Major;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class LessonResponse {

  private final Long id;
  private final UUID userId;
  private final String imageUrl;
  private final List<String> locations;
  private final String name;
  private final List<String> majors;

  public LessonResponse(Lesson lesson) {
    this.id = lesson.getId();
    this.userId = lesson.getUser().getId();
    this.imageUrl = lesson.getImageUrl();
    this.locations = lesson.getLocations().stream().map(Location::getName).collect(Collectors.toList());
    this.name = lesson.getName();
    this.majors = lesson.getMajors().stream().map(Major::getName).collect(Collectors.toList());
  }
}
