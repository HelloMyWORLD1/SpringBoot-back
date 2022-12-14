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
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "COMM001"), // 400
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMM002"), // 500
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "COMM003"), // 401
    VALIDATION_FAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "COMM0004"), // 500

    /**
     * 커스텀 에러 (아래 이외)
     */
    NO_SEARCH_RESOURCE(HttpStatus.NOT_FOUND, "C0001", "자원을 찾을 수 없습니다."), // 404
    NO_TARGET_NAME(HttpStatus.UNAUTHORIZED, "C0002", "대상의 이름이 없습니다."), // 401
    NOT_MATCH_NAME(HttpStatus.FORBIDDEN, "C0003", "권한이 없습니다. (허가되지 않은 사용자)"), // 403
    ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "C0004", "이미 처리된 요청입니다."), // 400
    NOT_FOUND_FOLLOW(HttpStatus.NOT_FOUND, "C0005", "팔로우 기록이 없습니다."), // 404
    NOT_FOUND_USER_BY_TOKEN(HttpStatus.NOT_FOUND, "C0006", "정상적인 TOKEN USER가 아닙니다."), // 404
    NOT_FOUND_BLOG(HttpStatus.NOT_FOUND, "C0007", "블로그를 찾을 수 없습니다."), // 404
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "C0008", "댓글을 찾을 수 없습니다."), // 404

    /**
     * 공통 에러
     */
    MISSING_REQUIRED_ITEMS(HttpStatus.BAD_REQUEST, "E000", "필수 항목이 누락되었습니다."), //400
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "E001", "이미 가입되어 있는 이메일입니다."), // 400
    SERVER_ERROR(HttpStatus.BAD_REQUEST, "E002", "Server error"), // 400
    TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, "E003", "토근을 보유하고 있지 않습니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "E004", "멤버를 찾을 수 없습니다."), // 404
    NOT_FOUND_EMAIL(HttpStatus.BAD_REQUEST, "E004", "이메일을 찾을 수 없습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "E005", "비밀번호가 틀렸습니다."), // 401
    NOT_FOUND_PAGE(HttpStatus.NOT_FOUND, "E006", "해당 페이지를 찾을 수 없습니다."), // 404
    NOT_FOUND_NICKNAME(HttpStatus.NOT_FOUND, "E007", "Nickname을 찾을 수 없습니다."), // 404
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "E009", "닉네임이 중복됩니다."),
    NOT_MATCH_EXT(HttpStatus.BAD_REQUEST, "E010", "확장자 명은 jpg, jpeg, img 이어야 합니다");


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
