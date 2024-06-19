package com.example.webprogrammingproject.controller;

import com.example.webprogrammingproject.config.jwt.TokenProvider;
import com.example.webprogrammingproject.domain.Member;
import com.example.webprogrammingproject.dto.AddMemberRequest;
import com.example.webprogrammingproject.repository.MemberRepository;
import com.example.webprogrammingproject.service.KakaoService;
import com.example.webprogrammingproject.service.MemberService;
import com.example.webprogrammingproject.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private KakaoService kakaoService;
    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private RefreshTokenService refreshTokenService;

    @DisplayName("회원 가입")
    @Test
    void signup() throws Exception {
        String UUID = java.util.UUID.randomUUID().toString();
        AddMemberRequest requestDto = AddMemberRequest.builder()
                .email(UUID + "@test.com")
                .name(UUID)
                .build();
        String request = objectMapper.writeValueAsString(requestDto);
        given(memberService.save(any()))
                .willReturn(Member.builder().email(requestDto.getEmail()).name(requestDto.getName()).build());

        mockMvc.perform(MockMvcRequestBuilders.post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(
                        MockMvcRestDocumentation.document("member/signup",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()))
                )
                .andExpect(status().isOk())
                .andReturn();

        Member found = Mockito.mock(Member.class);
        Mockito.when(found.getName()).thenReturn(UUID);
        Mockito.when(memberService.findById(UUID + "@test.com")).thenReturn(found);

        assertThat(found.getName()).isEqualTo(UUID);
    }
}