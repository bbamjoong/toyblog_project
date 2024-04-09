package klog.blog_project.entity.dto;

import static klog.blog_project.entity.ProfileMessage.SUCCESS_PROFILE_VIEW;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProfileDto {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProfileResponse {
        private String profileImage;
        private String introductionTitle;
        private String introductionContent;
        private String message;

        public static ProfileDto.ProfileResponse success(SingleProfileDto dto) {
            return ProfileResponse.builder()
                    .profileImage(dto.getProfileImage())
                    .introductionTitle(dto.getIntroductionTitle())
                    .introductionContent(dto.getIntroductionContent())
                    .message(SUCCESS_PROFILE_VIEW.getMessage())
                    .build();
        }

        // 실패한 경우를 위한 빌더 메서드
        public static ProfileDto.ProfileResponse failure(String errorMessage) {
            return ProfileDto.ProfileResponse.builder()
                    .message(errorMessage)
                    .build();
        }
    }
}
