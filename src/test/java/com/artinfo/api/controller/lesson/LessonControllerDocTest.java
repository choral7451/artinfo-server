package com.artinfo.api.controller.lesson;

import com.artinfo.api.domain.Degree;
import com.artinfo.api.domain.Location;
import com.artinfo.api.domain.Major;
import com.artinfo.api.domain.User;
import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.repository.lesson.LocationRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.DegreeRepository;
import com.artinfo.api.repository.user.LikeRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.lesson.LessonCreate;
import com.artinfo.api.request.lesson.LessonEdit;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class LessonControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

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
  private FeedRepository feedRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private LikeRepository likeRepository;
  @BeforeEach
  void clean() {
    likeRepository.deleteAll();
    imageRepository.deleteAll();
    locationRepository.deleteAll();
    majorRepository.deleteAll();
    lessonRepository.deleteAll();
    feedRepository.deleteAll();
    degreeRepository.deleteAll();
    userRepository.deleteAll();
  }


  @Test
  @DisplayName("레슨 단건 조회")
  void getLesson() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Lesson lesson = Lesson.builder()
      .user(user)
      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
      .name("김규성")
      .phone("010-4028-7451")
      .fee(80000)
      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
      .build();
    lessonRepository.save(lesson);

    Location location = Location.builder()
      .name("서울 우리집")
      .lesson(lesson)
      .build();
    locationRepository.save(location);

    Major major = Major.builder()
      .name("플루트")
      .lesson(lesson)
      .build();
    majorRepository.save(major);

    Degree degree1 = Degree.builder()
      .degree("MASTER")
      .campusName("서울대학교")
      .lesson(lesson)
      .user(user)
      .build();
    degreeRepository.save(degree1);

    Degree degree2 = Degree.builder()
      .degree("MASTER")
      .campusName("연세대학교")
      .lesson(lesson)
      .user(user)
      .build();
    degreeRepository.save(degree2);

    Degree degree3 = Degree.builder()
      .degree("BACHELOR")
      .campusName("연세대학교")
      .lesson(lesson)
      .user(user)
      .build();
    degreeRepository.save(degree3);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/lessons/{lessonId}", lesson.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-lesson",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("lessonId").description("레슨 ID")
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("id").type(JsonFieldType.NUMBER).description("레슨 ID"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("phone").type(JsonFieldType.STRING).description("연락처"),
          fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("이미지 URL"),
          fieldWithPath("locations").type(JsonFieldType.ARRAY).description("레슨 위치"),
          fieldWithPath("majors").type(JsonFieldType.ARRAY).description("레슨 과목"),
          fieldWithPath("fee").type(JsonFieldType.NUMBER).description("레슨비"),
          fieldWithPath("intro").type(JsonFieldType.STRING).description("레슨 소개"),
          fieldWithPath("degrees").type(JsonFieldType.ARRAY).description("학위:대학 목록"),
          fieldWithPath("degrees[].*").type(JsonFieldType.STRING).description("학위 정보")
        )
      ));
  }

  @Test
  @DisplayName("레슨 목록 조회")
  void getLessonList() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Lesson lesson = Lesson.builder()
      .user(user)
      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
      .name("김규성")
      .phone("010-4028-7451")
      .fee(80000)
      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
      .build();
    lessonRepository.save(lesson);

    Location location = Location.builder()
      .name("서울 전체")
      .lesson(lesson)
      .build();
    locationRepository.save(location);

    Major major = Major.builder()
      .name("피아노")
      .lesson(lesson)
      .build();
    majorRepository.save(major);

    Degree degree = Degree.builder()
      .degree("MASTER")
      .campusName("서울대학교")
      .lesson(lesson)
      .user(user)
      .build();
    degreeRepository.save(degree);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/lessons?page=1&size=5&location=서울&major=피아노")
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-lessons",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        queryParameters(
          parameterWithName("page").description("페이지 번호").optional()
            .attributes(key("type").value("Number")),
          parameterWithName("size").description("레슨 조회 개수").optional()
            .attributes(key("type").value("Number")),
          parameterWithName("location").description("레슨 위치 필터").optional()
            .attributes(key("type").value("String")),
          parameterWithName("major").description("레슨 과목 필터").optional()
            .attributes(key("type").value("String"))
        ),
        responseFields(
          fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("레슨 ID"),
          fieldWithPath("[].name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("[].userId").type(JsonFieldType.STRING).description("프로필 ID"),
          fieldWithPath("[].imageUrl").type(JsonFieldType.STRING).description("이미지 URL"),
          fieldWithPath("[].locations").type(JsonFieldType.ARRAY).description("레슨 위치"),
          fieldWithPath("[].majors").type(JsonFieldType.ARRAY).description("레슨 과목")
        )
      ));
  }

  @Test
  @DisplayName("레슨 생성")
  void save() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();

    userRepository.save(user);

    LessonCreate request = LessonCreate.builder()
      .userId(user.getId())
      .imageUrl("www.sample_image_url.com")
      .locations(List.of("서울 전체", "강원도 전체"))
      .name("임성준")
      .majors(List.of("피아노", "성악"))
      .phone("010-0000-0000")
      .fee(800000)
      .intro("안녕하세요")
      .degrees(List.of(Map.of("MASTER", "서울대학교"), Map.of("BACHELOR", "연세대학교")))
      .build();

    String json = objectMapper.writeValueAsString(request);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/lessons")
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk())
      .andDo(document("create-lesson",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("이미지 주소"),
          fieldWithPath("locations").type(JsonFieldType.ARRAY).description("레슨 장소"),
          fieldWithPath("majors").type(JsonFieldType.ARRAY).description("전공 과목"),
          fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
          fieldWithPath("fee").type(JsonFieldType.NUMBER).description("레슨비"),
          fieldWithPath("intro").type(JsonFieldType.STRING).description("소개"),
          fieldWithPath("degrees").type(JsonFieldType.ARRAY).description("학위:대학 목록"),
          fieldWithPath("degrees[].*").type(JsonFieldType.STRING).description("학위 정보")
        )
      ));
  }

  @Test
  @DisplayName("레슨 수정")
  void update() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Lesson lesson = Lesson.builder()
      .user(user)
      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
      .name("김규성")
      .phone("010-4028-7451")
      .fee(80000)
      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
      .build();
    lessonRepository.save(lesson);

    Location location = Location.builder()
      .name("서울 우리집")
      .lesson(lesson)
      .build();
    locationRepository.save(location);

    Major major = Major.builder()
      .name("플루트")
      .lesson(lesson)
      .build();
    majorRepository.save(major);

    Degree degree1 = Degree.builder()
      .degree("MASTER")
      .campusName("서울대학교")
      .lesson(lesson)
      .user(user)
      .build();
    degreeRepository.save(degree1);

    Degree degree2 = Degree.builder()
      .degree("BACHELOR")
      .campusName("연세대학교")
      .lesson(lesson)
      .user(user)
      .build();
    degreeRepository.save(degree2);

    LessonEdit request = LessonEdit.builder()
      .userId(user.getId())
      .imageUrl("www.sample_image_url.com")
      .locations(List.of("서울 전체", "강원도 전체"))
      .name("임성준")
      .majors(List.of("피아노", "성악"))
      .phone("010-0000-0000")
      .fee(800000)
      .intro("안녕하세요")
      .degrees(List.of(Map.of("MASTER", "서울대학교"), Map.of("BACHELOR", "연세대학교")))
      .build();

    String json = objectMapper.writeValueAsString(request);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.put("/lessons/{lessonId}", lesson.getId())
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk())
      .andDo(document("edit-lesson",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("이미지 주소"),
          fieldWithPath("locations").type(JsonFieldType.ARRAY).description("레슨 장소"),
          fieldWithPath("majors").type(JsonFieldType.ARRAY).description("전공 과목"),
          fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
          fieldWithPath("fee").type(JsonFieldType.NUMBER).description("레슨비"),
          fieldWithPath("intro").type(JsonFieldType.STRING).description("소개"),
          fieldWithPath("degrees").type(JsonFieldType.ARRAY).description("학위:대학 목록"),
          fieldWithPath("degrees[].*").type(JsonFieldType.STRING).description("학위 정보")
        )
      ));
  }

  @Test
  @DisplayName("레슨 삭제")
  void remove() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Lesson lesson = Lesson.builder()
      .user(user)
      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
      .name("김규성")
      .phone("010-4028-7451")
      .fee(80000)
      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
      .build();
    lessonRepository.save(lesson);

    Location location = Location.builder()
      .name("서울 우리집")
      .lesson(lesson)
      .build();
    locationRepository.save(location);

    Major major = Major.builder()
      .name("플루트")
      .lesson(lesson)
      .build();
    majorRepository.save(major);

    Degree degree1 = Degree.builder()
      .degree("MASTER")
      .campusName("서울대학교")
      .lesson(lesson)
      .user(user)
      .build();
    degreeRepository.save(degree1);

    Degree degree2 = Degree.builder()
      .degree("BACHELOR")
      .campusName("연세대학교")
      .lesson(lesson)
      .user(user)
      .build();
    degreeRepository.save(degree2);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/lessons/{lessonId}", lesson.getId())
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("delete-lesson",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("lessonId").description("레슨 ID")
            .attributes(key("type").value("Number"))
        )
      ));
  }
}
