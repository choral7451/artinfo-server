package com.artinfo.api.controller.feed;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Feed;
import com.artinfo.api.domain.User;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.lesson.LessonRepository;
import com.artinfo.api.repository.lesson.LocationRepository;
import com.artinfo.api.repository.lesson.MajorRepository;
import com.artinfo.api.repository.user.DegreeRepository;
import com.artinfo.api.repository.user.LikeRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FeedControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private LocationRepository locationRepository;

  @Autowired
  private MajorRepository majorRepository;

  @Autowired
  private DegreeRepository degreeRepository;

  @Autowired
  private FeedRepository feedRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private ArtistRepository artistRepository;

  @Autowired
  private LikeRepository likeRepository;

  @BeforeEach
  void clean() {
    likeRepository.deleteAll();
    imageRepository.deleteAll();
    majorRepository.deleteAll();
    lessonRepository.deleteAll();
    locationRepository.deleteAll();
    feedRepository.deleteAll();
    degreeRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("존재하지 않는 피드 조회")
  void getNullFeed() throws Exception {
    // given
    long countOfFeeds = feedRepository.count();

    //expected
    mockMvc.perform(get("/feeds/{feedId}", countOfFeeds + 1L )
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("삭제된 피드 조회")
  void getDeletedFeed() throws Exception {
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    // given
    Feed feed = Feed.builder()
      .title("제목")
      .contents("내용")
      .user(user)
      .build();

    feed.delete();
    feedRepository.save(feed);

    //expected
    mockMvc.perform(get("/feeds/{feedId}", feed.getId() )
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("삭제된 피드 목록 조회")
  void getDeletedFeeds() throws Exception {
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Artist artist = Artist.builder()
      .koreanName("서울시립교향악단")
      .englishName("SPO")
      .mainImageUrl("https://ycuajmirzlqpgzuonzca.supabase.co/storage/v1/object/public/artinfo/artists/seoul_philharmonic_orchestra.png")
      .build();
    artistRepository.save(artist);
    // given
    Feed feed1 = Feed.builder()
      .title("제목")
      .contents("내용")
      .user(user)
      .artist(artist)
      .build();

    feed1.delete();

    Feed feed2 = Feed.builder()
      .title("제목2")
      .contents("내용2")
      .user(user)
      .artist(artist)
      .build();
    feedRepository.saveAll(List.of(feed1,feed2));

    //expected
    mockMvc.perform(get("/feeds?artistId={artistId}", artist.getId())
        .contentType(APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()").value(1)
    );
  }
}