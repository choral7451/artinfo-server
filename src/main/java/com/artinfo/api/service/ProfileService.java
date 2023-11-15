package com.artinfo.api.service;

import com.artinfo.api.repository.profile.ProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
}
