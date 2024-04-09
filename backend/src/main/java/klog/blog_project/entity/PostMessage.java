package klog.blog_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostMessage {
    SUCCESS_DETAIL_POST_VIEW("상세 글이 성공적으로 조회되었습니다."),
    NOT_EXIST_POST("존재하지 않는 포스트 입니다."),

    SUCCESS_MODIFY("글 수정이 성공적으로 완료되었습니다."),
    UNAUTHORIZED("권한이 없는 사용자 입니다."),
    FORBIDDEN("권한이 없는 사용자 입니다."),

    SUCCESS_DELETE("글 삭제가 성공적으로 완료되었습니다.");

    private final String message;
}
