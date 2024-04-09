package klog.blog_project.controller;

import static klog.blog_project.entity.UserMessage.INTERNAL_SERVER_ERROR;
import static klog.blog_project.entity.UserMessage.UNAUTHORIZED;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import klog.blog_project.entity.Post;
import klog.blog_project.entity.dto.PostDto;
import klog.blog_project.entity.dto.PostDto.DeletePostResponse;
import klog.blog_project.entity.dto.PostDto.ModifyPostResponse;
import klog.blog_project.entity.dto.PostDto.PostsResponse;
import klog.blog_project.entity.dto.PostDto.WriteResponse;
import klog.blog_project.entity.dto.SinglePostDto;
import klog.blog_project.exception.ForbiddenUserException;
import klog.blog_project.exception.PostNotFoundException;
import klog.blog_project.exception.UserNotFoundException;
import klog.blog_project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/write")
    public ResponseEntity<PostDto.WriteResponse> writePost(@Valid @RequestBody PostDto.WriteRequest dto,
                                                           HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // 로그인한 유저가 존재할 경우 글 작성
        if (session != null) {
            Long userId = (Long) session.getAttribute("userId");
            dto.addUserId(userId);
            postService.write(dto);
            return ResponseEntity.status(HttpStatus.OK).body(WriteResponse.success(userId));
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
    public ResponseEntity<String> handleException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(INTERNAL_SERVER_ERROR.getMessage());
    }

    @GetMapping("/{nickname}/{postId}")
    public ResponseEntity<PostDto.DetailPostResponse> viewDetailPost(@PathVariable("nickname") String nickname,
                                                                     @PathVariable("postId") Long postId) {
        Post post = postService.findPost(nickname, postId);
        return ResponseEntity.status(HttpStatus.OK).body(PostDto.DetailPostResponse.success(post));
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseBody
    public ResponseEntity<PostDto.DetailPostResponse> handlePostNotFoundException(PostNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PostDto.DetailPostResponse.failure(ex.getMessage()));
    }

    @PatchMapping("/update/{postId}")
    public ResponseEntity<PostDto.ModifyPostResponse> modifyPost(@Valid @RequestBody PostDto.ModifyPostRequest dto,
                                                                 HttpServletRequest request,
                                                                 @PathVariable("postId") Long postId) {
        HttpSession session = request.getSession(false);
        // 로그인한 유저가 존재할 경우 글 수정
        if (session != null) {
            Long userId = (Long) session.getAttribute("userId");
            postService.modify(dto, userId, postId);
            return ResponseEntity.status(HttpStatus.OK).body(ModifyPostResponse.success(userId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(PostDto.ModifyPostResponse.failure(UNAUTHORIZED.getMessage()));
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<PostDto.DeletePostResponse> deletePost(HttpServletRequest request,
                                                                 @PathVariable("postId") Long postId) {
        HttpSession session = request.getSession(false);
        // 로그인한 유저가 존재할 경우 글 삭제
        if (session != null) {
            Long userId = (Long) session.getAttribute("userId");
            postService.delete(userId, postId);
            return ResponseEntity.status(HttpStatus.OK).body(DeletePostResponse.success(userId, postId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(PostDto.DeletePostResponse.failure(UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(ForbiddenUserException.class)
    @ResponseBody
    public ResponseEntity<PostDto.DeletePostResponse> handleForbiddenPostException(ForbiddenUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(PostDto.DeletePostResponse.failure(ex.getMessage()));
    }

    @GetMapping("/{nickname}/posts")
    public ResponseEntity<PostDto.PostsResponse> viewAllPosts(@PathVariable("nickname") String nickname) {
        List<SinglePostDto> posts = postService.findAllPosts(nickname);
        return ResponseEntity.status(HttpStatus.OK).body(PostsResponse.success(posts));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity<PostDto.PostsResponse> handleForbiddenPostException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PostDto.PostsResponse.failure(ex.getMessage()));
    }
}
