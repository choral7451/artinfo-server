package com.artinfo.api.controller.job;

import com.artinfo.api.domain.Job;
import com.artinfo.api.domain.enums.RecruitJobsCategory;
import com.artinfo.api.repository.job.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class JobControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JobRepository recruitJobRepository;

//  @BeforeEach
//  void clean() {
//
//  }

  @Test
  @DisplayName("채용 단건 조회")
  void getRecruitJob() throws Exception {
    // given
    Job recruitJob = Job.builder()
      .profileId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .title("제목")
      .companyName("회사 이름")
      .contents("내용")
      .category(RecruitJobsCategory.ART_ORGANIZATION)
      .linkUrl("www.sample_link_url.com")
      .companyImageUrl("www.sample_company_image_url.com")
      .build();

    recruitJobRepository.save(recruitJob);

    //expected
    mockMvc.perform(get("/jobs/{jobId}", recruitJob.getId())
        .contentType(APPLICATION_JSON))
      .andExpect(jsonPath("$.id").value(recruitJob.getId()))
      .andExpect(jsonPath("$.title").value("제목"))
      .andExpect(jsonPath("$.companyName").value("회사 이름"))
      .andExpect(jsonPath("$.contents").value("내용"))
      .andExpect(jsonPath("$.category").value("ART_ORGANIZATION"))
      .andExpect(jsonPath("$.linkUrl").value("www.sample_link_url.com"))
      .andExpect(jsonPath("$.companyImageUrl").value("www.sample_company_image_url.com"))
      .andExpect(status().isOk());
  }

//  @Test
//  @DisplayName("존재하지 않는 채용 조회")
//  void getRecruitJobNullException() throws Exception {
//    // given
//    long countOfRecruitJob = recruitJobRepository.count();
//
//    //expected
//    mockMvc.perform(get("/jobs/{jobId}", countOfRecruitJob + 1L)
//        .contentType(APPLICATION_JSON))
//      .andExpect(status().isNotFound());
//  }
}