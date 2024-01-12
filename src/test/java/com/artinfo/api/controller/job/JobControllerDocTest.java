package com.artinfo.api.controller.job;

import com.artinfo.api.domain.job.Job;
import com.artinfo.api.domain.Major;
import com.artinfo.api.domain.User;
import com.artinfo.api.repository.job.JobRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.job.JobCreate;
import com.artinfo.api.request.job.JobEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
  private ObjectMapper objectMapper;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private MajorRepository majorRepository;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void clean() {
    majorRepository.deleteAll();
    jobRepository.deleteAll();
    userRepository.deleteAll();
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

    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/jobs")
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk())
      .andDo(document("create-job",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
          fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
          fieldWithPath("companyImageUrl").type(JsonFieldType.STRING).description("회사 이미지 주소"),
          fieldWithPath("linkUrl").type(JsonFieldType.STRING).description("채용 정보 주소").optional(),
          fieldWithPath("contents").type(JsonFieldType.STRING).description("채용 내용"),
          fieldWithPath("majors").type(JsonFieldType.ARRAY).description("해당 전공")
        )
      ));
  }

  @Test
  @DisplayName("채용 수정")
  void editJob() throws Exception {
    // given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Job job = Job.builder()
      .userId(user.getId())
      .title("제목")
      .companyName("회사 이름")
      .contents("내용")
      .linkUrl("www.sample_link_url.com")
      .companyImageUrl("www.sample_company_image_url.com")
      .build();
    jobRepository.save(job);

    Major major = Major.builder()
      .name("플루트")
      .job(job)
      .build();
    majorRepository.save(major);


    JobEdit request = JobEdit.builder()
      .userId(user.getId())
      .title("수정 제목")
      .companyName("수정 회사이름")
      .companyImageUrl("test.update-sample-image-url.com")
      .linkUrl("test.update-sample-link-url.com")
      .contents("수정 내용")
      .majors(List.of("가야금", "지휘"))
      .build();

    String json = objectMapper.writeValueAsString(request);

    this.mockMvc.perform(RestDocumentationRequestBuilders.put("/jobs/{jobId}", job.getId())
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk())
      .andDo(document("edit-job",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("jobId").description("채용 ID")
            .attributes(key("type").value("Number"))
        ),
        requestFields(
          fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
          fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사명"),
          fieldWithPath("companyImageUrl").type(JsonFieldType.STRING).description("회사 이미지 주소"),
          fieldWithPath("linkUrl").type(JsonFieldType.STRING).description("채용 정보 주소").optional(),
          fieldWithPath("contents").type(JsonFieldType.STRING).description("채용 내용"),
          fieldWithPath("majors").type(JsonFieldType.ARRAY).description("해당 전공")
        )
      ));
  }

  @Test
  @DisplayName("채용 삭제")
  void deleteJob() throws Exception {
    // given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Job job = Job.builder()
      .userId(user.getId())
      .title("제목")
      .companyName("회사 이름")
      .contents("내용")
      .linkUrl("www.sample_link_url.com")
      .companyImageUrl("www.sample_company_image_url.com")
      .build();
    jobRepository.save(job);

    Major major = Major.builder()
      .name("플루트")
      .job(job)
      .build();
    majorRepository.save(major);


    this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/jobs/{jobId}", job.getId())
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("delete-job",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("jobId").description("채용 ID")
            .attributes(key("type").value("Number"))
        )
      ));
  }

  @Test
  @DisplayName("채용 단건 조회")
  void getJob() throws Exception {
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

    Major major1 = Major.builder()
      .name("플루트")
      .job(job)
      .build();

    Major major2 = Major.builder()
      .name("피아노")
      .job(job)
      .build();
    majorRepository.saveAll(List.of(major1, major2));

    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/jobs/{jobId}", job.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-job",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("jobId").description("채용 ID")
            .attributes(Attributes.key("type").value("Numnber"))
        ),
        responseFields(
          fieldWithPath("id").type(JsonFieldType.NUMBER).description("채용 ID"),
          fieldWithPath("userId").type(JsonFieldType.STRING).description("프로필 ID"),
          fieldWithPath("title").type(JsonFieldType.STRING).description("채용 제목"),
          fieldWithPath("companyName").type(JsonFieldType.STRING).description("회사 이름"),
          fieldWithPath("companyImageUrl").type(JsonFieldType.STRING).description("회사 이미지 주소"),
          fieldWithPath("linkUrl").type(JsonFieldType.STRING).description("채용 링크 주소"),
          fieldWithPath("contents").type(JsonFieldType.STRING).description("채용 내용"),
          fieldWithPath("majors").type(JsonFieldType.ARRAY).description("채용 해당 전공")
        )
      ));
  }

  @Test
  @DisplayName("채용 목록 조회")
  void getList() throws Exception {
    // given
    Job job1 = Job.builder()
      .userId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .title("제목1")
      .companyName("회사 이름1")
      .contents("내용1")
      .linkUrl("www.sample_link_url_1.com")
      .companyImageUrl("www.sample_company_image_url_1.com")
      .build();

    Job job2 = Job.builder()
      .userId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .title("제목2")
      .companyName("회사 이름2")
      .contents("내용2")
      .linkUrl("www.sample_link_url_2.com")
      .companyImageUrl("www.sample_company_image_url_2.com")
      .build();

    jobRepository.saveAll(List.of(job1, job2));

    Major major1 = Major.builder()
      .name("플루트")
      .job(job1)
      .build();

    Major major2 = Major.builder()
      .name("피아노")
      .job(job2)
      .build();
    majorRepository.saveAll(List.of(major1, major2));



    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/jobs?page=1&size=2&major=피아노")
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-jobs",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
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
