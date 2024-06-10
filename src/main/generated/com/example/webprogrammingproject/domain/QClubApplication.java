package com.example.webprogrammingproject.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubApplication is a Querydsl query type for ClubApplication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubApplication extends EntityPathBase<ClubApplication> {

    private static final long serialVersionUID = -1797690757L;

    public static final QClubApplication clubApplication = new QClubApplication("clubApplication");

    public final StringPath advisorContact = createString("advisorContact");

    public final StringPath advisorMajor = createString("advisorMajor");

    public final StringPath advisorName = createString("advisorName");

    public final StringPath applicantEmail = createString("applicantEmail");

    public final StringPath applicantMajor = createString("applicantMajor");

    public final StringPath applicantName = createString("applicantName");

    public final StringPath applicantStudentNumber = createString("applicantStudentNumber");

    public final StringPath clubName = createString("clubName");

    public final StringPath clubType = createString("clubType");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath rejectionReason = createString("rejectionReason");

    public final StringPath result = createString("result");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QClubApplication(String variable) {
        super(ClubApplication.class, forVariable(variable));
    }

    public QClubApplication(Path<? extends ClubApplication> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubApplication(PathMetadata metadata) {
        super(ClubApplication.class, metadata);
    }

}

