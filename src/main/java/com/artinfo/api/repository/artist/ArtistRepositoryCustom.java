package com.artinfo.api.repository.artist;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.request.artist.ArtistSearch;

import java.util.List;

public interface ArtistRepositoryCustom {
  List<Artist> getList(ArtistSearch artistSearch);
}
