package com.backend.TravelGuide.member.error.exception;

public abstract class CustomException extends RuntimeException{
    abstract public int getStatusCode();
    abstract public String getMessage();
}
