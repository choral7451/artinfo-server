package com.artinfo.api.controller.profile;

import com.artinfo.api.response.ProfileResponse;
import com.artinfo.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;

  @GetMapping("/profiles/{profileId}")
  public ProfileResponse get(@PathVariable(name = "profileId") UUID id) {
    return profileService.get(id);
  }
}
