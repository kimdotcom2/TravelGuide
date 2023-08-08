package com.backend.TravelGuide.member.error.exception;

import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends CustomException{
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "비밀번호가 일치하지 않습니다!";
    }
}
