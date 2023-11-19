package com.artinfo.api.service;

import com.artinfo.api.domain.RecruitJob;
import com.artinfo.api.exception.RecruitJobNotFound;
import com.artinfo.api.repository.recruit_job.RecruitJobRepository;
import com.artinfo.api.response.recruit_job.RecruitJobDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitJobService {

  private final RecruitJobRepository recruitJobRepository;

  public RecruitJobDetailResponse get(Long id) {
    RecruitJob recruitJob = recruitJobRepository.findById(id)
      .orElseThrow(RecruitJobNotFound::new);

    return RecruitJobDetailResponse.builder()
      .id(recruitJob.getId())
      .profileId(recruitJob.getProfileId())
      .title(recruitJob.getTitle())
      .companyName(recruitJob.getCompanyName())
      .companyImageUrl(recruitJob.getCompanyImageUrl())
      .linkUrl(recruitJob.getLinkUrl())
      .contents(recruitJob.getContents())
      .category(recruitJob.getCategory())
      .isActive(recruitJob.isActive())
      .build();
  }
}
