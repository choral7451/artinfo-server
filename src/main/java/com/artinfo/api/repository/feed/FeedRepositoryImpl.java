package com.artinfo.api.repository.feed;

import com.artinfo.api.domain.Feed;
import com.artinfo.api.request.feed.FeedSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.artinfo.api.domain.QFeed.feed;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Feed> getList(FeedSearch feedSearch) {
    return jpaQueryFactory.selectFrom(feed)
      .where(feed.artist.id.eq(feedSearch.getArtistId()))
      .limit(feedSearch.getSize())
      .offset(feedSearch.getOffset())
      .orderBy(feed.createdAt.desc())
      .fetch();
  }
}
