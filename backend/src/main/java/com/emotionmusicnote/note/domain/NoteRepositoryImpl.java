package com.emotionmusicnote.note.domain;

import static com.emotionmusicnote.note.domain.QNote.note;

import com.emotionmusicnote.common.PageRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoteRepositoryImpl implements NoteCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Note> findAll(Long loginUserId, PageRequest pageRequest) {
    return jpaQueryFactory.selectFrom(note)
        .join(note.user).fetchJoin()
        .where(note.user.id.eq(loginUserId))
        .orderBy(note.id.desc())
        .offset(pageRequest.getOffset())
        .limit(pageRequest.getSize())
        .fetch();
  }

  @Override
  public List<Note> findAllByDate(Long loginUserId, PageRequest pageRequest,
      LocalDate startDate, LocalDate endDate) {
    return jpaQueryFactory.selectFrom(note)
        .join(note.user).fetchJoin()
        .where(
            note.user.id.eq(loginUserId),
            note.createdDate.between(startDate, endDate))
        .orderBy(note.id.desc())
        .offset(pageRequest.getOffset())
        .limit(pageRequest.getSize())
        .fetch();
  }
}
