package klog.blog_project.entity.dto;

import static klog.blog_project.entity.PostMessage.SUCCESS_DETAIL_POST_VIEW;
import static klog.blog_project.entity.PostMessage.SUCCESS_MODIFY;

import static klog.blog_project.entity.UserMessage.SUCCESS_WRITE;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import klog.blog_project.entity.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class WriteRequest {
        @NotNull(message = "제목은 null일 수 없습니다.")
        @NotBlank(message = "제목을 입력해 주세요.")
        private String title;

        @NotNull(message = "내용은 null일 수 없습니다.")
        @NotBlank(message = "내용을 입력해 주세요.")
        private String content;

        private Long userId;

        public void addUserId(Long userId) {
            this.userId = userId;
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WriteResponse {
        private Long userId;
        private String message;

        public static PostDto.WriteResponse success(Long userId) {
            return PostDto.WriteResponse.builder()
                    .userId(userId)
                    .message(SUCCESS_WRITE.getMessage())
                    .build();
        }

        // 실패한 경우를 위한 빌더 메서드
        public static PostDto.WriteResponse failure(String errorMessage) {
            return PostDto.WriteResponse.builder()
                    .message(errorMessage)
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(Include.NON_NULL)
    public static class DetailPostResponse {
        private String nickname;
        private String title;
        private String content;
        private long likeCount;
        private long viewCount;
        private LocalDateTime createdDate;
        private String message;

        public static PostDto.DetailPostResponse success(Post post) {
            return DetailPostResponse.builder()
                    .nickname(post.getUser().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .likeCount(post.getLikeCount())
                    .viewCount(post.getViewCount())
                    .createdDate(post.getCreatedDate())
                    .message(SUCCESS_DETAIL_POST_VIEW.getMessage())
                    .build();
        }

        public static PostDto.DetailPostResponse failure(String errorMessage) {
            return DetailPostResponse.builder()
                    .message(errorMessage)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ModifyPostRequest {
        @NotNull(message = "제목은 null일 수 없습니다.")
        @NotBlank(message = "제목을 입력해 주세요.")
        private String title;

        @NotNull(message = "내용은 null일 수 없습니다.")
        @NotBlank(message = "내용을 입력해 주세요.")
        private String content;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ModifyPostResponse {
        private Long userId;
        private String message;

        public static PostDto.ModifyPostResponse success(Long userId) {
            return PostDto.ModifyPostResponse.builder()
                    .userId(userId)
                    .message(SUCCESS_MODIFY.getMessage())
                    .build();
        }

        // 실패한 경우를 위한 빌더 메서드
        public static PostDto.ModifyPostResponse failure(String errorMessage) {
            return PostDto.ModifyPostResponse.builder()
                    .message(errorMessage)
                    .build();
        }
    }
}