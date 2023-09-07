package com.backend.TravelGuide.planner.error.exception;

import com.backend.TravelGuide.member.error.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends CustomException {
    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return "잘못된 요청입니다.";
    }
}
