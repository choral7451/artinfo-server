package com.artinfo.api.controller.lesson;

import com.artinfo.api.request.lesson.LessonCreate;
import com.artinfo.api.request.lesson.LessonEdit;
import com.artinfo.api.request.lesson.LessonSearch;
import com.artinfo.api.response.lesson.LessonDetailResponse;
import com.artinfo.api.response.lesson.LessonResponse;
import com.artinfo.api.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
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

  //TODO 로그인 구현 후 추구 개선 필요
  @PostMapping("/lessons")
  public void create(@RequestBody LessonCreate lessonCreate) {
    lessonService.create(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"), lessonCreate);
  }

  @PutMapping("/lessons/{lessonId}")
  public void edit(@PathVariable(name = "lessonId") Long id, @RequestBody LessonEdit lessonEdit) {
    lessonService.edit(id, lessonEdit);
  }

  @DeleteMapping("/lessons/{lessonId}")
  public void delete(@PathVariable(name = "lessonId") Long id) {
    lessonService.delete(id);
  }
}
