package com.artinfo.api.service;

import com.artinfo.api.domain.CustomOAuth2User;
import com.artinfo.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2Service extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    String email = userRequest.getClientRegistration().getClientName();
    log.info("email={}", userRequest.getClientRegistration());
    System.out.println(userRequest.getClientRegistration());

    OAuth2User user =  super.loadUser(userRequest);
    log.info("email={}", user.getAttributes().get("email"));
    log.info("user={}", user);




    return new CustomOAuth2User(user, email);
  }
}
