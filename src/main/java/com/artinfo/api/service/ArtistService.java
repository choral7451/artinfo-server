package com.artinfo.api.service;

import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.request.artist.ArtistSearch;
import com.artinfo.api.response.artist.ArtistResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArtistService {

  private final ArtistRepository artistRepository;

  public List<ArtistResponse> getList(ArtistSearch artistSearch) {
    return artistRepository.getList(artistSearch).stream()
      .map(ArtistResponse::new)
      .collect(Collectors.toList());
  }
}