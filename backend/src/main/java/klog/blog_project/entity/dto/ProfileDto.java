package klog.blog_project.entity.dto;

import static klog.blog_project.entity.ProfileMessage.SUCCESS_PROFILES_VIEW;
import static klog.blog_project.entity.ProfileMessage.SUCCESS_PROFILE_MODIFY;
import static klog.blog_project.entity.ProfileMessage.SUCCESS_PROFILE_VIEW;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ModifyProfileRequest {
        @NotNull(message = "URL은 null일 수 없습니다.")
        @NotBlank(message = "URL을 입력해 주세요.")
        private String profileImage;

        @NotNull(message = "소개 글 제목은 null일 수 없습니다.")
        @NotBlank(message = "제목을 입력해 주세요.")
        private String introductionTitle;

        @NotNull(message = "소개 글 내용은 null일 수 없습니다.")
        @NotBlank(message = "내용을 입력해 주세요.")
        private String introductionContent;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ModifyProfileResponse {
        private Long userId;
        private String message;

        public static ProfileDto.ModifyProfileResponse success(Long userId) {
            return ProfileDto.ModifyProfileResponse.builder()
                    .userId(userId)
                    .message(SUCCESS_PROFILE_MODIFY.getMessage())
                    .build();
        }

        // 실패한 경우를 위한 빌더 메서드
        public static ProfileDto.ModifyProfileResponse failure(String errorMessage) {
            return ProfileDto.ModifyProfileResponse.builder()
                    .message(errorMessage)
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SimpleProfilesResponse {
        private List<SingleSimpleProfileDto> profiles;
        private String message;

        public static SimpleProfilesResponse success(List<SingleSimpleProfileDto> profiles) {
            return SimpleProfilesResponse.builder()
                    .message(SUCCESS_PROFILES_VIEW.getMessage())
                    .profiles(profiles)
                    .build();
        }

        public static SimpleProfilesResponse failure(String errorMessage) {
            return SimpleProfilesResponse.builder()
                    .message(errorMessage)
                    .build();
        }
    }
}
