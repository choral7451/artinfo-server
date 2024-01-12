package com.artinfo.api.repository.job;

import com.artinfo.api.domain.job.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
  @Override
  @Query("select j from Job j join fetch j.majors")
  Page<Job> findAll(Pageable pageable);

  @Query("select j from Job j join fetch j.majors m where m.name in :majors")
  Page<Job> findByMajors_NameIn(List<String> majors, Pageable pageable);
}
