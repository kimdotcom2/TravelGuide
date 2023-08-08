package com.backend.TravelGuide.member.error.exception;

import org.springframework.http.HttpStatus;

public class UserNotMatchException extends CustomException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "로그인한 사용자와 불일치합니다";
    }
}
