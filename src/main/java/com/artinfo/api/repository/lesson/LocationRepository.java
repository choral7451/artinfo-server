package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.Location;
import com.artinfo.api.domain.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

  void deleteAllByLesson(Lesson lesson);
}
