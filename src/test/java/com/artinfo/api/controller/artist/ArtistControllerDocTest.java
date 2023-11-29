package com.artinfo.api.controller.artist;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.repository.artist.ArtistRepository;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class ArtistControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ArtistRepository artistRepository;


  @BeforeEach
  void clean() {
    artistRepository.deleteAll();
  }

  @Test
  @DisplayName("아티스트 목록 조회")
  void getArtistList() throws Exception {
    //given
    Artist artist1 = Artist.builder()
      .name("서울시립교향악단")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seoul_philharmonic_orchestra.png")
      .build();

    Artist artist2 = Artist.builder()
      .name("조성진")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seongjin_cho.jpeg")
      .build();

    Artist artist3 = Artist.builder()
      .name("손열음")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/yeoleum_son.jpeg")
      .build();

    artistRepository.saveAll(List.of(artist1, artist2, artist3));

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/artists?page=1&size=2")
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-artists",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        queryParameters(
          parameterWithName("page").description("페이지 번호").optional()
            .attributes(key("type").value("Number")),
          parameterWithName("size").description("아티스트 조회 개수").optional()
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("아티스트 ID"),
          fieldWithPath("[].name").type(JsonFieldType.STRING).description("이름"),
          fieldWithPath("[].mainImageUrl").type(JsonFieldType.STRING).description("메인 이미지 URL")
        )
      ));
  }
}
