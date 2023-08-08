package com.backend.TravelGuide.member.service;

import com.backend.TravelGuide.member.domain.Member;
import com.backend.TravelGuide.member.domain.MemberRequestDTO;

public interface LoginService {
    Member saveKakaoMemberIfNotExists(MemberRequestDTO.KakaoLoginDTO loginDTO)throws Exception;

    String kakaoLogin(MemberRequestDTO.KakaoLoginDTO loginDTO) throws Exception;

    String normalLogin(MemberRequestDTO.MemberLoginDTO loginDTO) throws Exception;

    void authenticate(String username, String pwd) throws Exception;
}
