package com.artinfo.api.service;

import com.artinfo.api.domain.Lesson;
import com.artinfo.api.exception.ProfileNotFound;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.request.LessonSearch;
import com.artinfo.api.response.LessonDetailResponse;
import com.artinfo.api.response.LessonResponse;
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
      .orElseThrow(ProfileNotFound::new);

    return LessonDetailResponse.builder()
      .id(lesson.getId())
      .profileId(lesson.getProfileId())
      .imageUrl(lesson.getImageUrl())
      .locations(lesson.getLocations())
      .name(lesson.getName())
      .subjects(lesson.getSubjects())
      .phone(lesson.getPhone())
      .fee(lesson.getFee())
      .intro(lesson.getIntro())
      .degree(lesson.getDegree())
      .createdAt(lesson.getCreatedAt())
      .build();
  }

  public List<LessonResponse> getList(LessonSearch lessonSearch) {
    return lessonRepository.getList(lessonSearch).stream()
      .map(LessonResponse::new)
      .collect(Collectors.toList());
  }
}