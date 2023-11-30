package com.artinfo.api.service;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.exception.ArtistNotFound;
import com.artinfo.api.repository.artist.ArtistRepository;
import com.artinfo.api.request.artist.ArtistSearch;
import com.artinfo.api.response.artist.ArtistDetailResponse;
import com.artinfo.api.response.artist.ArtistResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService {

  private final ArtistRepository artistRepository;

  public List<ArtistResponse> getList(ArtistSearch artistSearch) {
    return artistRepository.getList(artistSearch).stream()
      .map(ArtistResponse::new)
      .collect(Collectors.toList());
  }

  public ArtistDetailResponse get(Long artistId) {
    Artist artist = artistRepository.findById(artistId)
      .orElseThrow(ArtistNotFound::new);

    return ArtistDetailResponse.fromArtist(artist);
  }
}