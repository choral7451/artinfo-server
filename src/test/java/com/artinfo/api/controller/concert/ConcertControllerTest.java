package com.artinfo.api.controller.concert;

import com.artinfo.api.domain.*;
import com.artinfo.api.domain.concert.Concert;
import com.artinfo.api.domain.enums.ConcertCategory;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.repository.youtube.YoutubeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ConcertControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

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
  @DisplayName("공연 카테고리 솔로 목록 조회")
  void getArtiYoutubeList() throws Exception {
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
      .posterUrl("test1.sample-image-url.com")
      .category(ConcertCategory.ENSEMBLE)
      .location("롯데콘서트홀")
      .user(user)
      .isActive(true)
      .performanceTime(LocalDateTime.now())
      .build();

    Concert concert2 = Concert.builder()
      .title("제목2")
      .contents("내용2")
      .posterUrl("test2.sample-image-url.com")
      .category(ConcertCategory.SOLO)
      .location("롯데콘서트홀")
      .user(user)
      .isActive(true)
      .performanceTime(LocalDateTime.now())
      .build();

    Concert concert3 = Concert.builder()
      .title("제목3")
      .contents("내용3")
      .posterUrl("test3.sample-image-url.com")
      .category(ConcertCategory.SOLO)
      .location("롯데콘서트홀")
      .user(user)
      .isActive(true)
      .performanceTime(LocalDateTime.now())
      .build();
    concertRepository.saveAll(List.of(concert1, concert2, concert3));

    //expected
    mockMvc.perform(MockMvcRequestBuilders.get("/concerts?page=1&size=5&category=SOLO")
        .contentType(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()").value(2))
      .andExpect(jsonPath("$[0].posterUrl").value("test3.sample-image-url.com"))
      .andExpect(jsonPath("$[0].title").value("제목3"))
      .andExpect(jsonPath("$[0].location").value("롯데콘서트홀"))
      .andExpect(jsonPath("$[1].posterUrl").value("test2.sample-image-url.com"))
      .andExpect(jsonPath("$[1].title").value("제목2"))
      .andExpect(jsonPath("$[1].location").value("롯데콘서트홀"))
      .andDo(print());
  }

  @Test
  @DisplayName("공연 목록 조회시 활성화 상태인 공연만 조회")
  void getActiveList() throws Exception {
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
      .posterUrl("test1.sample-image-url.com")
      .category(ConcertCategory.ENSEMBLE)
      .location("롯데콘서트홀")
      .user(user)
      .isActive(false)
      .performanceTime(LocalDateTime.now())
      .build();

    Concert concert2 = Concert.builder()
      .title("제목2")
      .contents("내용2")
      .posterUrl("test2.sample-image-url.com")
      .category(ConcertCategory.SOLO)
      .location("롯데콘서트홀")
      .user(user)
      .isActive(true)
      .performanceTime(LocalDateTime.now())
      .build();

    Concert concert3 = Concert.builder()
      .title("제목3")
      .contents("내용3")
      .posterUrl("test3.sample-image-url.com")
      .category(ConcertCategory.SOLO)
      .location("롯데콘서트홀")
      .user(user)
      .isActive(true)
      .performanceTime(LocalDateTime.now())
      .build();
    concertRepository.saveAll(List.of(concert1, concert2, concert3));

    //expected
    mockMvc.perform(MockMvcRequestBuilders.get("/concerts?page=1&size=5")
        .contentType(APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()").value(2))
      .andExpect(jsonPath("$[0].posterUrl").value("test3.sample-image-url.com"))
      .andExpect(jsonPath("$[0].title").value("제목3"))
      .andExpect(jsonPath("$[0].location").value("롯데콘서트홀"))
      .andExpect(jsonPath("$[1].posterUrl").value("test2.sample-image-url.com"))
      .andExpect(jsonPath("$[1].title").value("제목2"))
      .andExpect(jsonPath("$[1].location").value("롯데콘서트홀"))
      .andDo(print());
  }
}