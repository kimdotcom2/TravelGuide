package com.backend.TravelGuide.member.service;

import com.backend.TravelGuide.member.domain.Member;
import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.domain.MemberResponseDTO;
import com.backend.TravelGuide.member.domain.Role;
import com.backend.TravelGuide.member.error.exception.PasswordNotMatchException;
import com.backend.TravelGuide.member.error.exception.UserAlreadyExistsException;
import com.backend.TravelGuide.member.repository.MemberRepository;
import com.backend.TravelGuide.member.service.impl.MemberServiceImpl;
import org.hibernate.annotations.Check;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    private final String email = "user@email.com";
    private final String name = "user";
    private final String nickname = "user";

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Principal principal;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member createMember() {
        Map<Integer, String> answerMap = new HashMap<>();
        answerMap.put(1, "answer1");
        answerMap.put(2, "answer2");

        return Member.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .password("11111111")
                .role(Role.USER)
                .answers(answerMap)
                .build();
    }

    @Test
    @DisplayName("회원가입 테스트")
    void testJoin() {
        // given
        given(memberRepository.existsByEmail(anyString()))
                .willReturn(false);

        MemberRequestDTO.MemberJoinDTO joinDTO =
                MemberRequestDTO.MemberJoinDTO.builder()
                        .email("user@email.com")
                        .password("1111")
                        .name("user")
                        .nickname("user")
                        .answers(Arrays.asList("answer1", "answer2"))
                        .build();

        // when
        memberService.join(joinDTO);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());

        Member savedMember = memberCaptor.getValue();
        assertEquals(savedMember.getEmail(), joinDTO.getEmail());
        assertEquals(savedMember.getName(), joinDTO.getName());
        assertEquals(savedMember.getNickname(), joinDTO.getNickname());
        assertEquals(savedMember.getAnswers().size(), joinDTO.getAnswers().size());
    }

    @Test
    @DisplayName("회원가입시 이미 존재하는 아이디일경우 예외 발생 테스트")
    void testJoin_UserAlreadyExistsException() {
        // given
        MemberRequestDTO.MemberJoinDTO joinDTO = MemberRequestDTO.MemberJoinDTO.builder()
                .email("user@email.com")
                .password("1111")
                .name("user")
                .build();

        // when
        when(memberRepository.existsByEmail(anyString()))
                .thenReturn(true);

        // then
        assertThrows(UserAlreadyExistsException.class, () -> memberService.join(joinDTO));
    }

    @Test
    @DisplayName("회원정보조회 테스트")
    void testSearchInfo() {
        // given
        Member member = createMember();

        given(principal.getName()).willReturn("user@email.com");

        given(memberRepository.findByEmail(anyString()))
                .willReturn(Optional.of(member));

        // when
        MemberResponseDTO.MemberInformationDTO informationDTO =
                memberService.searchInfo("user@email.com");

        // then
        assertEquals(informationDTO.getEmail(), "user@email.com");
        assertEquals(informationDTO.getName(), "user");
        assertEquals(informationDTO.getNickname(), "user");
    }

    @Test
    @DisplayName("회원정보조회시 로그인한 사용자의 이메일과 일치하지 않을 경우 예외 발생 테스트")
    void testSearchInfo_UserNotMatchException() {
        // given
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // when
        // then
        assertThrows(UsernameNotFoundException.class,
                () -> memberService.searchInfo("user@email.com"));
    }

    @Test
    @DisplayName("회원정보조회시 해당 이메일의 회원이 존재하지 않을경우 예외 발생 테스트")
    void testSearchInfo_UsernameNotFoundException() {
        // given
        given(principal.getName()).willReturn("user@email.com");

        given(memberRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        // when
        // then
        assertThrows(UsernameNotFoundException.class,
                () -> memberService.searchInfo("user@email.com"));
    }

    @Test
    @DisplayName("회원정보수정 테스트")
    void testUpdateInfo() {
        // given
        Member member = createMember();

        MemberRequestDTO.UpdateInfoDTO updateInfoDTO = MemberRequestDTO.UpdateInfoDTO
                .builder()
                .email(email)
                .name("modified_user")
                .nickname("modified_user")
                .build();

        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        // when
        memberService.updateInfo(updateInfoDTO);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());

        Member savedMember = memberCaptor.getValue();
        assertEquals(savedMember.getNickname(), updateInfoDTO.getNickname());
    }

    @Test
    @DisplayName("회원정보수정시 해당 이메일의 회원이 존재하지 않을 경우 예외 발생 테스트")
    void testUpdateInfo_UsernameNotFoundException() {
        // given
        MemberRequestDTO.UpdateInfoDTO updateInfoDTO =
                MemberRequestDTO.UpdateInfoDTO.builder()
                        .email("user@email.com")
                        .name("user")
                        .build();

        // when
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(UsernameNotFoundException.class,
                () -> memberService.updateInfo(updateInfoDTO));
    }

    @Test
    @DisplayName("비밀번호 초기화 테스트")
    void testResetPassword() {
        // given
        Member member = createMember();

        MemberRequestDTO.ResetPwdDTO resetPwdDTO = MemberRequestDTO.ResetPwdDTO.builder()
                .email(email)
                .oldPassword("11111111")
                .newPassword("22222222")
                .build();

        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        memberService.resetPassword(resetPwdDTO);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("비밀번호 초기화시 해당 이메일의 회원이 존재하지 않을경우 예외 발생 테스트")
    void testResetPassword_UsernameNotFoundException() {
        // given
        MemberRequestDTO.ResetPwdDTO resetPwdDTO = MemberRequestDTO.ResetPwdDTO.builder()
                .email("user@email.com")
                .oldPassword("1111")
                .newPassword("2222")
                .build();

        // when
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(UsernameNotFoundException.class,
                () -> memberService.resetPassword(resetPwdDTO));
    }

    @Test
    @DisplayName("비밀번호 초기화시 비밀번호가 일치하지 않을 경우 예외 발생 테스트")
    void testResetPassword_PasswordNotMatchException() {
        // given
        MemberRequestDTO.ResetPwdDTO resetPwdDTO = MemberRequestDTO.ResetPwdDTO.builder()
                .email("user@email.com")
                .oldPassword("1111")
                .newPassword("2222")
                .build();

        Member member = createMember();

        // when
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(member));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        // then
        assertThrows(PasswordNotMatchException.class,
                () -> memberService.resetPassword(resetPwdDTO));
    }

    @Test
    @DisplayName("비밀번호 찾기 질문에 대한 대답 검사")
    void testCheckAnswer() {
        // given
        Member member = createMember();

        MemberRequestDTO.CheckAnswerDTO answerDTO = MemberRequestDTO.CheckAnswerDTO
                .builder()
                .email(email)
                .name(name)
                .questionId(2)
                .answer("answer2")
                .build();

        given(memberRepository.findByEmailAndName(email, name))
                .willReturn(Optional.of(member));

        // when
        boolean result = memberService.checkAnswer(answerDTO);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("비밀번호 찾기 질문에 대한 대답 검사시 회원이 존재하지 않을 경우 예외 발생 테스트")
    void testCheckAnswer_UsernameNotFoundException() {
        // given
        MemberRequestDTO.CheckAnswerDTO answerDTO = MemberRequestDTO.CheckAnswerDTO
                .builder()
                .email(email)
                .name(name)
                .questionId(2)
                .answer("answer2")
                .build();

        // when
        when(memberRepository.findByEmailAndName(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(UsernameNotFoundException.class, () -> memberService.checkAnswer(answerDTO));
    }

    @Test
    @DisplayName("비밀번호 찾기 질문을 통과한후 새로운 비밀번호 등록 테스트")
    void testSetNewPassword() {
        // given
        Member member = createMember();

        MemberRequestDTO.NewPasswordDTO newPasswordDTO = MemberRequestDTO.NewPasswordDTO
                .builder()
                .email(email)
                .newPassword("22222222")
                .build();

        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        // when
        memberService.setNewPassword(newPasswordDTO);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("비밀번호 찾기 질문을 통과한후 새로운 비밀번호 등록시 회원이 존재하지 않을 경우 예외 발생 테스트")
    void testSetNewPassword_UsernameNotFoundException() {
        // given
        MemberRequestDTO.NewPasswordDTO newPasswordDTO = MemberRequestDTO.NewPasswordDTO
                .builder()
                .email(email)
                .newPassword("22222222")
                .build();

        // when
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // then
        assertThrows(UsernameNotFoundException.class,
                () -> memberService.setNewPassword(newPasswordDTO));
    }

    @Test
    @DisplayName("회원탈퇴 테스트")
    void testWithdraw() {
        // given
        Member member = Member.builder()
                .email(email)
                .password("$2a$10$qhSeIwUoC2aipwkv7tHDm.AfmWw/L0ncXSHewdT7rS3d4GHnRpAcO")
                .build();

        MemberRequestDTO.WithdrawDTO withdrawDTO = MemberRequestDTO.WithdrawDTO
                        .builder()
                        .email(email)
                        .password("1111")
                        .build();

        given(memberRepository.findByEmail(anyString())).willReturn(Optional.of(member));

        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        memberService.withdraw(withdrawDTO);

        // then
        verify(memberRepository, times(1)).delete(member);
    }

    @Test
    @DisplayName("회원탈퇴시 존재하지 않는 회원일경우 예외 발생 테스트")
    void testWithdraw_UsernameNotFoundException() {
        // given
        MemberRequestDTO.WithdrawDTO withdrawDTO = MemberRequestDTO.WithdrawDTO
                .builder()
                .email("user@email.com")
                .password("1111")
                .build();

        // when
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        // then
        assertThrows(UsernameNotFoundException.class,
                () -> memberService.withdraw(withdrawDTO));
    }

    @Test
    @DisplayName("회원탈퇴시 비밀번호가 일치하지 않을 경우 예외 발생 테스트")
    void testWithdraw_PasswordNotMatchException() {
        // given
        Member member = createMember();

        MemberRequestDTO.WithdrawDTO withdrawDTO = MemberRequestDTO.WithdrawDTO
                .builder()
                .email("user@email.com")
                .password("2222")
                .build();

        // when
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(member));

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        // then
        assertThrows(PasswordNotMatchException.class,
                () -> memberService.withdraw(withdrawDTO));
    }

    @Test
    @DisplayName("이메일 중복 검사 테스트")
    void testIsEmailDuplicated() {
        // given
        MemberRequestDTO.CheckDuplicationDTO duplicationDTO = MemberRequestDTO.CheckDuplicationDTO
                .builder()
                .email(email)
                .build();

        given(memberRepository.existsByEmail(anyString())).willReturn(true);

        // when
        boolean result = memberService.isDuplicated(duplicationDTO);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("닉네임 중복 검사 테스트")
    void testIsNicknameDuplicated() {
        // given
        MemberRequestDTO.CheckDuplicationDTO duplicationDTO = MemberRequestDTO.CheckDuplicationDTO
                .builder()
                .nickname(nickname)
                .build();

        given(memberRepository.existsByNickname(anyString())).willReturn(true);

        // when
        boolean result = memberService.isDuplicated(duplicationDTO);

        // then
        assertFalse(result);
    }
}