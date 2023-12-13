package com.artinfo.api.service;

import com.artinfo.api.config.AppConfig;
import com.artinfo.api.domain.Major;
import com.artinfo.api.domain.job.Job;
import com.artinfo.api.domain.job.JobEditor;
import com.artinfo.api.exception.JobNotFound;
import com.artinfo.api.exception.LessonNotFound;
import com.artinfo.api.exception.UserNotFound;
import com.artinfo.api.repository.job.JobRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.job.JobCreate;
import com.artinfo.api.request.job.JobEdit;
import com.artinfo.api.request.job.JobSearch;
import com.artinfo.api.response.job.JobDetailResponse;
import com.artinfo.api.response.job.JobResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

  private final AppConfig appConfig;
  private final MajorRepository majorRepository;
  private final JobRepository jobRepository;
  private final UserRepository userRepository;

  public void create(JobCreate jobCreate) {
    userRepository.findById(jobCreate.getUserId())
      .orElseThrow(UserNotFound::new);

    List<Major> majors = new ArrayList<>();
    for(String major: jobCreate.getMajors()) {
      Optional<Major> fetchedMajor = majorRepository.findByName(major);
      if(fetchedMajor.isPresent()) {
        majors.add(fetchedMajor.get());
      } else {
        Major CreatedMajor = Major.builder().name(major).build();
        majorRepository.save(CreatedMajor);
        majors.add(CreatedMajor);
      }
    }

    String companyImageUrl = jobCreate.getCompanyImageUrl();
    if(jobCreate.getCompanyImageUrl() == null || jobCreate.getCompanyImageUrl().isEmpty()) {
      companyImageUrl = appConfig.jobDefault;
    }

    Job job = Job.builder()
      .userId(jobCreate.getUserId())
      .title(jobCreate.getTitle())
      .contents(jobCreate.getContents())
      .companyName(jobCreate.getCompanyName())
      .companyImageUrl(companyImageUrl)
      .linkUrl(jobCreate.getLinkUrl())
      .majors(majors)
      .build();

    jobRepository.save(job);
  }

  @Transactional
  public void edit(Long jobId, JobEdit jobEdit) {
    userRepository.findById(jobEdit.getUserId())
      .orElseThrow(UserNotFound::new);

    Job job = jobRepository.findById(jobId)
      .orElseThrow(JobNotFound::new);

    List<Major> majors = new ArrayList<>();
    for(String major: jobEdit.getMajors()) {
      Optional<Major> fetchedMajor = majorRepository.findByName(major);
      if(fetchedMajor.isPresent()) {
        majors.add(fetchedMajor.get());
      } else {
        Major CreatedMajor = Major.builder().name(major).build();
        majorRepository.save(CreatedMajor);
        majors.add(CreatedMajor);
      }
    }

    String companyImageUrl = jobEdit.getCompanyImageUrl();
    if(jobEdit.getCompanyImageUrl() == null || jobEdit.getCompanyImageUrl().isEmpty()) {
      companyImageUrl = appConfig.jobDefault;
    }

    JobEditor jobEditor = JobEditor.builder()
      .title(jobEdit.getTitle())
      .companyName(jobEdit.getCompanyName())
      .companyImageUrl(companyImageUrl)
      .linkUrl(jobEdit.getLinkUrl())
      .contents(jobEdit.getContents())
      .majors(majors)
      .build();

    job.edit(jobEditor);
  }

  @Transactional
  public void delete(Long id) {
    Job job = jobRepository.findById(id)
      .orElseThrow(LessonNotFound::new);

    jobRepository.deleteById(id);
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
      .majors(job.getMajors().stream().map(Major::getName).toList())
      .build();
  }
}
