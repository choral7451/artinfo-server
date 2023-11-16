package com.artinfo.api.controller.lesson;

import com.artinfo.api.request.LessonSearch;
import com.artinfo.api.response.LessonDetailResponse;
import com.artinfo.api.response.LessonResponse;
import com.artinfo.api.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
