package com.artinfo.api.repository.feed;

import com.artinfo.api.domain.Artist;
import com.artinfo.api.domain.Feed;
import com.artinfo.api.repository.artist.ArtistRepositoryCustom;
import com.artinfo.api.request.artist.ArtistSearch;
import com.artinfo.api.request.feed.FeedSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.artinfo.api.domain.QArtist.artist;
import static com.artinfo.api.domain.QConcert.concert;
import static com.artinfo.api.domain.QFeed.feed;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Feed> getListByArtistId(FeedSearch feedSearch) {
    return jpaQueryFactory.selectFrom(feed)
      .where(feed.artist.id.eq(feedSearch.getArtistId()))
      .limit(feedSearch.getSize())
      .offset(feedSearch.getOffset())
      .orderBy(feed.createdAt.desc())
      .fetch();
  }
}
