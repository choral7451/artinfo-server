package com.artinfo.api.repository.job;

import com.artinfo.api.domain.QMajor;
import com.artinfo.api.domain.job.Job;
import com.artinfo.api.domain.job.QJob;
import com.artinfo.api.request.job.JobSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class JobRepositoryImpl implements JobRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  public List<Job> getList(JobSearch jobSearch) {
    QJob job = QJob.job;

    var query = jpaQueryFactory.selectFrom(job);

    if (jobSearch.getMajor() != null && !jobSearch.getMajor().isEmpty()) {
      query.join(job.majors, QMajor.major)
        .where(QMajor.major.name.in(jobSearch.getMajor()));
    }

    return query.distinct()
      .limit(jobSearch.getSize())
      .offset(jobSearch.getOffset())
      .orderBy(job.id.desc())
      .fetch();
  }
}
