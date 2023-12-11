package com.artinfo.api.controller.job;

import com.artinfo.api.request.job.JobCreate;
import com.artinfo.api.request.job.JobEdit;
import com.artinfo.api.request.job.JobSearch;
import com.artinfo.api.response.job.JobDetailResponse;
import com.artinfo.api.response.job.JobResponse;
import com.artinfo.api.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobController {

  private final JobService jobService;

  @PostMapping("/jobs")
  public void create(@RequestBody JobCreate jobCreate) {
    jobService.create(jobCreate);
  }

  @PutMapping("/jobs/{jobId}")
  public void edit(@PathVariable(name = "jobId") Long jobId, @RequestBody JobEdit jobEdit) {
    jobService.edit(jobId, jobEdit);
  }

  @GetMapping("/jobs/{jobId}")
  public JobDetailResponse get(@PathVariable(name = "jobId") Long id) {
    return jobService.get(id);
  }

  @GetMapping("/jobs")
  public List<JobResponse> getList(@ModelAttribute JobSearch jobSearch) {
    return jobService.getList(jobSearch);
  }
}
