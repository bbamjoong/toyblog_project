package klog.blog_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignupMessage {
    EXIST_ID("이미 존재하는 아이디 입니다."),
    EXIST_NICKNAME("이미 존재하는 닉네임 입니다."),
    SUCCESS_SIGNUP("회원가입이 성공적으로 완료되었습니다."),
    INTERNAL_SERVER_ERROR("서버 에러입니다.");

    private final String message;
}
