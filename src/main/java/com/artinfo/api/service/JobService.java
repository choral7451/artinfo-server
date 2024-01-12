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

import java.util.List;

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
      .build();

    jobRepository.save(job);

    List<Major> majors = jobCreate.getMajors()
      .stream()
      .map(major -> Major.builder().name(major).job(job).build())
      .toList();

    majorRepository.saveAll(majors);
  }

  @Transactional
  public void edit(Long jobId, JobEdit jobEdit) {
    userRepository.findById(jobEdit.getUserId())
      .orElseThrow(UserNotFound::new);

    Job job = jobRepository.findById(jobId)
      .orElseThrow(JobNotFound::new);

    majorRepository.deleteAllByJob(job);

    List<Major> majors = jobEdit.getMajors()
      .stream()
      .map(major -> Major.builder().name(major).job(job).build())
      .toList();
    majorRepository.saveAll(majors);

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
      .build();

    job.edit(jobEditor);
  }

  @Transactional
  public void delete(Long id) {
    Job job = jobRepository.findById(id)
      .orElseThrow(LessonNotFound::new);

    jobRepository.deleteById(id);
  }

  public List<JobResponse> getList(JobSearch request) {
    return (request.getMajor() != null)
      ? getJobsByMajors(request)
      : getAllJobs(request);
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

  private List<JobResponse> getJobsByMajors(JobSearch request) {
    log.info("?>>>>>>>>>>>>>>>{}",request.getMajor());
    return jobRepository.findByMajors_NameIn(request.getMajor(), request.toPageable())
      .stream()
      .map(JobResponse::new)
      .toList();
  }


  private List<JobResponse> getAllJobs(JobSearch request) {
    return jobRepository.findAll(request.toPageable())
      .stream()
      .map(JobResponse::new)
      .toList();
  }
}
