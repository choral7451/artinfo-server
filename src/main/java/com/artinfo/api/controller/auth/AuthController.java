package com.artinfo.api.controller.auth;

import com.artinfo.api.domain.enums.AuthenticationType;
import com.artinfo.api.request.Signup;
import com.artinfo.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/auth/signup")
  public void signup(@RequestBody Signup signup) {
    authService.signup(signup, AuthenticationType.EMAIL);
  }
}