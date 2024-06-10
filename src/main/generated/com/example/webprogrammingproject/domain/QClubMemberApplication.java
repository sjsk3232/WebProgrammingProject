package com.example.webprogrammingproject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubMemberApplication is a Querydsl query type for ClubMemberApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubMemberApplication extends EntityPathBase<ClubMemberApplication> {

    private static final long serialVersionUID = -1953499327L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubMemberApplication clubMemberApplication = new QClubMemberApplication("clubMemberApplication");

    public final QMember applicant;

    public final StringPath application = createString("application");

    public final QClub club;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath result = createString("result");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QClubMemberApplication(String variable) {
        this(ClubMemberApplication.class, forVariable(variable), INITS);
    }

    public QClubMemberApplication(Path<? extends ClubMemberApplication> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubMemberApplication(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubMemberApplication(PathMetadata metadata, PathInits inits) {
        this(ClubMemberApplication.class, metadata, inits);
    }

    public QClubMemberApplication(Class<? extends ClubMemberApplication> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.applicant = inits.isInitialized("applicant") ? new QMember(forProperty("applicant")) : null;
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club")) : null;
    }

}

