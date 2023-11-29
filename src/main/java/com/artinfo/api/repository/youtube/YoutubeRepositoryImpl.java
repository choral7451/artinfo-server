package com.artinfo.api.repository.youtube;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Concert;
import com.artinfo.api.domain.Youtube;
import com.artinfo.api.repository.artist.ArtistRepositoryCustom;
import com.artinfo.api.request.artist.ArtistSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.artinfo.api.domain.QArtist.artist;
import static com.artinfo.api.domain.QConcert.concert;
import static com.artinfo.api.domain.QYoutube.youtube;

@RequiredArgsConstructor
public class YoutubeRepositoryImpl implements YoutubeRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Youtube> getListByArtistId(Long artistId) {
    return jpaQueryFactory.selectFrom(youtube)
      .where(youtube.artist.id.eq(artistId))
      .orderBy(youtube.id.desc())
      .fetch();
  }
}
