package com.helloworld.v1.common.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {
    /**
     * 공통 에러 (No massage)
     */
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"), // 400
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"), // 401
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"), // 500
    VALIDATION_FAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "E0004"), // 500

    /**
     * 커스텀 에러
     */
    NO_SEARCH_RESOURCE(HttpStatus.NOT_FOUND, "C0001", "ID를 찾을 수 없습니다."), // 404
    NO_TARGET_NAME(HttpStatus.UNAUTHORIZED, "C0002", "대상의 이름이 없습니다."), // 401
    NOT_MATCH_NAME(HttpStatus.FORBIDDEN, "C0003", "권한이 없습니다. (허가되지 않은 사용자)"), // 403
    ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "C0004", "이미 처리된 요청입니다."); // 400


    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
