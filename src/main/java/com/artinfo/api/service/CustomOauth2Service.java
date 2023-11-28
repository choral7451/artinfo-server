package com.artinfo.api.service;

import com.artinfo.api.domain.CustomOAuth2User;
import com.artinfo.api.domain.User;
import com.artinfo.api.domain.enums.AuthenticationType;
import com.artinfo.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2Service extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User =  super.loadUser(userRequest);
    Map<String, Object> attributes = oAuth2User.getAttributes();
    String authString = userRequest.getClientRegistration().getRegistrationId();
    AuthenticationType auth = AuthenticationType.valueOf(authString.toUpperCase());

    String email = null;
    String name = null;

    if(auth == AuthenticationType.GOOGLE) {
      email = (String) attributes.get("email");
      name = (String) attributes.get("name");
    } else if(auth == AuthenticationType.NAVER) {
      Map<String, Object> response = (Map<String, Object>) attributes.get("response");
      email = (String) response.get("email");
      name = (String) response.get("name");
    }

    Optional<User> user = userRepository.findByEmail(email);

    if (user.isEmpty()) {
      User UserEntity = User.builder()
        .email(email)
        .name(name)
        .authType(auth)
        .build();
      userRepository.save(UserEntity);
    }

    return new CustomOAuth2User(oAuth2User, email);
  }
}
