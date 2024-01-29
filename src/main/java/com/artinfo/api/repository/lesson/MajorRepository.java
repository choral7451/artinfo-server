package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.Major;
import com.artinfo.api.domain.job.Job;
import com.artinfo.api.domain.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {

  void deleteAllByLesson(Lesson lesson);

  void deleteAllByJob(Job job);
}
