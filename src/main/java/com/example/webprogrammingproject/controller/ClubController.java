package com.example.webprogrammingproject.controller;

import com.example.webprogrammingproject.dto.*;
import com.example.webprogrammingproject.service.ClubService;
import com.example.webprogrammingproject.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClubController {
    private final ClubService clubService;
    private final TokenService tokenService;

    @PostMapping("/club-application")
    public ResponseEntity<Void> addClubApplication(
            @RequestHeader(name="Authorization") String token, @RequestBody AddClubApplicationRequest request) {
        clubService.addClubApplication(tokenService.getMemberEmail(token), request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/club-application/result")
    public ResponseEntity<Void> reviewClubApplication(@RequestBody ReviewClubApplicationRequest request) {
        clubService.reviewClubApplication(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/club-application/member")
    public ResponseEntity<Page<GetClubApplicationResponse>> getClubApplicationsByMemberEmail(
            @RequestHeader(name="Authorization") String token, @RequestParam int pageNum, @RequestParam int size
    ) {
        return ResponseEntity.ok().body(clubService.findAllClubApplicationsByMemberEmail(
                tokenService.getMemberEmail(token), PageRequest.of(pageNum, size)
        ));
    }

    @GetMapping("/club-application/admin")
    public ResponseEntity<Page<GetClubApplicationResponse>> getClubApplications(
            @RequestParam int pageNum, @RequestParam int size
    ) {
        return ResponseEntity.ok().body(clubService.findAllClubApplications(PageRequest.of(pageNum, size)));
    }

    @GetMapping("/club")
    public ResponseEntity<Page<GetClubInfoResponse>> getClubList(
            @RequestParam int pageNum, @RequestParam int size
    ) {
        return ResponseEntity.ok().body(clubService.findAllClub(PageRequest.of(pageNum, size)));
    }

    @GetMapping("/club/joined")
    public ResponseEntity<List<GetClubInfoResponse>> getJoinedClubList(
            @RequestHeader(name="Authorization") String token
    ) {
        return ResponseEntity.ok().body(clubService.findAllJoinedClub(tokenService.getMemberEmail(token)));
    }

    @GetMapping("/club/managin")
    public ResponseEntity<List<GetClubInfoResponse>> getManagingClubList(
            @RequestHeader(name="Authorization") String token
    ) {
        return ResponseEntity.ok().body(clubService.findAllManagingClub(tokenService.getMemberEmail(token)));
    }

    @GetMapping("/club/info")
    public ResponseEntity<GetClubInfoResponse> getClubInfo(@RequestParam Long clubId) {
        return ResponseEntity.ok().body(clubService.getClubInfo(clubId));
    }

    @PatchMapping("/club/info") // form-data 형식 필수
    public ResponseEntity<Void> updateClubInfo(
            @RequestHeader(name="Authorization") String token, UpdateClubInfoRequest request
    ) throws IOException {
        clubService.updateClubInfo(
                tokenService.getMemberEmail(token),
                request
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/club/member")
    public ResponseEntity<Page<GetClubMemberResponse>> getClubMember(
            @RequestHeader(name="Authorization") String token,
            @RequestParam Long clubId, @RequestParam int pageNum, @RequestParam int size
    ) {
        return ResponseEntity.ok().body(clubService.findAllClubMember(
                tokenService.getMemberEmail(token), clubId, PageRequest.of(pageNum, size)
        ));
    }

    @PatchMapping("/club/member")
    public ResponseEntity<Void> updateClubMember(
            @RequestHeader(name="Authorization") String token, @RequestBody UpdateClubMemberRequest request
    ) {
        clubService.updateClubMember(
                tokenService.getMemberEmail(token), request
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/club/application") // form-data 형식 필수
    public ResponseEntity<Void> addClubMemberApplication(
            @RequestHeader(name="Authorization") String token, AddClubMemberApplicationRequest request
    ) throws IOException {
        clubService.addClubMemberApplication(
                tokenService.getMemberEmail(token), request
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/club/application")
    public ResponseEntity<Void> updateClubMemberApplication(
            @RequestHeader(name="Authorization") String token, @RequestBody UpdateClubMemberApplicationRequest request
    ) {
        clubService.updateClubMemberApplication(
                tokenService.getMemberEmail(token), request
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/club/application")
    public ResponseEntity<Page<GetClubMemberApplicationResponse>> findAllClubMemberApplicationByClubId(
            @RequestHeader(name="Authorization") String token,
            @RequestParam Long clubId, @RequestParam int pageNum, @RequestParam int size
    ) {
        return ResponseEntity.ok().body(clubService.findAllClubMemberApplicationByClubId(
                tokenService.getMemberEmail(token), clubId, PageRequest.of(pageNum, size))
        );
    }

    @GetMapping("/application")
    public ResponseEntity<Page<GetClubMemberApplicationResponse>> findAllClubMemberApplicationByMemberEmail(
            @RequestHeader(name="Authorization") String token, @RequestParam int pageNum, @RequestParam int size
    ) {
        return ResponseEntity.ok().body(clubService.findAllClubMemberApplicationByMemberEmail(
                tokenService.getMemberEmail(token), PageRequest.of(pageNum, size))
        );
    }

    @PostMapping("/club/post")
    public ResponseEntity<Void> addClubPost(
            @RequestHeader(name="Authorization") String token, AddClubPostRequest request
    ) throws IOException {
        clubService.addClubPost(
                tokenService.getMemberEmail(token), request
        );
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/club/post")
    public ResponseEntity<Void> updateClubPost(
            @RequestHeader(name="Authorization") String token, UpdateClubPostRequest request
    ) throws IOException {
        clubService.updateClubPost(
                tokenService.getMemberEmail(token), request
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/club/post")
    public ResponseEntity<Void> deleteClubPost(
            @RequestHeader(name="Authorization") String token, @RequestParam Long postId
    ) {
        clubService.deleteClubPost(
                tokenService.getMemberEmail(token), postId
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/club/post/public")
    public ResponseEntity<Page<GetClubPostResponse>> findAllPublicClubPostByPostType(
            @RequestParam(required = false) Long clubId, @RequestParam String postType,
            @RequestParam int pageNum, @RequestParam int size
    ) {
        return ResponseEntity.ok().body(
                clubService.findAllPublicClubPostByClubIdAndPostType(
                        clubId, postType, PageRequest.of(pageNum, size)
                )
        );
    }

    @GetMapping("/club/post")
    public ResponseEntity<Page<GetClubPostResponse>> findAllClubPostByPostType(
            @RequestHeader(name="Authorization") String token,
            @RequestParam Long clubId, @RequestParam String postType,
            @RequestParam int pageNum, @RequestParam int size
    ) {
        return ResponseEntity.ok().body(
                clubService.findAllClubPostByClubIdAndPostType(
                        tokenService.getMemberEmail(token), clubId, postType, PageRequest.of(pageNum, size)
                )
        );
    }

    @GetMapping("/club/post/{postId}")
    public ResponseEntity<GetClubPostResponse> findClubPostByPostId(
            @PathVariable("postId") Long id
    ) {
        return ResponseEntity.ok().body(
                clubService.findClubPostByPostId(id)
        );
    }
}
