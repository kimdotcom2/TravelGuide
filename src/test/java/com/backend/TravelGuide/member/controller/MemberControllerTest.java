package com.backend.TravelGuide.member.controller;

import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.domain.MemberResponseDTO;
import com.backend.TravelGuide.member.service.impl.MemberServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Check;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberService;

    @Mock
    private Principal principal;

    @InjectMocks
    private MemberController memberController;

    private static final String jwt =
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY5MTMwNjMwNywiZXhwIjoxNjkxMzA5OTA3fQ.VnY_R6q1KxdGHAq3spewbTZRbduhB16QDJf4cOGwGKk_YGQ9VME2pvqmIMbusi_8_HPWpIl0-_ZP8Q2Di9lLew";

    @Test
    @DisplayName("회원가입 컨트롤러 테스트")
    void testJoin() throws Exception {
        // given
        MemberRequestDTO.MemberJoinDTO joinDTO = MemberRequestDTO.MemberJoinDTO
                .builder()
                .email("user@email.com")
                .name("user")
                .password("11111111")
                .answers(Arrays.asList("answer1", "answer2", "answer3"))
                .build();

        // when
        doNothing().when(memberService).join(any());

        // then
        mockMvc.perform(post("/member/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(joinDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 찾기 대답 확인 컨트롤러 테스트")
    void testCheckAnswer() throws Exception {
        // given
        MemberRequestDTO.CheckAnswerDTO checkAnswerDTO =
                MemberRequestDTO.CheckAnswerDTO
                        .builder()
                        .email("user@email.com")
                        .name("user")
                        .questionId(1)
                        .answer("answer1")
                        .build();

        // when
        when(memberService.checkAnswer(any())).thenReturn(true);

        // then
        mockMvc.perform(get("/member/checkAnswer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(checkAnswerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("새 비밀번호 세팅 컨트롤러 테스트")
    void testNewPassword() throws Exception {
        // given
        MemberRequestDTO.NewPasswordDTO newPasswordDTO =
                MemberRequestDTO.NewPasswordDTO
                        .builder()
                        .email("user@email.com")
                        .newPassword("22222222")
                        .build();

        // when
        doNothing().when(memberService).setNewPassword(newPasswordDTO);

        // then
        mockMvc.perform(post("/member/newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newPasswordDTO)))
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("회원정보조회 컨트롤러 테스트")
    void testInfo() throws Exception {
        // given
        MemberResponseDTO.MemberInformationDTO infoDTO = MemberResponseDTO.MemberInformationDTO
                        .builder()
                        .email("user@email.com")
                        .name("user")
                        .build();

        given(principal.getName()).willReturn("user@email.com");

        // when
        when(memberService.searchInfo(anyString())).thenReturn(infoDTO);

        // then
        mockMvc.perform(get("/member/info")
                .header("Authorization", jwt)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@email.com"))
                .andExpect(jsonPath("$.name").value("user"));
    }

    @Test
    @DisplayName("회원정보수정 컨트롤러 테스트")
    void testUpdate() throws Exception {
        // given
        MemberRequestDTO.UpdateInfoDTO updateInfoDTO = MemberRequestDTO.UpdateInfoDTO.builder()
                .email("user@email.com")
                .name("modified user")
                .build();

        // when
        when(principal.getName()).thenReturn("user@email.com");
        doNothing().when(memberService).updateInfo(any());

        // then
        mockMvc.perform(put("/member/update")
                .header("Authorization", jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateInfoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 초기화 컨트롤러 테스트")
    void testPassword() throws Exception {
        // given
        MemberRequestDTO.ResetPwdDTO resetPwdDTO = MemberRequestDTO.ResetPwdDTO
                .builder()
                .email("user@email.com")
                .oldPassword("11111111")
                .newPassword("22222222")
                .build();

        // when
        doNothing().when(memberService).resetPassword(resetPwdDTO);

        // then
        mockMvc.perform(put("/member/password_reset")
                .header("Authorization", jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetPwdDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원탈퇴 컨트롤러 테스트")
    void testWithdraw() throws Exception {
        // given
        MemberRequestDTO.WithdrawDTO withdrawDTO = MemberRequestDTO.WithdrawDTO
                .builder()
                .email("user@email.com")
                .password("11111111")
                .build();

        // when
        when(principal.getName()).thenReturn("user@email.com");
        doNothing().when(memberService).withdraw(any());

        // then
        mockMvc.perform(delete("/member/withdraw")
                .header("Authorization", jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(withdrawDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("이메일/닉네인 중복검사 컨트롤러 테스트")
    void testDuplication() throws Exception {
        // given
        MemberRequestDTO.CheckDuplicationDTO duplicationDTO =
                MemberRequestDTO.CheckDuplicationDTO
                        .builder()
                        .email("user@email.com")
                        .build();

        // when
        when(memberService.isDuplicated(any())).thenReturn(true);

        // then
        mockMvc.perform(get("/member/duplication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(duplicationDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}