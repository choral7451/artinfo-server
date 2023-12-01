package com.artinfo.api.controller.youtube;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Youtube;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.user.LikeRepository;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class YoutubeControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ArtistRepository artistRepository;

  @Autowired
  private YoutubeRepository youtubeRepository;

  @Autowired
  private ConcertRepository concertRepository;

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
    concertRepository.deleteAll();
    youtubeRepository.deleteAll();
    artistRepository.deleteAll();
  }

  @Test
  @DisplayName("아티스트 유튜브 목록 조회")
  void getArtistYoutubeList() throws Exception {
    //given
    Artist artist = Artist.builder()
      .koreanName("서울시립교향악단")
      .englishName("SPO")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seoul_philharmonic_orchestra.png")
      .build();
    artistRepository.save(artist);

    Youtube youtube1 = Youtube.builder()
      .title("제목1")
      .linkUrl("https://www.youtube.com/watch?v=9u6W8n9p8SY&t=3s")
      .artist(artist)
      .publishedAt(LocalDateTime.now())
      .build();

    Youtube youtube2 = Youtube.builder()
      .title("제목2")
      .linkUrl("https://www.youtube.com/watch?v=9u6W8n9p8SY&t=3s")
      .artist(artist)
      .publishedAt(LocalDateTime.now())
      .build();
    youtubeRepository.saveAll(List.of(youtube1, youtube2));

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/youtubes/artist/{artistId}", artist.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-youtubes-by-artistId",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("artistId").description("아티스트 번호")
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("유튜브 ID"),
          fieldWithPath("[].artistName").type(JsonFieldType.STRING).description("아티스트 이름"),
          fieldWithPath("[].title").type(JsonFieldType.STRING).description("유튜브 제목"),
          fieldWithPath("[].linkUrl").type(JsonFieldType.STRING).description("링크 주소"),
          fieldWithPath("[].publishedAt").type(JsonFieldType.STRING).description("발행 시간")
        )
      ));
  }
}
