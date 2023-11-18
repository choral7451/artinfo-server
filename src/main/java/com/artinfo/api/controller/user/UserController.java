package com.artinfo.api.controller.user;

import com.artinfo.api.response.UserResponse;
import com.artinfo.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users/{userId}")
  public UserResponse get(@PathVariable(name = "userId") UUID id) {
    return userService.get(id);
  }
}
