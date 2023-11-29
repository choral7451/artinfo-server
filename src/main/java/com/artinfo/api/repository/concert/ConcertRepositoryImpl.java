package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.Concert;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.artinfo.api.domain.QConcert.concert;

@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Concert> getListByArtistId(Long artistId) {
    return jpaQueryFactory.selectFrom(concert)
      .where(concert.artist.id.eq(artistId))
      .orderBy(concert.performanceTime.asc())
      .fetch();
  }
}
