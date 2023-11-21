package com.artinfo.api.controller.lesson;

import com.artinfo.api.domain.User;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.LessonCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class LessonControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

//  @BeforeEach
//  void clean() {
//
//  }

  @Test
  @DisplayName("레슨 생성")
  void getRecruitJob() throws Exception {
    // given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();

    userRepository.save(user);

    LessonCreate request = LessonCreate.builder()
      .userId(user.getId())
      .imageUrl("www.sample_image_url.com")
      .locations(List.of("서울 전체, 강원도 전체"))
      .name("임성준")
      .majors(List.of("피아노", "성악"))
      .phone("010-0000-0000")
      .fee(800000)
      .intro("안녕하세요")
      .degrees(Map.of("MASTER", List.of("서울대학교", "연세대학교")))
      .build();

    String json = objectMapper.writeValueAsString(request);

    //expected
    mockMvc.perform(post("/lessons")
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk());
  }
}