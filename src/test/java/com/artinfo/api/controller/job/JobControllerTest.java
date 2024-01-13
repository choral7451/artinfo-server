package com.artinfo.api.controller.job;

import com.artinfo.api.domain.User;
import com.artinfo.api.domain.job.Job;
import com.artinfo.api.repository.job.JobRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.job.JobCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class JobControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void clean() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("채용 단건 조회")
  void getRecruitJob() throws Exception {
    // given
    Job job = Job.builder()
      .userId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .title("제목")
      .companyName("회사 이름")
      .contents("내용")
      .linkUrl("www.sample_link_url.com")
      .companyImageUrl("www.sample_company_image_url.com")
      .build();

    jobRepository.save(job);

    //expected
    mockMvc.perform(get("/jobs/{jobId}", job.getId())
        .contentType(APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(job.getId()))
      .andExpect(jsonPath("$.title").value("제목"))
      .andExpect(jsonPath("$.companyName").value("회사 이름"))
      .andExpect(jsonPath("$.contents").value("내용"))
      .andExpect(jsonPath("$.linkUrl").value("www.sample_link_url.com"))
      .andExpect(jsonPath("$.companyImageUrl").value("www.sample_company_image_url.com"))
      .andExpect(status().isOk());
  }

  @Test
  @DisplayName("채용 생성")
  void createJob() throws Exception {
    // given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    JobCreate request = JobCreate.builder()
      .userId(user.getId())
      .title("제목")
      .companyName("회사이름")
      .companyImageUrl("test.sample-image-url.com")
      .linkUrl("test.sample-link-url.com")
      .contents("내용")
      .majors(List.of("피아노", "지휘"))
      .build();

    String json = objectMapper.writeValueAsString(request);

    //expected
    mockMvc.perform(post("/jobs")
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk());

  }
}