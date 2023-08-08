package com.backend.TravelGuide.member.error.exception;

import org.springframework.http.HttpStatus;

public class NoJwtTokenException extends CustomException{
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "Jwt토큰을 전달받지 못했습니다!";
    }
}
