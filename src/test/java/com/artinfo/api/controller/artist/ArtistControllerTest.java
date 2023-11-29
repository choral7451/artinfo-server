package com.artinfo.api.controller.artist;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.repository.artist.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ArtistControllerTest {

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
      .koreanName("서울시립교향악단")
      .englishName("SPO")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seoul_philharmonic_orchestra.png")
      .build();

    Artist artist2 = Artist.builder()
      .koreanName("조성진")
      .englishName("Seong Jin Cho")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seongjin_cho.jpeg")
      .build();

    artistRepository.saveAll(List.of(artist1, artist2));

    //expected
    mockMvc.perform(get("/artists")
        .param("page","1")
        .param("size", "2")
        .contentType(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].koreanName").value("조성진"))
      .andExpect(jsonPath("$[0].englishName").value("Seong Jin Cho"))
      .andExpect(jsonPath("$[0].mainImageUrl").value("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seongjin_cho.jpeg"))
      .andExpect(jsonPath("$[1].koreanName").value("서울시립교향악단"))
      .andExpect(jsonPath("$[1].englishName").value("SPO"))
      .andExpect(jsonPath("$[1].mainImageUrl").value("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seoul_philharmonic_orchestra.png"))
      .andDo(print());
  }
}
