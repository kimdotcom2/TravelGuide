package com.backend.TravelGuide.planner.error.exception;

import com.backend.TravelGuide.member.error.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoAuthorityException extends CustomException {
    @Override
    public int getStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }

    @Override
    public String getMessage() {
        return "권한이 없습니다.";
    }
}
