package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.concert.Concert;
import com.artinfo.api.request.concert.ConcertSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Pattern;

import static com.artinfo.api.domain.concert.QConcert.concert;

@Slf4j
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Concert> getListByArtist(Artist artist) {
    return jpaQueryFactory.selectFrom(concert)
      .where(concert.artists.contains(artist))
      .orderBy(concert.performanceTime.asc())
      .fetch();
  }

  @Override
  public List<Concert> getList(ConcertSearch concertSearch) {
    BooleanBuilder condition = new BooleanBuilder(concert.isActive.eq(true));

    if (concertSearch.getCategory() != null && concertSearch.getKeyword() == null) {
      condition.and(concert.category.eq(concertSearch.getCategory().toString()));
    }

    if (concertSearch.getKeyword() != null && concertSearch.getCategory() == null) {
      String keywordRegex = ".*" + Pattern.quote(concertSearch.getKeyword()) + ".*";
      condition.and(concert.keywordData.containsIgnoreCase(concertSearch.getKeyword()));
    }

    if (concertSearch.getKeyword() != null && concertSearch.getCategory() != null) {
      condition.andAnyOf(
        concert.category.eq(concertSearch.getCategory().toString()),
        concert.keywordData.containsIgnoreCase(concertSearch.getKeyword())
      );
    }

    return jpaQueryFactory.selectFrom(concert)
      .where(condition)
      .limit(concertSearch.getSize())
      .offset(concertSearch.getOffset())
      .orderBy(concert.createdAt.desc())
      .fetch();
  }
}
