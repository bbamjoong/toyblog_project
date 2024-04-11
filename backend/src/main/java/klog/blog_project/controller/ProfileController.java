package klog.blog_project.controller;

import static klog.blog_project.entity.UserMessage.INTERNAL_SERVER_ERROR;
import static klog.blog_project.entity.UserMessage.UNAUTHORIZED;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import klog.blog_project.entity.dto.ProfileDto;
import klog.blog_project.entity.dto.ProfileDto.ModifyProfileResponse;
import klog.blog_project.entity.dto.ProfileDto.ProfileResponse;
import klog.blog_project.entity.dto.ProfileDto.SimpleProfilesResponse;
import klog.blog_project.entity.dto.SingleProfileDto;
import klog.blog_project.entity.dto.SingleSimpleProfileDto;
import klog.blog_project.exception.ForbiddenUserException;
import klog.blog_project.exception.UserNotFoundException;
import klog.blog_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/profile/{nickname}/profileInfo")
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

    @PatchMapping("/profile/{nickname}/updateProfile")
    public ResponseEntity<ProfileDto.ModifyProfileResponse> modifyProfile(
            @Valid @RequestBody ProfileDto.ModifyProfileRequest dto,
            HttpServletRequest request, @PathVariable("nickname") String nickname) {
        HttpSession session = request.getSession(false);
        // 로그인한 유저가 존재할 경우 프로필 수정
        if (session != null) {
            Long userId = (Long) session.getAttribute("userId");
            userService.modifyProfile(dto, userId, nickname);
            return ResponseEntity.status(HttpStatus.OK).body(ModifyProfileResponse.success(userId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ProfileDto.ModifyProfileResponse.failure(UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ProfileDto.ModifyProfileResponse> handleNonCorrectInputException(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProfileDto.ModifyProfileResponse.failure(message));
    }

    @ExceptionHandler(ForbiddenUserException.class)
    @ResponseBody
    public ResponseEntity<ProfileDto.ModifyProfileResponse> handleForbiddenUserException(ForbiddenUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ProfileDto.ModifyProfileResponse.failure(ex.getMessage()));
    }

    @GetMapping("/profile/usersInfo")
    public ResponseEntity<SimpleProfilesResponse> viewAllSimpleProfiles() {
        List<SingleSimpleProfileDto> profiles = userService.findAllSimpleProfiles();
        return ResponseEntity.status(HttpStatus.OK).body(SimpleProfilesResponse.success(profiles));
    }
}
