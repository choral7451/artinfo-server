package com.artinfo.api.repository.lesson;

import com.artinfo.api.domain.Lesson;
import com.artinfo.api.domain.QLesson;
import com.artinfo.api.domain.QLocation;
import com.artinfo.api.domain.QMajor;
import com.artinfo.api.request.LessonSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LessonRepositoryImpl implements LessonsRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Lesson> getList(LessonSearch lessonSearch) {
    QLesson lesson = QLesson.lesson;
    QLocation location = QLocation.location;
    QMajor major = QMajor.major;

    var query = jpaQueryFactory.selectFrom(lesson);

    if (lessonSearch.getLocation() != null && !lessonSearch.getLocation().isEmpty()) {
      BooleanExpression locationCondition = null;
      for (String loc : lessonSearch.getLocation()) {
        BooleanExpression currentCondition = location.name.like("%" + loc + "%");
        locationCondition = locationCondition == null ? currentCondition : locationCondition.or(currentCondition);
      }
      query.leftJoin(lesson.locations, location).where(locationCondition);
    }

    if (lessonSearch.getMajor() != null && !lessonSearch.getMajor().isEmpty()) {
      query.leftJoin(lesson.majors, major)
        .where(major.name.in(lessonSearch.getMajor()));
    }

    return query.distinct()
      .limit(lessonSearch.getSize())
      .offset(lessonSearch.getOffset())
      .orderBy(lesson.id.desc())
      .fetch();
  }
}
