package klog.blog_project.entity.dto;

import static klog.blog_project.entity.UserMessage.SUCCESS_LOGIN;
import static klog.blog_project.entity.UserMessage.SUCCESS_SIGNUP;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SignupRequest {
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        private String id;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용해 주세요.")
        private String password;
        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리로 입려해 주세요.")
        private String nickname;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignupResponse {
        private String id;
        private String nickname;
        private String message;

        public static SignupResponse success(SignupRequest dto) {
            return SignupResponse.builder()
                    .id(dto.getId())
                    .nickname(dto.getNickname())
                    .message(SUCCESS_SIGNUP.getMessage())
                    .build();
        }

        // 실패한 경우를 위한 빌더 메서드
        public static SignupResponse failure(String errorMessage) {
            return SignupResponse.builder()
                    .message(errorMessage)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LoginRequest {
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @NotNull(message = "아이디는 null일 수 없습니다.")
        private String id;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @NotNull(message = "비밀번호는 null일 수 없습니다.")
        private String password;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginResponse {
        private String id;
        private String password;
        private String message;

        public static LoginResponse success(LoginRequest dto) {
            return LoginResponse.builder()
                    .id(dto.getId())
                    .password(dto.getPassword())
                    .message(SUCCESS_LOGIN.getMessage())
                    .build();
        }

        // 실패한 경우를 위한 빌더 메서드
        public static LoginResponse failure(String errorMessage) {
            return LoginResponse.builder()
                    .message(errorMessage)
                    .build();
        }
    }
}
