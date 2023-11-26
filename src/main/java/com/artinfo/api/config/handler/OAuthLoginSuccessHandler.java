package com.artinfo.api.config.handler;

import com.artinfo.api.domain.CustomOAuth2User;
import com.artinfo.api.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final UserService userService;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws ServletException, IOException {
    CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
    String oauth2ClientName = oauth2User.getOauth2ClientName();
    String email = oauth2User.getEmail();

    userService.editAuthenticationType(email, oauth2ClientName);

    super.onAuthenticationSuccess(request, response, authentication);
  }
}