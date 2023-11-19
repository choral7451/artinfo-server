package com.artinfo.api.controller.recruit_job;

import com.artinfo.api.domain.RecruitJob;
import com.artinfo.api.domain.enums.RecruitJobsCategory;
import com.artinfo.api.repository.recruit_job.RecruitJobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class RecruitJobControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RecruitJobRepository recruitJobRepository;

  @Test
  @DisplayName("채용 단건 조회")
  void getRecruitJob() throws Exception {
    // given
    RecruitJob recruitJob = RecruitJob.builder()
      .profileId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .title("제목")
      .companyName("회사 이름")
      .contents("내용")
      .category(RecruitJobsCategory.ART_ORGANIZATION)
      .linkUrl("www.sample_link_url.com")
      .companyImageUrl("www.sample_company_image_url.com")
      .build();

    recruitJobRepository.save(recruitJob);

    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/jobs/{jobId}", recruitJob.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-recruit_job",
        pathParameters(
          parameterWithName("jobId").description("채용 ID")
        ),
        responseFields(
          fieldWithPath("id").description("채용 ID"),
          fieldWithPath("profileId").description("프로필 ID"),
          fieldWithPath("title").description("채용 제목"),
          fieldWithPath("companyName").description("회사 이름"),
          fieldWithPath("companyImageUrl").description("회사 이미지 주소"),
          fieldWithPath("linkUrl").description("채용 링크 주소"),
          fieldWithPath("contents").description("채용 내용"),
          fieldWithPath("category").description("채용 카테고리"),
          fieldWithPath("active").description("채용 활성화 여부")
        )
      ));

  }
}
