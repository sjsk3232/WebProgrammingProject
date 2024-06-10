package com.example.webprogrammingproject.controller;

import com.example.webprogrammingproject.dto.AddMemberRequest;
import com.example.webprogrammingproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<Void> signup(@RequestBody AddMemberRequest request) {
        memberService.save(request);
        return ResponseEntity.ok().build();
    }
}
