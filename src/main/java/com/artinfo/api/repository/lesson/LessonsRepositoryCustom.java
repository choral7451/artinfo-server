package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.Lesson;
import com.artinfo.api.request.LessonSearch;

import java.util.List;

public interface LessonsRepositoryCustom {
    List<Lesson> getList(LessonSearch lessonSearch);
}
