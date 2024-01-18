package com.artinfo.api.controller.system;

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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
class SystemControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("통계 조회")
  void getLesson() throws Exception {
    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/statistics")
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-statistics",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        responseFields(
          fieldWithPath("visitors").type(JsonFieldType.NUMBER).description("방문자 수"),
          fieldWithPath("users").type(JsonFieldType.NUMBER).description("회원 수")
        )
      ));
  }

  @Test
  @DisplayName("방문자 수 증가")
  void update() throws Exception {
    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/statistics/visitors")
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("increase-visitors",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
      ));
  }
}