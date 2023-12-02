package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.Concert;
import com.artinfo.api.request.concert.ConcertSearch;
import com.querydsl.core.BooleanBuilder;
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

  @Override
  public List<Concert> getList(ConcertSearch concertSearch) {
    BooleanBuilder condition = new BooleanBuilder(concert.isActive.eq(true));

    if (concertSearch.getCategory() != null) {
      condition.and(concert.category.eq(concertSearch.getCategory().toString()));
    }

    return jpaQueryFactory.selectFrom(concert)
      .where(condition)
      .limit(concertSearch.getSize())
      .offset(concertSearch.getOffset())
      .orderBy(concert.createdAt.desc())
      .fetch();
  }
}
