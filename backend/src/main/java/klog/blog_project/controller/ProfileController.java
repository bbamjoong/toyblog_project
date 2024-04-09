package klog.blog_project.controller;

import static klog.blog_project.entity.UserMessage.INTERNAL_SERVER_ERROR;

import klog.blog_project.entity.dto.ProfileDto;
import klog.blog_project.entity.dto.ProfileDto.ProfileResponse;
import klog.blog_project.entity.dto.SingleProfileDto;
import klog.blog_project.exception.UserNotFoundException;
import klog.blog_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/{nickname}/profileInfo")
    public ResponseEntity<ProfileResponse> viewProfile(@PathVariable("nickname") String nickname) {
        SingleProfileDto dto = userService.findUserProfileDto(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(ProfileResponse.success(dto));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ProfileDto.ProfileResponse> handleForbiddenPostException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProfileDto.ProfileResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(INTERNAL_SERVER_ERROR.getMessage());
    }
}
