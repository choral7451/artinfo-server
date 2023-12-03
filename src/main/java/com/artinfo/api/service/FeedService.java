package com.artinfo.api.service;

import com.artinfo.api.domain.*;
import com.artinfo.api.exception.ArtistNotFound;
import com.artinfo.api.exception.FeedNotFound;
import com.artinfo.api.exception.UserNotFound;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.feed.FeedRepository;
import com.artinfo.api.repository.image.ImageRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.feed.FeedCreate;
import com.artinfo.api.request.feed.FeedSearch;
import com.artinfo.api.response.feed.FeedDetailResponse;
import com.artinfo.api.response.feed.FeedResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {

  private final FeedRepository feedRepository;
  private final UserRepository userRepository;
  private final ArtistRepository artistRepository;
  private final ImageRepository imageRepository;

  public FeedDetailResponse get(Long feedId, UUID requestUserId) {
    Feed feed = feedRepository.findById(feedId)
      .orElseThrow(FeedNotFound::new);

    if(feed.getIsDeleted()) throw new FeedNotFound();

    List<UUID> likedUserIds = feed.getLikes().stream().map(Like::getUserId).toList();

    return FeedDetailResponse.builder()
      .feedId(feed.getId())
      .authorId(feed.getUser().getId())
      .authorName(feed.getUser().getName())
      .authorIconImageUrl(feed.getUser().getIconImageUrl())
      .title(feed.getTitle())
      .contents(feed.getContents())
      .imageUrls(feed.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
      .countOfLikes(feed.getCountOfLikes())
      .countOfComments(feed.getCountOfComments())
      .isLiking(likedUserIds.contains(requestUserId))
      .createdAt(feed.getCreatedAt())
      .build();
  }

  public void delete(Long feedId) {
    Feed feed = feedRepository.findById(feedId)
      .orElseThrow(FeedNotFound::new);

    if(feed.getIsDeleted()) {
      throw new FeedNotFound();
    }

    feed.delete();
    feedRepository.save(feed);
  }

  public List<FeedResponse> getList(FeedSearch feedSearch) {
    return feedRepository.getList(feedSearch).stream()
      .map(feed -> {
        List<UUID> likedUserIds = feed.getLikes().stream().map(Like::getUserId).toList();
        return FeedResponse.builder()
          .feedId(feed.getId())
          .authorId(feed.getUser().getId())
          .authorName(feed.getUser().getName())
          .authorIconImageUrl(feed.getUser().getIconImageUrl())
          .title(feed.getTitle())
          .contents(feed.getContents())
          .imageUrls(feed.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
          .countOfLikes(feed.getCountOfLikes())
          .countOfComments(feed.getCountOfComments())
          .isLiking(likedUserIds.contains(feedSearch.getRequestUserId()))
          .createdAt(feed.getCreatedAt())
          .build();
      })
      .collect(Collectors.toList());
  }

  @Transactional
  public void create(FeedCreate feedCreate) {
    User user = userRepository.findById(feedCreate.getUserId())
      .orElseThrow(UserNotFound::new);

    Artist artist = artistRepository.findById(feedCreate.getArtistId())
      .orElseThrow(ArtistNotFound::new);

    Feed feed = Feed.builder()
      .title(feedCreate.getTitle())
      .contents(feedCreate.getContents())
      .artist(artist)
      .user(user)
      .build();

    feedRepository.save(feed);

    List<Image> images = feedCreate.getImageUrls().stream()
      .map(imageUrl -> {
        return Image.builder()
          .url(imageUrl)
          .feed(feed)
          .build();
    }).toList();

    imageRepository.saveAll(images);
  }
}
