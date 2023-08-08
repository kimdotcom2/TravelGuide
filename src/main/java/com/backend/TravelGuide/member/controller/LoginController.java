package com.backend.TravelGuide.member.controller;

import com.backend.TravelGuide.member.domain.MemberRequestDTO;
import com.backend.TravelGuide.member.service.impl.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginServiceImpl loginService;

    @PostMapping("/kakao")
    public ResponseEntity<String> kakaoLoginJs(@RequestBody MemberRequestDTO.KakaoLoginDTO loginDto) throws Exception {
        log.info(">> 카카오 회원 로그인");

        String token = loginService.kakaoLogin(loginDto);

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/normal")
    public ResponseEntity<String> login(@RequestBody MemberRequestDTO.MemberLoginDTO loginDTO) {
        log.info(">> 일반 회원 로그인");

        String token = loginService.normalLogin(loginDTO);

        return ResponseEntity.ok().body(token);
    }
}
