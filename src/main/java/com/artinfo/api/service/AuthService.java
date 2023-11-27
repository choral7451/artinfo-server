package com.artinfo.api.service;

import com.artinfo.api.domain.User;
import com.artinfo.api.domain.enums.AuthenticationType;
import com.artinfo.api.exception.AlreadyExistsEmailException;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  public void signup(Signup signup, AuthenticationType authType) {
    Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
    if (userOptional.isPresent()) {
      throw new AlreadyExistsEmailException();
    }

    String encryptedPassword = passwordEncoder.encode(signup.getPassword());

    var user = User.builder()
      .name(signup.getName())
      .password(encryptedPassword)
      .email(signup.getEmail())
      .authType(authType)
      .build();

    userRepository.save(user);
  }
}
