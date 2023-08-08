package com.backend.TravelGuide.member.error.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends CustomException{
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "이미 존재하는 아이디입니다!";
    }
}
