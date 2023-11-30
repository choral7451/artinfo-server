package com.artinfo.api.controller.auth;

import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.Signup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FeedRepository feedRepository;

  @Autowired
  private ImageRepository imageRepository;

  @BeforeEach
  void clean() {
    imageRepository.deleteAll();
    feedRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("회원가입")
  void signup() throws Exception {
    // given
    Signup signup = Signup.builder()
      .name("danolman")
      .email("danolman@gmail.com")
      .password("1234")
      .build();

    //expected
    mockMvc.perform(post("/auth/signup")
        .content(objectMapper.writeValueAsString(signup))
        .contentType(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andDo(print());
  }

//  @Test
//  @DisplayName("회원가입시 중복 이메일 검사")
//  void signup() throws Exception {
//    // given
//    Signup signup = Signup.builder()
//      .name("danolman")
//      .email("danolman@gmail.com")
//      .password("1234")
//      .build();
//
//    //expected
//    mockMvc.perform(post("/auth/signup")
//        .content(objectMapper.writeValueAsString(signup))
//        .contentType(APPLICATION_JSON))
//      .andExpect(status().isOk())
//      .andDo(print());
//  }
}