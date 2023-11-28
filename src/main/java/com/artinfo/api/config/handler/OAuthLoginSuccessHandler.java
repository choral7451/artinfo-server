package com.artinfo.api.config.handler;

import com.artinfo.api.domain.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws ServletException, IOException {
    CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
    String email = oauth2User.getOauth2ClientName();

    HttpSession session = request.getSession();
    session.setAttribute("OAUTH2_CLIENT_NAME", email);

    super.onAuthenticationSuccess(request, response, authentication);
  }
}