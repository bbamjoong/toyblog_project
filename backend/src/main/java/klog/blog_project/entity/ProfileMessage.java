package klog.blog_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfileMessage {
    SUCCESS_PROFILE_VIEW("프로필이 성공적으로 조회되었습니다."),
    SUCCESS_PROFILE_MODIFY("프로필 수정이 성공적으로 완료되었습니다."),
    SUCCESS_PROFILES_VIEW("전체 프로필 조회가 성공적으로 완료되었습니다.");

    private final String message;
}
