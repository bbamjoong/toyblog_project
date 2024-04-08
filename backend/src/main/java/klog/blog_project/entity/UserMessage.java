package klog.blog_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserMessage {
    EXIST_ID("이미 존재하는 아이디 입니다."),
    EXIST_NICKNAME("이미 존재하는 닉네임 입니다."),
    SUCCESS_SIGNUP("회원가입이 성공적으로 완료되었습니다."),
    INTERNAL_SERVER_ERROR("서버 에러입니다."),

    NOT_EXIST_USER("존재하지 않는 사용자 입니다."),
    SUCCESS_LOGIN("로그인이 성공적으로 완료되었습니다."),

    SUCCESS_WRITE("글 작성이 성공적으로 완료되었습니다."),
    UNAUTHORIZED("권한이 없는 사용자 입니다.");

    private final String message;
}
