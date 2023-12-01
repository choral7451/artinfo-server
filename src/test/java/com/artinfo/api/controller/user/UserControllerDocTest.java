package com.artinfo.api.controller.user;

import com.artinfo.api.domain.User;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.repository.lesson.LocationRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.DegreeRepository;
import com.artinfo.api.repository.user.LikeRepository;
import com.artinfo.api.repository.user.UserRepository;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
  private UserRepository userRepository;

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
  void getLesson() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
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
          fieldWithPath("teacher").type(JsonFieldType.BOOLEAN).description("레슨 등록 여부")
        )
      ));
  }
}