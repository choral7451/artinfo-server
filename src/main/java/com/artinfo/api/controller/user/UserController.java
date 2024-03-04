package com.artinfo.api.controller.user;

import com.artinfo.api.request.CompanyCertificationCreate;
import com.artinfo.api.request.UserEdit;
import com.artinfo.api.response.MeResponse;
import com.artinfo.api.response.UserResponse;
import com.artinfo.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users/{userId}")
  public UserResponse get(@PathVariable(name = "userId") UUID id) {
    return userService.get(id);
  }

  @GetMapping("/users/me/{userId}")
  public MeResponse getMe(@PathVariable(name = "userId") UUID id) {
    return userService.getMe(id);
  }

  @PatchMapping("/users/me")
  public void edit(@RequestBody @Valid UserEdit userEdit) {
    userService.edit(userEdit);
  }

  @PostMapping("/users/me/company-certification")
  public void createCompanyCertification(@RequestBody @Valid CompanyCertificationCreate create) {
    userService.createCompanyCertification(create);
  }
}
