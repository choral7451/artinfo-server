package com.artinfo.api.controller.youtube;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Youtube;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.repository.youtube.YoutubeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class YoutubeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ArtistRepository artistRepository;

  @Autowired
  private YoutubeRepository youtubeRepository;

  @Autowired
  private ConcertRepository concertRepository;

  @BeforeEach
  void clean() {
    concertRepository.deleteAll();
    youtubeRepository.deleteAll();
    artistRepository.deleteAll();
  }

  @Test
  @DisplayName("아티스트 유튜브 목록 조회")
  void getArtiYoutubeList() throws Exception {
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
    mockMvc.perform(get("/youtubes/artist/{artistId}", artist.getId())
        .contentType(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].artistName").value("서울시립교향악단"))
      .andExpect(jsonPath("$[0].title").value("제목2"))
      .andExpect(jsonPath("$[0].linkUrl").value("https://www.youtube.com/watch?v=9u6W8n9p8SY&t=3s"))
      .andExpect(jsonPath("$[1].artistName").value("서울시립교향악단"))
      .andExpect(jsonPath("$[1].title").value("제목1"))
      .andExpect(jsonPath("$[1].linkUrl").value("https://www.youtube.com/watch?v=9u6W8n9p8SY&t=3s"))
      .andDo(print());
  }

  @Test
  @DisplayName("존재하지 않는 아티스트 유튜브 목록 조회")
  void getNullArtiYoutubeList() throws Exception {
    //given
    Artist artist = Artist.builder()
      .koreanName("서울시립교향악단")
      .englishName("SPO")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seoul_philharmonic_orchestra.png")
      .build();
    artistRepository.save(artist);

    //expected
    mockMvc.perform(get("/youtubes/artist/{artistId}", artist.getId() + 1)
        .contentType(APPLICATION_JSON))
      .andExpect(status().isNotFound())
      .andDo(print());
  }
}
