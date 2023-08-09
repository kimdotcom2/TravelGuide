package com.backend.TravelGuide.member.service;

import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.domain.MemberResponseDTO;

public interface MemberService {
    // 일반회원가입
    void join(MemberRequestDTO.MemberJoinDTO joinDTO);

    // 회원정보조회
    MemberResponseDTO.MemberInformationDTO searchInfo(String email);

    // 회원정보수정
    void updateInfo(MemberRequestDTO.UpdateInfoDTO updateInfoDTO);

    // 비밀번호 초기화
    void resetPassword(MemberRequestDTO.ResetPwdDTO resetPwdDTO);

    // 비밀번호 찾기 질문의 답변
    Boolean checkAnswer(MemberRequestDTO.CheckAnswerDTO checkAnswerDTO);

    // 비밀번호 찾기 후 새 비밀번호 세팅
    void setNewPassword(MemberRequestDTO.NewPasswordDTO newPasswordDTO);

    // 회원탈퇴
    void withdraw(MemberRequestDTO.WithdrawDTO withdrawDTO);

    // 이메일/닉네임 중복 확인
    boolean isDuplicated(MemberRequestDTO.CheckDuplicationDTO duplicationDTO);

    // TODO 내 플래너 목록

    // TODO 내 게시글 목록

    // TODO 내 댓글 목록
}
