package klog.blog_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostMessage {
    SUCCESS_DETAIL_POST_VIEW("상세 글이 성공적으로 조회되었습니다."),
    NOT_EXIST_POST("존재하지 않는 포스트 입니다.");

    private final String message;
}
