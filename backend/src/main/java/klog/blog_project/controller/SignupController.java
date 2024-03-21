package klog.blog_project.controller;

import static klog.blog_project.entity.SignupMessage.INTERNAL_SERVER_ERROR;

import jakarta.validation.Valid;
import klog.blog_project.entity.dto.UserDto;
import klog.blog_project.exception.UserDuplicateException;
import klog.blog_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto.SignupResponse> signup(@Valid @RequestBody UserDto.SignupRequest dto) {
        userService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.SignupResponse.success(dto));
    }

    @ExceptionHandler(UserDuplicateException.class)
    @ResponseBody
    public ResponseEntity<UserDto.SignupResponse> handleUserDuplicateException(UserDuplicateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(UserDto.SignupResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<UserDto.SignupResponse> handleNonCorrectInputException(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserDto.SignupResponse.failure(message));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<UserDto.SignupResponse> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(UserDto.SignupResponse.failure(INTERNAL_SERVER_ERROR.getMessage()));
    }
}
