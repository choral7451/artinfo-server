package com.artinfo.api.repository.concert;

import com.artinfo.api.domain.concert.ConcertKeyword;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.artinfo.api.domain.concert.QConcertKeyword.concertKeyword;

@Slf4j
@RequiredArgsConstructor
public class ConcertKeywordRepositoryImpl implements ConcertKeywordRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ConcertKeyword> findTopNByOrderByFetchCountDesc(Integer size) {
    return jpaQueryFactory.selectFrom(concertKeyword)
      .limit(size)
      .orderBy(concertKeyword.fetchCount.desc())
      .fetch();
  }
}
