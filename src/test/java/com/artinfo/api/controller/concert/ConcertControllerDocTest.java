package com.artinfo.api.controller.concert;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Concert;
import com.artinfo.api.domain.enums.ConcertCategory;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class ConcertControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ArtistRepository artistRepository;

  @Autowired
  private ConcertRepository concertRepository;


  @BeforeEach
  void clean() {
    concertRepository.deleteAll();
    artistRepository.deleteAll();
  }

  @Test
  @DisplayName("아티스트 공연 목록 조회")
  void getArtistList() throws Exception {
    //given
    Artist artist = Artist.builder()
      .koreanName("서울시립교향악단")
      .englishName("SPO")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seoul_philharmonic_orchestra.png")
      .build();
    artistRepository.save(artist);

    Concert concert1 = Concert.builder()
      .title("제목")
      .contents("내용")
      .category(ConcertCategory.ENSEMBLE)
      .location("롯데콘서트홀")
      .artist(artist)
      .isActive(false)
      .performanceTime(LocalDateTime.now())
      .build();

    Concert concert2 = Concert.builder()
      .title("제목2")
      .contents("내용2")
      .category(ConcertCategory.SOLO)
      .location("롯데콘서트홀")
      .artist(artist)
      .isActive(false)
      .performanceTime(LocalDateTime.now())
      .build();

    concertRepository.saveAll(List.of(concert1, concert2));

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/concerts/artist/{artistId}", artist.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-concerts-by-artistId",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("artistId").description("아티스트 번호")
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("콘서트 ID"),
          fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
          fieldWithPath("[].location").type(JsonFieldType.STRING).description("연주 장소"),
          fieldWithPath("[].performanceTime").type(JsonFieldType.STRING).description("연주 시간"),
          fieldWithPath("[].isActive").type(JsonFieldType.BOOLEAN).description("활성화 상태")
        )
      ));
  }
}
