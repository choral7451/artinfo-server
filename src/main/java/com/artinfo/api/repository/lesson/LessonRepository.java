package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long>, LessonsRepositoryCustom {
}
