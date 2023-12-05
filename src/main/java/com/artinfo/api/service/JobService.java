package com.artinfo.api.service;

import com.artinfo.api.domain.Job;
import com.artinfo.api.domain.Major;
import com.artinfo.api.exception.JobNotFound;
import com.artinfo.api.repository.job.JobRepository;
import com.artinfo.api.request.job.JobCreate;
import com.artinfo.api.request.job.JobSearch;
import com.artinfo.api.response.job.JobResponse;
import com.artinfo.api.response.job.JobDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

  private final JobRepository jobRepository;

  public void create(JobCreate jobCreate) {
    List<Major> majors = jobCreate.getMajors().stream().map(majorName -> {
      return Major.builder()
        .name(majorName)
        .build();
    }).toList();

    Job job = Job.builder()
      .userId(jobCreate.getUserId())
      .title(jobCreate.getTitle())
      .contents(jobCreate.getContents())
      .companyName(jobCreate.getCompanyName())
      .companyImageUrl(jobCreate.getCompanyImageUrl())
      .linkUrl(jobCreate.getLinkUrl())
      .majors(majors)
      .build();

    jobRepository.save(job);
  }

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
      .userId(job.getUserId())
      .title(job.getTitle())
      .companyName(job.getCompanyName())
      .companyImageUrl(job.getCompanyImageUrl())
      .linkUrl(job.getLinkUrl())
      .contents(job.getContents())
      .build();
  }
}
