package klog.blog_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfileMessage {
    SUCCESS_PROFILE_VIEW("프로필이 성공적으로 조회되었습니다.");

    private final String message;
}
