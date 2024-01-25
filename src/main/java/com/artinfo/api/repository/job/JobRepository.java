package com.artinfo.api.repository.job;

import com.artinfo.api.domain.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long>, JobRepositoryCustom{

}
