package com.artinfo.api.repository.job;

import com.artinfo.api.domain.job.Job;
import com.artinfo.api.request.job.JobSearch;

import java.util.List;

public interface JobRepositoryCustom {

  List<Job> getList(JobSearch jobSearch);
}
