package com.artinfo.api.controller.lesson;

import com.artinfo.api.config.UserPrincipal;
import com.artinfo.api.request.LessonCreate;
import com.artinfo.api.request.LessonSearch;
import com.artinfo.api.response.lesson.LessonDetailResponse;
import com.artinfo.api.response.lesson.LessonResponse;
import com.artinfo.api.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LessonController {

  private final LessonService lessonService;

  @GetMapping("/lessons")
  public List<LessonResponse> getList(@ModelAttribute LessonSearch lessonSearch) {
    return this.lessonService.getList(lessonSearch);
  }

  @GetMapping("/lessons/{lessonId}")
  public LessonDetailResponse get(@PathVariable(name = "lessonId") Long id) {
    return lessonService.get(id);
  }

  @PostMapping("/lessons")
  public void create(@AuthenticationPrincipal UserPrincipal userPrincipal, @ModelAttribute LessonCreate lessonCreate) {
    lessonService.create(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"), lessonCreate);
  }
}
