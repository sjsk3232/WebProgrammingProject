package com.example.webprogrammingproject.service;

import com.example.webprogrammingproject.domain.*;
import com.example.webprogrammingproject.dto.*;
import com.example.webprogrammingproject.repository.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ClubService {
    private final MemberRepository memberRepository;
    private final ClubApplicationRepository clubApplicationRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubMemberApplicationRepository clubMemberApplicationRepository;
    private final ClubRepository clubRepository;
    private final ClubPostRepository clubPostRepository;
    private final BucketService bucketService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addClubApplication(String memberEmail, AddClubApplicationRequest request) {
        Member found = memberRepository.findByEmail(memberEmail).orElseThrow(
                () -> new IllegalArgumentException("addClubApplication error: not found member")
        );

        clubApplicationRepository.save(
                ClubApplication.builder()
                .applicantEmail(found.getEmail())
                .applicantName(found.getName())
                .applicantMajor(found.getMajor())
                .applicantStudentNumber(found.getStudentNumber())
                .clubType(request.getClubType())
                .clubName(request.getClubName())
                .advisorName(request.getAdvisorName())
                .advisorMajor(request.getAdvisorMajor())
                .advisorContact(request.getAdvisorContact())
                .build()
        );
    }

    @Transactional
    public void reviewClubApplication(ReviewClubApplicationRequest request) {
        ClubApplication found = clubApplicationRepository.findById(request.getClubApplicationId())
                .orElseThrow(() -> new IllegalArgumentException("reviewClubApplication error: not found clubApplication"));

        found.setResult(request.getResult());
        found.setRejectionReason(request.getRejectionReason());
        clubApplicationRepository.save(found);

        if(!found.getResult().equals("승인")) return;

        Club newClub = Club.builder()
                .clubType(found.getClubType())
                .clubName(found.getClubName())
                .advisorName(found.getAdvisorName())
                .advisorMajor(found.getAdvisorMajor())
                .advisorContact(found.getAdvisorContact())
                .build();

        ClubMember newClubMember = ClubMember.builder()
                .club(newClub)
                .member(Member.builder()
                        .email(found.getApplicantEmail())
                        .build()
                )
                .isMaster(true)
                .position("대표")
                .build();

        newClub.getMembers().add(newClubMember);

        clubRepository.save(newClub);
        clubMemberRepository.save(newClubMember);
    }

    public Page<GetClubApplicationResponse> findAllClubApplications(Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClubApplication clubApplication = QClubApplication.clubApplication;

        List<GetClubApplicationResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubApplicationResponse.class,
                                clubApplication.id, clubApplication.applicantEmail, clubApplication.applicantName,
                                clubApplication.applicantMajor, clubApplication.applicantStudentNumber,
                                clubApplication.clubType, clubApplication.clubName, clubApplication.advisorName,
                                clubApplication.advisorMajor, clubApplication.advisorContact, clubApplication.result,
                                clubApplication.rejectionReason, clubApplication.createdAt)
                )
                .from(clubApplication)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                queryFactory.select(clubApplication.count())
                        .from(clubApplication)::fetchCount
        );
    }

    public Page<GetClubApplicationResponse> findAllClubApplicationsByMemberEmail(String memberEmail, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClubApplication clubApplication = QClubApplication.clubApplication;

        BooleanBuilder whereClause = new BooleanBuilder(clubApplication.applicantEmail.eq(memberEmail));

        List<GetClubApplicationResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubApplicationResponse.class,
                                clubApplication.id, clubApplication.applicantEmail, clubApplication.applicantName,
                                clubApplication.applicantMajor, clubApplication.applicantStudentNumber,
                                clubApplication.clubType, clubApplication.clubName, clubApplication.advisorName,
                                clubApplication.advisorMajor, clubApplication.advisorContact, clubApplication.result,
                                clubApplication.rejectionReason, clubApplication.createdAt)
                )
                .from(clubApplication)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                queryFactory.select(clubApplication.count())
                        .from(clubApplication)
                        .where(whereClause)::fetchCount
        );
    }

    public Page<GetClubInfoResponse> findAllClub(Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClub club = QClub.club;

        List<GetClubInfoResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubInfoResponse.class,
                                club.id, club.clubType, club.clubName,
                                club.clubIntro, club.clubImg, club.advisorName,
                                club.advisorMajor, club.advisorContact, club.regularMeeting, club.createdAt)
                )
                .from(club)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                queryFactory.select(club.count())
                        .from(club)::fetchCount
        );
    }

    public List<GetClubInfoResponse> findAllJoinedClub(String memberId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClub club = QClub.club;
        QClubMember clubMember = QClubMember.clubMember;

        BooleanBuilder whereClause = new BooleanBuilder(clubMember.member.email.eq(memberId));

        List<GetClubInfoResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubInfoResponse.class,
                                club.id, club.clubType, club.clubName,
                                club.clubIntro, club.clubImg, club.advisorName,
                                club.advisorMajor, club.advisorContact, club.regularMeeting, club.createdAt)
                )
                .from(clubMember)
                .where(whereClause)
                .leftJoin(clubMember.club, club)
                .fetch();

        return results;
    }

    public List<GetClubInfoResponse> findAllManagingClub(String memberId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClub club = QClub.club;
        QClubMember clubMember = QClubMember.clubMember;

        BooleanBuilder whereClause = new BooleanBuilder(clubMember.member.email.eq(memberId).and(clubMember.isMaster));

        List<GetClubInfoResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubInfoResponse.class,
                                club.id, club.clubType, club.clubName,
                                club.clubIntro, club.clubImg, club.advisorName,
                                club.advisorMajor, club.advisorContact, club.regularMeeting, club.createdAt)
                )
                .from(clubMember)
                .where(whereClause)
                .leftJoin(clubMember.club, club)
                .fetch();

        return results;
    }

    public GetClubInfoResponse getClubInfo(Long clubId) {
        Club found = clubRepository.findById(clubId).orElseThrow(
                () -> new IllegalArgumentException("getClubInfo error: not found club")
        );
        return GetClubInfoResponse.of(found);
    }

    @Transactional
    public void updateClubInfo(String memberEmail, UpdateClubInfoRequest request) throws IOException {
        Club foundClub = clubRepository.findById(request.getTargetClubId()).orElseThrow(
                () -> new IllegalArgumentException("updateClubInfo error: not found club")
        );

        ClubMember foundMember = clubMemberRepository.findByClub_IdAndMember_Email(foundClub.getId(), memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("updateClubInfo error: not found clubMember")
        );

        if(!foundMember.getIsMaster())
            throw new IllegalArgumentException("updateClubInfo error: only master can change");

        if(request.getClubIntro() != null) foundClub.setClubIntro(request.getClubIntro());
        if(request.getAdvisorName() != null) foundClub.setAdvisorName(request.getAdvisorName());
        if(request.getAdvisorMajor() != null) foundClub.setAdvisorMajor(request.getAdvisorMajor());
        if(request.getAdvisorContact() != null) foundClub.setAdvisorContact(request.getAdvisorContact());
        if(request.getRegularMeeting() != null) foundClub.setRegularMeeting(request.getRegularMeeting());

        if(request.getImg() != null) {
            if(foundClub.getClubImg() != null) bucketService.delete(foundClub.getClubImg());
            String uuid = bucketService.save(request.getImg());
            foundClub.setClubImg(uuid);
        }

        clubRepository.save(foundClub);
    }

    public Page<GetClubMemberResponse> findAllClubMember(String memberEmail, Long clubId, Pageable pageable) {
        ClubMember foundMember = clubMemberRepository.findByClub_IdAndMember_Email(clubId, memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("findAllClubMember error: not found clubMember")
        );

        if(!foundMember.getIsMaster())
            throw new IllegalArgumentException("findAllClubMember error: only master can access");

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClubMember clubMember = QClubMember.clubMember;
//        QMember member = QMember.member;

        BooleanBuilder whereClause = new BooleanBuilder(clubMember.club.id.eq(clubId));

        List<GetClubMemberResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubMemberResponse.class,
                                clubMember.position, clubMember.isMaster, clubMember.member.email,
                                clubMember.member.name, clubMember.state
                        )
                )
                .from(clubMember)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                queryFactory.select(clubMember.count())
                        .from(clubMember)
                        .where(whereClause)::fetchCount
        );
    }

    @Transactional
    public void updateClubMember(String memberEmail, UpdateClubMemberRequest request) {
        ClubMember foundMember = clubMemberRepository.findByClub_IdAndMember_Email(request.getClubId(), memberEmail)
                .orElseThrow(
                        () -> new IllegalArgumentException("updateClubMember error: not found clubMember")
                );

        if(!foundMember.getIsMaster())
            throw new IllegalArgumentException("updateClubMember error: only master can change");

        ClubMember targetMember = clubMemberRepository.findByClub_IdAndMember_Email(
                request.getClubId(), request.getMemberId()
        ).orElseThrow(
                () -> new IllegalArgumentException("updateClubMember error: not found target member")
        );

        targetMember.setIsMaster(request.getIsMaster());
        targetMember.setPosition(request.getPosition());
        targetMember.setState(request.getState());

        clubMemberRepository.save(targetMember);
    }

    @Transactional
    public void addClubMemberApplication(
            String memberEmail, AddClubMemberApplicationRequest request
    ) throws IOException {
        Club foundClub = clubRepository.findById(request.getClubId()).orElseThrow(
                () -> new IllegalArgumentException("addClubMemberApplication error: not found club")
        );

        if(request.getApplication() == null) return;

        String uuid = bucketService.save(request.getApplication());

        clubMemberApplicationRepository.save(ClubMemberApplication.builder()
                .club(foundClub)
                .applicant(Member.builder().email(memberEmail).build())
                .application(uuid)
                .build()
        );
    }

    @Transactional
    public void updateClubMemberApplication(
            String memberEmail, UpdateClubMemberApplicationRequest request
    ) {
        ClubMemberApplication foundApplication = clubMemberApplicationRepository.findById(request.getApplicationId())
                .orElseThrow(
                        () -> new IllegalArgumentException("updateClubMemberApplication error: not found application")
                );

        ClubMember foundMember = clubMemberRepository.findByClub_IdAndMember_Email(foundApplication.getClub().getId(), memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("updateClubMemberApplication error: not found clubMember")
                );

        if(!foundMember.getIsMaster())
            throw new IllegalArgumentException("updateClubMemberApplication error: only master can change");

        foundApplication.setResult(request.getResult());
        clubMemberApplicationRepository.save(foundApplication);

        if(!request.getResult().equals("승인")) return;

        clubMemberRepository.save(ClubMember.builder()
                .club(foundApplication.getClub())
                .member(foundApplication.getApplicant())
                .build()
        );
    }

    public Page<GetClubMemberApplicationResponse> findAllClubMemberApplicationByClubId(
            String memberEmail, Long clubId, Pageable pageable
    ) {
        ClubMember foundMember = clubMemberRepository.findByClub_IdAndMember_Email(clubId, memberEmail).orElseThrow(
                () -> new IllegalArgumentException("findAllClubMemberApplicationByClubId error: not found clubMember")
        );

        if(!foundMember.getIsMaster())
            throw new IllegalArgumentException("findAllClubMemberApplicationByClubId error: only master can access");

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClubMemberApplication clubMemberApplication = QClubMemberApplication.clubMemberApplication;

        BooleanBuilder whereClause = new BooleanBuilder(clubMemberApplication.club.id.eq(clubId));

        List<GetClubMemberApplicationResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubMemberApplicationResponse.class,
                                clubMemberApplication.id, clubMemberApplication.club.id,
                                clubMemberApplication.club.clubName, clubMemberApplication.applicant.name,
                                clubMemberApplication.application, clubMemberApplication.result,
                                clubMemberApplication.createdAt
                        )
                )
                .from(clubMemberApplication)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                queryFactory.select(clubMemberApplication.count())
                        .from(clubMemberApplication)
                        .where(whereClause)::fetchCount
        );
    }

    public Page<GetClubMemberApplicationResponse> findAllClubMemberApplicationByMemberEmail(
            String memberEmail, Pageable pageable
    ) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClubMemberApplication clubMemberApplication = QClubMemberApplication.clubMemberApplication;

        BooleanBuilder whereClause = new BooleanBuilder(clubMemberApplication.applicant.email.eq(memberEmail));

        List<GetClubMemberApplicationResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubMemberApplicationResponse.class,
                                clubMemberApplication.id, clubMemberApplication.club.id,
                                clubMemberApplication.club.clubName, clubMemberApplication.applicant.name,
                                clubMemberApplication.application, clubMemberApplication.result,
                                clubMemberApplication.createdAt
                        )
                )
                .from(clubMemberApplication)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                queryFactory.select(clubMemberApplication.count())
                        .from(clubMemberApplication)
                        .where(whereClause)::fetchCount
        );
    }

    public void addClubPost(String memberEmail, AddClubPostRequest request) throws IOException {
        ClubMember found = clubMemberRepository.findByClub_IdAndMember_Email(request.getClubId(), memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("addClubPost error: not found member"));

        if(!found.getIsMaster()) throw new IllegalArgumentException("addClubPost error: only master can post");


        if(request.getPostType().equals("사진")) {
            request.setMultipart(bucketService.save(request.getMultipartFile()));
        }

        clubPostRepository.save(ClubPost.builder()
                .club(Club.builder().id(request.getClubId()).build())
                .postType(request.getPostType())
                .isPublic(request.getIsPublic())
                .title(request.getTitle())
                .content(request.getContent())
                .multipart(request.getMultipart())
                .build()
        );
    }

    public void updateClubPost(String memberEmail, UpdateClubPostRequest request) throws IOException {
        ClubPost foundPost = clubPostRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("updateClubPost error: not found post"));

        ClubMember foundMember = clubMemberRepository.findByClub_IdAndMember_Email(foundPost.getClub().getId(), memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("updateClubPost error: not found member"));

        if(!foundMember.getIsMaster()) throw new IllegalArgumentException("updateClubPost error: only master can change");

        if(request.getIsPublic() != null) foundPost.setIsPublic(request.getIsPublic());
        if(request.getTitle() != null) foundPost.setTitle(request.getTitle());
        if(request.getContent() != null) foundPost.setContent(request.getContent());
        if(foundPost.getPostType().equals("사진") && request.getMultipartFile() != null) {
            if(foundPost.getMultipart() != null) bucketService.delete(foundPost.getMultipart());
            foundPost.setMultipart(bucketService.save(request.getMultipartFile()));
        } else if (foundPost.getPostType().equals("영상") && request.getMultipart() != null) {
            foundPost.setMultipart(request.getMultipart());
        }

        clubPostRepository.save(foundPost);
    }

    public void deleteClubPost(String memberEmail, Long postId) {
        ClubPost foundPost = clubPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("deleteClubPost error: not found post"));

        ClubMember foundMember = clubMemberRepository.findByClub_IdAndMember_Email(foundPost.getClub().getId(), memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("deleteClubPost error: not found member"));

        if(!foundMember.getIsMaster()) throw new IllegalArgumentException("deleteClubPost error: only master can delete");

        if(foundPost.getPostType().equals("사진") && foundPost.getMultipart() != null) {
            bucketService.delete(foundPost.getMultipart());
        }

        clubPostRepository.delete(foundPost);
    }

    public Page<GetClubPostResponse> findAllPublicClubPostByClubIdAndPostType(
            Long clubId, String postType, Pageable pageable
    ) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClubPost clubPost = QClubPost.clubPost;

        BooleanBuilder whereClause = new BooleanBuilder(
                clubPost.club.id.eq(clubId)
                        .and(clubPost.isPublic.eq(true))
                        .and(clubPost.postType.eq(postType))
        );

        List<GetClubPostResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubPostResponse.class,
                                clubPost.id, clubPost.postType, clubPost.title,
                                clubPost.content, clubPost.multipart, clubPost.createdAt
                        )
                )
                .from(clubPost)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                queryFactory.select(clubPost.count())
                        .from(clubPost)
                        .where(whereClause)::fetchCount
        );
    }

    public Page<GetClubPostResponse> findAllClubPostByClubIdAndPostType(
            String memberEmail, Long clubId, String postType, Pageable pageable
    ) {
        ClubMember foundMember = clubMemberRepository.findByClub_IdAndMember_Email(clubId, memberEmail).orElseThrow(
                () -> new IllegalArgumentException("findAllPrivateClubPostByClubId error: only clubMember can access")
        );

        if(foundMember.getState().equals("탈퇴"))
            throw new IllegalArgumentException("findAllPrivateClubPostByClubId error: members who have withdrawn are not allowed to access");

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QClubPost clubPost = QClubPost.clubPost;

        BooleanBuilder whereClause = new BooleanBuilder(
                clubPost.club.id.eq(clubId)
                        .and(clubPost.postType.eq(postType))
        );

        List<GetClubPostResponse> results = queryFactory
                .select(
                        Projections.constructor(
                                GetClubPostResponse.class,
                                clubPost.id, clubPost.postType, clubPost.title,
                                clubPost.content, clubPost.multipart, clubPost.createdAt
                        )
                )
                .from(clubPost)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(results, pageable,
                queryFactory.select(clubPost.count())
                        .from(clubPost)
                        .where(whereClause)::fetchCount
        );
    }

    public GetClubPostResponse findClubPostByPostId(Long postId) {
        ClubPost found = clubPostRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("findClubPostByPostId error: not found post")
        );

        return GetClubPostResponse.of(found);
    }
}
