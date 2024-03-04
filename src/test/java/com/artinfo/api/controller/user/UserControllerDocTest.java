package com.artinfo.api.controller.user;

import com.artinfo.api.domain.CompanyCertification;
import com.artinfo.api.domain.User;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.repository.lesson.LocationRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.CompanyCertificationRepository;
import com.artinfo.api.repository.user.DegreeRepository;
import com.artinfo.api.repository.user.LikeRepository;
import com.artinfo.api.repository.user.UserRepository;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class UserControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CompanyCertificationRepository companyCertificationRepository;

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private MajorRepository majorRepository;

  @Autowired
  private DegreeRepository degreeRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private FeedRepository feedRepository;

  @Autowired
  private LikeRepository likeRepository;

  @BeforeEach
  void clean() {
    likeRepository.deleteAll();
    imageRepository.deleteAll();
    feedRepository.deleteAll();
    majorRepository.deleteAll();
    lessonRepository.deleteAll();
    locationRepository.deleteAll();
    degreeRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("유저 단건 조회")
  void getUser() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .publicNickname("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/users/{userId}", user.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-user",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("userId").description("유저 ID")
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("id").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("email").type(JsonFieldType.STRING).description("이메일 주소"),
          fieldWithPath("iconImageUrl").type(JsonFieldType.STRING).description("유저 아이콘 이미지 주소").optional(),
          fieldWithPath("lessonId").type(JsonFieldType.NUMBER).description("유저 레슨 아이디").optional(),
          fieldWithPath("articleCnt").type(JsonFieldType.NUMBER).description("글 등록 수"),
          fieldWithPath("commentCnt").type(JsonFieldType.NUMBER).description("댓글 등록 "),
          fieldWithPath("isTeacher").type(JsonFieldType.BOOLEAN).description("레슨 등록 여부")
        )
      ));
  }

  @Test
  @DisplayName("내 정보 조회")
  void getMe() throws Exception {
    //given
    User user = User.builder()
      .publicNickname("따니엘")
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    CompanyCertification companyCertification = CompanyCertification.builder()
      .name("국립합창단")
      .user(user)
      .build();

    companyCertificationRepository.save(companyCertification);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/users/me/{userId}", user.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-me",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("userId").description("유저 ID")
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("id").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("publicNickname").type(JsonFieldType.STRING).description("공개 아이디"),
          fieldWithPath("secretNickname").type(JsonFieldType.STRING).description("익명 아이디").optional(),
          fieldWithPath("email").type(JsonFieldType.STRING).description("이메일 주소"),
          fieldWithPath("iconImageUrl").type(JsonFieldType.STRING).description("유저 아이콘 이미지 주소").optional(),
          fieldWithPath("companyCategory").type(JsonFieldType.STRING).description("속속 단체 분류").optional(),
          fieldWithPath("companyName").type(JsonFieldType.STRING).description("소속 단체 이름").optional(),
          fieldWithPath("lessonId").type(JsonFieldType.NUMBER).description("유저 레슨 아이디").optional(),
          fieldWithPath("isTeacher").type(JsonFieldType.BOOLEAN).description("레슨 등록 여부")
        )
      ));
  }

  @Test
  @DisplayName("내 정보 수정")
  void edit() throws Exception {
    //given
    User user = User.builder()
      .publicNickname("따니엘")
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    String request = objectMapper.writeValueAsString(Map.of(
      "userId", user.getId(),
      "name", "따따니엘",
      "publicNickname", "따따따니엘",
      "secretNickname", "따따따니엘",
      "iconImageUrl", "www.test-url.com"
    ));

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.patch("/users/me")
        .contentType(APPLICATION_JSON)
        .content(request)
      )
      .andExpect(status().isOk())
      .andDo(document("edit-user",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("publicNickname").type(JsonFieldType.STRING).description("공개 아이디"),
          fieldWithPath("secretNickname").type(JsonFieldType.STRING).description("익명 아이디").optional(),
          fieldWithPath("iconImageUrl").type(JsonFieldType.STRING).description("유저 아이콘 이미지 주소").optional()
        )
      ));
  }

  @Test
  @DisplayName("소속 단체 인증")
  void createCompanyCertification() throws Exception {
    //given
    User user = User.builder()
      .publicNickname("따니엘")
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    String request = objectMapper.writeValueAsString(Map.of(
      "userId", user.getId(),
      "name", "임성준",
      "companyName","투썬",
      "secretNickname", "비밀아이디",
      "imageUrls", List.of("www.test-url2.com")
    ));

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/users/me/company-certification")
        .contentType(APPLICATION_JSON)
        .content(request)
      )
      .andExpect(status().isOk())
      .andDo(document("create-company-certification",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("companyName").type(JsonFieldType.STRING).description("소속 단체 명"),
          fieldWithPath("secretNickname").type(JsonFieldType.STRING).description("익명 아이디").optional(),
          fieldWithPath("imageUrls").type(JsonFieldType.ARRAY).description("이미지 주소 목록").optional()
        )
      ));
  }
}