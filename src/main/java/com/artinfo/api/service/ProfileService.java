package com.artinfo.api.service;

import com.artinfo.api.domain.Profile;
import com.artinfo.api.exception.ProfileNotFound;
import com.artinfo.api.repository.profile.ProfileRepository;
import com.artinfo.api.response.ProfileResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
  public ProfileResponse get(UUID id) {
    Profile profile = profileRepository.findById(id)
      .orElseThrow(ProfileNotFound::new);

    return ProfileResponse.builder()
      .id(profile.getId())
      .name(profile.getName())
      .email(profile.getEmail())
      .iconImageUrl(profile.getIconImageUrl())
      .commentCnt(profile.getCommentCnt())
      .articleCnt(profile.getArticleCnt())
      .isTeacher(profile.isTeacher())
      .build();
  }
}
