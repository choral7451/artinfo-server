package com.artinfo.api.controller.lesson;

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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class LessonControllerDocTest {

  private MockMvc mockMvc;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
      .apply(documentationConfiguration(restDocumentation))
      .build();
  }

  //TODO 수정 필요
  @Test
  @DisplayName("")
  void test1() throws Exception {
    this.mockMvc.perform(get("/")
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("index"));
  }
}
