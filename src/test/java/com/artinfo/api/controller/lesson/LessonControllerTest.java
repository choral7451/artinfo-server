package com.artinfo.api.controller.lesson;

import com.artinfo.api.domain.Location;
import com.artinfo.api.domain.Major;
import com.artinfo.api.domain.User;
import com.artinfo.api.domain.lesson.Lesson;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.repository.lesson.LocationRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.lesson.LessonCreate;
import com.artinfo.api.request.lesson.LessonEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private MajorRepository majorRepository;

  @BeforeEach
  void clean() {
    locationRepository.deleteAll();
    majorRepository.deleteAll();
    lessonRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("레슨 생성")
  void create() throws Exception {
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
    mockMvc.perform(post("/lessons")
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk());
  }

  @Test
  @DisplayName("레슨 생성시 모든 필드는 필수 값이다.")
  void createWithInvalidRequest() throws Exception {
    // given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();

    userRepository.save(user);

    LessonCreate request = LessonCreate.builder()
      .userId(user.getId())
//      .imageUrl("www.sample_image_url.com")
//      .locations(List.of("서울 전체", "강원도 전체"))
      .name("임성준")
      .majors(List.of("피아노", "성악"))
      .phone("010-0000-0000")
      .fee(800000)
      .intro("안녕하세요")
      .degrees(List.of(Map.of("MASTER", "서울대학교"), Map.of("BACHELOR", "연세대학교")))
      .build();

    String json = objectMapper.writeValueAsString(request);

    //expected
    mockMvc.perform(post("/lessons")
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("레슨 수정")
  void edit() throws Exception {
    // given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();

    userRepository.save(user);

    Location location = Location.builder()
      .name("서울 1")
      .build();
    locationRepository.save(location);

    Major major = Major.builder()
      .name("오르간")
      .build();
    majorRepository.save(major);

    Lesson lesson = Lesson.builder()
      .user(user)
      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
      .locations(List.of(location))
      .name("김규성")
      .majors(List.of(major))
      .phone("010-4028-7451")
      .fee(80000)
      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
      .build();
    lessonRepository.save(lesson);


    LessonEdit request = LessonEdit.builder()
      .userId(user.getId())
      .imageUrl("www.sample_image_url.com")
      .locations(List.of("서울 2", "강원도 3"))
      .name("임성준")
      .majors(List.of("기타", "드람"))
      .phone("010-0000-0000")
      .fee(800000)
      .intro("안녕하세요")
      .degrees(List.of(Map.of("MASTER", "서울대학교"), Map.of("BACHELOR", "연세대학교")))
      .build();

    String json = objectMapper.writeValueAsString(request);

    //expected
    mockMvc.perform(put("/lessons/{lessonId}", lesson.getId())
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk());
  }

  @Test
  @DisplayName("레슨 삭제")
  void remove() throws Exception {
    // given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();

    userRepository.save(user);

    Location location = Location.builder()
      .name("서울 전체")
      .build();
    locationRepository.save(location);

    Major major = Major.builder()
      .name("오르간")
      .build();
    majorRepository.save(major);

    Lesson lesson = Lesson.builder()
      .user(user)
      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
      .locations(List.of(location))
      .name("김규성")
      .majors(List.of(major))
      .phone("010-4028-7451")
      .fee(80000)
      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
      .build();
    lessonRepository.save(lesson);

    //expected
    mockMvc.perform(delete("/lessons/{lessonId}", lesson.getId())
        .contentType(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andDo(print());
  }
}