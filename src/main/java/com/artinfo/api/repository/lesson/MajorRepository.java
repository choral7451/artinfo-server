package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.Major;
import com.artinfo.api.domain.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {

  Optional<Major> findByName(String name);

  void deleteAllByJob(Job job);
}
