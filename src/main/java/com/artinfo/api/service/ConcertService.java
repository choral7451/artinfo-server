package com.artinfo.api.service;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Concert;
import com.artinfo.api.domain.User;
import com.artinfo.api.exception.ArtistNotFound;
import com.artinfo.api.exception.UserNotFound;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.repository.user.UserRepository;
import com.artinfo.api.request.concert.ConcertCreate;
import com.artinfo.api.request.concert.ConcertSearch;
import com.artinfo.api.response.concert.ArtistConcertResponse;
import com.artinfo.api.response.concert.ConcertResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConcertService {

  private final ArtistRepository artistRepository;
  private final ConcertRepository concertRepository;
  private final UserRepository userRepository;

  @Transactional
  public void create(ConcertCreate concertCreate) {
    User user = userRepository.findById(concertCreate.getUserId())
      .orElseThrow(UserNotFound::new);

    Artist artist = null;
    if(concertCreate.getArtistId() != null) {
      artist = artistRepository.findById(concertCreate.getArtistId())
        .orElseThrow(ArtistNotFound::new);
    }

    Concert concert = Concert.builder()
      .title(concertCreate.getTitle())
      .contents(concertCreate.getContents())
      .location(concertCreate.getLocation())
      .posterUrl(concertCreate.getPosterUrl())
      .linkUrl(concertCreate.getLinkUrl())
      .isActive(concertCreate.getIsActive())
      .user(user)
      .artist(artist)
      .performanceTime(concertCreate.getPerformanceTime())
      .build();

    concertRepository.save(concert);
  }

  public List<ConcertResponse> getList(ConcertSearch concertSearch) {
    return concertRepository.getList(concertSearch).stream()
      .map(ConcertResponse::new).toList();
  }

  public List<ArtistConcertResponse> getListByArtistId(Long artistId) {
    artistRepository.findById(artistId)
      .orElseThrow(ArtistNotFound::new);

    return concertRepository.getListByArtistId(artistId).stream()
      .map(ArtistConcertResponse::new)
      .collect(Collectors.toList());
  }
}
