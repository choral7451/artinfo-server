package com.artinfo.api.service;

import com.artinfo.api.domain.Degree;
import com.artinfo.api.domain.Lesson;
import com.artinfo.api.domain.Location;
import com.artinfo.api.domain.Major;
import com.artinfo.api.exception.UserNotFound;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.request.LessonSearch;
import com.artinfo.api.response.lesson.LessonDetailResponse;
import com.artinfo.api.response.lesson.LessonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonService {

  private final LessonRepository lessonRepository;

  public LessonDetailResponse get(Long id) {
    Lesson lesson = lessonRepository.findById(id)
      .orElseThrow(UserNotFound::new);

    return LessonDetailResponse.builder()
      .id(lesson.getId())
      .profileId(lesson.getProfileId())
      .imageUrl(lesson.getImageUrl())
      .locations(lesson.getLocations().stream().map(Location::getName)
        .collect(Collectors.toList()))
      .name(lesson.getName())
      .majors(lesson.getMajors().stream().map(Major::getName)
        .collect(Collectors.toList())
      )
      .phone(lesson.getPhone())
      .fee(lesson.getFee())
      .intro(lesson.getIntro())
      .degrees(lesson.getDegrees().stream()
        .collect(Collectors.groupingBy(
          Degree::getDegree,
          Collectors.mapping(Degree::getCampusName, Collectors.toList())
        ))
      ).build();
  }

  public List<LessonResponse> getList(LessonSearch lessonSearch) {
    return lessonRepository.getList(lessonSearch).stream()
      .map(LessonResponse::new)
      .collect(Collectors.toList());
  }
}