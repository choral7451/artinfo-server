package com.artinfo.api.controller.lesson;

import com.artinfo.api.domain.Degree;
import com.artinfo.api.domain.Lesson;
import com.artinfo.api.domain.Location;
import com.artinfo.api.domain.Major;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.repository.lesson.LocationRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.DegreeRepository;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class LessonControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private MajorRepository majorRepository;

  @Autowired
  private DegreeRepository degreeRepository;

  @BeforeEach
  void clean() {
    majorRepository.deleteAll();
    lessonRepository.deleteAll();
    locationRepository.deleteAll();
    degreeRepository.deleteAll();
  }


  //TODO 수정 필요
  @Test
  @DisplayName("레슨 단건 조회")
  void getLesson() throws Exception {
    //given

    Location location = Location.builder()
      .name("서울 전체")
      .build();
    locationRepository.save(location);

    Major major = Major.builder()
      .name("피아노")
      .build();
    majorRepository.save(major);



    Lesson lesson = Lesson.builder()
      .profileId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
      .locations(Set.of(location))
      .name("김규성")
      .majors(Set.of(major))
      .phone("010-4028-7451")
      .fee(80000L)
      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
      .build();
    lessonRepository.save(lesson);

    Degree degree = Degree.builder()
      .degree("MASTER")
      .campusName("서울대학교")
      .lesson(lesson)
      .build();
    degreeRepository.save(degree);

    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/lessons/{lessonId}", lesson.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-lesson",
        pathParameters(
          parameterWithName("lessonId").description("레슨 ID")
        ),
        responseFields(
          fieldWithPath("id").description("레슨 ID"),
          fieldWithPath("name").description("이름"),
          fieldWithPath("phone").description("연락처"),
          fieldWithPath("profileId").description("프로필 ID"),
          fieldWithPath("imageUrl").description("이미지 URL"),
          fieldWithPath("locations").description("레슨 위치"),
          fieldWithPath("majors").description("레슨 과목"),
          fieldWithPath("fee").description("레슨비"),
          fieldWithPath("intro").description("레슨 소개"),
          fieldWithPath("degrees").description("학위 목록"),
          fieldWithPath("degrees.*").description("학위"),
          fieldWithPath("degrees.*[]").description("해당 학위 캠퍼스 이름 목록")
        )
      ));
  }

//  @Testz
//  @DisplayName("레슨 목록 조회")
//  void getLessonList() throws Exception {
//    //given
//    Lesson lesson = Lesson.builder()
//      .profileId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
//      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
//      .locations("[\"서울전체\", \"경기도 고양시 일산동구\"]")
//      .name("김규성")
//      .subjects("[\"피아노\"]")
//      .phone("010-4028-7451")
//      .fee(80000L)
//      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
//        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
//        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
//      .degree("[{\"석사\": \"Manhattan School of Music\"}]")
//      .build();
//
//    lessonRepository.save(lesson);
//
//    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/lessons")
//        .accept(MediaType.APPLICATION_JSON)
//      )
//      .andExpect(status().isOk())
//      .andDo(document("get-lessons",
//        queryParameters(
//          parameterWithName("page").description("페이지 번호").optional(),
//          parameterWithName("size").description("레슨 조회 개수").optional(),
//          parameterWithName("location").description("레슨 위치 필터").optional(),
//          parameterWithName("subjects").description("레슨 과목 필터").optional()
//        ),
//        responseFields(
//          fieldWithPath("[].id").description("레슨 ID"),
//          fieldWithPath("[].name").description("이름"),
//          fieldWithPath("[].profileId").description("프로필 ID"),
//          fieldWithPath("[].imageUrl").description("이미지 URL"),
//          fieldWithPath("[].locations").description("레슨 위치"),
//          fieldWithPath("[].subjects").description("레슨 과목")
//        )
//      ));
//  }
}
