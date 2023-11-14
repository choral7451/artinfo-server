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
  private final String phone;
  private final Long fee;
  private final String intro;
  private final String degree;
  private final LocalDateTime createdAt;

  public LessonResponse(Lesson lesson) {
    this.id = lesson.getId();
    this.profileId = lesson.getProfileId();
    this.imageUrl = lesson.getImageUrl();
    this.locations = lesson.getLocations();
    this.name = lesson.getName();
    this.subjects = lesson.getSubjects();
    this.phone = lesson.getPhone();
    this.fee = lesson.getFee();
    this.intro = lesson.getIntro();
    this.degree = lesson.getDegree();
    this.createdAt = lesson.getCreatedAt();
  }
}
