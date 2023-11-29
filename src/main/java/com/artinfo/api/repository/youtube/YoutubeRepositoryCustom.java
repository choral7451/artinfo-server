package com.artinfo.api.repository.youtube;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Concert;
import com.artinfo.api.domain.Youtube;
import com.artinfo.api.request.artist.ArtistSearch;

import java.util.List;

public interface YoutubeRepositoryCustom {

  List<Youtube> getListByArtistId(Long artistId);
}
