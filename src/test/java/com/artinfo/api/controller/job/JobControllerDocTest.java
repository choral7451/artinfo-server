package com.artinfo.api.controller.job;

import com.artinfo.api.domain.Job;
import com.artinfo.api.domain.Major;
import com.artinfo.api.domain.enums.RecruitJobsCategory;
import com.artinfo.api.repository.job.JobRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class JobControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private MajorRepository majorRepository;

  @BeforeEach
  void clean() {
    majorRepository.deleteAll();
    jobRepository.deleteAll();
  }

  @Test
  @DisplayName("채용 단건 조회")
  void getJob() throws Exception {
    // given
    Job job = Job.builder()
      .profileId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .title("제목")
      .companyName("회사 이름")
      .contents("내용")
      .category(RecruitJobsCategory.ART_ORGANIZATION)
      .linkUrl("www.sample_link_url.com")
      .companyImageUrl("www.sample_company_image_url.com")
      .build();

    jobRepository.save(job);

    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/jobs/{jobId}", job.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-recruit_job",
        pathParameters(
          parameterWithName("jobId").description("채용 ID")
            .attributes(Attributes.key("type").value("Numnber"))
        ),
        responseFields(
          fieldWithPath("id").type(JsonFieldType.NUMBER).description("채용 ID"),
          fieldWithPath("profileId").type(JsonFieldType.STRING).description("프로필 ID"),
          fieldWithPath("title").type(JsonFieldType.STRING).description("채용 제목"),
          fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름"),
          fieldWithPath("companyImageUrl").type(JsonFieldType.STRING).description("회사 이미지 주소"),
          fieldWithPath("linkUrl").type(JsonFieldType.STRING).description("채용 링크 주소"),
          fieldWithPath("contents").type(JsonFieldType.STRING).description("채용 내용"),
          fieldWithPath("category").type(JsonFieldType.STRING).description("채용 카테고리"),
          fieldWithPath("active").type(JsonFieldType.BOOLEAN).description("채용 활성화 여부")
        )
      ));

  }

  @Test
  @DisplayName("채용 목록 조회")
  void getList() throws Exception {
    // given
    Major major1 = Major.builder()
      .name("플루트")
      .build();

    Major major2 = Major.builder()
      .name("피아노")
      .build();
    majorRepository.saveAll(List.of(major1, major2));

    Job job1 = Job.builder()
      .profileId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .title("제목1")
      .companyName("회사 이름1")
      .contents("내용1")
      .category(RecruitJobsCategory.ART_ORGANIZATION)
      .linkUrl("www.sample_link_url_1.com")
      .companyImageUrl("www.sample_company_image_url_1.com")
      .majors(List.of(major1,major2))
      .build();

    Job job2 = Job.builder()
      .profileId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .title("제목2")
      .companyName("회사 이름2")
      .contents("내용2")
      .category(RecruitJobsCategory.ART_ORGANIZATION)
      .linkUrl("www.sample_link_url_2.com")
      .companyImageUrl("www.sample_company_image_url_2.com")
      .majors(List.of(major2))
      .build();

    jobRepository.saveAll(List.of(job1, job2));



    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/jobs?page=1&size=2&major=피아노")
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-jobs",
        queryParameters(
          parameterWithName("page").description("페이지 번호").optional()
            .attributes(key("type").value("Number")),
          parameterWithName("size").description("채용 조회 개수").optional()
            .attributes(key("type").value("Number")),
          parameterWithName("major").description("채용 전공 필터").optional()
            .attributes(key("type").value("String"))
        ),
        responseFields(
          fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("채용 ID"),
          fieldWithPath("[].title").type(JsonFieldType.STRING).description("채용 제목"),
          fieldWithPath("[].companyName").type(JsonFieldType.STRING).description("회사 이름"),
          fieldWithPath("[].companyImageUrl").type(JsonFieldType.STRING).description("회사 이미지 주소"),
          fieldWithPath("[].majors").type(JsonFieldType.ARRAY).description("채용 해당 전공"),
          fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("채용 생성일")
        )
      ));

  }
}
