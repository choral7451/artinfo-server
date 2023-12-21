package com.artinfo.api.repository.youtube;

import com.artinfo.api.domain.Youtube;

import java.util.List;

public interface YoutubeRepositoryCustom {

  List<Youtube> getListByArtistId(Long artistId);
}
