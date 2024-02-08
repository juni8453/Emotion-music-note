package com.emotionmusicnote.note.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNote is a Querydsl query type for Note
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNote extends EntityPathBase<Note> {

    private static final long serialVersionUID = 1934410725L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNote note = new QNote("note");

    public final com.emotionmusicnote.common.QBaseTime _super = new com.emotionmusicnote.common.QBaseTime(this);

    public final StringPath content = createString("content");

    //inherited
    public final DatePath<java.time.LocalDate> createdDate = _super.createdDate;

    public final StringPath emotion = createString("emotion");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DatePath<java.time.LocalDate> modifiedDate = _super.modifiedDate;

    public final ListPath<com.emotionmusicnote.song.domain.Song, com.emotionmusicnote.song.domain.QSong> songs = this.<com.emotionmusicnote.song.domain.Song, com.emotionmusicnote.song.domain.QSong>createList("songs", com.emotionmusicnote.song.domain.Song.class, com.emotionmusicnote.song.domain.QSong.class, PathInits.DIRECT2);

    public final com.emotionmusicnote.user.domain.QUser user;

    public QNote(String variable) {
        this(Note.class, forVariable(variable), INITS);
    }

    public QNote(Path<? extends Note> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNote(PathMetadata metadata, PathInits inits) {
        this(Note.class, metadata, inits);
    }

    public QNote(Class<? extends Note> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.emotionmusicnote.user.domain.QUser(forProperty("user")) : null;
    }

}

