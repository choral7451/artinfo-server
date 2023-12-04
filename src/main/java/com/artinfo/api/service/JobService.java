package com.artinfo.api.service;

import com.artinfo.api.domain.Job;
import com.artinfo.api.exception.JobNotFound;
import com.artinfo.api.repository.job.JobRepository;
import com.artinfo.api.request.job.JobSearch;
import com.artinfo.api.request.lesson.LessonSearch;
import com.artinfo.api.response.job.JobResponse;
import com.artinfo.api.response.lesson.LessonResponse;
import com.artinfo.api.response.job.JobDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

  private final JobRepository jobRepository;

  public List<JobResponse> getList(JobSearch jobSearch) {
    return jobRepository.getList(jobSearch).stream()
      .map(JobResponse::new)
      .collect(Collectors.toList());
  }
  public JobDetailResponse get(Long id) {
    Job job = jobRepository.findById(id)
      .orElseThrow(JobNotFound::new);

    return JobDetailResponse.builder()
      .id(job.getId())
      .profileId(job.getProfileId())
      .title(job.getTitle())
      .companyName(job.getCompanyName())
      .companyImageUrl(job.getCompanyImageUrl())
      .linkUrl(job.getLinkUrl())
      .contents(job.getContents())
      .category(job.getCategory())
      .isActive(job.isActive())
      .build();
  }
}
