package com.artinfo.api.controller.recruit_job;

import com.artinfo.api.response.recruit_job.RecruitJobDetailResponse;
import com.artinfo.api.service.RecruitJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecruitJobController {

  private final RecruitJobService recruitJobService;

  @GetMapping("/jobs/{jobId}")
  public RecruitJobDetailResponse get(@PathVariable(name = "jobId") Long id) {
    return recruitJobService.get(id);
  }
}
