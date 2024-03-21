package com.artinfo.api.service;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.User;
import com.artinfo.api.domain.concert.Concert;
import com.artinfo.api.domain.concert.ConcertKeyword;
import com.artinfo.api.exception.ArtistNotFound;
import com.artinfo.api.exception.ConcertNotFound;
import com.artinfo.api.exception.UserNotFound;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertKeywordRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.concert.ConcertCreate;
import com.artinfo.api.request.concert.ConcertKeywordSearch;
import com.artinfo.api.request.concert.ConcertSearch;
import com.artinfo.api.response.concert.ArtistConcertResponse;
import com.artinfo.api.response.concert.ConcertDetailResponse;
import com.artinfo.api.response.concert.ConcertResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConcertService {

  private final ArtistRepository artistRepository;
  private final ConcertRepository concertRepository;
  private final UserRepository userRepository;
  private final ConcertKeywordRepository concertKeywordRepository;

  public ConcertDetailResponse get(Long concertId) {
    Concert concert = concertRepository.findById(concertId)
      .orElseThrow(ConcertNotFound::new);

    return ConcertDetailResponse.builder()
      .id(concert.getId())
      .authorId(concert.getUser().getId())
      .authorEmail(concert.getUser().getEmail())
      .authorPublicNickName(concert.getUser().getName())
      .authorIconImageUrl(concert.getUser().getIconImageUrl())
      .title(concert.getTitle())
      .contents(concert.getContents())
      .linkUrl(concert.getLinkUrl())
      .posterUrl(concert.getPosterUrl())
      .location(concert.getLocation())
      .isActive(concert.getIsActive())
      .category(concert.getCategory())
      .performanceTime(concert.getPerformanceTime())
      .build();
  }

  @Transactional
  public void create(ConcertCreate concertCreate) {
    User user = userRepository.findById(concertCreate.getUserId())
      .orElseThrow(UserNotFound::new);


    List<Artist> artists = new ArrayList<>();
    if(concertCreate.getArtistIds() != null && !concertCreate.getArtistIds().isEmpty()) {
      concertCreate.getArtistIds().forEach(artistId -> {
        Artist artist = artistRepository.findById(artistId)
          .orElseThrow(ArtistNotFound::new);

        artists.add(artist);
      });
    }


    Concert concert = Concert.builder()
      .title(concertCreate.getTitle())
      .contents(concertCreate.getContents())
      .location(concertCreate.getLocation())
      .posterUrl(concertCreate.getPosterUrl())
      .linkUrl(concertCreate.getLinkUrl())
      .isActive(concertCreate.getIsActive())
      .user(user)
      .artist(artists)
      .performanceTime(concertCreate.getPerformanceTime())
      .build();

    concertRepository.save(concert);
  }

  public List<ConcertResponse> getList(ConcertSearch concertSearch) {
    if (concertSearch.getKeyword() != null) {
      Optional<ConcertKeyword> optionalConcertKeyword = concertKeywordRepository.findByKeyword(concertSearch.getKeyword());
      if (optionalConcertKeyword.isPresent()) {
        ConcertKeyword keyword = optionalConcertKeyword.get();
        keyword.increaseFetchCount(keyword.getFetchCount());
        concertKeywordRepository.save(keyword);
      } else {
        ConcertKeyword keyword = ConcertKeyword.builder().keyword(concertSearch.getKeyword()).build();
        concertKeywordRepository.save(keyword);
      }
    }

    return concertRepository.getList(concertSearch).stream()
      .map(ConcertResponse::new).toList();
  }

  public List<ArtistConcertResponse> getListByArtistId(Long artistId) {
    Artist artist =  artistRepository.findById(artistId)
      .orElseThrow(ArtistNotFound::new);

    return concertRepository.getListByArtist(artist).stream()
      .map(ArtistConcertResponse::new)
      .collect(Collectors.toList());
  }

  public List<String> getKeywordList(ConcertKeywordSearch concertKeywordSearch) {
    List<ConcertKeyword> topKeywords = concertKeywordRepository.findTopNByOrderByFetchCountDesc(concertKeywordSearch.getSize());

    return topKeywords.stream().map(ConcertKeyword::getKeyword).toList();
  }
}
