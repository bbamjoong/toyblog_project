package klog.blog_project.controller;

import static klog.blog_project.entity.UserMessage.INTERNAL_SERVER_ERROR;

import jakarta.validation.Valid;
import klog.blog_project.entity.dto.UserDto;
import klog.blog_project.exception.UserNotFoundException;
import klog.blog_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto.LoginResponse> login(@Valid @RequestBody UserDto.LoginRequest dto) {
        userService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(UserDto.LoginResponse.success(dto));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<UserDto.LoginResponse> handleNonCorrectInputException(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserDto.LoginResponse.failure(message));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<UserDto.LoginResponse> handleUserNotExistException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UserDto.LoginResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<UserDto.LoginResponse> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(UserDto.LoginResponse.failure(INTERNAL_SERVER_ERROR.getMessage()));
    }
}
