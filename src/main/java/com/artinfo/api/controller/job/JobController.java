package com.artinfo.api.controller.job;

import com.artinfo.api.request.job.JobSearch;
import com.artinfo.api.response.job.JobDetailResponse;
import com.artinfo.api.response.job.JobResponse;
import com.artinfo.api.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobController {

  private final JobService jobService;

  @GetMapping("/jobs/{jobId}")
  public JobDetailResponse get(@PathVariable(name = "jobId") Long id) {
    return jobService.get(id);
  }

  @GetMapping("/jobs")
  public List<JobResponse> getList(@ModelAttribute JobSearch jobSearch) {
    return jobService.getList(jobSearch);
  }
}
