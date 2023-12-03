package com.artinfo.api.controller.feed;

import com.artinfo.api.domain.*;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.user.LikeRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.repository.youtube.YoutubeRepository;
import com.artinfo.api.request.feed.FeedCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api-artinfokorea.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class FeedControllerDocTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private FeedRepository feedRepository;

  @Autowired
  private ArtistRepository artistRepository;

  @Autowired
  private YoutubeRepository youtubeRepository;

  @Autowired
  private ConcertRepository concertRepository;

  @Autowired
  private LikeRepository likeRepository;

  @BeforeEach
  void clean() {
    likeRepository.deleteAll();
    concertRepository.deleteAll();
    imageRepository.deleteAll();
    feedRepository.deleteAll();
    youtubeRepository.deleteAll();
    artistRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("피드 단건 조회")
  void getFeed() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Feed feed = Feed.builder()
      .title("제목")
      .contents("내용")
      .user(user)
      .build();
    feedRepository.save(feed);

    Image image = Image.builder()
      .url("www.sample_image_url.com")
      .feed(feed)
      .build();
    imageRepository.save(image);

    Like like = Like.builder()
      .userId(user.getId())
      .feed(feed)
      .build();
    likeRepository.save(like);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/feeds/{feedId}?requestUserId={requestUserId}", feed.getId(), user.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-feed",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("feedId").description("피드 ID")
            .attributes(key("type").value("Number"))
        ),
        queryParameters(
          parameterWithName("requestUserId").description("요청 유저 ID").optional()
            .attributes(key("type").value("String"))
        ),
        responseFields(
          fieldWithPath("feedId").type(JsonFieldType.NUMBER).description("피드 ID"),
          fieldWithPath("authorId").type(JsonFieldType.STRING).description("작성자 ID"),
          fieldWithPath("authorName").type(JsonFieldType.STRING).description("작성자 이름"),
          fieldWithPath("authorIconImageUrl").type(JsonFieldType.STRING).description("작성자 아이콘 이미지 주소").optional(),
          fieldWithPath("title").type(JsonFieldType.STRING).description("피드 제목"),
          fieldWithPath("contents").type(JsonFieldType.STRING).description("피드 내용"),
          fieldWithPath("imageUrls").type(JsonFieldType.ARRAY).description("이미지 주소 목록"),
          fieldWithPath("countOfLikes").type(JsonFieldType.NUMBER).description("피드 좋아요 수"),
          fieldWithPath("countOfComments").type(JsonFieldType.NUMBER).description("피드 댓글 수"),
          fieldWithPath("isLiking").type(JsonFieldType.BOOLEAN).description("요청 유저 피드 좋아요 여부"),
          fieldWithPath("createdAt").type(JsonFieldType.STRING).description("피드 생성일")
        )
      ));
  }

  @Test
  @DisplayName("피드 삭제")
  void deleteFeed() throws Exception {
    //given
    User user = User.builder()
      .name("따니엘")
      .email("artinfokorea2022@gmail.com")
      .password("a123456!")
      .build();
    userRepository.save(user);

    Feed feed = Feed.builder()
      .title("제목")
      .contents("내용")
      .user(user)
      .build();
    feedRepository.save(feed);

    Image image = Image.builder()
      .url("www.sample_image_url.com")
      .feed(feed)
      .build();
    imageRepository.save(image);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/feeds/{feedId}", feed.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("delete-feed",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        pathParameters(
          parameterWithName("feedId").description("피드 ID")
            .attributes(key("type").value("Number"))
        )));
  }

  @Test
  @DisplayName("피드 목록 조회")
  void getListByArtistId() throws Exception {
    //given
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

    Feed feed1 = Feed.builder()
      .title("제목1")
      .contents("내용1")
      .user(user)
      .artist(artist)
      .build();

    Feed feed2 = Feed.builder()
      .title("제목2")
      .contents("내용2")
      .user(user)
      .artist(artist)
      .build();
    feedRepository.saveAll(List.of(feed1, feed2));

    Image image1 = Image.builder()
      .url("www.sample_image_ur1l.com")
      .feed(feed1)
      .build();

    Image image2 = Image.builder()
      .url("www.sample_image_url2.com")
      .feed(feed2)
      .build();
    imageRepository.saveAll(List.of(image1, image2));

    Like like = Like.builder()
      .userId(user.getId())
      .feed(feed1)
      .build();
    likeRepository.save(like);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/feeds?artistId={artistId}&page=1&size=5&requestUserId={requestUserId}", artist.getId(), user.getId())
        .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andDo(document("get-feeds-by-artistId",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        queryParameters(
          parameterWithName("artistId").description("아티스트 ID")
            .attributes(key("type").value("Number")),
          parameterWithName("requestUserId").description("요청 유저 ID").optional()
            .attributes(key("type").value("String")),
          parameterWithName("page").description("페이지 번호").optional()
            .attributes(key("type").value("Number")),
          parameterWithName("size").description("레슨 조회 개수").optional()
            .attributes(key("type").value("Number"))
        ),
        responseFields(
          fieldWithPath("[].feedId").type(JsonFieldType.NUMBER).description("피드 ID"),
          fieldWithPath("[].authorId").type(JsonFieldType.STRING).description("작성자 ID"),
          fieldWithPath("[].authorName").type(JsonFieldType.STRING).description("작성자 이름"),
          fieldWithPath("[].authorIconImageUrl").type(JsonFieldType.STRING).description("작성자 아이콘 이미지 주소").optional(),
          fieldWithPath("[].title").type(JsonFieldType.STRING).description("피드 제목"),
          fieldWithPath("[].contents").type(JsonFieldType.STRING).description("피드 내용"),
          fieldWithPath("[].imageUrls").type(JsonFieldType.ARRAY).description("이미지 주소 목록"),
          fieldWithPath("[].countOfLikes").type(JsonFieldType.NUMBER).description("피드 좋아요 수"),
          fieldWithPath("[].countOfComments").type(JsonFieldType.NUMBER).description("피드 댓글 수"),
          fieldWithPath("[].isLiking").type(JsonFieldType.BOOLEAN).description("요청 유저 피드 좋아요 여부"),
          fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("피드 생성일")
        )
      ));
  }

  @Test
  @DisplayName("피드 생성")
  void create() throws Exception {
    //given
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

    FeedCreate request = FeedCreate.builder()
      .artistId(artist.getId())
      .userId(user.getId())
      .title("제목")
      .contents("내용")
      .imageUrls(List.of("test.sample_image_url.com"))
      .build();

    String json = objectMapper.writeValueAsString(request);

    //expected
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/feeds")
        .contentType(APPLICATION_JSON)
        .content(json)
      )
      .andExpect(status().isOk())
      .andDo(document("create-feed",
        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
        requestFields(
          fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 ID"),
          fieldWithPath("artistId").type(JsonFieldType.NUMBER).description("아티스트 ID"),
          fieldWithPath("title").type(JsonFieldType.STRING).description("피드 제목"),
          fieldWithPath("contents").type(JsonFieldType.STRING).description("피드 내용"),
          fieldWithPath("imageUrls").type(JsonFieldType.ARRAY).description("이미지 주소 목록")
        )
      ));
  }
}
