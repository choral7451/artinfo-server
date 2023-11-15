package com.artinfo.api.service;

import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.request.LessonSearch;
import com.artinfo.api.response.LessonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonService {

  private final LessonRepository lessonRepository;

  public List<LessonResponse> getList(LessonSearch lessonSearch) {
    return lessonRepository.getList(lessonSearch).stream()
      .map(LessonResponse::new)
      .collect(Collectors.toList());
  }
}
