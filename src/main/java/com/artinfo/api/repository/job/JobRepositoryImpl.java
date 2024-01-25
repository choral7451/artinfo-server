package com.artinfo.api.repository.job;

import com.artinfo.api.domain.job.Job;
import com.artinfo.api.request.job.JobSearch;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.artinfo.api.domain.job.QJob.job;

@RequiredArgsConstructor
public class JobRepositoryImpl implements JobRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Job> findListByMajors(JobSearch jobSearch) {
    List<Long> ids = findJobIds(jobSearch);

    return findJobsByIds(ids);
  }

  private List<Long> findJobIds(JobSearch jobSearch) {
    JPAQuery<Long> jpaQuery = jpaQueryFactory.select(job.id)
      .from(job)
      .limit(jobSearch.getSize())
      .offset(jobSearch.getOffset())
      .orderBy(job.createdAt.desc());

    if (jobSearch.getMajor() != null && !jobSearch.getMajor().isEmpty()) {
      jpaQuery = jpaQuery.where(job.majors.any().name.in(jobSearch.getMajor()));
    }

    return jpaQuery.fetch();
  }

  private List<Job> findJobsByIds(List<Long> ids) {
    return jpaQueryFactory.selectFrom(job)
      .leftJoin(job.majors)
      .fetchJoin()
      .where(job.id.in(ids))
      .orderBy(job.createdAt.desc())
      .fetch();
  }
}