package klog.blog_project.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {

    @NotEmpty(message = "ID는 필수 입니다")
    private String id;

    @NotEmpty(message = "Password는 필수 입니다")
    private String password;

    @NotEmpty(message = "Nickname은 필수 입니다")
    private String nickname;
}
