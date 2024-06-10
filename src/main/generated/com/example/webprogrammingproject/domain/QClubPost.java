package com.example.webprogrammingproject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubPost is a Querydsl query type for ClubPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubPost extends EntityPathBase<ClubPost> {

    private static final long serialVersionUID = -1684677163L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubPost clubPost = new QClubPost("clubPost");

    public final QClub club;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final StringPath multipart = createString("multipart");

    public final StringPath postType = createString("postType");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QClubPost(String variable) {
        this(ClubPost.class, forVariable(variable), INITS);
    }

    public QClubPost(Path<? extends ClubPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubPost(PathMetadata metadata, PathInits inits) {
        this(ClubPost.class, metadata, inits);
    }

    public QClubPost(Class<? extends ClubPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club")) : null;
    }

}

