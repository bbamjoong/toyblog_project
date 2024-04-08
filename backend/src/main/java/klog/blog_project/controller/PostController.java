package klog.blog_project.controller;

import static klog.blog_project.entity.UserMessage.INTERNAL_SERVER_ERROR;
import static klog.blog_project.entity.UserMessage.UNAUTHORIZED;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import klog.blog_project.entity.dto.PostDto;
import klog.blog_project.entity.dto.PostDto.WriteResponse;
import klog.blog_project.service.PostService;
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
public class PostController {
    private final PostService postService;

    @PostMapping("/write")
    public ResponseEntity<WriteResponse> login(@Valid @RequestBody PostDto.WriteRequest dto,
                                               HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // 로그인한 유저가 존재할 경우 글 작성
        if (session != null) {
            Long userId = (Long) session.getAttribute("userId");
            dto.addUserId(userId);
            postService.write(dto);
            System.out.println("글 다 썼나??");
            return ResponseEntity.status(HttpStatus.OK).body(WriteResponse.success());
        }
        // 그렇지 않을 경우 Unauthorized status 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(WriteResponse.failure(UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<PostDto.WriteResponse> handleNonCorrectInputException(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PostDto.WriteResponse.failure(message));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<PostDto.WriteResponse> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(PostDto.WriteResponse.failure(INTERNAL_SERVER_ERROR.getMessage()));
    }
}