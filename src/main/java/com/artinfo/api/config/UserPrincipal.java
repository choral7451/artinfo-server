package com.artinfo.api.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.UUID;

public class UserPrincipal extends User {

  private final UUID userId;

  public UserPrincipal(com.artinfo.api.domain.User user) {
    super(user.getEmail(), user.getPassword(),
      List.of(
        new SimpleGrantedAuthority("USER")
      ));
    this.userId = user.getId();
  }

  public UUID getUserId() {
    return userId;
  }
}
