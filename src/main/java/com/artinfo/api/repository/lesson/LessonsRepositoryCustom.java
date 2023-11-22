package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.request.lesson.LessonSearch;

import java.util.List;

public interface LessonsRepositoryCustom {
    List<Lesson> getList(LessonSearch lessonSearch);
}
