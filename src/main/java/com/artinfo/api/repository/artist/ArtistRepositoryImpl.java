package com.artinfo.api.repository.artist;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.request.artist.ArtistSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.artinfo.api.domain.QArtist.artist;

@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Artist> getList(ArtistSearch artistSearch) {
    return jpaQueryFactory.selectFrom(artist)
      .limit(artistSearch.getSize())
      .offset(artistSearch.getOffset())
      .orderBy(artist.id.desc())
      .fetch();
  }
}
