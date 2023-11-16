package com.artinfo.api.controller.lesson;

import com.artinfo.api.domain.Lesson;
import com.artinfo.api.repository.lesson.LessonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class LessonControllerDocTest {

  private MockMvc mockMvc;

  @Autowired
  private LessonRepository lessonRepository;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
      .apply(documentationConfiguration(restDocumentation))
      .build();
  }

  //TODO 수정 필요
  @Test
  @DisplayName("레슨 단건 조회 테스트")
  void test1() throws Exception {
    //given
    Lesson lesson = Lesson.builder()
      .profileId(UUID.fromString("ef03de92-798d-4aa8-a750-831e97f8e889"))
      .imageUrl("https://artinfokorea.com/_next/image?url=https%3A%2F%2Fycuajmirzlqpgzuonzca.supabase.co%2Fstorage%2Fv1%2Fobject%2Fpublic%2Fartinfo%2Flessons%2F17%2F1698037484500.54&w=256&q=100")
      .locations("[\"서울전체\", \"경기도 고양시 일산동구\"]")
      .name("김규성")
      .subjects("[\"피아노\"]")
      .phone("010-4028-7451")
      .fee(80000L)
      .intro("안녕하세요 미국에서 피아노 석사학위를 따고 한국에 들어온 김규성입니다.\n" +
        "많은 학생들을 가르쳐본 경험으로 학생에게 딱 맞는 레슨을 하고자 합니다.\n" +
        "위 번호로 연락 주시면 성실히 답해드리도록 하겠습니다. :D")
      .degree("[{\"석사\": \"Manhattan School of Music\"}]")
      .build();

    lessonRepository.save(lesson);

    this.mockMvc.perform(get("/lessons/{lessonId}", 1L)
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("index"));
  }
}
