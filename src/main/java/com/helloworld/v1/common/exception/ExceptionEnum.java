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
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E001"), // 400
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002"), // 500
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E003"), // 401
    VALIDATION_FAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "E0004"), // 500

    /**
     * 커스텀 에러
     */
    NO_SEARCH_RESOURCE(HttpStatus.NOT_FOUND, "C0001", "자원을 찾을 수 없습니다."), // 404
    NO_TARGET_NAME(HttpStatus.UNAUTHORIZED, "C0002", "대상의 이름이 없습니다."), // 401
    NOT_MATCH_NAME(HttpStatus.FORBIDDEN, "C0003", "권한이 없습니다. (허가되지 않은 사용자)"), // 403
    ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "C0004", "이미 처리된 요청입니다."), // 400

    /**
     * 회원가입 로그인 에러
     */
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "E001", "이미 가입되어 있는 이메일입니다."), // 400
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "E004", "멤버를 찾을 수 없습니다."), // 404
    MISSING_REQUIRED_ITEMS(HttpStatus.BAD_REQUEST, "E000", "필수 항목이 누락되었습니다."), //400
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "E009", "닉네임이 중복됩니다."),
    TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, "E003", "토근을 보유하고 있지 않습니다."),
    NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST, "E004", "이메일을 찾을 수 없습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "E005", "비밀번호가 틀렸습니다."); // 401


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
