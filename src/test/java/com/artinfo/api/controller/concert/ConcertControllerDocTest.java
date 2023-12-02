package com.artinfo.api.controller.concert;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Concert;
import com.artinfo.api.domain.User;
import com.artinfo.api.domain.enums.ConcertCategory;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.repository.youtube.YoutubeRepository;
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

  @Autowired
  private FeedRepository feedRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private YoutubeRepository youtubeRepository;


  @BeforeEach
  void clean() {
    youtubeRepository.deleteAll();
    imageRepository.deleteAll();
    feedRepository.deleteAll();
    concertRepository.deleteAll();
    artistRepository.deleteAll();
  }

  @Test
  @DisplayName("공연 목록 조회")
  void getConcert() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Concert concert1 = Concert.builder()
      .title("제목")
      .contents("내용")
      .posterUrl("test.sample-image-url.com")
      .category(ConcertCategory.ENSEMBLE)
      .location("롯데콘서트홀")
      .user(user)
      .isActive(true)
      .performanceTime(LocalDateTime.now())
      .build();

    Concert concert2 = Concert.builder()
      .title("제목2")
      .contents("내용2")
      .posterUrl("test.sample-image-url.com")
      .category(ConcertCategory.SOLO)
      .location("롯데콘서트홀")
      .user(user)
      .isActive(true)
      .performanceTime(LocalDateTime.now())
      .build();
    concertRepository.saveAll(List.of(concert1, concert2));

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/concerts?page=1&size=2&category=SOLO")
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-concerts",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        queryParameters(
          parameterWithName("category").description("콘서트 카테고리").optional()
            .attributes(key("type").value("String")),
          parameterWithName("page").description("페이지 번호").optional()
            .attributes(key("type").value("Number")),
          parameterWithName("size").description("레슨 조회 개수").optional()
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("콘서트 ID"),
          fieldWithPath("[].posterUrl").type(JsonFieldType.STRING).description("콘서트 포스터 이미지 주소"),
          fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
          fieldWithPath("[].location").type(JsonFieldType.STRING).description("연주 장소"),
          fieldWithPath("[].performanceTime").type(JsonFieldType.STRING).description("연주 시간")
        )
      ));
  }

  @Test
  @DisplayName("아티스트 공연 목록 조회")
  void getArtistConcertList() throws Exception {
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