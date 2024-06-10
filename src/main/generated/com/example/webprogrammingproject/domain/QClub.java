package com.example.webprogrammingproject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClub is a Querydsl query type for Club
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClub extends EntityPathBase<Club> {

    private static final long serialVersionUID = -2067422315L;

    public static final QClub club = new QClub("club");

    public final StringPath advisorContact = createString("advisorContact");

    public final StringPath advisorMajor = createString("advisorMajor");

    public final StringPath advisorName = createString("advisorName");

    public final StringPath clubImg = createString("clubImg");

    public final StringPath clubIntro = createString("clubIntro");

    public final StringPath clubName = createString("clubName");

    public final StringPath clubType = createString("clubType");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ClubMember, QClubMember> members = this.<ClubMember, QClubMember>createList("members", ClubMember.class, QClubMember.class, PathInits.DIRECT2);

    public final ListPath<ClubPost, QClubPost> posts = this.<ClubPost, QClubPost>createList("posts", ClubPost.class, QClubPost.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QClub(String variable) {
        super(Club.class, forVariable(variable));
    }

    public QClub(Path<? extends Club> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClub(PathMetadata metadata) {
        super(Club.class, metadata);
    }

}

