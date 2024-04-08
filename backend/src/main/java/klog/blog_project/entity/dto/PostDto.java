package klog.blog_project.entity.dto;

import static klog.blog_project.entity.UserMessage.SUCCESS_WRITE;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
}