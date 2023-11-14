package com.artinfo.api.controller;

import com.artinfo.api.request.LessonSearch;
import com.artinfo.api.response.LessonResponse;
import com.artinfo.api.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LessonController {

  private final LessonService lessonService;

  @GetMapping("/lessons")
  public List<LessonResponse> getList(@ModelAttribute LessonSearch lessonSearch) {
    return this.lessonService.getList(lessonSearch);
  }
}
