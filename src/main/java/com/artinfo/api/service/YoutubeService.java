package com.artinfo.api.service;

import com.artinfo.api.exception.ArtistNotFound;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.repository.concert.ConcertRepository;
import com.artinfo.api.repository.youtube.YoutubeRepository;
import com.artinfo.api.response.concert.ArtistConcertResponse;
import com.artinfo.api.response.youtube.ArtistYoutubeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class YoutubeService {

  private final ArtistRepository artistRepository;
  private final YoutubeRepository youtubeRepository;

  public List<ArtistYoutubeResponse> getListByArtistId(Long artistId) {
    artistRepository.findById(artistId)
      .orElseThrow(ArtistNotFound::new);

    return youtubeRepository.getListByArtistId(artistId).stream()
      .map(ArtistYoutubeResponse::new)
      .collect(Collectors.toList());
  }
}
